package com.shtoone.chenjiang.mvp.view.main.upload;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class DetailLeftFragment extends BaseFragment {
    private static final String TAG = DetailLeftFragment.class.getSimpleName();
    @BindView(R.id.tv_bianhao_measure_left_fragment)
    TextView tvBianhao;
    @BindView(R.id.tv_route_type_measure_left_fragment)
    TextView tvRouteType;
    @BindView(R.id.tv_observe_type_measure_left_fragment)
    TextView tvObserveType;
    @BindView(R.id.tv_biaoduan_measure_left_fragment)
    TextView tvBiaoduan;
    @BindView(R.id.tv_gongdian_measure_left_fragment)
    TextView tvGongdian;
    @BindView(R.id.tv_jidian_measure_left_fragment)
    TextView tvJidian;
    @BindView(R.id.tv_weather_measure_left_fragment)
    TextView tvWeather;
    @BindView(R.id.tv_staff_measure_left_fragment)
    TextView tvStaff;
    @BindView(R.id.tv_pressure_measure_left_fragment)
    TextView tvPressure;
    @BindView(R.id.tv_temperature_measure_left_fragment)
    TextView tvTemperature;
    @BindView(R.id.tv_date_measure_left_fragment)
    TextView tvDate;
    @BindView(R.id.tv_brand_measure_left_fragment)
    TextView tvBrand;
    @BindView(R.id.tv_xinghao_measure_left_fragment)
    TextView tvXinghao;
    @BindView(R.id.tv_shebeihao_measure_left_fragment)
    TextView tvShebeihao;
    private ShuizhunxianData mShuizhunxianData;

    public static DetailLeftFragment newInstance(ShuizhunxianData mShuizhunxianData) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.YUSHESHUIZHUNXIAN, mShuizhunxianData);

        DetailLeftFragment fragment = new DetailLeftFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mShuizhunxianData = (ShuizhunxianData) args.getSerializable(Constants.YUSHESHUIZHUNXIAN);
        }
    }

    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measure_left, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        if (BaseApplication.mUserInfoBean != null && BaseApplication.mUserInfoBean.getDept() != null
                && !TextUtils.isEmpty(BaseApplication.mUserInfoBean.getDept().getOrgName())) {
            tvBiaoduan.setText(BaseApplication.mUserInfoBean.getDept().getOrgName());
        }

        if (mShuizhunxianData != null) {
            tvBianhao.setText(mShuizhunxianData.getBiaoshi());
            tvRouteType.setText(mShuizhunxianData.getRouteType());
            tvObserveType.setText(mShuizhunxianData.getObserveType());
            tvJidian.setText(getJidian());
            tvWeather.setText(mShuizhunxianData.getWeather());
            tvStaff.setText(mShuizhunxianData.getStaff());
            tvPressure.setText(mShuizhunxianData.getPressure());
            tvTemperature.setText(mShuizhunxianData.getTemperature());
            tvDate.setText(mShuizhunxianData.getXiugaishijian());
            tvBianhao.setText(mShuizhunxianData.getBiaoshi());
        }
    }

    private String getJidian() {
        StringBuffer sbJidian = new StringBuffer();
        String[] arrayJidianAndCedian = mShuizhunxianData.getXianluxinxi().split(",");
        for (String s : arrayJidianAndCedian) {
            if (s.contains("jd")) {
                sbJidian.append(s + "/");
            }
        }
        return sbJidian.toString().substring(0, sbJidian.toString().length() - 1);
    }
}
