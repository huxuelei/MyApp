package com.sidney.devlib.comment.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.sidney.devlib.R;

/**
 * Android Lollipop对话框
 * huxuelei
 */
public class LollipopDialog {

    private Context context;
    private Dialog dialog;
    public static final int STYLE_SELECT = 0;
    public static final int STYLE_PROMPT = 1;
    private TextView titleView;
    private TextView textView;
    private TextView positiveButton;
    private TextView negativeButton;
    private LollipopDialogListener listener;


    public LollipopDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.LollipopDialogTheme);
        dialog.setContentView(R.layout.dialog_extra_lollipop);

        titleView = (TextView) dialog.findViewById(R.id.title);
        textView = (TextView) dialog.findViewById(R.id.text);
        positiveButton = (TextView) dialog.findViewById(R.id.positive);
        negativeButton = (TextView) dialog.findViewById(R.id.negative);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = view.getId();
                if (i == R.id.positive) {
                    if (listener != null) {
                        listener.onPositiveButtonClicked(LollipopDialog.this);
                    }
                    dialog.dismiss();

                } else if (i == R.id.negative) {
                    if (listener != null) {
                        listener.onNegativeButtonClicked(LollipopDialog.this);
                    }
                    dialog.dismiss();

                }
            }
        };


        negativeButton.setOnClickListener(onClickListener);
        positiveButton.setOnClickListener(onClickListener);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (listener != null) {
                    listener.onCanceled(LollipopDialog.this);
                }
            }
        });
    }


    public LollipopDialog setTitle(CharSequence cs) {
        titleView.setText(cs);
        return this;
    }

    public LollipopDialog setTitle(int resId) {
        return setTitle(context.getString(resId));
    }

    public LollipopDialog setText(CharSequence cs) {
        textView.setText(cs);
        return this;
    }

    public LollipopDialog setText(int resId) {
        return setText(context.getString(resId));
    }

    public LollipopDialog setPositiveButtonText(CharSequence cs) {
        positiveButton.setText(cs);
        return this;
    }

    public LollipopDialog setPositiveButtonText(int resId) {
        return setPositiveButtonText(context.getString(resId));
    }

    public LollipopDialog setNegativeButtonText(CharSequence cs) {
        negativeButton.setText(cs);
        return this;
    }

    public LollipopDialog setNegativeButtonText(int resId) {
        return setNegativeButtonText(context.getString(resId));
    }

    public LollipopDialog setStyle(int style) {
        switch (style) {
            case STYLE_SELECT:
                negativeButton.setVisibility(View.VISIBLE);
                break;
            case STYLE_PROMPT:
                negativeButton.setVisibility(View.GONE);
                break;
        }
        return this;
    }


    public LollipopDialog setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
        return this;
    }

    public LollipopDialog setDialogListener(LollipopDialogListener listener) {
        this.listener = listener;
        return this;
    }


    public void show() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }


    public static interface LollipopDialogListener {
        void onPositiveButtonClicked(LollipopDialog lollipopDialog);

        void onNegativeButtonClicked(LollipopDialog lollipopDialog);

        void onCanceled(LollipopDialog lollipopDialog);
    }
}
