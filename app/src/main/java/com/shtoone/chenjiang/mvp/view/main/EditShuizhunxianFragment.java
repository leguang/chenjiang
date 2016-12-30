package com.shtoone.chenjiang.mvp.view.main;

import android.animation.Animator;
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
public class EditShuizhunxianFragment extends BaseFragment<ShuizhunxianContract.Presenter> implements ShuizhunxianContract.View {

    private static final String TAG = EditShuizhunxianFragment.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bianhao_edit_shuizhunxian_fragment)
    TextView tvBianhao;
    @BindView(R.id.spinner_route_type_edit_shuizhunxian_fragment)
    MaterialSpinner spinnerRouteType;
    @BindView(R.id.spinner_observe_type_edit_shuizhunxian_fragment)
    MaterialSpinner spinnerObserveType;
    @BindView(R.id.tv_gongdian_edit_shuizhunxian_fragment)
    TextView tvGongdian;
    @BindView(R.id.tv_jidian_edit_shuizhunxian_fragment)
    TextView tvJidian;
    @BindView(R.id.spinner_weather_edit_shuizhunxian_fragment)
    MaterialSpinner spinnerWeather;
    @BindView(R.id.spinner_staff_edit_shuizhunxian_fragment)
    MaterialSpinner spinnerStaff;
    @BindView(R.id.et_pressure_edit_shuizhunxian_fragment)
    EditText etPressure;
    @BindView(R.id.et_temperature_edit_shuizhunxian_fragment)
    EditText etTemperature;
    @BindView(R.id.tv_date_edit_shuizhunxian_fragment)
    TextView tvDate;
    @BindView(R.id.ll_edit_shuizhunxian_fragment)
    LinearLayout ll;
    @BindView(R.id.tv_cedian_edit_shuizhunxian_fragment)
    TextView tvCedian;
    private ImageView ivSave;
    private String[] arrayRouteType;
    private String[] arrayObserveType;
    private String[] arrayWeather;
    private YusheshuizhunxianData mYusheshuizhunxianData;

    public static EditShuizhunxianFragment newInstance(YusheshuizhunxianData mYusheshuizhunxianData) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.YUSHESHUIZHUNXIAN, mYusheshuizhunxianData);

        EditShuizhunxianFragment fragment = new EditShuizhunxianFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mYusheshuizhunxianData = (YusheshuizhunxianData) args.getSerializable(Constants.YUSHESHUIZHUNXIAN);
        }
    }

    @NonNull
    @Override
    protected ShuizhunxianContract.Presenter createPresenter() {
        return new ShuizhunxianPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_shuizhunxian, container, false);
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
        toolbar.setTitle("编辑水准线");
        initToolbarBackNavigation(toolbar);

        toolbar.inflateMenu(R.menu.menu_save);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:
                        ViewGroup viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();
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
        mYusheshuizhunxianData.setXiugaishijian(tvDate.getText().toString());
        mYusheshuizhunxianData.setStatus(Constants.status_daiceliang);
        int rowsAffected = mYusheshuizhunxianData.update(mYusheshuizhunxianData.getId());
        ViewGroup viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();
        if (rowsAffected > 0) {
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
        if (mYusheshuizhunxianData == null) {
            return;
        }
        //设置编号
        tvBianhao.setText(mYusheshuizhunxianData.getXianlubianhao());
        //设置日期
        if (TextUtils.isEmpty(mYusheshuizhunxianData.getXiugaishijian())) {
            tvDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            tvDate.setText(mYusheshuizhunxianData.getXiugaishijian());
        }
        //设置基点和测点
        if (!TextUtils.isEmpty(mYusheshuizhunxianData.getXianluxinxi())) {
            StringBuffer sbJidian = new StringBuffer();
            StringBuffer sbCedian = new StringBuffer();
            String[] arrayJidianAndCedian = mYusheshuizhunxianData.getXianluxinxi().split(",");
            for (String s : arrayJidianAndCedian) {
                if (s.contains("jd")) {
                    sbJidian.append(s + "/");
                } else if (s.contains("cd")) {
                    sbCedian.append(s + "/");
                }
            }
            tvJidian.setText(sbJidian.toString().substring(0, sbJidian.toString().length() - 1));
            tvCedian.setText(sbCedian.toString().substring(0, sbCedian.toString().length() - 1));
        }
        etPressure.setText(mYusheshuizhunxianData.getPressure());
        etTemperature.setText(mYusheshuizhunxianData.getTemperature());

        //请求司镜人员数据
        mPresenter.requestStaffData();
        Resources res = getResources();
        arrayRouteType = res.getStringArray(R.array.route_type);
        arrayObserveType = res.getStringArray(R.array.observe_type);
        arrayWeather = res.getStringArray(R.array.weather);

        spinnerRouteType.setItems(arrayRouteType);
        if (!TextUtils.isEmpty(mYusheshuizhunxianData.getRouteType())) {
            for (int i = 0; i < arrayRouteType.length; i++) {
                if (mYusheshuizhunxianData.getRouteType().equals(arrayRouteType[i])) {
                    spinnerRouteType.setSelectedIndex(i);
                }
            }
        }

        spinnerObserveType.setItems(arrayObserveType);
        if (!TextUtils.isEmpty(mYusheshuizhunxianData.getObserveType())) {
            for (int i = 0; i < arrayObserveType.length; i++) {
                if (mYusheshuizhunxianData.getObserveType().equals(arrayObserveType[i])) {
                    spinnerObserveType.setSelectedIndex(i);
                }
            }
        }

        spinnerWeather.setItems(arrayWeather);
        if (!TextUtils.isEmpty(mYusheshuizhunxianData.getWeather())) {
            for (int i = 0; i < arrayWeather.length; i++) {
                if (mYusheshuizhunxianData.getWeather().equals(arrayWeather[i])) {
                    spinnerWeather.setSelectedIndex(i);
                }
            }
        }
    }

    @Override
    public void responseData(Map<String, String[]> map) {
    }

    //响应司镜人员数据
    @Override
    public void responseStaffData(List<String> mStaffData) {
        if (mStaffData == null || mStaffData.size() == 0) {
            spinnerStaff.setItems("请先下载人员数据");
        } else {
            spinnerStaff.setItems(mStaffData);
            if (!TextUtils.isEmpty(mYusheshuizhunxianData.getStaff())) {
                spinnerStaff.setSelectedIndex(mStaffData.indexOf(mYusheshuizhunxianData.getStaff()));
            }
        }
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

                Animator mAnimator = ViewAnimationUtils.createCircularReveal(ll, cx, cy, finalRadius, 0);
                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimator.setDuration(618);
                mAnimator.start();
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

                Animator mAnimator = ViewAnimationUtils.createCircularReveal(ll, cx, cy, 0, finalRadius);
                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimator.setDuration(618);
                mAnimator.start();
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        revealClose();
        return super.onBackPressedSupport();
    }

    @OnClick(R.id.tv_cedian_edit_shuizhunxian_fragment)
    public void onClick() {
    }
}
