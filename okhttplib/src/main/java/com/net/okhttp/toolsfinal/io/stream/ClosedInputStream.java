package com.net.okhttp.toolsfinal.io.stream;

/**
 * Created by 20164237 on 2016/4/19.
 */
import java.io.InputStream;

public class ClosedInputStream extends InputStream {
    public static final ClosedInputStream CLOSED_INPUT_STREAM = new ClosedInputStream();

    public ClosedInputStream() {
    }

    public int read() {
        return -1;
    }
}
