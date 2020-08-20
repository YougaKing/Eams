package com.example.eams;

import android.app.Application;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2020/8/20 11:33
 * @description:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        YogaEmas.init(this, BuildConfig.VERSION_NAME, "", BuildConfig.DEBUG);
    }
}
