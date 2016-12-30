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
import com.shtoone.chenjiang.common.DialogHelper;
import com.shtoone.chenjiang.event.EventData;
import com.shtoone.chenjiang.mvp.contract.ShuizhunxianContract;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.ShuizhunxianPresenter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.utils.DensityUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

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
    private ImageView ivSave;
    private String[] arrayRouteType;
    private String[] arrayObserveType;
    private String[] arrayWeather;
    private Integer[] gongdianIndices = new Integer[0];
    private Integer[] jidianIndices = new Integer[0];
    private CharSequence[] arraySelectedGongdian;
    private CharSequence[] arraySelectedJidian;
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
        ivSave = (ImageView) inflater.inflate(R.layout.menu_item_sync, null);
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
                        ViewGroup viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();

                        if (TextUtils.isEmpty(tvGongdian.getText())) {
                            etTemperature.setBackgroundResource(R.drawable.rect_bg_red_stroke_table);
                            DialogHelper.warningSnackbar(viewGroup, "工点不能为空", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
                            return true;
                        }
                        if (TextUtils.isEmpty(tvJidian.getText())) {
                            etTemperature.setBackgroundResource(R.drawable.rect_bg_red_stroke_table);
                            DialogHelper.warningSnackbar(viewGroup, "基点不能为空", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
                            return true;
                        }

                        if (TextUtils.isEmpty(etTemperature.getText())) {
                            etTemperature.setBackgroundResource(R.drawable.rect_bg_red_stroke_table);
                            DialogHelper.warningSnackbar(viewGroup, "温度不能为空", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
                            return true;
                        }
                        if (TextUtils.isEmpty(etPressure.getText())) {
                            etPressure.setBackgroundResource(R.drawable.rect_bg_red_stroke_table);
                            DialogHelper.warningSnackbar(viewGroup, "气压不能为空", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
                            return true;
                        }

                        String strStaff = (String) spinnerStaff.getItems().get(spinnerStaff.getSelectedIndex());
                        if (strStaff.equals("请先下载人员数据")) {
                            spinnerStaff.setBackgroundResource(R.drawable.rect_bg_red_stroke_table);
                            DialogHelper.warningSnackbar(viewGroup, "司镜人员不能为空", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
                            return true;
                        }
                        //恢复原始样式
                        etTemperature.setBackgroundResource(R.drawable.rect_bg_stroke_table);
                        etPressure.setBackgroundResource(R.drawable.rect_bg_stroke_table);
                        startAnimation(item);
                        save();
                        break;
                }
                return true;
            }
        });
    }

    private void save() {
        YusheshuizhunxianData mYusheshuizhunxianData = new YusheshuizhunxianData();
        mYusheshuizhunxianData.setXianlubianhao(tvBianhao.getText().toString());
        String strRouteType = (String) spinnerRouteType.getItems().get(spinnerRouteType.getSelectedIndex());
        mYusheshuizhunxianData.setRouteType(strRouteType);
        String strObserveType = (String) spinnerObserveType.getItems().get(spinnerObserveType.getSelectedIndex());
        mYusheshuizhunxianData.setObserveType(strObserveType);
        String strWeather = (String) spinnerWeather.getItems().get(spinnerWeather.getSelectedIndex());
        mYusheshuizhunxianData.setWeather(strWeather);
        String strStaff = (String) spinnerStaff.getItems().get(spinnerStaff.getSelectedIndex());
        mYusheshuizhunxianData.setStaff(strStaff);
        mYusheshuizhunxianData.setTemperature(etTemperature.getText().toString());
        mYusheshuizhunxianData.setPressure(etPressure.getText().toString());
        mYusheshuizhunxianData.setChuangjianshijian(tvDate.getText().toString());
        mYusheshuizhunxianData.setXiugaishijian(tvDate.getText().toString());
        mYusheshuizhunxianData.setStatus(Constants.status_daiceliang);
        mYusheshuizhunxianData.setXianlumingcheng(tvBiaoduan.getText().toString() + tvBianhao.getText().toString());
        mYusheshuizhunxianData.setBiaoshi(Constants.biaoshi_app);
        if (BaseApplication.mUserInfoBean != null && !TextUtils.isEmpty(BaseApplication.mUserInfoBean.getUserFullName())) {
            mYusheshuizhunxianData.setShezhiren(BaseApplication.mUserInfoBean.getUserFullName());
            mYusheshuizhunxianData.setDepartId(BaseApplication.mUserInfoBean.getDept().getOrgId());
        }

        ViewGroup viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();
        if (mYusheshuizhunxianData.save()) {
            DialogHelper.successSnackbar(viewGroup, "恭喜，保存成功", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
            EventBus.getDefault().post(new EventData(Constants.EVENT_REFRESH));
        } else {
            DialogHelper.errorSnackbar(viewGroup, "保存失败，请重新保存", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
        }

        //清除动画，恢复初始状态。
        ivSave.clearAnimation();
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_save);
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
        spinnerObserveType.setItems(arrayObserveType);
        spinnerWeather.setItems(arrayWeather);
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
                .itemsCallbackMultiChoice(gongdianIndices, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        gongdianIndices = which;
                        arraySelectedGongdian = text;
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < which.length; i++) {
                            sb.append(text[i]);
                            if (i != which.length - 1) {
                                sb.append("/");
                            }
                        }
                        tvGongdian.setText(sb.toString());
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .positiveText(R.string.dialog_positiveText)
                .negativeText(R.string.dialog_negativeText)
                .build();
    }

    private MaterialDialog jidianDialog(String[] arrayJidianName) {
        return new MaterialDialog.Builder(_mActivity)
                .title(R.string.dialog_select_jidian)
                .items(arrayJidianName)
                .itemsCallbackMultiChoice(jidianIndices, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        jidianIndices = which;
                        arraySelectedJidian = text;

                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < which.length; i++) {
                            sb.append(text[i]);
                            if (i != which.length - 1) {
                                sb.append("/");
                            }
                        }
                        tvJidian.setText(sb.toString());
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .positiveText(R.string.dialog_positiveText)
                .negativeText(R.string.dialog_negativeText)
                .build();
    }

    @Override
    public void responseStaffData(List<String> mStaffData) {
    }

    private void startAnimation(MenuItem item) {
        ivSave.clearAnimation();
        ivSave.setImageResource(R.drawable.ic_sync_white_24dp);
        item.setActionView(ivSave);
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivSave, "rotation", 360f, 0f);
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
                gongdianDialog.setSelectedIndices(gongdianIndices);
                gongdianDialog.show();
                break;
            case R.id.tv_jidian_add_shuizhunxian_fragment:
                jidianDialog.setSelectedIndices(jidianIndices);
                jidianDialog.show();
                break;
        }
    }
}
