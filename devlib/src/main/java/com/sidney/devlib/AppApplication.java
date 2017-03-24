package com.sidney.devlib;

import android.app.Application;
import android.content.Context;

import com.sidney.devlib.utils.CrashHandlerUtil;
import com.sidney.devlib.utils.LogUtil;

/**
 * Created by 20164237 on 2016/4/14.
 */
public class AppApplication extends Application {
    private static AppApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        LogUtil.setLog(true);
        //崩溃处理
        CrashHandlerUtil crashHandlerUtil = CrashHandlerUtil.getInstance();
        crashHandlerUtil.init(this);
        crashHandlerUtil.setCrashTip("很抱歉，程序出现异常，即将退出！");
    }

    public static synchronized AppApplication getInstance() {
        if (null == mContext) {
            mContext = new AppApplication();
        }
        return mContext;
    }

    public static Context getContext() {
        return mContext;
    }
}