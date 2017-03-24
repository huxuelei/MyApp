package com.sidney.devlib.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * ================================================
 * 上海建业信息科技股份有限公司  经营管家
 * 日 期： 2016/7/7 13:18
 * 作 者：huxuelei
 * =================================================
 **/
public class MoneyUtils {

    public static String FormatmoneyTwo(String money) {
        if (TextUtils.isEmpty(money)) {
            return "0.00";
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
     * 小写金额转换为大写
     */
    private static final String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";
    private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";
    private static final double MAX_VALUE = 9999999999999.99D;

    public static String change(double v) {
        if (v < 0 || v > MAX_VALUE) {
            return "参数非法!";
        }
        long l = Math.round(v * 100);
        if (l == 0) {
            return "零元整";
        }
        String strValue = l + "";
        // i用来控制数
        int i = 0;
        // j用来控制单位
        int j = UNIT.length() - strValue.length();
        String rs = "";
        boolean isZero = false;
        for (; i < strValue.length(); i++, j++) {
            char ch = strValue.charAt(i);
            if (ch == '0') {
                isZero = true;
                if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {
                    rs = rs + UNIT.charAt(j);
                    isZero = false;
                }
            } else {
                if (isZero) {
                    rs = rs + "零";
                    isZero = false;
                }
                rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
            }
        }
        if (!rs.endsWith("分")) {
            rs = rs + "整";
        }
        rs = rs.replaceAll("亿万", "亿");
        return rs;
    }
}
