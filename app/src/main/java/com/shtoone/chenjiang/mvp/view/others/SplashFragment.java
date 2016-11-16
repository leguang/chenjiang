package com.shtoone.chenjiang.mvp.view.others;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.SplashContract;
import com.shtoone.chenjiang.mvp.presenter.SplashPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.mvp.view.main.MainActivity;
import com.shtoone.chenjiang.utils.SharedPreferencesUtils;
import com.shtoone.chenjiang.widget.CircleTextProgressbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class SplashFragment extends BaseFragment<SplashContract.Presenter> implements SplashContract.View {

    private static final String TAG = SplashFragment.class.getSimpleName();
    //目的是为了判断网络请求时，用户是否退出
    private boolean isExit;
    private boolean isFirstentry;

    @BindView(R.id.ctp_skip)
    CircleTextProgressbar ctpSkip;

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @NonNull
    @Override
    protected SplashContract.Presenter createPresenter() {
        return new SplashPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ctpSkip.setOutLineColor(Color.TRANSPARENT);
        ctpSkip.setInCircleColor(Color.parseColor("#99000000"));
        ctpSkip.setProgressColor(Color.RED);
        ctpSkip.setProgressLineWidth(3);
        ctpSkip.setTimeMillis(Constants.DEFAULT_TIMEOUT * 1000);
        ctpSkip.setText(Constants.DEFAULT_TIMEOUT + "s");
        ctpSkip.setCountdownProgressListener(1, new CircleTextProgressbar.OnCountdownProgressListener() {
            int intTime = Constants.DEFAULT_TIMEOUT;
            double temp = 100.0 / Constants.DEFAULT_TIMEOUT;

            @Override
            public void onProgress(int what, int progress) {
                if (((int) (progress % temp)) == 0) {
                    if (ctpSkip != null) {
                        ctpSkip.setText((--intTime) + "s");
                    }
                }
            }
        });
        ctpSkip.start();
        initData();
    }

    private void initData() {
        isFirstentry = (boolean) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.ISFIRSTENTRY, true);
        //严格按照流程来，Presenter层的代码应该在View层代码的必要数据加载完成之后才调用
        mPresenter.checkLogin();
        mPresenter.checkUpdate();
    }

    @Override
    public void go2LoginOrGuide() {
        //目的是为了判断网络请求时，用户是否退出
        if (isExit) {
            return;
        }
        if (isFirstentry) {
//            start(GuideFragment.newInstance());
            _mActivity.startActivity(new Intent(_mActivity, MainActivity.class));

        } else {
//            start(LoginFragment.newInstance());
            _mActivity.startActivity(new Intent(_mActivity, MainActivity.class));
        }
    }

    @Override
    public void go2Main() {
        if (isExit) {
            return;
        }
        _mActivity.startActivity(new Intent(_mActivity, MainActivity.class));
    }

    @Override
    public void onDestroyView() {
        isExit = true;
        //以下操作是为了防止内存泄漏
        if (ctpSkip != null) {
            ctpSkip.stop();
            ctpSkip.setCountdownProgressListener(1, null);
            ctpSkip = null;
        }
        super.onDestroyView();
    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable t) {

    }

    @Override
    public void showLoading() {

    }

}
