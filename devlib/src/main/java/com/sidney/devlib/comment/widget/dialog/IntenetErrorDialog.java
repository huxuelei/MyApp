package com.sidney.devlib.comment.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.sidney.devlib.R;

/**
 * author:hxl
 * e-mail:huxl@bjhzwq.com
 * time:2019/1/25 16:43
 * desc:网络错误
 * version:1.0
 *
 *    final IntenetErrorDialog dialog = new IntenetErrorDialog(getActivity());
 *                         dialog.show("", "", new View.OnClickListener() {
 *                                     @Override
 *                                     public void onClick(View v) {
 *                                         dialog.dismiss();
 *                                     }
 *                                 },
 *                                 new View.OnClickListener() {
 *                                     @Override
 *                                     public void onClick(View v) {
 *                                         dialog.dismiss();
 *                                     }
 *                                 });
 */
public class IntenetErrorDialog extends BaseDialog {

    private TextView mTvSure;

    public IntenetErrorDialog(@NonNull Context context) {
        super(context, R.style.Dialog_FullScreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_intenet_error);
        initView();
    }

    private void initView() {
        mTvSure = findViewById(R.id.tv_sure);
    }

    public void show(View.OnClickListener okListener) {
        this.setCancelable(false);
        super.show();
        if (okListener != null) {
            mTvSure.setOnClickListener(okListener);
        }
    }

}
