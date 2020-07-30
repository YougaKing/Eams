//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure;

import android.os.SystemClock;
import android.text.TextUtils;
import com.taobao.monitor.procedure.model.Biz;
import com.taobao.monitor.procedure.model.Event;
import com.taobao.monitor.procedure.model.Stage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Value {
    private static final String TAG = "Value";
    private final String topic;
    private final String simpleTopic;
    private long timestamp = SystemClock.uptimeMillis();
    private List<Value> subValues;
    private List<Event> events;
    private List<Stage> stages;
    private Map<String, Object> statistics;
    private Map<String, Object> properties;
    private List<Biz> bizs;
    private Map<String, Biz> bizIndex;
    private Map<String, Integer> counters;
    private final boolean independent;
    private final boolean parentNeedStats;

    Value(String var1, boolean var2, boolean var3) {
        this.topic = var1;
        int var4 = var1.lastIndexOf("/");
        if (var4 != -1 && var1.length() > var4 + 1) {
            this.simpleTopic = var1.substring(var4 + 1);
        } else {
            this.simpleTopic = var1;
        }

        this.independent = var2;
        this.parentNeedStats = var3;
        this.initialize();
    }

    private void initialize() {
        this.subValues = new LinkedList();
        this.events = new LinkedList();
        this.stages = new LinkedList();
        this.statistics = new ConcurrentHashMap();
        this.counters = new ConcurrentHashMap();
        this.properties = new ConcurrentHashMap();
        this.bizs = new LinkedList();
        this.bizIndex = new ConcurrentHashMap();
    }

    public String topic() {
        return this.topic;
    }

    Value event(Event var1) {
        if (var1 != null) {
            synchronized(this.events) {
                this.events.add(var1);
            }
        }

        return this;
    }

    Value stage(Stage var1) {
        if (var1 != null) {
            synchronized(this.stages) {
                this.stages.add(var1);
            }
        }

        return this;
    }

    Value addProperty(String var1, Object var2) {
        if (var2 != null && var1 != null) {
            this.properties.put(var1, var2);
            return this;
        } else {
            return this;
        }
    }

    Value addStatistic(String var1, Object var2) {
        if (var2 != null && var1 != null) {
            this.statistics.put(var1, var2);
            return this;
        } else {
            return this;
        }
    }

    Value addBiz(String var1, Map<String, Object> var2) {
        if (var1 != null) {
            Biz var3 = (Biz)this.bizIndex.get(var1);
            if (var3 == null) {
                Biz var4 = new Biz(var1, var2);
                this.bizIndex.put(var1, var4);
                synchronized(this.bizs) {
                    this.bizs.add(var4);
                }

                var3 = var4;
            }

            var3.addProperties(var2);
        }

        return this;
    }

    Value addBizAbTest(String var1, Map<String, Object> var2) {
        if (var1 != null) {
            Biz var3 = (Biz)this.bizIndex.get(var1);
            if (var3 == null) {
                Biz var4 = new Biz(var1, (Map)null);
                this.bizIndex.put(var1, var4);
                synchronized(this.bizs) {
                    this.bizs.add(var4);
                }

                var3 = var4;
            }

            var3.addAbTest(var2);
        }

        return this;
    }

    Value addBizStage(String var1, Map<String, Object> var2) {
        if (var1 != null) {
            Biz var3 = (Biz)this.bizIndex.get(var1);
            if (var3 == null) {
                Biz var4 = new Biz(var1, (Map)null);
                this.bizIndex.put(var1, var4);
                synchronized(this.bizs) {
                    this.bizs.add(var4);
                }

                var3 = var4;
            }

            var3.addStage(var2);
        }

        return this;
    }

    Value addSubValue(Value var1) {
        if (var1 != null) {
            String var2 = var1.simpleTopic;
            if (TextUtils.isEmpty(var2)) {
                return this;
            }

            Integer var3 = (Integer)this.counters.get(var2);
            if (var3 == null) {
                this.counters.put(var2, 1);
            } else {
                this.counters.put(var2, var3 + 1);
            }

            if (var1.parentNeedStats) {
                List var4 = var1.stages;
                Iterator var5 = var4.iterator();

                while(var5.hasNext()) {
                    Stage var6 = (Stage)var5.next();
                    String var7 = var6.name();
                    char[] var8 = var7.toCharArray();
                    if (var8[0] >= 'a') {
                        var8[0] = (char)(var8[0] - 32);
                    }

                    var7 = String.valueOf(var8);
                    String var9 = var2 + var7;
                    Integer var10 = (Integer)this.counters.get(var9);
                    if (var10 == null) {
                        this.counters.put(var9, 1);
                    } else {
                        this.counters.put(var9, var10 + 1);
                    }
                }
            }

            synchronized(this.subValues) {
                if (!var1.independent) {
                    this.subValues.add(var1);
                }
            }
        }

        return this;
    }

    Value removeSubValue(Value var1) {
        if (var1 != null) {
            synchronized(this.subValues) {
                this.subValues.remove(var1);
            }
        }

        return this;
    }

    Value summary() {
        Value var1 = new Value(this.simpleTopic, this.independent, this.parentNeedStats);
        var1.stages = this.stages;
        var1.properties = this.properties;
        return var1;
    }

    public long timestamp() {
        return this.timestamp;
    }

    public List<Value> subValues() {
        return this.subValues;
    }

    public List<Event> events() {
        return this.events;
    }

    public List<Stage> stages() {
        return this.stages;
    }

    public List<Biz> bizs() {
        return this.bizs;
    }

    public Map<String, Object> statistics() {
        return this.statistics;
    }

    public Map<String, Object> properties() {
        return this.properties;
    }

    public Map<String, Integer> counters() {
        return this.counters;
    }

    public String toString() {
        return this.topic;
    }
}
