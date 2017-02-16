package com.feicui.android_easyshop.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.feicui.android_easyshop.R;
import com.feicui.android_easyshop.commons.ActivityUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/2/12.
 */

public class SplashActivity extends AppCompatActivity{
    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activityUtils=new ActivityUtils(this);
        // TODO: 2017/2/7 0007 环信登录相关（账号冲突踢出）
        // TODO: 2017/2/7 0007 判断用户是否登录，自动登录

        //1.5秒跳转到主页
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //跳转页面
                activityUtils.startActivity(MainActivity.class);
                finish();
            }
        },1500);
    }
}
