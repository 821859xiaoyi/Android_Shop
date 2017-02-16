package com.feicui.android_easyshop.user;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.feicui.android_easyshop.R;
import com.feicui.android_easyshop.commons.ActivityUtils;
import com.feicui.android_easyshop.commons.LogUtils;
import com.feicui.android_easyshop.commons.RegexUtils;
import com.feicui.android_easyshop.components.ProgressDialogFragment;
import com.feicui.android_easyshop.network.EasyShopClient;
import com.feicui.android_easyshop.network.UICallBack;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 注册登记
 * Created by Administrator on 2017/2/11.
 */
public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.et_pwdAgain)
    EditText et_pwdAgain;
    @BindView(R.id.btn_register)
    Button btn_register;

    private  String username;
    private String password;
    private String pwd_again;
    private ProgressDialogFragment dialogFragment;
    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        activityUtils=new ActivityUtils(this);

        init();
    }

    private void init() {
        //给左上角加一个返回图标，需要重写菜单点击事件，否则点击无效
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_username.addTextChangedListener(textWatcher);
        et_pwd.addTextChangedListener(textWatcher);
        et_pwdAgain.addTextChangedListener(textWatcher);
    }
    //给左上角加一个返回图标，需要重写菜单点击事件，否则点击无效

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //如果点击的是返回键，则finish
        if (item.getItemId()==android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
    private final TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            username=et_username.getText().toString();
            password=et_pwd.getText().toString();
            pwd_again=et_pwdAgain.getText().toString();
            boolean canLogin=!(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)||TextUtils.isEmpty(pwd_again));
            btn_register.setEnabled(canLogin);
        }
    };
    @OnClick(R.id.btn_register)
    public void onClick(){
        if (RegexUtils.verifyUsername(username) != RegexUtils.VERIFY_SUCCESS) {
            activityUtils.showToast(R.string.username_rules);
            return;
        } else if (RegexUtils.verifyPassword(password) != RegexUtils.VERIFY_SUCCESS) {
            activityUtils.showToast(R.string.password_rules);
            return;
        } else if (!TextUtils.equals(password, pwd_again)) {
            activityUtils.showToast(R.string.username_equal_pwd);
            return;
        }
        Call call= EasyShopClient.getInstance().register(username,password);
        call.enqueue(new UICallBack(){
            @Override
            public void onResponseUI(Call call, Response response) {
                activityUtils.showToast("注册成功");

            }

            @Override
            public void onFailureUI(Call call, IOException e) {

            }
        });
        LogUtils.e("注册成功");
    }
}
