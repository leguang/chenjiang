package com.shtoone.chenjiang.mvp.view.others;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.view.base.BaseActivity;
import com.shtoone.chenjiang.utils.DeviceUtils;
import com.shtoone.chenjiang.utils.KeyBoardUtils;
import com.shtoone.chenjiang.widget.processbutton.iml.ActionProcessButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

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

        etRegisterCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bt.setProgress(0);
                if (TextUtils.isEmpty(s)) {
                    etRegisterCode.setError("注册码不能为空");
                    etRegisterCode.setErrorEnabled(true);
                } else {
                    etRegisterCode.setError("");
                    etRegisterCode.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

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

    @Override
    public void initPresenter() {
    }


    @OnClick({R.id.bt_register_activity, R.id.fab_register_activity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register_activity:
                KeyBoardUtils.hideKeybord(bt, this);
                String strRegisterCode = etRegisterCode.getEditText().getText().toString().trim();
                if (!TextUtils.isEmpty(strRegisterCode)) {
                    bt.setProgress(50);
                    bt.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bt.setProgress(100);
                            onBackPressedSupport();
                        }
                    }, 3500);
                } else if (TextUtils.isEmpty(strRegisterCode)) {
                    etRegisterCode.setErrorEnabled(true);
                    etRegisterCode.setError("");
                    etRegisterCode.setError("用户名不能为空");
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
}
