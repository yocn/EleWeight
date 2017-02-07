package com.bjw.ComAssistant.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjw.ComAssistant.R;
import com.bjw.ComAssistant.application.EApplication;
import com.bjw.ComAssistant.bean.user.UserBean;
import com.bjw.ComAssistant.presenter.LoginPresenter;
import com.bjw.ComAssistant.util.Loger;
import com.bjw.ComAssistant.util.MyToast;
import com.bjw.ComAssistant.util.RequestCallback;
import com.bjw.ComAssistant.util.SharedPreferencesUtil;
import com.bjw.ComAssistant.util.Utils;
import com.bjw.ComAssistant.view.ProgressDialog;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    ImageView iv_close;
    ImageView iv_check;
    EditText et_login;
    EditText et_psd;
    //    CheckBox checkBox;
    Button btn_login;
    Activity mContext;
    private boolean isRemeberPass = false;
    LoginPresenter mLoginPresenter;
    ProgressDialog mProgressDialog;
    TextView tv_wrong_password;
    TextView tv_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        initView();
        initData();
    }

    protected void initView() {
        tv_wrong_password = (TextView) findViewById(R.id.tv_wrong_password);
        tv_check = (TextView) findViewById(R.id.tv_check);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_check = (ImageView) findViewById(R.id.iv_check);
        et_login = (EditText) findViewById(R.id.et_login);
        et_psd = (EditText) findViewById(R.id.et_psd);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_wrong_password.setVisibility(View.GONE);
    }

    protected void initData() {
        mProgressDialog = new ProgressDialog(this);
        mLoginPresenter = new LoginPresenter(mRequestCallback);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
        btn_login.setOnClickListener(this);
        iv_check.setOnClickListener(this);
        tv_check.setOnClickListener(this);
    }


    private void login(String account, String password) {
        if (!Utils.isMobileNO(account)) {
            Toast.makeText(this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
        tv_wrong_password.setVisibility(View.GONE);
        mLoginPresenter.doLogin(account, Utils.MD5(password));
    }

    RequestCallback mRequestCallback = new RequestCallback() {
        @Override
        public void success(Object o) {
            if (mProgressDialog != null && !isFinishing()) {
                mProgressDialog.dismiss();
            }
            Loger.d("o-----" + o.toString());
            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_wrong_password.setVisibility(View.GONE);
                }
            });
            UserBean use = (UserBean) o;
            EApplication.user = use;
//            Toast.makeText(LoginActivity.this, "isRemeberPass---" + isRemeberPass, Toast.LENGTH_SHORT).show();
            Loger.d("\"isRemeberPass---\" + isRemeberPass---"+"isRemeberPass---" + isRemeberPass);
            if (isRemeberPass) {
                /**如果勾选了记住密码，登录成功之后记住密码*/
                EApplication.isLoginSuccess = true;
                SharedPreferencesUtil.getInstance(mContext).saveBoolean(EApplication.LoginString, true);
                SharedPreferencesUtil.getInstance(mContext).saveUser(use);
            }
            startActivity(new Intent(LoginActivity.this, DetailListActivity.class));
            finish();
        }

        @Override
        public void fail(int code, final String msg) {
            if (mProgressDialog != null && !isFinishing()) {
                mProgressDialog.dismiss();
            }
            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_wrong_password.setVisibility(View.VISIBLE);
                    MyToast.show(mContext, msg);
                }
            });
        }
    };

    private void notifyCheck() {
        if (isRemeberPass) {
            /**选中状态*/
            iv_check.setImageResource(R.drawable.check_un);
            isRemeberPass = false;
        } else {
            iv_check.setImageResource(R.drawable.check);
            isRemeberPass = true;
        }
        Loger.d("notifyCheck---"+"isRemeberPass---" + isRemeberPass);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String userName = et_login.getText().toString();
                String userPassword = et_psd.getText().toString();
                if ("".equals(userName.trim())) {
                    MyToast.show(mContext, "请输入11位用户名");
                    return;
                }
                if ("".equals(userPassword.trim())) {
                    MyToast.show(mContext, "请输入密码");
                    return;
                }
                login(userName, userPassword);
                break;
            case R.id.iv_check:
            case R.id.tv_check:
                notifyCheck();
                break;
            default:
                break;
        }
    }
}
