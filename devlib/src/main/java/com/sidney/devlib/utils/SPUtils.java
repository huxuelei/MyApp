package com.sidney.devlib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * PreferencesUtils, easy to get or put data
 */
public class SPUtils {

    public static String PREFERENCE_NAME = "com.sgcc.pda.pre";

    /**
     * 设置保存的文件名
     *
     * @param name -- 文件名
     */
    public static void setPreferenceName(String name) {
        PREFERENCE_NAME = name;
    }

    /**
     * put string preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent
     * storage.
     */
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * @param context
     * @param key
     * @param value
     * @param mode    传Context.MODE_MULTI_PROCESS表示进程间访问
     * @return
     */
    public static boolean putString(Context context, String key, String value, int mode) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, mode);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or null. Throws
     * ClassCastException if there is a preference with this name that
     * is not a string
     * @see #getString(Context, String, String)
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws
     * ClassCastException if there is a preference with this name that
     * is not a string
     */
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * @param context
     * @param key
     * @param defaultValue
     * @param mode         传Context.MODE_MULTI_PROCESS表示进程间访问
     * @return
     */
    public static String getString(Context context, String key, String defaultValue, int mode) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, mode);
        return settings.getString(key, defaultValue);
    }

    /**
     * put int preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent
     * storage.
     */
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * @param context
     * @param key
     * @param value
     * @param mode    传Context.MODE_MULTI_PROCESS表示进程间访问
     * @return
     */
    public static boolean putInt(Context context, String key, int value, int mode) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, mode);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }


    /**
     * get int preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws
     * ClassCastException if there is a preference with this name that
     * is not a int
     * @see #getInt(Context, String, int)
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws
     * ClassCastException if there is a preference with this name that
     * is not a int
     */
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * @param context
     * @param key
     * @param defaultValue
     * @param mode         传Context.MODE_MULTI_PROCESS表示进程间访问
     * @return
     */
    public static int getInt(Context context, String key, int defaultValue, int mode) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, mode);
        return settings.getInt(key, defaultValue);
    }

    /**
     * put long preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent
     * storage.
     */
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * @param context
     * @param key
     * @param value
     * @param mode    传Context.MODE_MULTI_PROCESS表示进程间访问
     * @return
     */
    public static boolean putLong(Context context, String key, long value, int mode) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, mode);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws
     * ClassCastException if there is a preference with this name that
     * is not a long
     * @see #getLong(Context, String, long)
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws
     * ClassCastException if there is a preference with this name that
     * is not a long
     */
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * @param context
     * @param key
     * @param defaultValue
     * @param mode         传Context.MODE_MULTI_PROCESS表示进程间访问
     * @return
     */
    public static long getLong(Context context, String key, long defaultValue, int mode) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, mode);
        return settings.getLong(key, defaultValue);
    }

    /**
     * put float preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent
     * storage.
     */
    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * @param context
     * @param key
     * @param value
     * @param mode    传Context.MODE_MULTI_PROCESS表示进程间访问
     * @return
     */
    public static boolean putFloat(Context context, String key, float value, int mode) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, mode);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws
     * ClassCastException if there is a preference with this name that
     * is not a float
     * @see #getFloat(Context, String, float)
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws
     * ClassCastException if there is a preference with this name that
     * is not a float
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * @param context
     * @param key
     * @param defaultValue
     * @param mode         传Context.MODE_MULTI_PROCESS表示进程间访问
     * @return
     */
    public static float getFloat(Context context, String key, float defaultValue, int mode) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, mode);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent
     * storage.
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * @param context
     * @param key
     * @param value
     * @param mode    传Context.MODE_MULTI_PROCESS表示进程间访问
     * @return
     */
    public static boolean putBoolean(Context context, String key, boolean value, int mode) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, mode);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean preferences, default is false
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws
     * ClassCastException if there is a preference with this name that
     * is not a boolean
     * @see #getBoolean(Context, String, boolean)
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * get boolean preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws
     * ClassCastException if there is a preference with this name that
     * is not a boolean
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * @param context
     * @param key
     * @param defaultValue
     * @param mode         传Context.MODE_MULTI_PROCESS表示进程间访问
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue, int mode) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, mode);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void removeKey(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * @param context
     * @param key
     * @param mode    传Context.MODE_MULTI_PROCESS表示进程间访问
     */
    public static void removeKey(Context context, String key, int mode) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clearAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * @param context
     * @param mode    传Context.MODE_MULTI_PROCESS表示进程间访问
     */
    public static void clearAll(Context context, int mode) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean isContainKey(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * @param context
     * @param key
     * @param mode    传Context.MODE_MULTI_PROCESS表示进程间访问
     * @return
     */
    public static boolean isContainKey(Context context, String key, int mode) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, mode);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * @param context
     * @param mode    传Context.MODE_MULTI_PROCESS表示进程间访问
     * @return
     */
    public static Map<String, ?> getAll(Context context, int mode) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, mode);
        return sp.getAll();
    }
}
