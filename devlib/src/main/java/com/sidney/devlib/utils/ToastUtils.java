package com.sidney.devlib.utils;


import android.widget.Toast;

import com.sidney.devlib.AppApplication;

/**
 * ToastUtils
 */
public class ToastUtils {

    public static void show(String text) {
        Toast toast = Toast.makeText(AppApplication.getContext(), text, Toast.LENGTH_SHORT);
        show(toast, text, Toast.LENGTH_LONG);
    }

    public static void show(int textRes) {
        Toast toast = Toast.makeText(AppApplication.getContext(), textRes, Toast.LENGTH_SHORT);
        show(toast, textRes, Toast.LENGTH_SHORT);
    }

    /**
     * @param text 显示内容
     * @param t    显示时间
     */
    public static void show(Toast toast, String text, int t) {
        //toast.cancel();
        toast.setDuration(t);
        toast.setText(text);
        toast.show();
    }

    public static void show(Toast toast, int textRes, int t) {
        // toast.cancel();
        toast.setDuration(t);
        toast.setText(textRes);
        toast.show();
    }

}
