package com.sidney.devlib.comment.widget.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;

public class BaseDialog extends AppCompatDialog {
    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


}
