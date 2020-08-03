package com.taobao.monitor.adapter.db;

import com.taobao.monitor.impl.common.Global;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class SenderLiteDb implements ILiteDb {

    private final File file = new File(Global.instance().context().getCacheDir() + "/" + "apm_db.db");

    public SenderLiteDb() {
    }

    @Override
    public void append(String string) {
        try {
            this.create();
            long length = this.file.length();
            if (length < 4194304L) {
                FileWriter fileWriter = null;

                try {
                    fileWriter = new FileWriter(this.file, true);
                    fileWriter.append(string).append("\n");
                } finally {
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> readAll() {
        try {
            this.create();
            long length = this.file.length();
            if (length > 0L) {
                BufferedReader bufferedReader = null;

                try {
                    bufferedReader = new BufferedReader(new FileReader(this.file));
                    ArrayList<String> list = new ArrayList<>();

                    for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                        list.add(line);
                    }
                    return list;
                } finally {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete() {
        if (this.file.exists()) {
            this.file.delete();
        }
    }

    private void create() throws Exception {
        if (!this.file.exists()) {
            this.file.createNewFile();
        } else if (this.file.isDirectory()) {
            this.file.delete();
            this.file.createNewFile();
        }

    }
}
