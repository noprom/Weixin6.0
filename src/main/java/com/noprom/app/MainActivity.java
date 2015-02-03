package com.noprom.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.ViewConfiguration;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity{

    private ViewPager mViewPager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private String[] mTitles = new String[]{
            "第一个Fragment",
            "第二个Fragment",
            "第三个Fragment",
            "第四个Fragment"
    };
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setOverflowButtonAlways();
        getActionBar().setDisplayShowHomeEnabled(false);

        // 初始化布局
        initView();

        // 初始化数据
        initDatas();

        // 设置适配器
        mViewPager.setAdapter(mAdapter);
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        for (String title : mTitles){
            TabFragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TabFragment.TITLE,title);
            tabFragment.setArguments(bundle);
            mTabs.add(tabFragment);
        }

        // 初始化适配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mTabs.get(i);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 总是显示浮动按钮
     */
    private void setOverflowButtonAlways() {

        try {
            ViewConfiguration configuration = ViewConfiguration.get(this);

            Field menuKey = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(configuration, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置menu显示icon
     *
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}
