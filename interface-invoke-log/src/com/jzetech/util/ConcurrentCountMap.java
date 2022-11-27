package com.jzetech.util;

import com.jzetech.entity.InterfaceInvokeLog;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 公用的线程安全静态计数 Map
 *
 * @author 0703
 */
public class ConcurrentCountMap {

    /**
     * 本工具类不需要创建新实例，故将构造函数私有化
     */
    private ConcurrentCountMap() {
    }

    /**
     * 线程公用的静态计数 Map
     */
    private static ConcurrentHashMap<String, InterfaceInvokeLog> concurrentCountMap = new ConcurrentHashMap();

    /**
     * Get 方法
     *
     * @return concurrentCountMap
     */
    public static ConcurrentHashMap<String, InterfaceInvokeLog> take() {
        return concurrentCountMap;
    }

}
