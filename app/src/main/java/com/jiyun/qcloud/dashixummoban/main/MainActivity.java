package com.jiyun.qcloud.dashixummoban.main;

import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jiyun.qcloud.dashixummoban.R;
import com.jiyun.qcloud.dashixummoban.app.App;
import com.jiyun.qcloud.dashixummoban.base.BaseActivity;
import com.jiyun.qcloud.dashixummoban.base.BaseFragment;
import com.jiyun.qcloud.dashixummoban.manager.ActivityCollector;
import com.jiyun.qcloud.dashixummoban.manager.FragmentMager;
import com.jiyun.qcloud.dashixummoban.ui.home.HomePageFragment;
import com.jiyun.qcloud.dashixummoban.ui.live.LivePageFragment;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chj on 2017/8/20.
 */

public class MainActivity extends BaseActivity   {


    @BindView(R.id.iconImg)
    ImageView iconImg;
    @BindView(R.id.personImg)
    ImageView personImg;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.hudongImg)
    ImageView hudongImg;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.homePage)
    RadioButton homePage;
    @BindView(R.id.homePandaLive)
    RadioButton homePandaLive;
    @BindView(R.id.homeRollVideo)
    RadioButton homeRollVideo;
    @BindView(R.id.homePandaBroadcast)
    RadioButton homePandaBroadcast;
    @BindView(R.id.homeLiveChina)
    RadioButton homeLiveChina;
    @BindView(R.id.homeBottomGroup)
    RadioGroup homeBottomGroup;
    private FragmentManager fragmentManager;
    private long mExitTime;

    @Override
    protected void initData() {
        fragmentManager =App.mBaseActivity.getSupportFragmentManager();
        FragmentMager.getInstance().start(R.id.container, HomePageFragment.class,false).build();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        Logger.d("===========");
        return R.layout.activity_main;
    }


    @OnClick({R.id.homePage, R.id.homePandaLive, R.id.homeRollVideo, R.id.homePandaBroadcast, R.id.homeLiveChina, R.id.homeBottomGroup})
    public void onClicks(View view) {
        switch (view.getId()) {
            case R.id.homePage:
                FragmentMager.getInstance().start(R.id.container, HomePageFragment.class,false).build();
                Logger.d("22222");
                break;
            case R.id.homePandaLive:
                FragmentMager.getInstance().start(R.id.container, LivePageFragment.class,false).build();
                Logger.d("33333");
                break;
            case R.id.homeRollVideo:
                break;
            case R.id.homePandaBroadcast:
                break;
            case R.id.homeLiveChina:
                break;
            case R.id.homeBottomGroup:
                break;
        }
    }

    ///////////////////
    /**
     * 自定义回退栈管理；
     * 获取栈顶的fragment的名字，判断名字是否和主页的名字是否一样，
     * 如果一样就退出应用，如果不是就回退上一个fragment；
     *
     * onBackPressed()与onKeyDown
     */
    @Override
    public void onBackPressed() {
        String simpleName = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        if ("HomePageFragment".equals(simpleName) ||
                "LivePageFragment".equals(simpleName) ||
                "MyFragment".equals(simpleName)||
                "MoreFragment".equals(simpleName)
                ) {
            finish();
        } else {
            if (fragmentManager.getBackStackEntryCount() > 1) {
                fragmentManager.popBackStackImmediate();//
                String name = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
                App.lastfragment = (BaseFragment) fragmentManager.findFragmentByTag(name);
            }
        }
    }

    /**
     * 双击退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String name = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        if ("HomePageFragment".equals(name) ||
                "LivePageFragment".equals(name) ||
                "MyFragment".equals(name)||
                "MoreFragment".equals(name)
                ){
            if (keyCode == KeyEvent.KEYCODE_BACK) {//back键被按下了
                if ((System.currentTimeMillis() - mExitTime) >2000) {//第二次点击判断是否在两秒内完成，是的话Finish掉（退出）
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    ActivityCollector.getInstance().exit(App.mBaseActivity);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}




