package com.sidney.app.ui.testmvp;

import com.sidney.app.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 */
public class TestmvpPresenter extends BasePresenterImpl<TestmvpContract.View> implements TestmvpContract.Presenter {

    @Override
    public void init() {
        if (null != mView) {
            mView.get().initView();
        }
    }


}
