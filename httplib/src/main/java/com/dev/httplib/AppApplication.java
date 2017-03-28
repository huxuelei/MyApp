package com.dev.httplib;

import android.app.Application;

public class AppApplication extends Application {
    private static AppApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static synchronized AppApplication getInstance() {
        if (null == mContext) {
            mContext = new AppApplication();
        }
        return mContext;
    }
}