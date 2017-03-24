package com.dev.retrofitlib.common.loader;

/**
 * @Description: 加载工厂，可定制图片加载框架
 * LoaderFactory.getLoader().init(this);
 * LoaderFactory.getLoader().loadNet(icon, s, new ILoader.Options(R.mipmap.github_head_portrait, R.mipmap.github_head_portrait));
 */
public class LoaderFactory {
    private static ILoader loader;

    public static ILoader getLoader() {
        if (loader == null) {
            synchronized (LoaderFactory.class) {
                if (loader == null) {
                    loader = new GlideLoader();
                }
            }
        }
        return loader;
    }
}
