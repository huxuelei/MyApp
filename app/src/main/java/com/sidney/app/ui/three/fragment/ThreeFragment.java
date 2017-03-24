package com.sidney.app.ui.three.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sidney.app.R;
import com.sidney.devlib.comment.widget.dialog.CustomDialog;
import com.sidney.devlib.comment.widget.dialog.LollipopDialog;
import com.sidney.devlib.ui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends BaseFragment {


    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three, container, false);
    }

    private void showDialog() {
        LollipopDialog dialog = new LollipopDialog(mActivity);
        dialog.setText("确认删除合作伙伴?");
        dialog.show();
        dialog.setDialogListener(new LollipopDialog.LollipopDialogListener() {
            @Override
            public void onPositiveButtonClicked(LollipopDialog lollipopDialog) {

            }

            @Override
            public void onNegativeButtonClicked(LollipopDialog lollipopDialog) {

            }

            @Override
            public void onCanceled(LollipopDialog lollipopDialog) {

            }
        });
    }

    private void dialogCheckIdea() {
        CustomDialog dialog = new CustomDialog(mActivity);
        dialog.setButtonText("请领导批示", "妥否,请批示", "取消");
        final Dialog bottomDialog = dialog.create();
        dialog.setOnCDialogCalback(new CustomDialog.OnCDialogCalback() {


            @Override
            public void onYes(CustomDialog dialog) {

                bottomDialog.dismiss();
            }

            @Override
            public void onNo(CustomDialog dialog) {

                bottomDialog.dismiss();
            }

            @Override
            public void onCancel(CustomDialog dialog) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.show();
    }

}
