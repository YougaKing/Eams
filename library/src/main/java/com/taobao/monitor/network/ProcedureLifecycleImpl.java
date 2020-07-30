package com.taobao.monitor.network;

import com.taobao.monitor.log.Logger;
import com.taobao.monitor.procedure.Header;
import com.taobao.monitor.procedure.ProcedureImpl.IProcedureLifeCycle;
import com.taobao.monitor.procedure.Value;
import com.taobao.monitor.procedure.model.Biz;
import com.taobao.monitor.procedure.model.Event;
import com.taobao.monitor.procedure.model.Stage;
import com.taobao.monitor.util.ThreadUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ProcedureLifecycleImpl implements IProcedureLifeCycle {
    public ProcedureLifecycleImpl() {
    }

    public void begin(Value var1) {
    }

    public void event(Value var1, Event var2) {
    }

    public void stage(Value var1, Stage var2) {
    }

    public void end(final Value var1) {
        ThreadUtils.start(new Runnable() {
            public void run() {
                ProcedureLifecycleImpl.this.dataUpdate(var1);
            }
        });
    }

    private void dataUpdate(Value value) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("version", Header.apmVersion);
            jsonObject.put("topic", value.topic());
            JSONObject var3 = new JSONObject();
            var3.put("X-timestamp", value.timestamp()).put("X-appId", Header.appId).put("X-appKey", Header.appKey).put("X-appBuild", Header.appBuild).put("X-appPatch", Header.appPatch).put("X-channel", Header.channel).put("X-utdid", Header.utdid).put("X-brand", Header.brand).put("X-deviceModel", Header.deviceModel).put("X-os", Header.os).put("X-osVersion", Header.osVersion).put("X-userId", Header.userId).put("X-userNick", Header.userNick).put("X-session", Header.session).put("X-processName", Header.processName).put("X-appVersion", Header.appVersion).put("X-launcherMode", Header.launcherMode);
            jsonObject.put("headers", var3);
            jsonObject.put("value", this.a(value));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        String string = jsonObject.toString();
        Logger.i("NetworkDataUpdate", string);
        NetworkSenderProxy.instance().dataUpdate(value.topic(), string);
    }

    private JSONObject a(Value var1) throws Exception {
        JSONObject var2 = new JSONObject();
        JSONObject var3 = new JSONObject();
        boolean var4 = false;
        Map var5 = var1.properties();
        if (var5 != null && var5.size() != 0) {
            Iterator var6 = var5.entrySet().iterator();

            while (var6.hasNext()) {
                Entry var7 = (Entry) var6.next();
                String var8 = (String) var7.getKey();
                Object var9 = var7.getValue();
                this.a(var3, var8, var9);
            }

            var4 = true;
        }

        List var20 = var1.bizs();
        JSONObject var23;
        if (var20 != null && var20.size() != 0) {
            var23 = new JSONObject();

            JSONObject var11;
            Biz var28;
            for (Iterator var25 = var20.iterator(); var25.hasNext(); var23.put(var28.bizID(), var11)) {
                var28 = (Biz) var25.next();
                Map var10 = var28.properties();
                var11 = new JSONObject();
                if (var10 != null && var10.size() != 0) {
                    this.a(var11, var10);
                }

                Map var12 = var28.abTest();
                if (var12 != null && var12.size() != 0) {
                    JSONObject var13 = new JSONObject();
                    this.a(var13, var12);
                    var11.put("abtest", var13);
                }

                Map var29 = var28.stages();
                if (var29 != null && var29.size() != 0) {
                    JSONObject var14 = new JSONObject();
                    this.a(var14, var29);
                    var11.put("stages", var14);
                }
            }

            var3.put("bizTags", var23);
            var4 = true;
        }

        if (var4) {
            var2.put("properties", var3);
        }

        Map var15 = var1.statistics();
        JSONObject var17 = new JSONObject();
        if (var15 != null && var15.size() != 0) {
            this.a(var17, var15);
        }

        var5 = var1.counters();
        if (var5 != null && var5.size() != 0) {
            this.a(var17, var5);
        }

        if (var5.size() != 0 || var15.size() != 0) {
            var2.put("stats", var17);
        }

        List var16 = var1.events();
        JSONArray var18;
        Iterator var19;
        if (var16 != null && var16.size() != 0) {
            var18 = new JSONArray();
            var19 = var16.iterator();

            while (var19.hasNext()) {
                Event var21 = (Event) var19.next();
                var23 = new JSONObject();
                var23.put("timestamp", var21.timestamp());
                var23.put("name", var21.name());
                Map var26 = var21.properties();
                this.a(var23, var26);
                var18.put(var23);
            }

            var2.put("events", var18);
        }

        var16 = var1.stages();
        if (var16 != null && var16.size() != 0) {
            var17 = new JSONObject();
            var19 = var16.iterator();

            while (var19.hasNext()) {
                Stage var22 = (Stage) var19.next();
                var17.put(var22.name(), var22.timestamp());
            }

            var2.put("stages", var17);
        }

        var16 = var1.subValues();
        if (var16 != null && var16.size() != 0) {
            var18 = new JSONArray();
            var19 = var16.iterator();

            while (var19.hasNext()) {
                Value var24 = (Value) var19.next();
                var23 = this.a(var24);
                JSONObject var27 = new JSONObject();
                var27.put(var24.topic(), var23);
                var18.put(var27);
            }

            var2.put("subProcedures", var18);
        }

        return var2;
    }

    private void a(JSONObject var1, Map<String, ?> var2) throws Exception {
        this.a(var1, var2, 2);
    }

    private void a(JSONObject var1, Map<String, ?> var2, int var3) throws Exception {
        if (var2 != null && var3 > 0) {
            Iterator var4 = var2.entrySet().iterator();

            while (var4.hasNext()) {
                Entry var5 = (Entry) var4.next();
                String var6 = (String) var5.getKey();
                Object var7 = var5.getValue();
                this.a(var1, var6, var7, var3);
            }

        }
    }

    private void a(JSONObject var1, String var2, Object var3) throws Exception {
        this.a(var1, var2, var3, 2);
    }

    private void a(JSONObject var1, String var2, Object var3, int var4) throws Exception {
        if (var3 instanceof Integer) {
            var1.put(var2, (Integer) var3);
        } else if (var3 instanceof Long) {
            var1.put(var2, (Long) var3);
        } else if (var3 instanceof Float) {
            var1.put(var2, (double) (Float) var3);
        } else if (var3 instanceof Double) {
            var1.put(var2, (Double) var3);
        } else if (var3 instanceof Boolean) {
            var1.put(var2, (Boolean) var3);
        } else if (var3 instanceof Character) {
            var1.put(var2, (Character) var3);
        } else if (var3 instanceof Short) {
            var1.put(var2, (Short) var3);
        } else if (var3 instanceof Map) {
            if (((Map) var3).size() != 0) {
                JSONObject var5 = new JSONObject();
                Map var10002 = (Map) var3;
                --var4;
                this.a(var5, var10002, var4);
                var1.put(var2, var5);
            }
        } else {
            var1.put(var2, var3);
        }

    }
}
