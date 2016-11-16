package com.shtoone.chenjiang.mvp.view.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
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

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.AddShuizhunxianContract;
import com.shtoone.chenjiang.mvp.presenter.AddShuizhunxianPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.utils.DensityUtils;
import com.shtoone.chenjiang.utils.ToastUtils;
import com.socks.library.KLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class AddShuizhunxianFragment extends BaseFragment<AddShuizhunxianContract.Presenter> implements AddShuizhunxianContract.View {

    private static final String TAG = AddShuizhunxianFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bianhao_add_shuizhunxian_fragment)
    TextView tvBianhao;
    @BindView(R.id.spinner_route_type_add_shuizhunxian_fragment)
    MaterialSpinner spinnerRouteType;
    @BindView(R.id.spinner_observe_type_add_shuizhunxian_fragment)
    MaterialSpinner spinnerObserveType;
    @BindView(R.id.spinner_biaoduan_add_shuizhunxian_fragment)
    MaterialSpinner spinnerBiaoduan;
    @BindView(R.id.spinner_gongdian_add_shuizhunxian_fragment)
    MaterialSpinner spinnerGongdian;
    @BindView(R.id.spinner_jidians_add_shuizhunxian_fragment)
    MaterialSpinner spinnerJidians;
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

    private static final String[] ANDROID_VERSIONS = {
            "1", "2", "3", "4", "5", "5", "5", "5", "5", "5", "5", "5", "5", "5"
    };
    private String[] arrayRouteType;
    private String[] arrayObserveType;
    private String[] arrayWeather;

    public static AddShuizhunxianFragment newInstance() {
        return new AddShuizhunxianFragment();
    }

    @NonNull
    @Override
    protected AddShuizhunxianContract.Presenter createPresenter() {
        return new AddShuizhunxianPresenter(this);
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
        toolbar.setTitle("添加水准线");
        initToolbarBackNavigation(toolbar);

        toolbar.inflateMenu(R.menu.menu_save);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:
                        KLog.e("111111111111");
                        startAnimation(item);
                        int index2 = spinnerJidians.getSelectedIndex();
                        KLog.e("responseDataindex2::" + index2);

                        int index1 = spinnerGongdian.getSelectedIndex();
                        KLog.e("responseDataindex1::" + index1);

                        int index3 = spinnerStaff.getSelectedIndex();
                        KLog.e("responseDataindex3::" + index3);

                        ToastUtils.showToast(getContext(), "保存中……………………");
                        break;
                }
                return true;
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KLog.e("iv^^^^^^^^^^^^^");
                iv.clearAnimation();
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.menu_save);
            }
        });
    }

    private void initData() {
        tvDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        mPresenter.start();
        Resources res = getResources();
        arrayRouteType = res.getStringArray(R.array.route_type);
        arrayObserveType = res.getStringArray(R.array.observe_type);
        arrayWeather = res.getStringArray(R.array.weather);


        spinnerRouteType.setItems(arrayRouteType);
        spinnerRouteType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, item, Snackbar.LENGTH_LONG).show();
                KLog.e(arrayRouteType[position]);
            }
        });

        spinnerObserveType.setItems(arrayObserveType);
        spinnerObserveType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, item, Snackbar.LENGTH_LONG).show();
                KLog.e(arrayObserveType[position]);
            }
        });

        spinnerWeather.setItems(arrayWeather);
        spinnerWeather.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, item, Snackbar.LENGTH_LONG).show();
                KLog.e(arrayWeather[position]);
            }
        });

        if (BaseApplication.mUserInfoBean.getDept() == null) {
            //还是要把闪屏页的登录检测，用来检测本地登录
        }
        spinnerBiaoduan.setItems(BaseApplication.mUserInfoBean.getDept().getOrgName());
        int index1 = spinnerGongdian.getSelectedIndex();
        int index2 = spinnerJidians.getSelectedIndex();
        int index3 = spinnerStaff.getSelectedIndex();

        KLog.e("index1::" + index1);
        KLog.e("index2::" + index2);
        KLog.e("index3::" + index3);

    }

    @Override
    public void responseData(Map<String, String[]> map) {
        String[] arrayGongdianName = map.get(Constants.GONGDIAN);
        String[] arrayJidianName = map.get(Constants.JIDIAN);
        String[] arrayStaffName = map.get(Constants.STAFF);

        if (arrayGongdianName == null) {
            spinnerGongdian.setItems("请先下载工点数据");
        } else {
            KLog.e("arrayGongdianName长度：：" + arrayGongdianName.length);
            spinnerGongdian.setItems(arrayGongdianName);
            int index1 = spinnerGongdian.getSelectedIndex();
            KLog.e("responseDataindex1::" + index1);
        }

        if (arrayJidianName == null) {
            spinnerJidians.setItems("请先下载基点数据");
        } else {
            KLog.e("arrayJidianName长度：：" + arrayJidianName.length);
            spinnerJidians.setItems(arrayJidianName);
            int index2 = spinnerJidians.getSelectedIndex();
            KLog.e("responseDataindex2::" + index2);
        }


        if (arrayStaffName == null) {
            spinnerStaff.setItems("请先下载人员数据");
        } else {
            spinnerStaff.setItems(arrayStaffName);
            KLog.e("arrayStaffName长度：：" + arrayStaffName.length);
            int index3 = spinnerStaff.getSelectedIndex();
            KLog.e("responseDataindex3::" + index3);
        }
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
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
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
}
