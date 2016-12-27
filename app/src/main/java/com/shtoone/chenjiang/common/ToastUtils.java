package com.shtoone.chenjiang.common;

import android.content.Context;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class ToastUtils {
    private static final String TAG = ToastUtils.class.getSimpleName();

    public static Toast mToast;

    public static void showToast(Context mContext, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    public static void showSuccessToast(Context mContext, String msg) {
        TastyToast.makeText(mContext, msg, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
    }

    public static void showInfoToast(Context mContext, String msg) {
        TastyToast.makeText(mContext, msg, TastyToast.LENGTH_SHORT, TastyToast.INFO);
    }

    public static void showDefaultToast(Context mContext, String msg) {
        TastyToast.makeText(mContext, msg, TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
    }

    public static void showWarningToast(Context mContext, String msg) {
        TastyToast.makeText(mContext, msg, TastyToast.LENGTH_SHORT, TastyToast.WARNING);
    }

    public static void showErrorToast(Context mContext, String msg) {
        TastyToast.makeText(mContext, msg, TastyToast.LENGTH_SHORT, TastyToast.ERROR);
    }
}
