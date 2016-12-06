package com.shtoone.chenjiang.mvp.view.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.ShuizhunxianContract;
import com.shtoone.chenjiang.mvp.presenter.ShuizhunxianPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.utils.DensityUtils;
import com.socks.library.KLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class AddShuizhunxianFragment extends BaseFragment<ShuizhunxianContract.Presenter> implements ShuizhunxianContract.View {

    private static final String TAG = AddShuizhunxianFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bianhao_add_shuizhunxian_fragment)
    TextView tvBianhao;
    @BindView(R.id.spinner_route_type_add_shuizhunxian_fragment)
    MaterialSpinner spinnerRouteType;
    @BindView(R.id.spinner_observe_type_add_shuizhunxian_fragment)
    MaterialSpinner spinnerObserveType;
    @BindView(R.id.tv_biaoduan_add_shuizhunxian_fragment)
    TextView tvBiaoduan;
    @BindView(R.id.tv_gongdian_add_shuizhunxian_fragment)
    TextView tvGongdian;
    @BindView(R.id.tv_jidian_add_shuizhunxian_fragment)
    TextView tvJidian;
    @BindView(R.id.spinner_weather_add_shuizhunxian_fragment)
    MaterialSpinner spinnerWeather;
    @BindView(R.id.spinner_staff_add_shuizhunxian_fragment)
    MaterialSpinner spinnerStaff;
    @BindView(R.id.et_pressure_add_shuizhunxian_fragment)
    EditText etPressure;
    @BindView(R.id.et_temperature_add_shuizhunxian_fragment)
    EditText etTemperature;
    @BindView(R.id.tv_date_add_shuizhunxian_fragment)
    TextView tvDate;
    @BindView(R.id.ll_add_shuizhunxian_fragment)
    LinearLayout ll;
    private ImageView iv;
    private String[] arrayRouteType;
    private String[] arrayObserveType;
    private String[] arrayWeather;
    private String strSaveStaff;
    private String strSaveWeather;
    private String strSaveRouteType;
    private String strSaveObserveType;
    private Integer[] arraySelectedGongdian;
    private Integer[] arraySelectedJidian;
    private MaterialDialog gongdianDialog;
    private MaterialDialog jidianDialog;

    public static AddShuizhunxianFragment newInstance() {
        return new AddShuizhunxianFragment();
    }

    @NonNull
    @Override
    protected ShuizhunxianContract.Presenter createPresenter() {
        return new ShuizhunxianPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_shuizhunxian, container, false);
        ((MainActivity) _mActivity).closeDrawer();
        //更改menu的view，提前在这里实例化
        iv = (ImageView) inflater.inflate(R.layout.menu_item_sync, null);
        ButterKnife.bind(this, view);
        getFragmentManager().beginTransaction()
                .show(getPreFragment())
                .commit();
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateBar(toolbar);
        revealShow();
        initToolbar();
        initData();
    }


    private void initToolbar() {
        toolbar.setTitle("新增水准线");
        initToolbarBackNavigation(toolbar);

        toolbar.inflateMenu(R.menu.menu_save);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:
                        startAnimation(item);


                        break;
                }
                return true;
            }
        });

