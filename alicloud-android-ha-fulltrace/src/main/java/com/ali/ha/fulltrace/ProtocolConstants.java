package com.ali.ha.fulltrace;

import com.ali.ha.fulltrace.logger.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class ProtocolConstants {
    public static short EVENT_APP_START_UP_BEGIN = 1;
    public static short EVENT_APP_START_UP_END = 2;
    public static short EVENT_BACKGROUD = 7;
    public static short EVENT_BACKGROUND_LAUNCH = 34;
    public static short EVENT_CPU_USAGE = 8;
    public static short EVENT_CRASH = 21;
    public static short EVENT_DISPLAYED = 23;
    public static short EVENT_FINISH_LOAD_PAGE = 5;
    public static short EVENT_FIRST_DRAW = 24;
    public static short EVENT_FIRST_INTERACTION = 25;
    public static short EVENT_FLING = 33;
    public static short EVENT_FOREGROUND = 6;
    public static short EVENT_FPS = 16;
    public static short EVENT_GC = 22;
    public static short EVENT_JANK = 20;
    public static short EVENT_LAUNCHER_USABLE = 35;
    public static short EVENT_MEM_USAGE = 9;
    public static short EVENT_OPEN_APP_FROM_URL = 3;
    public static short EVENT_OPEN_PAGE = 4;
    public static short EVENT_RECEIVE_MEMORY_WARN = 19;
    public static short EVENT_SCROLL = 18;
    public static short EVENT_TAP = 17;
    public static short EVENT_USABLE = 32;
    public static String PROTOCAL_APP_START_UP_BEGIN = "startupBegin firstInstall:z,isBackgroundLaunch:z,type:u4:u1*";
    public static String PROTOCAL_APP_START_UP_END = "startupEnd";
    public static String PROTOCAL_BACKGROUD = "background";
    public static String PROTOCAL_CPU_USAGE = "cpuUsage cpuUsageOfApp:f,cpuUsageOfDevice:f";
    public static String PROTOCAL_CRASH = "crash";
    public static String PROTOCAL_DISPLAYED = "displayed";
    public static String PROTOCAL_FINISH_LOAD_PAGE = "finishLoadPage page:u4:u1*,duration:f,freeMemory:f,residentMemory:f,virtualMemory:f,cpuUsageOfApp:f,cpuUsageOfDevice:f";
    public static String PROTOCAL_FIRST_DRAW = "firstDraw";
    public static String PROTOCAL_FIRST_INTERACTION = "firstInteraction";
    public static String PROTOCAL_FLING = "fling direction:u1";
    public static String PROTOCAL_FOREGROUND = "foreground";
    public static String PROTOCAL_FPS = "fps loadFps:f,useFps:f";
    public static String PROTOCAL_GC = "gc";
    public static String PROTOCAL_JANK = "jank";
    public static String PROTOCAL_LAUNCHER_USEBLE = "launcherUsable duration:f";
    public static String PROTOCAL_MEM_USAGE = "memoryUsage freeMemory:f,residentMemory:f,virtualMemory:f";
    public static String PROTOCAL_OPEN_APP_FROM_URL = "openApplicationFromUrl url:u4:u1*";
    public static String PROTOCAL_OPEN_PAGE = "openPage page:u4:u1*,freeMemory:f,residentMemory:f,virtualMemory:f,cpuUsageOfApp:f,cpuUsageOfDevice:f";
    public static String PROTOCAL_RECEIVE_MEMORY_WARN = "receiveMemoryWarning level:f";
    public static String PROTOCAL_SCROLL = "scroll beginX:f,endX:f,beginY:f,endY:f";
    public static String PROTOCAL_TAP = "tap x:f,y:f,isLongTouch:z";
    public static String PROTOCAL_USABLE = "usable duration:f";

    public static HashMap<String, String> getTypeDescriptor() {
        HashMap<String, String> typeDescriptors = new HashMap<>();
        typeDescriptors.put(Integer.toString(EVENT_APP_START_UP_BEGIN), PROTOCAL_APP_START_UP_BEGIN);
        typeDescriptors.put(Integer.toString(EVENT_APP_START_UP_END), PROTOCAL_APP_START_UP_END);
        typeDescriptors.put(Integer.toString(EVENT_OPEN_APP_FROM_URL), PROTOCAL_OPEN_APP_FROM_URL);
        typeDescriptors.put(Integer.toString(EVENT_OPEN_PAGE), PROTOCAL_OPEN_PAGE);
        typeDescriptors.put(Integer.toString(EVENT_FINISH_LOAD_PAGE), PROTOCAL_FINISH_LOAD_PAGE);
        typeDescriptors.put(Integer.toString(EVENT_FOREGROUND), PROTOCAL_FOREGROUND);
        typeDescriptors.put(Integer.toString(EVENT_BACKGROUD), PROTOCAL_BACKGROUD);
        typeDescriptors.put(Integer.toString(EVENT_CPU_USAGE), PROTOCAL_CPU_USAGE);
        typeDescriptors.put(Integer.toString(EVENT_MEM_USAGE), PROTOCAL_MEM_USAGE);
        typeDescriptors.put(Integer.toString(EVENT_FPS), PROTOCAL_FPS);
        typeDescriptors.put(Integer.toString(EVENT_TAP), PROTOCAL_TAP);
        typeDescriptors.put(Integer.toString(EVENT_SCROLL), PROTOCAL_SCROLL);
        typeDescriptors.put(Integer.toString(EVENT_RECEIVE_MEMORY_WARN), PROTOCAL_RECEIVE_MEMORY_WARN);
        typeDescriptors.put(Integer.toString(EVENT_JANK), PROTOCAL_JANK);
        typeDescriptors.put(Integer.toString(EVENT_CRASH), PROTOCAL_CRASH);
        typeDescriptors.put(Integer.toString(EVENT_GC), PROTOCAL_GC);
        typeDescriptors.put(Integer.toString(EVENT_DISPLAYED), PROTOCAL_DISPLAYED);
        typeDescriptors.put(Integer.toString(EVENT_FIRST_DRAW), PROTOCAL_FIRST_DRAW);
        typeDescriptors.put(Integer.toString(EVENT_FIRST_INTERACTION), PROTOCAL_FIRST_INTERACTION);
        typeDescriptors.put(Integer.toString(EVENT_USABLE), PROTOCAL_USABLE);
        typeDescriptors.put(Integer.toString(EVENT_FLING), PROTOCAL_FLING);
        typeDescriptors.put(Integer.toString(EVENT_LAUNCHER_USABLE), PROTOCAL_LAUNCHER_USEBLE);
        return typeDescriptors;
    }

    public static void getDocTypeDescriptor() {
        HashMap<String, String> tmpMap = getTypeDescriptor();
        List<Entry> arrayList = new ArrayList<Entry>(tmpMap.entrySet());
        Collections.sort(arrayList, new Comparator() {
            public int compare(Object o1, Object o2) {
                return Integer.valueOf((String) ((Entry) o1).getKey()).compareTo(Integer.valueOf((String) ((Entry) o2).getKey()));
            }
        });
        Logger.i("ProtocolConstants", "*type-descriptors");
        for (Entry entry : arrayList) {
            Logger.i("ProtocolConstants", String.format("0x%04X", new Object[]{Integer.valueOf((String) entry.getKey())}) + "=" + entry.getValue());
        }
        Logger.i("ProtocolConstants", "*end");
    }
}
