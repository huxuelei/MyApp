package com.sidney.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.sidney.app.ui.one.fragment.OneFragment;
import com.sidney.app.ui.three.fragment.ThreeFragment;
import com.sidney.app.ui.two.fragment.TwoFragment;
import com.sidney.devlib.ui.BaseActivity;
import com.sidney.devlib.utils.ToastUtils;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    // 底部菜单
    private RadioGroup mTabMenu;
    // 正在显示的fragment
    private Fragment lastFragment;

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
        //setCurrentItem(1);
        //setCurrentItem(2);
        setCurrentItem(0);
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
