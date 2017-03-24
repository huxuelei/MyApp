package com.sidney.devlib.comment.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sidney.devlib.R;


/**
 * Created by Administrator on 2016/11/29.
 * 左边图片文字  右边文字  组合控件
 */
public class ImageAndText extends LinearLayout {

    private static final int BTN_LIMIT_TIME = 500; // 防重复点击时间
    private ImageView mImgLeft, mImgRight;
    private TextView mTxtLeft;
    private int mLeftImg, mRightImg;
    private String mLeftTxt;

    public ImageAndText(Context context) {
        super(context);
    }

    public ImageAndText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.ImageAndText);
        mLeftImg = arr.getResourceId(R.styleable.ImageAndText_leftImg, 0);
        mLeftTxt = arr.getString(R.styleable.ImageAndText_leftTxt);
        mRightImg = arr.getResourceId(R.styleable.ImageAndText_rightImg, 0);
        if (isInEditMode()) {
            LayoutInflater.from(context).inflate(R.layout.layout_image_text, this);
            return;
        }
        LayoutInflater.from(context).inflate(R.layout.layout_image_text, this);
        arr.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) {
            return;
        }
        mImgLeft = (ImageView) findViewById(R.id.img_left);
        mTxtLeft = (TextView) findViewById(R.id.txt_left);
        mImgRight = (ImageView) findViewById(R.id.img_right);
        setLeftImg(mLeftImg);
        setLeftText(mLeftTxt);
        setRightImg(mRightImg);
    }

    public void setLeftImg(int img) {
        if (img != 0) {
            mImgLeft.setImageResource(img);
        }
    }

    public void setLeftText(String txt) {
        if (!TextUtils.isEmpty(txt)) {
            mTxtLeft.setText(txt);
        }
    }

    public void setRightImg(int img) {
        if (img != 0) {
            mImgRight.setImageResource(img);
        }
    }

    public void setOnItemListener(OnClickListener listener) {
        OnClickListener myListener = new ToHeavyOnClickListener(listener, BTN_LIMIT_TIME);
        findViewById(R.id.lineLay_layout).setOnClickListener(myListener);
    }

}
