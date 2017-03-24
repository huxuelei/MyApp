package com.sidney.devlib.utils;


import android.widget.Toast;

import com.sidney.devlib.AppApplication;

/**
 * ToastUtils
 */
public class ToastUtils {
    static Toast toast = Toast.makeText(AppApplication.getContext(), "", Toast.LENGTH_SHORT);

    public static void show(String text) {
        show(text, Toast.LENGTH_LONG);
    }

    public static void show(int textRes) {
        show(textRes, Toast.LENGTH_SHORT);
    }

    /**
     * @param text 显示内容
     * @param t    显示时间
     */
    public static void show(String text, int t) {
        //toast.cancel();
        toast.setDuration(t);
        toast.setText(text);
        toast.show();
    }

    public static void show(int textRes, int t) {
        // toast.cancel();
        toast.setDuration(t);
        toast.setText(textRes);
        toast.show();
    }

}
