package com.dev.httplib.http.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.dev.httplib.AppApplication;

/**
 * Created by sidney on 2017/3/28.
 */

public class NetUtil {

    public static boolean isConnectInternet() {
        ConnectivityManager conManager = (ConnectivityManager) AppApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

}
