package com.taobao.monitor.adapter.db;

import java.util.List;

/* compiled from: ILiteDb */
public interface ILiteDb {
    List<String> getAll();

    void save(String str);

    void delete();
}
