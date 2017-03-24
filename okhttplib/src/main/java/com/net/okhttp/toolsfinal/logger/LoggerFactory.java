package com.net.okhttp.toolsfinal.logger;

/**
 * Created by 20164237 on 2016/4/19.
 */
public class LoggerFactory {
    public LoggerFactory() {
    }

    public static LoggerPrinter getFactory(String tag, boolean debug) {
        LoggerPrinter printer = new LoggerPrinter();
        printer.init(tag);
        LogLevel level = LogLevel.NONE;
        if(debug) {
            level = LogLevel.FULL;
        }

        printer.getSettings().methodCount(3).logLevel(level);
        return printer;
    }
}

