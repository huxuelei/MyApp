package com.sidney.devlib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 20164237 on 2016/4/14.
 */
public class CommonUtil {
    /**
     * 保留两位小数点
     */
    public static String getMoney(String strNumValue) {
        if (strNumValue == null) {
            return "";
        }
        BigDecimal b = new BigDecimal(strNumValue);
        DecimalFormat d1 = new DecimalFormat("#,##0.00");
        // 设置舍入模式
        d1.setRoundingMode(RoundingMode.FLOOR);
        return d1.format(b);
    }

    /**
     * money的格式化，保留两位小数
     */
    public static String FormatmoneyTwo(String money) {
        if (TextUtils.isEmpty(money)) {
            return "0";
        }
        try {
            DecimalFormat format = new DecimalFormat("###,###.00");
            String wallet = format.format(Double.valueOf(money));
            money = "" + wallet;
        } catch (Exception e) {
            money = "" + money;
        }
        if (money.startsWith(".")) {
            money = money.replace(".", "0.");
        }
        return money;
    }

    /**
     * 讲数字文本设置成千分位制 Method name: addComma <BR>
     * Description: addComma <BR>
     *
     * @param str
     * @return String<BR>
     */
    public static String addComma(String str) {
        boolean neg = false;
        if (str.startsWith("-")) { // 处理负数
            str = str.substring(1);
            neg = true;
        }
        String tail = null;
        if (str.indexOf('.') != -1) { // 处理小数点
            tail = str.substring(str.indexOf('.'));
            str = str.substring(0, str.indexOf('.'));
        }
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        for (int i = 3; i < sb.length(); i += 4) {
            sb.insert(i, ',');
        }
        sb.reverse();
        if (neg) {
            sb.insert(0, '-');
        }
        if (tail != null) {
            sb.append(tail);
        }
        str = sb.toString();
        if (str.endsWith(".00")) {
            str = str.substring(0, str.indexOf("."));
        }
        return str;
    }

    /**
     * 获得当前版本号
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        int version = 0;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            version = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    /**
     * 获取手机屏幕的像素宽
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * Method name: isResponseError <BR>
     * Description: 判断返回的数据是否为正确的JSON <BR>
     * Remark: <BR>
     *
     * @param responseString
     * @return boolean<BR>
     */
    public static boolean isResStrError(Context context, String responseString) {
        if (TextUtils.isEmpty(responseString) || !responseString.startsWith("{")
                || !responseString.endsWith("}")) {
            ToastUtils.show("数据异常");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 手机号的样式
     */
    public static boolean isPhoneStyle(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }

        boolean msg = false;
        if (TextUtils.isDigitsOnly(str)) {
            if (str.length() == 11) {
                String temp = str.substring(0, 1);
                if (temp.equals("1")) {
                    msg = true;
                }
            }
        }
        return msg;
    }

    /**
     * 去除某些字符 比如空格 ' '
     */
    public static String remove(String resource, char ch) {
        StringBuffer buffer = new StringBuffer();
        int position = 0;
        char currentChar;

        while (position < resource.length()) {
            currentChar = resource.charAt(position++);
            if (currentChar != ch) buffer.append(currentChar);
        }
        return buffer.toString();
    }

    /**
     * 测量View的宽高
     *
     * @param view View
     */
    public static void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }


}
