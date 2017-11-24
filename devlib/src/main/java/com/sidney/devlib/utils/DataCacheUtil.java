package com.sidney.devlib.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据缓存
 *
 * @author huxuelei
 */
public class DataCacheUtil {
    private static Map<String, Object> mCache = new HashMap<String, Object>();

    /**
     * 缓存数据
     *
     * @param key
     * @param value
     */
    public static void setValue(String key, Object value) {
        mCache.put(key, value);
    }

    /**
     * 由key获得缓存数据
     *
     * @param key
     * @return
     */
    public static Object getValue(String key) {
        return mCache.get(key);
    }

    /**
     * 清除缓存数据
     *
     * @param key
     * @param
     */
    public static Object clearValue(String key) {
        return mCache.remove(key);
    }

    /**
     * 由key获得int数据
     *
     * @param key
     * @param defaultValue 默认数据
     * @return
     */
    public static int getInt(String key, int defaultValue) {
        try {
            return (Integer) mCache.get(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

}