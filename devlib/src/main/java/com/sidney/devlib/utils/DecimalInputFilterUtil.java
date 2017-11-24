package com.sidney.devlib.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * 金额控制
 */

public class DecimalInputFilterUtil implements InputFilter {

    private String mRegularExpression;

    public DecimalInputFilterUtil() {
        this(5);
    }

    public DecimalInputFilterUtil(int firstLength) {
        this(firstLength, 2);
    }

    public DecimalInputFilterUtil(int firstLength, int lastLength) {
        mRegularExpression = String.format("(\\d{0,%d}(\\.\\d{0,%d})?)", firstLength, lastLength);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        //验证删除等按键
        if ("".equals(source.toString())) {
            return "";
        }

        if ("0".equals(source) && "0".equals(dest.toString())) {
            return "";
        }

        if (".".equals(source) && TextUtils.isEmpty(dest.toString())) {
            return "";
        }

        boolean delete = false;
        StringBuilder builder = new StringBuilder(dest);

        if (TextUtils.isEmpty(source)) {
            delete = true;
            builder.delete(dstart, dend);
        } else {
            builder.insert(dstart, source);
        }

        String value = builder.toString();

        return value.matches(mRegularExpression) ? null : delete ? "." : "";
    }
}
