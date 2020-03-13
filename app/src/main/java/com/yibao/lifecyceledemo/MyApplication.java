package com.yibao.lifecyceledemo;

import android.app.Application;
import android.content.Context;

/**
 * @author luoshipeng
 * createDate：2019/11/28 0028 10:11
 * className   MyApplication
 * Des：TODO
 */
public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
