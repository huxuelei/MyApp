package com.sidney.app.ui.testmvp;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sidney.app.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 */
public class TestmvpActivity extends MVPBaseActivity<TestmvpContract.View, TestmvpPresenter> implements TestmvpContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.init();
    }

    @Override
    public void initView() {

    }
}
