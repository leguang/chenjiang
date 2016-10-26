package com.shtoone.chenjiang.common;

import android.content.Context;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.utils.NetworkUtils;

import rx.Subscriber;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */

public abstract class RxSubscriber1<T> extends Subscriber<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog = true;


    public void showDialog() {
        this.showDialog = true;
    }

    public void hideDialog() {
        this.showDialog = true;
    }

    public RxSubscriber1(Context context, String msg, boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog = showDialog;
    }

    public RxSubscriber1(Context context) {
        this(context, "请稍后…", true);
    }

    public RxSubscriber1(Context context, boolean showDialog) {
        this(context, "请稍后…", showDialog);
    }

    @Override
    public void onCompleted() {
        if (showDialog){}
//            LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
//                LoadingDialog.showDialogForLoading((Activity) mContext, msg, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        if (showDialog)
//            LoadingDialog.cancelDialogForLoading();
        e.printStackTrace();
        //网络
        if (!NetworkUtils.isConnected(BaseApplication.mContext)) {
            _onError("当前网络不可用");
        }
//        //服务器
//        else if (e instanceof ServerException) {
//            _onError(e.getMessage());
//        }
//        //其它
//        else {
//            _onError(BaseApplication.getAppContext().getString(R.string.net_error));
//        }

        //这里模仿登录presenter里来完善
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
