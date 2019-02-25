package com.sidney.app.ui.testmvp;

import com.sidney.app.mvp.BasePresenter;
import com.sidney.app.mvp.BaseView;

/**
 * MVPPlugin
 */
public class TestmvpContract {

    interface View extends BaseView {
        void initView();
    }

    interface Presenter extends BasePresenter<View> {
        void init();
    }
}
