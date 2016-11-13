package com.shtoone.chenjiang.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Author：leguang on 2016/11/11 0011 18:50
 * Email：langmanleguang@qq.com
 */
public class Dialoghelper {

    private static MaterialDialog.Builder builder;

    public static void showDialog(Context context, int icon, String title, String content, String positiveText, String negativeText, final Call call) {

        if (context != null) {
            builder = new MaterialDialog.Builder(context);
        }

        if (icon != 0) {
            builder.iconRes(icon);
            builder.limitIconToDefaultSize();
        }

        if (TextUtils.isEmpty(title)) {
            builder.title(title);
        }

        if (TextUtils.isEmpty(content)) {
            builder.content(content);
        }

        if (TextUtils.isEmpty(positiveText)) {
            builder.positiveText(positiveText);
        }

        if (TextUtils.isEmpty(negativeText)) {
            builder.negativeText(negativeText);
        }

        if (call != null) {
            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    call.onPositive();
                }
            });

            builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    call.onNegative();
                }
            });
        }

        builder.show();
    }

    public static void showDialog(Context context, int icon, int title, int content, int positiveText, int negativeText, final Call call) {

        if (context != null) {
            builder = new MaterialDialog.Builder(context);
        }

        if (icon != 0) {
            builder.iconRes(icon);
            builder.limitIconToDefaultSize();
        }

        if (title != 0) {
            builder.title(title);
        }

        if (content != 0) {
            builder.content(content);
        }

        if (positiveText != 0) {
            builder.positiveText(positiveText);
        }

        if (negativeText != 0) {
            builder.negativeText(negativeText);
        }

        if (call != null) {
            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    call.onPositive();
                }
            });

            builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    call.onNegative();
                }
            });
        }

        builder.show();
    }

    public interface Call {
        public void onNegative();

        public void onPositive();
    }
}
