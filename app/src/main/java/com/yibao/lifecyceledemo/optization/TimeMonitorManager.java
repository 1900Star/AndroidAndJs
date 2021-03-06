package com.yibao.lifecyceledemo.optization;

import java.util.HashMap;

/**
 * @author luoshipeng
 * createDate：2019/11/28 0028 10:13
 * className   TimeMonitorManager
 * Des：TODO
 */
public class TimeMonitorManager {

    private static TimeMonitorManager mTimeMonitorManager = null;
    private HashMap<Integer, TimeMonitor> mTimeMonitorMap = null;

    public synchronized static TimeMonitorManager getInstance() {
        if (mTimeMonitorManager == null) {
            mTimeMonitorManager = new TimeMonitorManager();
        }
        return mTimeMonitorManager;
    }

    public TimeMonitorManager() {
        this.mTimeMonitorMap = new HashMap<>();
    }

    /**
     * 初始化打点模块
     */
    public void resetTimeMonitor(int id) {
        if (mTimeMonitorMap.get(id) != null) {
            mTimeMonitorMap.remove(id);
        }
        getTimeMonitor(id);
    }

    /**
     * 获取打点器
     */
    public TimeMonitor getTimeMonitor(int id) {
        TimeMonitor monitor = mTimeMonitorMap.get(id);
        if (monitor == null) {
            monitor = new TimeMonitor(id);
            mTimeMonitorMap.put(id, monitor);
        }
        return monitor;
    }
}
