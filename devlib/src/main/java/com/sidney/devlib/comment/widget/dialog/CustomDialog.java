package com.sidney.devlib.comment.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.sidney.devlib.R;


/**
 * ========================================================
 * 作  者：胡雪磊
 * 功能描述：自定义dialog 从底部弹出
 * ========================================================
 **/
public class CustomDialog implements View.OnClickListener {
    Context mContext;
    Dialog dialog;
    OnCDialogCalback onDialogCalback;

    View contentView;
    Button btnYes, btnNo, btnCancel;

    String yesText, noText, cancelText;

    public CustomDialog(Context context) {
        this.mContext = context;
    }

    public Dialog create() {
        init();

        dialog = new Dialog(mContext, R.style.dialog);
        dialog.setContentView(contentView);


        Window window = dialog.getWindow();
        // 可以在此设置显示动画
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = window.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);

        setValues();

        return dialog;
    }

    private void init() {
        contentView = View.inflate(mContext, R.layout.dialog_custom, null);
        findViewById();
        setListener();
    }

    private void findViewById() {
        btnYes = (Button) contentView.findViewById(R.id.btn_yes);
        btnNo = (Button) contentView.findViewById(R.id.btn_no);
        btnCancel = (Button) contentView.findViewById(R.id.btn_cancel);
    }

    private void setListener() {
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void setValues() {
        if (yesText != null) {
            btnYes.setText(yesText);
        }
        if (noText != null) {
            btnNo.setText(noText);
        }
        if (cancelText != null) {
            btnCancel.setText(cancelText);
        }
    }

    /**
     * 设置按钮显示，使用null，则显示默认(确定或者取消 )
     *
     * @ReqParam ok
     * @ReqParam cancel
     */
    public void setButtonText(String yes, String no, String cancel) {
        this.yesText = yes;
        this.noText = no;
        this.cancelText = cancel;
    }

    public void onClick(View v) {
        if (v == btnYes) {
            if (onDialogCalback != null) {
                onDialogCalback.onYes(this);
            }
        } else if (v == btnNo) {
            if (onDialogCalback != null) {
                onDialogCalback.onNo(this);
            }
        } else if (v == btnCancel) {
            if (onDialogCalback != null) {
                onDialogCalback.onCancel(this);
            }
        }
    }

    public void setOnCDialogCalback(OnCDialogCalback onDialogCalback) {
        this.onDialogCalback = onDialogCalback;
    }

    public interface OnCDialogCalback {
        void onYes(CustomDialog dialog);

        void onNo(CustomDialog dialog);

        void onCancel(CustomDialog dialog);
    }

}
