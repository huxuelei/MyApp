package com.sidney.devlib.utils;

/**
 * Created by sidney on 2017/11/24.
 */

public class StringUtils {
    private static String replaceStr(int startIndex, int endIndex, StringBuffer sb) {
        for (int i = startIndex; i < endIndex; i++) {
            sb.replace(i, i + 1, "*");
        }
        return sb.toString();
    }

    /**
     * 替换字符串
     *
     * @param from String 原始字符串
     * @param to String 目标字符串
     * @param source String 母字符串
     * @return String 替换后的字符串
     */
    public static String replace(String from, String to, String source) {
        if (source == null || from == null || to == null)
            return null;
        StringBuffer bf = new StringBuffer("");
        int index = -1;
        while ((index = source.indexOf(from)) != -1) {
            bf.append(source.substring(0, index) + to);
            source = source.substring(index + from.length());
            index = source.indexOf(from);
        }
        bf.append(source);
        return bf.toString();
    }
}
