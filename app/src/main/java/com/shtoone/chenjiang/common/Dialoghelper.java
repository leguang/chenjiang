package com.shtoone.chenjiang.common;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.socks.library.KLog;
import com.trycatch.mysnackbar.Prompt;
import com.trycatch.mysnackbar.TSnackbar;

import java.util.Collection;

/**
 * Author：leguang on 2016/11/11 0011 18:50
 * Email：langmanleguang@qq.com
 */
public class Dialoghelper {

    private static MaterialDialog.Builder builder;
    private static AlertDialog.Builder mBuilder;
    /**
     * Show the TSnackbar from top to down.
     */
    public static final int APPEAR_FROM_TOP_TO_DOWN = 0;

    /**
     * Show the TSnackbar from top to down.
     */
    public static final int APPEAR_FROM_BOTTOM_TO_TOP = 1;

    public static void showDialog(Context context, int icon, String title, String content, String positiveText, String negativeText, final Call call) {

        if (builder == null && context != null) {
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

        if (builder == null && context != null) {
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

    public static void dialog(Context context, int icon, String title, String content, String positiveText, String negativeText, final Call call) {
        if (mBuilder == null && context != null) {
            mBuilder = new AlertDialog.Builder(context);
        }
        mBuilder.setIcon(icon)
                .setTitle(title)
                .setMessage(content)
                .setNegativeButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        call.onNegative();
                    }
                })
                .setPositiveButton(negativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        call.onPositive();
                    }
                }).show();
    }

    public static void dialog(Context context, int icon, int title, int content, int positiveText, int negativeText, final Call call) {
        if (mBuilder == null && context != null) {
            mBuilder = new AlertDialog.Builder(context);
        }
        mBuilder.setIcon(icon)
                .setTitle(title)
                .setMessage(content)
                .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        call.onNegative();
                    }
                })
                .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        call.onPositive();
                    }
                }).show();
    }

    public static void errorSnackbar(View view, CharSequence text, int appearDirection) {
        TSnackbar.make(view, text, TSnackbar.LENGTH_SHORT, appearDirection)
                .setPromptThemBackground(Prompt.ERROR)
                .show();
    }

    public static void successSnackbar(View view, CharSequence text, int appearDirection) {
        TSnackbar.make(view, text, TSnackbar.LENGTH_SHORT, appearDirection)
                .setPromptThemBackground(Prompt.SUCCESS)
                .show();
    }

    public static void loadingSnackbar(View view, CharSequence text, int appearDirection) {
        TSnackbar.make(view, text, TSnackbar.LENGTH_INDEFINITE, appearDirection)
                .setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPromptThemBackground(Prompt.SUCCESS)
                .addIconProgressLoading(0, true, false)
                .show();
    }

    public static void warningSnackbar(View view, CharSequence text, int appearDirection) {
        TSnackbar.make(view, text, TSnackbar.LENGTH_SHORT, appearDirection)
                .setPromptThemBackground(Prompt.WARNING)
                .show();
    }

    public static void dialogList(Context context, int icon, String title, Collection collection, String positiveText, String negativeText, final ListCall listCall) {
        builder = new MaterialDialog.Builder(context);

        builder.content("");

        if (icon != 0) {
            builder.iconRes(icon);
            builder.limitIconToDefaultSize();
        }

        if (TextUtils.isEmpty(title)) {
            builder.title(title);
        }

        if (collection != null) {
            builder.items(collection);

        }

        if (TextUtils.isEmpty(positiveText)) {
            builder.positiveText(positiveText);
        }

        if (TextUtils.isEmpty(negativeText)) {
            builder.negativeText(negativeText);
        }

        if (listCall != null) {
            builder.itemsCallback(new MaterialDialog.ListCallback() {
                @Override
                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                    listCall.onSelection(dialog, view, which, text);
                }
            });
        }
        builder.show();
    }

    public static Dialog dialogList(Context context, int icon, int title, Collection collection, int positiveText, int negativeText, final ListCall listCall) {
        builder = new MaterialDialog.Builder(context);

        if (icon != 0) {
            builder.iconRes(icon);
            builder.limitIconToDefaultSize();
        }

        if (title != 0) {
            builder.title(title);
        }

        if (collection != null) {
            builder.items(collection);

        }

        if (positiveText != 0) {
            builder.positiveText(positiveText);
        }

        if (negativeText != 0) {
            builder.negativeText(negativeText);
        }

        if (listCall != null) {
            builder.itemsCallback(new MaterialDialog.ListCallback() {
                @Override
                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                    listCall.onSelection(dialog, view, which, text);
                }
            });
        }
        return builder.show();
    }


    public static Dialog progress(Context context, int title, int content, boolean isHorizontal) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .progress(true, 0)
                .cancelable(false)
                .progressIndeterminateStyle(isHorizontal)
                .build();
    }


    public static Dialog progressDialog(Context context, String msg, int progressType) {
        return new MaterialDialog.Builder(context)
                .content(msg)
                .progress(true, 0)
                .show();
    }

    public interface Call {
        void onNegative();

        void onPositive();
    }

    public interface ListCall {
        void onSelection(Dialog dialog, View itemView, int which, CharSequence text);
    }
}
