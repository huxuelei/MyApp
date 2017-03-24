package com.net.okhttp.toolsfinal.logger;

/**
 * Created by 20164237 on 2016/4/19.
 */
public final class Logger {
    public static final String DEFAULT_TAG = "Logger";
    private static boolean debug = false;
    private static LoggerPrinter loggerPrinter;

    public Logger() {
    }

    public static void setDebug(boolean isDebug) {
        debug = isDebug;
    }

    public static LoggerPrinter getDefaultLogger() {
        if(loggerPrinter == null) {
            loggerPrinter = LoggerFactory.getFactory("Logger", debug);
        }

        return loggerPrinter;
    }
}
