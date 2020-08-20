package com.taobao.monitor.procedure;

import android.os.SystemClock;
import android.text.TextUtils;

import com.taobao.monitor.procedure.model.Biz;
import com.taobao.monitor.procedure.model.Event;
import com.taobao.monitor.procedure.model.Stage;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Value {
    private static final String TAG = "Value";
    private Map<String, Biz> bizIndex;
    private List<Biz> bizs;
    private Map<String, Integer> counters;
    private List<Event> events;
    private final boolean independent;
    private final boolean parentNeedStats;
    private Map<String, Object> properties;
    private final String simpleTopic;
    private List<Stage> stages;
    private Map<String, Object> statistics;
    private List<Value> subValues;
    private long timestamp = SystemClock.uptimeMillis();
    private final String topic;

    Value(String topic, boolean independent, boolean parentNeedStats) {
        this.topic = topic;
        int lastIndexOf = topic.lastIndexOf("/");
        if (lastIndexOf == -1 || topic.length() <= lastIndexOf + 1) {
            this.simpleTopic = topic;
        } else {
            this.simpleTopic = topic.substring(lastIndexOf + 1);
        }
        this.independent = independent;
        this.parentNeedStats = parentNeedStats;
        initialize();
    }

    private void initialize() {
        this.subValues = new LinkedList<>();
        this.events = new LinkedList<>();
        this.stages = new LinkedList<>();
        this.statistics = new ConcurrentHashMap<>();
        this.counters = new ConcurrentHashMap<>();
        this.properties = new ConcurrentHashMap<>();
        this.bizs = new LinkedList<>();
        this.bizIndex = new ConcurrentHashMap<>();
    }

    public String topic() {
        return this.topic;
    }

    /* access modifiers changed from: 0000 */
    public Value event(Event event) {
        if (event != null) {
            synchronized (this.events) {
                this.events.add(event);
            }
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Value stage(Stage stage) {
        if (stage != null) {
            synchronized (this.stages) {
                this.stages.add(stage);
            }
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Value addProperty(String str, Object obj) {
        if (!(obj == null || str == null)) {
            this.properties.put(str, obj);
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Value addStatistic(String str, Object obj) {
        if (!(obj == null || str == null)) {
            this.statistics.put(str, obj);
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Value addBiz(String str, Map<String, Object> map) {
        if (str != null) {
            Biz biz = (Biz) this.bizIndex.get(str);
            if (biz == null) {
                biz = new Biz(str, map);
                this.bizIndex.put(str, biz);
                synchronized (this.bizs) {
                    this.bizs.add(biz);
                }
            }
            biz.addProperties(map);
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Value addBizAbTest(String str, Map<String, Object> map) {
        if (str != null) {
            Biz biz = (Biz) this.bizIndex.get(str);
            if (biz == null) {
                biz = new Biz(str, null);
                this.bizIndex.put(str, biz);
                synchronized (this.bizs) {
                    this.bizs.add(biz);
                }
            }
            biz.addAbTest(map);
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Value addBizStage(String str, Map<String, Object> map) {
        if (str != null) {
            Biz biz = (Biz) this.bizIndex.get(str);
            if (biz == null) {
                biz = new Biz(str, null);
                this.bizIndex.put(str, biz);
                synchronized (this.bizs) {
                    this.bizs.add(biz);
                }
            }
            biz.addStage(map);
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Value addSubValue(Value value) {
        if (value != null) {
            String str = value.simpleTopic;
            if (!TextUtils.isEmpty(str)) {
                Integer num = (Integer) this.counters.get(str);
                if (num == null) {
                    this.counters.put(str, Integer.valueOf(1));
                } else {
                    this.counters.put(str, Integer.valueOf(num.intValue() + 1));
                }
                if (value.parentNeedStats) {
                    for (Stage name : value.stages) {
                        char[] charArray = name.name().toCharArray();
                        if (charArray[0] >= 'a') {
                            charArray[0] = (char) (charArray[0] - ' ');
                        }
                        String str2 = str + String.valueOf(charArray);
                        Integer num2 = (Integer) this.counters.get(str2);
                        if (num2 == null) {
                            this.counters.put(str2, Integer.valueOf(1));
                        } else {
                            this.counters.put(str2, Integer.valueOf(num2.intValue() + 1));
                        }
                    }
                }
                synchronized (this.subValues) {
                    if (!value.independent) {
                        this.subValues.add(value);
                    }
                }
            }
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Value removeSubValue(Value value) {
        if (value != null) {
            synchronized (this.subValues) {
                this.subValues.remove(value);
            }
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Value summary() {
        Value value = new Value(this.simpleTopic, this.independent, this.parentNeedStats);
        value.stages = this.stages;
        value.properties = this.properties;
        return value;
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
