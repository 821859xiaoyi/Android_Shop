package com.feicui.android_easyshop.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.feicui.android_easyshop.R;
import com.feicui.android_easyshop.commons.ActivityUtils;
import com.feicui.android_easyshop.commons.LogUtils;
import com.feicui.android_easyshop.components.ProgressDialogFragment;
import com.feicui.android_easyshop.network.EasyShopClient;

import java.io.IOException;
import java.util.logging.LogRecord;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/11.
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.et_username)
    EditText et_userName;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ActivityUtils activityUtils;
    private ProgressDialogFragment dialogFragment;//对话框
    private String username;//用户名
    private String password;//密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        activityUtils=new ActivityUtils(this);


        init();
    }

    private void init() {
        //给EditText添加监听
        et_userName.addTextChangedListener(textWatcher);
        et_pwd.addTextChangedListener(textWatcher);
        //给左上角加一个返回图标
        setSupportActionBar(toolbar);
        //设置为true。显示返回图标，但是点击效果需要实现菜单点击事件
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //设置为ture，显示返回图标，但是点击效果需要实现菜单点击事件

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //如果点击的是返回键，则finish
        if (item.getItemId()==android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
    //EditText监听
    private TextWatcher textWatcher=new TextWatcher() {
        //这里的s表示改变之前的内容，通常start和count组合，可以在s中读取本次改变字段中被改变的内容。
        //而after表示改变后新的内容的数量
        @Override
        public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

        }
        //这里的s表示改变之后的内容，通常start和count组合，可以在s中读取本次改变字段中新的内容。
        //而before表示被改变的内容的数量。
        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {

        }
        //表示最终内容
        @Override
        public void afterTextChanged(Editable editable) {
            username=et_userName.getText().toString();
            password=et_pwd.getText().toString();
            //判断用户名和密码是否为空
            boolean canLogin=!(TextUtils.isEmpty(username)||TextUtils.isEmpty(password));
            btn_login.setEnabled(canLogin);
        }
    };
    @OnClick({R.id.btn_login,R.id.tv_register})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                Call call= EasyShopClient.getInstance().login(username,password);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //后台线程
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        //后台线程
                        handler.sendEmptyMessage(0);
                    }
                });
                break;
            case R.id.tv_register:
                activityUtils.startActivity(RegisterActivity.class);
                break;

        }
    }
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            activityUtils.showToast("登陆成功");
            LogUtils.e("登陆成功");
        }
    };
}
