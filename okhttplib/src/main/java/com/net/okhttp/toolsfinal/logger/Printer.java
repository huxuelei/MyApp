package com.net.okhttp.toolsfinal.logger;

/**
 * Created by 20164237 on 2016/4/19.
 */
public interface Printer {
    Printer t(String var1, int var2);

    Settings getSettings();

    void d(String var1, Object... var2);

    void e(Throwable var1);

    void e(String var1, Object... var2);

    void e(Throwable var1, String var2, Object... var3);

    void w(String var1, Object... var2);

    void i(String var1, Object... var2);

    void v(String var1, Object... var2);

    void wtf(String var1, Object... var2);

    void json(String var1);

    void xml(String var1);

    void clear();
}

