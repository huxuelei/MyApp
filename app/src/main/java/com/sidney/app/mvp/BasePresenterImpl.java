package com.sidney.app.mvp;

import java.lang.ref.WeakReference;

/**
 * MVPPlugin
 */
public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {

    protected WeakReference<V> mView;

    @Override
    public void attachView(V view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
