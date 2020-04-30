package com.yibao.lifecyceledemo;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author luoshipeng
 * createDate：2019/9/11 0011 16:36
 * className   MyLifecyclerObserver
 * Des：TODO
 */
public class MyLifecyclerObserver implements LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void connectStartListener() {
//        ...
        Log.d("lsp", "connectListener:  --------   ON_START");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void connectListener() {
//        ...
        Log.d("lsp", "connectListener:  --------   onResume");

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void disconnectListener() {
//        ...
        Log.d("lsp", "disconnectListener: -------   onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void disconnectStopListener() {
//        ...
        Log.d("lsp", "disconnectListener: -------   ON_STOP");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void disconnectDestroyListener() {
//        ...
        Log.d("lsp", "disconnectListener: -------   ON_DESTROY");
    }
}
