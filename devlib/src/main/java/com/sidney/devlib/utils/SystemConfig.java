package com.sidney.devlib.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.TouchDelegate;
import android.view.View;
import android.view.WindowManager;

public class SystemConfig {

	public static int getScreenWidth(Context context)
	{
		int screen_w = 480;
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		screen_w = display.getWidth();//宽度
    	
    	return screen_w;
	}
	
	public static int getScreenHeight(Context context)
	{
		int screen_H = 800;
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		screen_H = display.getHeight();//宽度
    	
    	return screen_H;
	}
	
	/******************'
	 * 获取当前网络状态
	 * @param context
	 * @return
	 */
	public static NetWorkStatus getConnectedStatus(Context context) {
		if (context != null) { 
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
			.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) { 
				return NetWorkStatus.ConnectSuccess; 
			} 
		

		} 
		return NetWorkStatus.ConnectFailed; 
	} 
	
	/********
	 * 获取状态栏高度
	 * @param context
	 * @return
	 */
	public static int getStatusBarH(Context context)
	{
		int statusHeight = 0;
	    try {
	        Class clazz = Class.forName("com.android.internal.R$dimen");
	        Object object = clazz.newInstance();
	        int height = Integer.parseInt(clazz.getField("status_bar_height")
	                .get(object).toString());
	        statusHeight = context.getResources().getDimensionPixelSize(height);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return statusHeight;
	}
	
	/** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    } 
    
    
    /** 
     * 扩大View的触摸和点击响应范围,最大不超过其父View范围 
     *  
     * @param view 
     * @param top 
     * @param bottom 
     * @param left 
     * @param right 
     */  
    public static void expandViewTouchDelegate(final View view, final int top,
											   final int bottom, final int left, final int right) {
  
        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {  
                Rect bounds = new Rect();
                view.setEnabled(true);  
                view.getHitRect(bounds);  
  
                bounds.top -= top;  
                bounds.bottom += bottom;  
                bounds.left -= left;  
                bounds.right += right;  
  
                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);
  
                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }  
            }  
        });  
    }  
    
    
    /** 
     * 还原View的触摸和点击响应范围,最小不小于View自身范围 
     *  
     * @param view 
     */  
    public static void restoreViewTouchDelegate(final View view) {
  
        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {  
                Rect bounds = new Rect();
                bounds.setEmpty();  
                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);
  
                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }  
            }  
        });  
    }  
    

	/***********
	 * 获取App名称
	 * @param context
	 * @return
	 */
	public static String getAppName(Context context)
	{
		PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try { 
            packageManager = context.getApplicationContext().getPackageManager(); 
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0); 
        } catch (NameNotFoundException e) {
            applicationInfo = null; 
        } 
        String applicationName =
        (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName; 
	}
    
    /***************
     * 获取系统版本号
     */
    public static int getAppCode(Context context)
    {
    	int version = 1;
    	// 获取packagemanager的实例
    	PackageManager packageManager = context.getPackageManager();
    	// getPackageName()是你当前类的包名，0代表是获取版本信息
    	PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			version = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return version;
    }
    
    /*************
   	 * 获取App版本号
   	 * @param context
   	 * @return
   	 */
   	public static String getAppVersion(Context context)
   	{
   		 String version="";
   		 // 获取packagemanager的实例
           PackageManager packageManager = context.getPackageManager();
           // getPackageName()是你当前类的包名，0代表是获取版本信息
           PackageInfo packInfo;
   		try {
   			packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
   			version =  packInfo.versionName;
   		} catch (NameNotFoundException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
           
           return version;
   	}
    
    /***************
     * 获取设备号
     * @param context
     * @return
     */
    public static String getDeviceId(Context context)
    {
    	TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    	String szImei = TelephonyMgr.getDeviceId();
    	return szImei;
    }
    
    public enum NetWorkStatus
    {
    	ConnectFailed,
    	ConnectSuccess
    }
}
