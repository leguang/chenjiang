package com.shtoone.chenjiang.mvp.view.main.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.common.DialogHelper;
import com.shtoone.chenjiang.event.EventData;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.MeasureSpecificationData;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class ParamSettingFragment extends BaseFragment {
    private static final String TAG = ParamSettingFragment.class.getSimpleName();
    @BindView(R.id.et0_qianhoushijuleijicha_parammax_setting_fragment)
    EditText et0Qianhoushijuleijicha;
    @BindView(R.id.view0_qianhoushijuleijicha_param_setting_fragment)
    View view0Qianhoushijuleijicha;
    @BindView(R.id.tv0_qianhoushijuleijicha_param_setting_fragment)
    TextView tv0Qianhoushijuleijicha;
    @BindView(R.id.et1_shixianchangdu_parammax_setting_fragment)
    EditText et1Shixianchangdu;
    @BindView(R.id.view1_shixianchangdu_param_setting_fragment)
    View view1Shixianchangdu;
    @BindView(R.id.tv1_shixianchangdu_param_setting_fragment)
    TextView tv1Shixianchangdu;
    @BindView(R.id.et2_qianhoushijucha_parammax_setting_fragment)
    EditText et2Qianhoushijucha;
    @BindView(R.id.view2_qianhoushijucha_param_setting_fragment)
    View view2Qianhoushijucha;
    @BindView(R.id.tv2_qianhoushijucha_param_setting_fragment)
    TextView tv2Qianhoushijucha;
    @BindView(R.id.et3_liangcidushucha_parammax_setting_fragment)
    EditText et3Liangcidushucha;
    @BindView(R.id.view3liangcidushucha_param_setting_fragment)
    View view3liangcidushucha;
    @BindView(R.id.tv3_liangcidushucha_param_setting_fragment)
    TextView tv3Liangcidushucha;
    @BindView(R.id.et4_liangcigaochazhicha_parammax_setting_fragment)
    EditText et4Liangcigaochazhicha;
    @BindView(R.id.view4_liangcigaochazhicha_param_setting_fragment)
    View view4Liangcigaochazhicha;
    @BindView(R.id.tv4_liangcigaochazhicha_param_setting_fragment)
    TextView tv4Liangcigaochazhicha;
    @BindView(R.id.et5_shixiangaodu_parammax_setting_fragment)
    EditText et5Shixiangaodu;
    @BindView(R.id.view5_shixiangaodu_param_setting_fragment)
    View view5Shixiangaodu;
    @BindView(R.id.tv5_shixiangaodu_param_setting_fragment)
    TextView tv5Shixiangaodu;
    @BindView(R.id.et_shixianchangdu_parammin_setting_fragment)
    EditText etShixianchangduParammin;
    @BindView(R.id.et_qianhoushijucha_parammin_setting_fragment)
    EditText etQianhoushijuchaParammin;
    @BindView(R.id.et_shixiangaodu_parammin_setting_fragment)
    EditText etShixiangaoduParammin;
    @BindView(R.id.et_liangcidushucha_parammin_setting_fragment)
    EditText etLiangcidushuchaParammin;
    @BindView(R.id.et_liangcigaochazhicha_parammin_setting_fragment)
    EditText etLiangcigaochazhichaParammin;
    @BindView(R.id.et_qianhoushijuleijicha_parammin_setting_fragment)
    EditText etQianhoushijuleijichaParammin;
    @BindView(R.id.sv_login_activity)
    ScrollView svLoginActivity;
    private MeasureSpecificationData mStandardData;
    private MeasureSpecificationData mCurrentData;
    private int intRetry = 0;
    private List<View> listLine;
    private ViewGroup rootView;

    public static ParamSettingFragment newInstance() {
        return new ParamSettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_param_setting, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();
        initData();
    }

    private void initData() {
        mStandardData = DataSupport.findFirst(MeasureSpecificationData.class);
        mCurrentData = DataSupport.findLast(MeasureSpecificationData.class);
        if (mCurrentData == null) {
            DialogHelper.warningSnackbar(rootView, "数据读取失败，请点击重置", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
            return;
        }

        listLine = new ArrayList<>();
        listLine.add(view0Qianhoushijuleijicha);
        listLine.add(view1Shixianchangdu);
        listLine.add(view2Qianhoushijucha);
        listLine.add(view3liangcidushucha);
        listLine.add(view4Liangcigaochazhicha);
        listLine.add(view5Shixiangaodu);

        et0Qianhoushijuleijicha.setText(mCurrentData.getQianhoushijuleijichamax());
        etQianhoushijuleijichaParammin.setText(mCurrentData.getQianhoushijuleijichamin());
        et1Shixianchangdu.setText(mCurrentData.getShixianchangdumax());
        etShixianchangduParammin.setText(mCurrentData.getShixianchangdumin());
        et2Qianhoushijucha.setText(mCurrentData.getQianhoushijuchamax());
        etQianhoushijuchaParammin.setText(mCurrentData.getQianhoushijuchamin());
        et3Liangcidushucha.setText(mCurrentData.getLiangcidushuchamax());
        etLiangcidushuchaParammin.setText(mCurrentData.getLiangcidushuchamin());
        et4Liangcigaochazhicha.setText(mCurrentData.getLiangcigaochazhichamax());
        etLiangcigaochazhichaParammin.setText(mCurrentData.getLiangcigaochazhichamin());
        et5Shixiangaodu.setText(mCurrentData.getShixiangaodumax());
        etShixiangaoduParammin.setText(mCurrentData.getShixiangaodumin());

        et0Qianhoushijuleijicha.addTextChangedListener(new BaseTextWatcher(tv0Qianhoushijuleijicha, view0Qianhoushijuleijicha, "0", "6"));
        etQianhoushijuleijichaParammin.addTextChangedListener(new BaseTextWatcher(tv0Qianhoushijuleijicha, view0Qianhoushijuleijicha, "0", "6"));


        et1Shixianchangdu.addTextChangedListener(new BaseTextWatcher(tv1Shixianchangdu, view1Shixianchangdu, "3", "50"));
        etShixianchangduParammin.addTextChangedListener(new BaseTextWatcher(tv1Shixianchangdu, view1Shixianchangdu, "3", "50"));

        et2Qianhoushijucha.addTextChangedListener(new BaseTextWatcher(tv2Qianhoushijucha, view2Qianhoushijucha, "0", "1.5"));
        etQianhoushijuchaParammin.addTextChangedListener(new BaseTextWatcher(tv2Qianhoushijucha, view2Qianhoushijucha, "0", "1.5"));

        et3Liangcidushucha.addTextChangedListener(new BaseTextWatcher(tv3Liangcidushucha, view3liangcidushucha, "0", "0.4"));
        etLiangcidushuchaParammin.addTextChangedListener(new BaseTextWatcher(tv3Liangcidushucha, view3liangcidushucha, "0", "0.4"));

        et4Liangcigaochazhicha.addTextChangedListener(new BaseTextWatcher(tv4Liangcigaochazhicha, view4Liangcigaochazhicha, "0", "0.6"));
        etLiangcigaochazhichaParammin.addTextChangedListener(new BaseTextWatcher(tv4Liangcigaochazhicha, view4Liangcigaochazhicha, "0", "0.6"));

        et5Shixiangaodu.addTextChangedListener(new BaseTextWatcher(tv5Shixiangaodu, view5Shixiangaodu, "0.55", "2.80"));
        etShixiangaoduParammin.addTextChangedListener(new BaseTextWatcher(tv5Shixiangaodu, view5Shixiangaodu, "0.55", "2.80"));

    }

    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventData event) {
        if (event.position == Constants.EVENT_SAVE_PARAM) {
            save();

        } else if (event.position == Constants.EVENT_RESET_SECOND_CLASS) {
            reset();
        }
    }

    private void save() {
        boolean isCanSave = true;
        for (View view : listLine) {
            if (view.getVisibility() == View.VISIBLE) {
                isCanSave = false;
                break;
            }
        }

        if (isCanSave) {
            mCurrentData.setQianhoushijuleijichamax(et0Qianhoushijuleijicha.getText().toString().trim());
            mCurrentData.setQianhoushijuleijichamin(etQianhoushijuleijichaParammin.getText().toString().trim());

            mCurrentData.setShixianchangdumax(et1Shixianchangdu.getText().toString().trim());
            mCurrentData.setShixianchangdumin(etShixianchangduParammin.getText().toString().trim());

            mCurrentData.setQianhoushijuchamax(et2Qianhoushijucha.getText().toString().trim());
            mCurrentData.setQianhoushijuchamin(etQianhoushijuchaParammin.getText().toString().trim());

            mCurrentData.setLiangcidushuchamax(et3Liangcidushucha.getText().toString().trim());
            mCurrentData.setLiangcidushuchamin(etLiangcidushuchaParammin.getText().toString().trim());

            mCurrentData.setLiangcigaochazhichamax(et4Liangcigaochazhicha.getText().toString().trim());
            mCurrentData.setLiangcigaochazhichamin(etLiangcigaochazhichaParammin.getText().toString().trim());

            mCurrentData.setShixiangaodumax(et5Shixiangaodu.getText().toString().trim());
            mCurrentData.setShixiangaodumin(etShixiangaoduParammin.getText().toString().trim());


            if (mCurrentData.update(mCurrentData.getId()) > 0) {
                DialogHelper.successSnackbar(rootView, "恭喜，保存成功", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
            } else {
                DialogHelper.errorSnackbar(rootView, "抱歉，保存失败，请重试", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
            }

        } else {
            DialogHelper.warningSnackbar(rootView, "请按照标准范围设置参考值", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
        }
    }

    private void reset() {
        intRetry++;
        if (intRetry > 10) {
            DialogHelper.errorSnackbar(rootView, "重置失败", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
            return;
        }
        //先删除，保证表里面永远都只有两行数据即可。
        DataSupport.deleteAll(MeasureSpecificationData.class);
        mStandardData = new MeasureSpecificationData();
        mStandardData.setQianhoushijuleijichamax(6.0f + "");
        mStandardData.setQianhoushijuleijichamin(0 + "");
        mStandardData.setShixianchangdumax(50 + "");
        mStandardData.setShixianchangdumin(3 + "");
        mStandardData.setQianhoushijuchamax(1.5f + "");
        mStandardData.setQianhoushijuchamin(0 + "");
        mStandardData.setLiangcidushuchamax(0.4f + "");
        mStandardData.setLiangcidushuchamin(0 + "");
        mStandardData.setLiangcigaochazhichamax(0.6f + "");
        mStandardData.setLiangcigaochazhichamin(0 + "");
        mStandardData.setShixiangaodumax(2.8f + "");
        mStandardData.setShixiangaodumin(0.55f + "");

        mCurrentData = new MeasureSpecificationData();
        mCurrentData.setQianhoushijuleijichamax(6.0f + "");
        mCurrentData.setQianhoushijuleijichamin(0 + "");
        mCurrentData.setShixianchangdumax(50 + "");
        mCurrentData.setShixianchangdumin(3 + "");
        mCurrentData.setQianhoushijuchamax(1.5f + "");
        mCurrentData.setQianhoushijuchamin(0 + "");
        mCurrentData.setLiangcidushuchamax(0.4f + "");
        mCurrentData.setLiangcidushuchamin(0 + "");
        mCurrentData.setLiangcigaochazhichamax(0.6f + "");
        mCurrentData.setLiangcigaochazhichamin(0 + "");
        mCurrentData.setShixiangaodumax(2.8f + "");
        mCurrentData.setShixiangaodumin(0.55f + "");

        if (mStandardData.save() && mCurrentData.save()) {
            DialogHelper.successSnackbar(rootView, "重置成功", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
//            et0Qianhoushijuleijicha.setText(mStandardData.getQianhoushijuleijicha());
//            et1Shixianchangdu.setText(mStandardData.getShixianchangdu());
//            et2Qianhoushijucha.setText(mStandardData.getQianhoushijucha());
//            et3Liangcidushucha.setText(mStandardData.getLiangcidushucha());
//            et4Liangcigaochazhicha.setText(mStandardData.getLiangcigaochazhicha());
//            et5Shixiangaodu.setText(mStandardData.getShixiangaodu());

            et0Qianhoushijuleijicha.setText(mStandardData.getQianhoushijuleijichamax());
            etQianhoushijuleijichaParammin.setText(mStandardData.getQianhoushijuleijichamin());
            et1Shixianchangdu.setText(mStandardData.getShixianchangdumax());
            etShixianchangduParammin.setText(mStandardData.getShixianchangdumin());
            et2Qianhoushijucha.setText(mStandardData.getQianhoushijuchamax());
            etQianhoushijuchaParammin.setText(mStandardData.getQianhoushijuchamin());
            et3Liangcidushucha.setText(mStandardData.getLiangcidushuchamax());
            etLiangcidushuchaParammin.setText(mStandardData.getLiangcidushuchamin());
            et4Liangcigaochazhicha.setText(mStandardData.getLiangcigaochazhichamax());
            etLiangcigaochazhichaParammin.setText(mStandardData.getLiangcigaochazhichamin());
            et5Shixiangaodu.setText(mStandardData.getShixiangaodumax());
            etShixiangaoduParammin.setText(mStandardData.getShixiangaodumin());

        } else {
            //递归10层。
            reset();
        }
    }

    class BaseTextWatcher implements TextWatcher {
        TextView mTextView;
        View mView;
        String min;
        String max;

        public BaseTextWatcher(TextView mTextView, View mView, String min, String max) {
            this.mTextView = mTextView;
            this.mView = mView;
            this.min = min;
            this.max = max;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                if (TextUtils.isEmpty(s)) {
                    mView.setVisibility(View.VISIBLE);
                    mTextView.setVisibility(View.VISIBLE);
                    mTextView.setText("不能为空");
                } else if (Float.parseFloat(s.toString()) >= Float.parseFloat(min) && Float.parseFloat(s.toString()) <= Float.parseFloat(max)) {
                    mView.setVisibility(View.GONE);
                    mTextView.setVisibility(View.GONE);
                } else if (Float.parseFloat(s.toString()) < Float.parseFloat(min)) {
                    mView.setVisibility(View.VISIBLE);
                    mTextView.setVisibility(View.VISIBLE);
                    mTextView.setText("不能小于" + min);
                } else if (Float.parseFloat(s.toString()) > Float.parseFloat(max)) {
                    mView.setVisibility(View.VISIBLE);
                    mTextView.setVisibility(View.VISIBLE);
                    mTextView.setText("不能大于" + max);
                }
            } catch (NumberFormatException e) {
                mView.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.VISIBLE);
                mTextView.setText("请输入正确值");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
