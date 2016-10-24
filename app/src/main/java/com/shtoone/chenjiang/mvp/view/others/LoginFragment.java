package com.shtoone.chenjiang.mvp.view.others;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.LoginContract;
import com.shtoone.chenjiang.mvp.presenter.LoginPresenter;
import com.shtoone.chenjiang.mvp.view.main.MainActivity;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.utils.AESCryptUtils;
import com.shtoone.chenjiang.utils.AnimationUtils;
import com.shtoone.chenjiang.utils.KeyBoardUtils;
import com.shtoone.chenjiang.utils.NetworkUtils;
import com.shtoone.chenjiang.utils.SharedPreferencesUtils;
import com.shtoone.chenjiang.widget.processbutton.iml.ActionProcessButton;

import java.security.GeneralSecurityException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class LoginFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View {

    private static final String TAG = LoginFragment.class.getSimpleName();
    @BindView(R.id.et_username_login_fragment)
    TextInputLayout etUsername;
    @BindView(R.id.et_password_login_fragment)
    TextInputLayout etPassword;
    @BindView(R.id.bt_login_fragment)
    ActionProcessButton btLogin;
    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.fab_login_fragment)
    FloatingActionButton fab;
    @BindView(R.id.cl_login_fragment)
    CoordinatorLayout cl;


    private String username;
    private String password;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getFragmentManager().beginTransaction()
                .show(getPreFragment())
                .commit();
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cl.post(new Runnable() {
            @Override
            public void run() {
                AnimationUtils.show(cl, 0, 1000);
            }
        });
        initData();
    }

    private void initData() {
        etUsername.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btLogin.setProgress(0);
                if (TextUtils.isEmpty(s)) {
                    etUsername.setError("用户名不能为空");
                    etUsername.setErrorEnabled(true);
                } else {
                    etUsername.setError("");
                    etUsername.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        etPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btLogin.setProgress(0);
                if (TextUtils.isEmpty(s)) {
                    etPassword.setError("密码不能为空");
                    etPassword.setErrorEnabled(true);
                } else {
                    etPassword.setError("");
                    etPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @OnClick(R.id.bt_login_fragment)
    public void onClick() {
        KeyBoardUtils.hideKeybord(btLogin, _mActivity);
        username = etUsername.getEditText().getText().toString().trim();
        password = etPassword.getEditText().getText().toString().trim();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            btLogin.setProgress(50);
            mPresenter.login(username, password);

        } else if (TextUtils.isEmpty(username)) {
            etUsername.setErrorEnabled(true);
            etUsername.setError("");
            etUsername.setError("用户名不能为空");
        } else if (TextUtils.isEmpty(password)) {
            etUsername.setErrorEnabled(true);
            etUsername.setError("");
            etPassword.setError("密码不能为空");
        }
    }

    @Override
    public void savaData() {
        try {
            String usernameEncrypted = AESCryptUtils.encrypt(Constants.ENCRYPT_KEY, username);
            String passwordEncrypted = AESCryptUtils.encrypt(Constants.ENCRYPT_KEY, password);
            SharedPreferencesUtils.put(_mActivity, Constants.USERNAME, usernameEncrypted);
            SharedPreferencesUtils.put(_mActivity, Constants.PASSWORD, passwordEncrypted);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setErrorMessage(String message) {
        //提示网络数据异常。1.可能是本机网络机场。2.可能是服务器异常。
        if (!NetworkUtils.isConnected(_mActivity)) {
            //提示网络异常
            btLogin.setErrorText("网络异常");
        } else {
            //服务器异常
            btLogin.setErrorText(message);
        }
        btLogin.setProgress(-1);
    }

    @Override
    public void setSuccessMessage() {
        //按钮提示成功信息
        btLogin.setProgress(100);
    }

    @Override
    public void go2Main() {
        btLogin.postDelayed(new Runnable() {
            @Override
            public void run() {
                _mActivity.startActivity(new Intent(_mActivity, MainActivity.class));
            }
        }, 300);

//        _mActivity.finish();
    }

    @OnClick(R.id.fab_login_fragment)
    public void onClick(View view) {
        fab.hide();
        _mActivity.startActivity(new Intent(_mActivity, RegisterActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        //返回到看见此fragment时，fab显示
        fab.show();
    }
}


