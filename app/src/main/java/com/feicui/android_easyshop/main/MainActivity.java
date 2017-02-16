package com.feicui.android_easyshop.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.feicui.android_easyshop.R;
import com.feicui.android_easyshop.commons.ActivityUtils;
import com.feicui.android_easyshop.main.me.MeFragment;
import com.feicui.android_easyshop.main.shop.ShopFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{

    @BindViews({R.id.tv_shop,R.id.tv_message,R.id.tv_mail_list,R.id.tv_me})
    TextView[] textViews;

    @BindView(R.id.main_toobar)
    Toolbar toolbar;
    @BindView(R.id.main_title)
    TextView tv_title;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    //点击2次返回，退出程序
    private boolean isExit=false;
    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        activityUtils=new ActivityUtils(this);

        //初始化视图
        init();
    }
    //初始化视图
    private void init() {
        viewPager.setAdapter(unLoginAdapter);
        //刚进来默认选择市场
        textViews[0].setSelected(true);
        //viewPager添加滑动监听，用于控制TextView的展示
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                //textView全部未选择
                for(TextView textView:textViews){
                    textView.setSelected(false);
                }
                //更改title，设置选择效果
                tv_title.setText(textViews[position].getText());
                textViews[position].setSelected(true);
            }

            //滑动状态变动
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //未登录时的ViewPager适配器
    private FragmentStatePagerAdapter unLoginAdapter=new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position){
                //市场
                case 0:
                    return  new ShopFragment();
                //消息
                case 1:
                    return new UnLoginFragment();
                //通讯录
                case 2:
                    return new UnLoginFragment();
                //我的
                case 3:
                    return new MeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    };



    //textview点击事情
    @OnClick({R.id.tv_shop,R.id.tv_message,R.id.tv_mail_list,R.id.tv_me})
    public void onCilk(TextView view){
        for (int i=0;i<textViews.length;i++){
            textViews[i].setSelected(false);
            textViews[i].setTag(i);
        }
        //设置选择效果
        view.setSelected(true);
        //参数false代表瞬间切换，而不是平滑过渡
        viewPager.setCurrentItem((Integer)view.getTag(),false);
        //设置一下toolbar的title
        tv_title.setText(textViews[(Integer)view.getTag()].getText());
    }
    //点击两次返回退出程序

    @Override
    public void onBackPressed() {
        if (!isExit){
            isExit=true;
            activityUtils.showToast("再按一次退出程序");
            //两秒内再次点击返回则退出
            //如果两秒内，用户没有再次点击，则把isExit设置为false
            viewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit=false;
                }
            },2000);
        }else{
            finish();
        }
    }

}
