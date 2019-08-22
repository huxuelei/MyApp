package com.sidney.app.ui.two.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sidney.app.R;
import com.sidney.app.ui.two.activity.ListviewDemoActivity;
import com.sidney.devlib.ui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends BaseFragment implements View.OnClickListener {

    private View mView;
    private Button mBtn_listview;


    public TwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_two, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mBtn_listview = (Button) mView.findViewById(R.id.btn_listview);
        mBtn_listview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_listview:
                Intent listView = new Intent(mContext, ListviewDemoActivity.class);
                startActivity(listView);
                break;
        }
    }
}
