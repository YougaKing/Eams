package com.alibaba.motu.tbrest.rest;

import com.alibaba.motu.tbrest.utils.StringUtils;
import java.util.Arrays;
import java.util.Comparator;

public class RestKeyArraySorter {
    private static RestKeyArraySorter s_instance = null;
    private ResourcesASCComparator mASCComparator = new ResourcesASCComparator();
    private ResourcesDESCComparator mDESCComparator = new ResourcesDESCComparator();

    private class ResourcesASCComparator implements Comparator<String> {
        private ResourcesASCComparator() {
        }

        public int compare(String o1, String o2) {
            if (StringUtils.isEmpty(o1) || StringUtils.isEmpty(o2)) {
                return 0;
            }
            return o1.compareTo(o2);
        }
    }

    private class ResourcesDESCComparator implements Comparator<String> {
        private ResourcesDESCComparator() {
        }

        public int compare(String o1, String o2) {
            if (StringUtils.isEmpty(o1) || StringUtils.isEmpty(o2)) {
                return 0;
            }
            return o1.compareTo(o2) * -1;
        }
    }

    private RestKeyArraySorter() {
    }

    public static synchronized RestKeyArraySorter getInstance() {
        RestKeyArraySorter restKeyArraySorter;
        synchronized (RestKeyArraySorter.class) {
            if (s_instance == null) {
                s_instance = new RestKeyArraySorter();
            }
            restKeyArraySorter = s_instance;
        }
        return restKeyArraySorter;
    }

    public String[] sortResourcesList(String[] resources, boolean aUseASC) {
        Comparator<String> lCompare;
        if (aUseASC) {
            lCompare = this.mASCComparator;
        } else {
            lCompare = this.mDESCComparator;
        }
        if (lCompare == null || resources == null || resources.length <= 0) {
            return null;
        }
        Arrays.sort(resources, lCompare);
        return resources;
    }
}
