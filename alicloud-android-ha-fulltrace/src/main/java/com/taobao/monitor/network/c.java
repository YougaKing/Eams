package com.taobao.monitor.network;

import com.taobao.monitor.thread.a;
import com.taobao.monitor.procedure.Header;
import com.taobao.monitor.procedure.ProcedureImpl.IProcedureLifeCycle;
import com.taobao.monitor.procedure.Value;
import com.taobao.monitor.procedure.model.Biz;
import com.taobao.monitor.procedure.model.Event;
import com.taobao.monitor.procedure.model.Stage;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: ProcedureLifecycleImpl */
public class c implements IProcedureLifeCycle {
    public void begin(Value value) {
    }

    public void event(Value value, Event event) {
    }

    public void stage(Value value, Stage stage) {
    }

    public void end(final Value value) {
        a.start(new Runnable() {
            public void run() {
                c.this.a(value);
            }
        });
    }

    /* renamed from: a reason: collision with other method in class */
    private void m0a(Value value) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("version", Header.apmVersion);
            jSONObject.put("topic", value.topic());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("X-timestamp", value.timestamp()).put("X-appId", Header.appId).put("X-appKey", Header.appKey).put("X-appBuild", Header.appBuild).put("X-appPatch", Header.appPatch).put("X-channel", Header.channel).put("X-utdid", Header.utdid).put("X-brand", Header.brand).put("X-deviceModel", Header.deviceModel).put("X-os", Header.os).put("X-osVersion", Header.osVersion).put("X-userId", Header.userId).put("X-userNick", Header.userNick).put("X-session", Header.session).put("X-processName", Header.processName).put("X-appVersion", Header.appVersion).put("X-launcherMode", Header.launcherMode);
            jSONObject.put("headers", jSONObject2);
            jSONObject.put("value", a(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jSONObject3 = jSONObject.toString();
        com.taobao.monitor.log.a.i("NetworkDataUpdate", jSONObject3);
        b.a().b(value.topic(), jSONObject3);
    }

    /* access modifiers changed from: private */
    public JSONObject a(Value value) throws Exception {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        boolean z = false;
        Map properties = value.properties();
        if (!(properties == null || properties.size() == 0)) {
            for (Entry entry : properties.entrySet()) {
                a(jSONObject2, (String) entry.getKey(), entry.getValue());
            }
            z = true;
        }
        List<Biz> bizs = value.bizs();
        if (!(bizs == null || bizs.size() == 0)) {
            JSONObject jSONObject3 = new JSONObject();
            for (Biz biz : bizs) {
                Map properties2 = biz.properties();
                JSONObject jSONObject4 = new JSONObject();
                if (!(properties2 == null || properties2.size() == 0)) {
                    a(jSONObject4, properties2);
                }
                Map abTest = biz.abTest();
                if (!(abTest == null || abTest.size() == 0)) {
                    JSONObject jSONObject5 = new JSONObject();
                    a(jSONObject5, abTest);
                    jSONObject4.put("abtest", jSONObject5);
                }
                Map stages = biz.stages();
                if (!(stages == null || stages.size() == 0)) {
                    JSONObject jSONObject6 = new JSONObject();
                    a(jSONObject6, stages);
                    jSONObject4.put("stages", jSONObject6);
                }
                jSONObject3.put(biz.bizID(), jSONObject4);
            }
            jSONObject2.put("bizTags", jSONObject3);
            z = true;
        }
        if (z) {
            jSONObject.put("properties", jSONObject2);
        }
        Map statistics = value.statistics();
        JSONObject jSONObject7 = new JSONObject();
        if (!(statistics == null || statistics.size() == 0)) {
            a(jSONObject7, statistics);
        }
        Map counters = value.counters();
        if (!(counters == null || counters.size() == 0)) {
            a(jSONObject7, counters);
        }
        if (!(counters.size() == 0 && statistics.size() == 0)) {
            jSONObject.put("stats", jSONObject7);
        }
        List<Event> events = value.events();
        if (!(events == null || events.size() == 0)) {
            JSONArray jSONArray = new JSONArray();
            for (Event event : events) {
                JSONObject jSONObject8 = new JSONObject();
                jSONObject8.put("timestamp", event.timestamp());
                jSONObject8.put("name", event.name());
                a(jSONObject8, event.properties());
                jSONArray.put(jSONObject8);
            }
            jSONObject.put("events", jSONArray);
        }
        List<Stage> stages2 = value.stages();
        if (!(stages2 == null || stages2.size() == 0)) {
            JSONObject jSONObject9 = new JSONObject();
            for (Stage stage : stages2) {
                jSONObject9.put(stage.name(), stage.timestamp());
            }
            jSONObject.put("stages", jSONObject9);
        }
        List<Value> subValues = value.subValues();
        if (!(subValues == null || subValues.size() == 0)) {
            JSONArray jSONArray2 = new JSONArray();
            for (Value value2 : subValues) {
                JSONObject a = a(value2);
                JSONObject jSONObject10 = new JSONObject();
                jSONObject10.put(value2.topic(), a);
                jSONArray2.put(jSONObject10);
            }
            jSONObject.put("subProcedures", jSONArray2);
        }
        return jSONObject;
    }

    private void a(JSONObject jSONObject, Map<String, ?> map) throws Exception {
        a(jSONObject, map, 2);
    }

    private void a(JSONObject jSONObject, Map<String, ?> map, int i) throws Exception {
        if (map != null && i > 0) {
            for (Entry entry : map.entrySet()) {
                a(jSONObject, (String) entry.getKey(), entry.getValue(), i);
            }
        }
    }

    private void a(JSONObject jSONObject, String str, Object obj) throws Exception {
        a(jSONObject, str, obj, 2);
    }

    private void a(JSONObject jSONObject, String str, Object obj, int i) throws Exception {
        if (obj instanceof Integer) {
            jSONObject.put(str, ((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            jSONObject.put(str, ((Long) obj).longValue());
        } else if (obj instanceof Float) {
            jSONObject.put(str, (double) ((Float) obj).floatValue());
        } else if (obj instanceof Double) {
            jSONObject.put(str, ((Double) obj).doubleValue());
        } else if (obj instanceof Boolean) {
            jSONObject.put(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Character) {
            jSONObject.put(str, ((Character) obj).charValue());
        } else if (obj instanceof Short) {
            jSONObject.put(str, ((Short) obj).shortValue());
        } else if (!(obj instanceof Map)) {
            jSONObject.put(str, obj);
        } else if (((Map) obj).size() != 0) {
            JSONObject jSONObject2 = new JSONObject();
            a(jSONObject2, (Map) obj, i - 1);
            jSONObject.put(str, jSONObject2);
        }
    }
}
