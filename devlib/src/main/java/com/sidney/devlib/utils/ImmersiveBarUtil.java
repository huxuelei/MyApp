package com.sidney.devlib.utils;

import android.app.Activity;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by sidney on 2017/8/4.
 * <p>
 * ImmersionBar.with(this)
 * .transparentStatusBar()  //透明状态栏，不写默认透明色
 * .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
 * .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
 * .statusBarColor(R.color.color_red)     //状态栏颜色，不写默认透明色
 * .navigationBarColor(R.color.color_red) //导航栏颜色，不写默认黑色
 * .barColor(R.color.color_red)  //同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
 * .statusBarAlpha(0.3f)  //状态栏透明度，不写默认0.0f
 * .navigationBarAlpha(0.4f)  //导航栏透明度，不写默认0.0F
 * .barAlpha(0.3f)  //状态栏和导航栏透明度，不写默认0.0f
 * .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
 * .fullScreen(true)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
 * .hideBar(BarHide.FLAG_SHOW_BAR)  //隐藏状态栏或导航栏或两者，不写默认不隐藏
 * .setViewSupportTransformColor(toolbar) //设置支持view变色，支持一个view，不指定颜色，默认和状态栏同色，还有两个重载方法
 * .addViewSupportTransformColor(toolbar)  //设置支持view变色，可以添加多个view，不指定颜色，默认和状态栏同色，还有两个重载方法
 * .statusBarView(view)  //解决状态栏和布局重叠问题
 * .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
 * .statusBarColorTransform(R.color.orange)  //状态栏变色后的颜色
 * .navigationBarColorTransform(R.color.color_font_grey) //导航栏变色后的颜色
 * .barColorTransform(R.color.color_font_grey)  //状态栏和导航栏变色后的颜色
 * .removeSupportView()  //移除通过setViewSupportTransformColor()方法指定的view
 * .removeSupportView(toolbar)  //移除指定view支持
 * .removeSupportAllView() //移除全部view支持
 * .init();  //必须调用方可沉浸式
 */

public class ImmersiveBarUtil {
    public static void statusBarColor(Activity activity, int color) {
        ImmersionBar.with(activity)
                .fitsSystemWindows(true)
                .statusBarColor(color)
                .hideBar(BarHide.FLAG_SHOW_BAR)
                .init();
    }

    public static void hideBar(Activity activity) {

        ImmersionBar.with(activity)
                .fitsSystemWindows(false)
                .transparentStatusBar()
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                .init();
    }

    public static void destroy(Activity activity) {
        //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        ImmersionBar.with(activity).destroy();
    }
}
