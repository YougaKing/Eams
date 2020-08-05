package com.taobao.application.common.impl;

/* compiled from: ICallbackGroup */
public interface ICallbackGroup<T> {
    void removeCallback(T t);

    void addCallback(T t);
}
