package com.sidney.devlib.comment.widget.titlebar;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sidney.devlib.R;
import com.sidney.devlib.comment.widget.ToHeavyOnClickListener;

/**
 * 自定义标题栏
 */
public class TitleLayout extends RelativeLayout {

    // 防重复点击时间
    private static final int BTN_LIMIT_TIME = 500;

    private TextView leftButton;
    private ImageView leftButtonImg;
    private TextView middleButton;
    private TextView rightButton;
    private ImageView rightButtonImg;
    private int leftBtnIconId;
    private String leftBtnStr;
    private String titleTxtStr;
    private String rightBtnStr;
    private int rightBtnIconId;
    private LinearLayout mTitleLeftArea;
    private LinearLayout mTitleRightArea;

    private Context mContext;


    public TitleLayout(Context context) {
        super(context);
        this.mContext = context;
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout);
        // 如果后续有文字按钮，可使用该模式设置
        leftBtnStr = arr.getString(R.styleable.TitleLayout_leftBtnTxt);
        leftBtnIconId = arr.getResourceId(R.styleable.TitleLayout_leftBtnIcon, 0);
        titleTxtStr = arr.getString(R.styleable.TitleLayout_titleTxt);
        rightBtnStr = arr.getString(R.styleable.TitleLayout_rightBtnTxt);
        rightBtnIconId = arr.getResourceId(R.styleable.TitleLayout_rightBtnIcon, 0);
        if (isInEditMode()) {
            LayoutInflater.from(context).inflate(R.layout.common_title_bar, this);
            return;
        }

        LayoutInflater.from(context).inflate(R.layout.common_title_bar, this);
        arr.recycle();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onFinishInflate() {
        if (isInEditMode()) {
            return;
        }
        mTitleLeftArea = (LinearLayout) findViewById(R.id.title_left_area);
        mTitleRightArea = (LinearLayout) findViewById(R.id.title_right_area);
        leftButtonImg = (ImageView) findViewById(R.id.title_left_btn);
        leftButton = (TextView) findViewById(R.id.title_left);
        middleButton = (TextView) findViewById(R.id.title_middle);
        rightButtonImg = (ImageView) findViewById(R.id.title_right_btn);
        rightButton = (TextView) findViewById(R.id.title_right);

        if (leftBtnIconId != 0) {
            leftButtonImg.setImageResource(leftBtnIconId);
            leftButtonImg.setVisibility(View.VISIBLE);
        } else {
            leftButtonImg.setVisibility(View.GONE);
        }
        if (rightBtnIconId != 0) {
            rightButtonImg.setImageResource(rightBtnIconId);
            rightButtonImg.setVisibility(View.VISIBLE);
        } else {
            rightButtonImg.setVisibility(View.GONE);
        }
        setLeftTxtBtn(leftBtnStr);
        setTitleTxt(titleTxtStr);
        setRightTxtBtn(rightBtnStr);
    }

    public void setRightTxtBtn(String btnTxt) {
        if (!TextUtils.isEmpty(btnTxt)) {
            rightButton.setText(btnTxt);
            rightButton.setVisibility(View.VISIBLE);
        } else {
            rightButton.setVisibility(View.GONE);
        }
    }

    public void setRightImgIconBtn(int rightBtnIconId) {
        rightButtonImg.setImageResource(rightBtnIconId);
        rightButtonImg.setVisibility(VISIBLE);
    }

    public void setRightTxtClor(int txtClor) {
        rightButton.setTextColor(ContextCompat.getColor(mContext, txtClor));
    }

    public void setEnabledRightBtn(boolean isEnabled) {
        mTitleRightArea.setEnabled(isEnabled);
    }

    public void hideRightBtn() {
        rightButton.setVisibility(View.GONE);
        rightButtonImg.setVisibility(View.GONE);
        findViewById(R.id.title_right_area).setOnClickListener(null);
    }

    public void setRightBtnOnclickListener(OnClickListener listener) {
        OnClickListener myListener = new ToHeavyOnClickListener(listener, BTN_LIMIT_TIME);
        findViewById(R.id.title_right_area).setOnClickListener(myListener);
    }

    public void setLeftTxtBtn(String leftBtnStr) {
        if (!TextUtils.isEmpty(leftBtnStr)) {
            leftButton.setText(leftBtnStr);
            leftButton.setVisibility(View.VISIBLE);
        } else {
            leftButton.setVisibility(View.GONE);
        }
    }

    public void setLeftTxtClor(int txtClor) {
        leftButton.setTextColor(ContextCompat.getColor(mContext, txtClor));
    }

    public void setLeftImgIconBtn(int leftBtnIconId) {
        leftButtonImg.setImageResource(leftBtnIconId);
        leftButtonImg.setVisibility(VISIBLE);
    }

    public void hideLeftBtn() {
        leftButton.setVisibility(View.GONE);
        leftButtonImg.setVisibility(View.GONE);
        findViewById(R.id.title_left_area).setOnClickListener(null);
    }

    public void setEnabledLeftBtn(boolean isEnabled) {
        mTitleLeftArea.setEnabled(isEnabled);
    }

    public void setLeftBtnOnclickListener(OnClickListener listener) {
        OnClickListener myListener = new ToHeavyOnClickListener(listener, BTN_LIMIT_TIME);
        findViewById(R.id.title_left_area).setOnClickListener(myListener);
    }

    public void setTitleTxt(String title) {
        if (!TextUtils.isEmpty(title)) {
            middleButton.setText(title);
            middleButton.setVisibility(View.VISIBLE);
        } else {
            middleButton.setVisibility(View.GONE);
        }
    }


}
