package com.taobao.monitor.adapter.db;

import com.taobao.monitor.impl.common.Global;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/* compiled from: SenderLiteDb */
class SenderLiteDb implements ILiteDb {

    private final File file = new File(Global.instance().context().getCacheDir() + "/" + "apm_db.db");

    public SenderLiteDb() {
    }

    public void a(String var1) {
        try {
            this.b();
            long var2 = this.file.length();
            if (var2 < 4194304L) {
                FileWriter var4 = null;

                try {
                    var4 = new FileWriter(this.file, true);
                    var4.append(var1).append("\n");
                } finally {
                    if (var4 != null) {
                        var4.close();
                    }

                }
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

    }

    public List<String> a() {
        try {
            this.b();
            long var1 = this.file.length();
            if (var1 > 0L) {
                BufferedReader var3 = null;

                try {
                    var3 = new BufferedReader(new FileReader(this.file));
                    ArrayList var4 = new ArrayList();

                    for (String var5 = var3.readLine(); var5 != null; var5 = var3.readLine()) {
                        var4.add(var5);
                    }

                    ArrayList var6 = var4;
                    return var6;
                } finally {
                    if (var3 != null) {
                        var3.close();
                    }

                }
            }
        } catch (Exception var11) {
        }

        return null;
    }

    public void delete() {
        if (this.file.exists()) {
            this.file.delete();
        }

    }

    private void b() throws Exception {
        if (!this.file.exists()) {
            this.file.createNewFile();
        } else if (this.file.isDirectory()) {
            this.file.delete();
            this.file.createNewFile();
        }

    }
}
