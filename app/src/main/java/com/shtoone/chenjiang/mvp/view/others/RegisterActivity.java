package com.shtoone.chenjiang.mvp.view.others;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.common.ToastUtils;
import com.shtoone.chenjiang.mvp.contract.RegisterContract;
import com.shtoone.chenjiang.mvp.model.entity.bean.RegisterBean;
import com.shtoone.chenjiang.mvp.presenter.RegisterPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseActivity;
import com.shtoone.chenjiang.utils.AESCryptUtils;
import com.shtoone.chenjiang.utils.DeviceUtils;
import com.shtoone.chenjiang.utils.KeyBoardUtils;
import com.shtoone.chenjiang.utils.SharedPreferencesUtils;
import com.shtoone.chenjiang.widget.processbutton.iml.ActionProcessButton;
import com.socks.library.KLog;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;

public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.View {

    @BindView(R.id.et_machine_code_register_activity)
    TextInputLayout etMachineCode;
    @BindView(R.id.et_register_code_register_activity)
    TextInputLayout etRegisterCode;
    @BindView(R.id.bt_register_activity)
    ActionProcessButton bt;
    @BindView(R.id.fab_register_activity)
    FloatingActionButton fab;
    @BindView(R.id.cv_register_activity)
    CardView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        revealShow();
        initData();

    }

    @NonNull
    @Override
    protected RegisterContract.Presenter createPresenter() {
        return new RegisterPresenter(this);
    }


    private void revealClose() {
        cv.post(new Runnable() {
            @Override
            public void run() {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
                    cv.setVisibility(View.INVISIBLE);
                    fab.hide();
                    return;
                }

                int cx = (cv.getLeft() + cv.getRight()) / 2;
                int cy = 0;

                int w = cv.getWidth();
                int h = cv.getHeight();

                // 勾股定理 & 进一法
                int finalRadius = (int) Math.hypot(w, h);

                Animator anim = ViewAnimationUtils.createCircularReveal(cv, cx, cy, finalRadius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        fab.hide();
                        cv.setVisibility(View.INVISIBLE);
                        finish();
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);

                    }
                });
                anim.setInterpolator(new AccelerateDecelerateInterpolator());
                anim.setDuration(618);
                anim.start();
            }
        });
    }

    private void initData() {
        etMachineCode.getEditText().setText(DeviceUtils.getIMEI(getApplicationContext()));

        String encryptRegisterCode = (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.REGISTER_CODE, "");
        KLog.e("encryptRegisterCode::" + encryptRegisterCode);
        if (!TextUtils.isEmpty(encryptRegisterCode)) {
            try {
                encryptRegisterCode = AESCryptUtils.decrypt(Constants.ENCRYPT_KEY, encryptRegisterCode);
                KLog.e("decode::" + encryptRegisterCode);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }

            etRegisterCode.getEditText().setText(encryptRegisterCode);
            bt.setText("您已注册");
            bt.setEnabled(false);

        } else {
            bt.setEnabled(true);
        }
    }

    private void revealShow() {
        cv.post(new Runnable() {
            @Override
            public void run() {
                fab.show();
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
                    cv.setVisibility(View.VISIBLE);
                    return;
                }

                int cx = (cv.getLeft() + cv.getRight()) / 2;
                int cy = 0;

                int w = cv.getWidth();
                int h = cv.getHeight();

                // 勾股定理 & 进一法
                int finalRadius = (int) Math.hypot(w, h);

                Animator anim = ViewAnimationUtils.createCircularReveal(cv, cx, cy, 0, finalRadius);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        cv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        cv.setVisibility(View.VISIBLE);
                    }
                });
                anim.setInterpolator(new AccelerateDecelerateInterpolator());
                anim.setDuration(1000);
                anim.start();
            }
        });
    }


    @OnClick({R.id.bt_register_activity, R.id.fab_register_activity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register_activity:
                KeyBoardUtils.hideKeybord(bt, this);
                String strMachineCode = etMachineCode.getEditText().getText().toString();
                String strphoneBrand = DeviceUtils.getPhoneBrand();
                String strphoneSysVersion = DeviceUtils.getOSVersion();
                String strphoneModel = DeviceUtils.getPhoneType();

                KLog.e(strMachineCode);
                KLog.e(strphoneBrand);
                KLog.e(strphoneSysVersion);
                KLog.e(strphoneModel);

                if (!TextUtils.isEmpty(strMachineCode)) {
                    mPresenter.register(strMachineCode, strphoneBrand, strphoneSysVersion, strphoneModel);
                } else {
                    etMachineCode.setErrorEnabled(true);
                    etMachineCode.setError("");
                    etMachineCode.setError("机器码获取失败");
                }
                break;
            case R.id.fab_register_activity:
                revealClose();
                break;
        }
    }

    @Override
    public void onBackPressedSupport() {
        revealClose();
    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable t) {

        if (t instanceof ConnectException) {
            setErrorMessage("网络异常");
        } else if (t instanceof HttpException) {
            setErrorMessage("服务器异常");
        } else if (t instanceof SocketTimeoutException) {
            setErrorMessage("连接超时");
        } else if (t instanceof JSONException) {
            setErrorMessage("解析异常");
        } else {
            setErrorMessage("数据异常");
        }
    }

    public void setErrorMessage(String message) {
        bt.setErrorText(message);
        bt.setProgress(-1);
    }

    @Override
    public void showLoading() {
        bt.setProgress(50);
    }

    @Override
    public void registerSuccessfully(RegisterBean mRegisterBean) {

        etRegisterCode.getEditText().setText(mRegisterBean.getRegCode());
        bt.setProgress(100);
        bt.postDelayed(new Runnable() {
            @Override
            public void run() {
                revealClose();
            }
        }, 618);

        ToastUtils.showSuccessToast(getApplicationContext(), "恭喜！注册成功请登录");

        //保存注册码到本地
        String encryptRegisterCode = "";
        try {
            encryptRegisterCode = AESCryptUtils.encrypt(Constants.ENCRYPT_KEY, mRegisterBean.getRegCode());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        SharedPreferencesUtils.put(getApplicationContext(), Constants.REGISTER_CODE, encryptRegisterCode);
    }

    @Override
    public void registerFailed(String message) {
        setErrorMessage(message);
    }
}
