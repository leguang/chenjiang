package com.shtoone.chenjiang.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.event.NoMultiClickListener;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class PageStateLayout extends FrameLayout {

    private int emptyView, errorView, netErrorView, loadingView;
    private OnClickListener onRetryClickListener, onNetErrorClickListener;
    public boolean isShowEmpty = false, isShowError = false, isShowNetError = false, isShowLoading = false, isShowContent = true;

    public PageStateLayout(Context context) {
        this(context, null);
    }

    public PageStateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PageStateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PageStateLayout, 0, 0);
        try {
            emptyView = a.getResourceId(R.styleable.PageStateLayout_emptyView, R.layout.page_state_empty_view);
            errorView = a.getResourceId(R.styleable.PageStateLayout_errorView, R.layout.page_state_error_view);
            netErrorView = a.getResourceId(R.styleable.PageStateLayout_netErrorView, R.layout.page_state_net_error_view);
            loadingView = a.getResourceId(R.styleable.PageStateLayout_loadingView, R.layout.page_state_loading_view);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(emptyView, this, true);
            inflater.inflate(errorView, this, true);
            inflater.inflate(netErrorView, this, true);
            inflater.inflate(loadingView, this, true);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0; i < getChildCount() - 1; i++) {
            getChildAt(i).setVisibility(GONE);
        }

        findViewById(R.id.btn_empty_retry).setOnClickListener(new NoMultiClickListener() {

            @Override
            public void onNoMultiClick(View view) {
                if (null != onRetryClickListener) {
                    onRetryClickListener.onClick(view);
                }
            }
        });

        findViewById(R.id.btn_error_retry).setOnClickListener(new NoMultiClickListener() {
            @Override
            public void onNoMultiClick(View view) {
                if (null != onRetryClickListener) {
                    onRetryClickListener.onClick(view);
                }
            }
        });

        findViewById(R.id.btn_net_error_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onNetErrorClickListener) {
                    onNetErrorClickListener.onClick(view);
                }
            }
        });
    }

    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
    }

    public void setOnNetErrorClickListener(OnClickListener onNetErrorClickListener) {
        this.onNetErrorClickListener = onNetErrorClickListener;
    }

    public void showEmpty() {
        if (isShowEmpty) {
            return;
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 0) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
        isShowEmpty = true;
        isShowError = false;
        isShowNetError = false;
        isShowLoading = false;
        isShowContent = false;
    }

    public void showError() {
        if (isShowError) {
            return;
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 1) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
        isShowEmpty = false;
        isShowError = true;
        isShowNetError = false;
        isShowLoading = false;
        isShowContent = false;
    }

    public void showNetError() {
        if (isShowNetError) {
            return;
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 2) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
        isShowEmpty = false;
        isShowError = false;
        isShowNetError = true;
        isShowLoading = false;
        isShowContent = false;
    }

    public void showLoading() {
        if (isShowLoading) {
            return;
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 3) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
        isShowEmpty = false;
        isShowError = false;
        isShowNetError = false;
        isShowLoading = true;
        isShowContent = false;
    }

    public void showContent() {
        if (isShowContent) {
            return;
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 4) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
        isShowEmpty = false;
        isShowError = false;
        isShowNetError = false;
        isShowLoading = false;
        isShowContent = true;
    }
}
