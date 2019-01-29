package com.sidney.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sidney.app.ui.one.fragment.OneFragment;
import com.sidney.app.ui.three.fragment.ThreeFragment;
import com.sidney.app.ui.two.fragment.TwoFragment;
import com.sidney.app.widget.BadgeView;
import com.sidney.devlib.ui.BaseActivity;
import com.sidney.devlib.utils.ToastUtils;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    // 底部菜单
    private RadioGroup mTabMenu;
    // 正在显示的fragment
    private Fragment lastFragment;

    private TextView mTvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        // 初始化底部菜单
        mTabMenu = (RadioGroup) findViewById(R.id.tab_menu);
        mTabMenu.setOnCheckedChangeListener(this);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        remind(mTvMsg);
        //setCurrentItem(1);
        //setCurrentItem(2);
        setCurrentItem(0);
    }

    /**
     * desc:消息提醒
     */
    private void remind(View view) { //BadgeView的具体使用
        // 创建一个BadgeView对象，view为你需要显示提醒的控件
        BadgeView badge1 = new BadgeView(this, view);
        // 需要显示的提醒类容
        badge1.setText("12");
        // 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        // 文本颜色
        badge1.setTextColor(Color.WHITE);
        // 提醒信息的背景颜色，自己设置
        badge1.setBadgeBackgroundColor(Color.RED);
        //还可以设置背景图片
        //badge1.setBackgroundResource(R.mipmap.icon_message_png);
        // 文本大小
        badge1.setTextSize(12);
        // 水平和竖直方向的间距
        //badge1.setBadgeMargin(3, 3);
        //各边间隔
        badge1.setBadgeMargin(5);
        //显示效果，如果已经显示，则隐藏，如果隐藏，则显示
        // badge1.toggle();
        // 显示
        badge1.show();
        //隐藏
        // badge1.hide();
    }


    // 代码切换tab
    public Fragment setCurrentItem(int position) {
        if (position > 3) {
            position = 3;
        } else if (position < 0) {
            position = 0;
        }
        switch (position) {
            case 0:
                mTabMenu.check(R.id.tab01);
                break;
            case 1:
                mTabMenu.check(R.id.tab02);
                break;
            case 2:
                mTabMenu.check(R.id.tab03);
                break;
            default:
                break;
        }
        return lastFragment;
    }

    private void switchFragment(int position) {
        // 想要即将显示的fragment
        Fragment currentFragment = null;
        switch (position) {
            case 0:
                currentFragment = generateFragmetWithTag("1", position);
                break;
            case 1:
                currentFragment = generateFragmetWithTag("2", position);
                break;
            case 2:
                currentFragment = generateFragmetWithTag("3", position);
                break;
//            case 3:
//                currentFragment = generateFragmetWithTag("4", position);
//                break;
            default:
                break;
        }
        // 如果未添加则添加
        if (!currentFragment.isAdded()) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.add(R.id.fragment_content, currentFragment, (position + 1) + "");
            transaction.commitAllowingStateLoss();
        }

        if (lastFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(lastFragment)
                    .commitAllowingStateLoss();
        }
        getSupportFragmentManager().beginTransaction().show(currentFragment)
                .commitAllowingStateLoss();

        lastFragment = currentFragment;
    }

    private Fragment generateFragmetWithTag(String tag, int position) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new OneFragment();
                    break;
                case 1:
                    fragment = new TwoFragment();
                    break;
                case 2:
                    fragment = new ThreeFragment();
                    break;
                default:
                    break;
            }
        }
        return fragment;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.tab01:
                switchFragment(0);
                break;
            case R.id.tab02:
                switchFragment(1);
                break;
            case R.id.tab03:
                switchFragment(2);
                break;
            default:
                break;
        }
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (lastFragment instanceof OneFragment) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.show("再按一次退出程序!");
                exitTime = System.currentTimeMillis();
            } else {
                finish();

            }
        } else if (lastFragment instanceof TwoFragment) {
            mTabMenu.check(R.id.tab01);
        } else if (lastFragment instanceof ThreeFragment) {
            mTabMenu.check(R.id.tab01);
        }
    }
}
