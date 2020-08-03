package com.taobao.monitor.adapter.db;


import java.util.List;

public interface ILiteDb {

    void append(String string);

    List<String> readAll();

    void delete();
}