//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                iv.clearAnimation();
//                toolbar.getMenu().clear();
//                toolbar.inflateMenu(R.menu.menu_save);
//            }
//        });
    }

    private void initData() {
        tvBianhao.setText("SD" + System.currentTimeMillis());
        tvDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (BaseApplication.mUserInfoBean != null && !TextUtils.isEmpty(BaseApplication.mUserInfoBean.getDept().getOrgName())) {
            tvBiaoduan.setText(BaseApplication.mUserInfoBean.getDept().getOrgName());
        } else {
            tvBiaoduan.setBackgroundResource(R.drawable.rect_bg_red_stroke_table);
        }
        mPresenter.start();

        Resources res = getResources();
        arrayRouteType = res.getStringArray(R.array.route_type);
        arrayObserveType = res.getStringArray(R.array.observe_type);
        arrayWeather = res.getStringArray(R.array.weather);


        spinnerRouteType.setItems(arrayRouteType);
        spinnerRouteType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                strSaveRouteType = item;
            }
        });

        spinnerObserveType.setItems(arrayObserveType);
        spinnerObserveType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                strSaveObserveType = item;
            }
        });

        spinnerWeather.setItems(arrayWeather);
        spinnerWeather.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                strSaveWeather = item;
            }
        });

        spinnerStaff.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                strSaveStaff = item;
            }
        });

    }

    @Override
    public void responseData(Map<String, String[]> map) {
        String[] arrayGongdianName = map.get(Constants.GONGDIAN);
        String[] arrayJidianName = map.get(Constants.JIDIAN);
        String[] arrayStaffName = map.get(Constants.STAFF);


        gongdianDialog = gongdianDialog(arrayGongdianName);

        jidianDialog = jidianDialog(arrayJidianName);

        if (arrayStaffName == null) {
            spinnerStaff.setItems("请先下载人员数据");
        } else {
            spinnerStaff.setItems(arrayStaffName);
        }
    }

    private MaterialDialog gongdianDialog(String[] arrayGongdianName) {
        return new MaterialDialog.Builder(_mActivity)
                .title(R.string.dialog_select_gongdian)
                .items(arrayGongdianName)
                .itemsCallbackMultiChoice(arraySelectedGongdian, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        arraySelectedGongdian = which;

                        for (int i = 0; i < which.length; i++) {
                            KLog.e(which[i]);
                            KLog.e(text[i]);
                        }
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.clearSelectedIndices();
                    }
                })
                .positiveText(R.string.dialog_positiveText)
                .neutralText(R.string.dialog_clear)
                .autoDismiss(false)
                .build();
    }

    private MaterialDialog jidianDialog(String[] arrayJidianName) {
        return new MaterialDialog.Builder(_mActivity)
                .title(R.string.dialog_select_jidian)
                .items(arrayJidianName)
                .itemsCallbackMultiChoice(arraySelectedJidian, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        arraySelectedJidian = which;
                        for (int i = 0; i < which.length; i++) {
                            KLog.e(which[i]);
                            KLog.e(text[i]);
                        }
                        return true;
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.clearSelectedIndices();
                    }
                })
                .positiveText(R.string.dialog_positiveText)
                .neutralText(R.string.dialog_clear)
                .autoDismiss(false)
                .build();
    }

    @Override
    public void responseStaffData(List<String> mStaffData) {

    }

    private void startAnimation(MenuItem item) {
        iv.clearAnimation();
        iv.setImageResource(R.drawable.ic_sync_white_24dp);
        item.setActionView(iv);
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "rotation", 360f, 0f);
        animator.setDuration(1000);
        animator.setRepeatMode(Animation.RESTART);
        animator.setRepeatCount(Animation.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //开启抽屉菜单，使其可以滑动弹出。
        KLog.e("onDestroyView");
        ((MainActivity) _mActivity).openDrawer();
    }

    private void revealClose() {
        ll.post(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    ll.setVisibility(View.INVISIBLE);
                    return;
                }

                int intTemp = DensityUtils.dp2px(BaseApplication.mContext, 26);
                int cx = ll.getRight() - intTemp;
                int cy = intTemp;

                int w = ll.getWidth();
                int h = ll.getHeight();

                // 勾股定理 & 进一法
                int finalRadius = (int) Math.hypot(w, h);

                Animator anim = ViewAnimationUtils.createCircularReveal(ll, cx, cy, finalRadius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ll.setVisibility(View.INVISIBLE);
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

    private void revealShow() {
        ll.post(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    ll.setVisibility(View.VISIBLE);
                    return;
                }

                int intTemp = DensityUtils.dp2px(BaseApplication.mContext, 26);
                int cx = ll.getRight() - intTemp;
                int cy = intTemp;
                KLog.e("intTemp::" + intTemp);
                int w = ll.getWidth();
                int h = ll.getHeight();

                // 勾股定理 & 进一法
                int finalRadius = (int) Math.hypot(w, h);

                Animator anim = ViewAnimationUtils.createCircularReveal(ll, cx, cy, 0, finalRadius);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
//                        ll.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
//                        ll.setVisibility(View.VISIBLE);
                    }
                });
                anim.setInterpolator(new AccelerateDecelerateInterpolator());
                anim.setDuration(618);
                anim.start();
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        revealClose();
        return super.onBackPressedSupport();
    }

    @OnClick({R.id.tv_gongdian_add_shuizhunxian_fragment, R.id.tv_jidian_add_shuizhunxian_fragment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_gongdian_add_shuizhunxian_fragment:
                gongdianDialog.show();
                break;
            case R.id.tv_jidian_add_shuizhunxian_fragment:
                jidianDialog.show();

                break;
        }
    }
}
