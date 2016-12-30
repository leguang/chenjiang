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
    @BindView(R.id.et0_qianhoushijuleijicha_param_setting_fragment)
    EditText et0Qianhoushijuleijicha;
    @BindView(R.id.view0_qianhoushijuleijicha_param_setting_fragment)
    View view0Qianhoushijuleijicha;
    @BindView(R.id.tv0_qianhoushijuleijicha_param_setting_fragment)
    TextView tv0Qianhoushijuleijicha;
    @BindView(R.id.et1_shixianchangdu_param_setting_fragment)
    EditText et1Shixianchangdu;
    @BindView(R.id.view1_shixianchangdu_param_setting_fragment)
    View view1Shixianchangdu;
    @BindView(R.id.tv1_shixianchangdu_param_setting_fragment)
    TextView tv1Shixianchangdu;
    @BindView(R.id.et2_qianhoushijucha_param_setting_fragment)
    EditText et2Qianhoushijucha;
    @BindView(R.id.view2_qianhoushijucha_param_setting_fragment)
    View view2Qianhoushijucha;
    @BindView(R.id.tv2_qianhoushijucha_param_setting_fragment)
    TextView tv2Qianhoushijucha;
    @BindView(R.id.et3_liangcidushucha_param_setting_fragment)
    EditText et3Liangcidushucha;
    @BindView(R.id.view3liangcidushucha_param_setting_fragment)
    View view3liangcidushucha;
    @BindView(R.id.tv3_liangcidushucha_param_setting_fragment)
    TextView tv3Liangcidushucha;
    @BindView(R.id.et4_liangcigaochazhicha_param_setting_fragment)
    EditText et4Liangcigaochazhicha;
    @BindView(R.id.view4_liangcigaochazhicha_param_setting_fragment)
    View view4Liangcigaochazhicha;
    @BindView(R.id.tv4_liangcigaochazhicha_param_setting_fragment)
    TextView tv4Liangcigaochazhicha;
    @BindView(R.id.et5_shixiangaodu_param_setting_fragment)
    EditText et5Shixiangaodu;
    @BindView(R.id.view5_shixiangaodu_param_setting_fragment)
    View view5Shixiangaodu;
    @BindView(R.id.tv5_shixiangaodu_param_setting_fragment)
    TextView tv5Shixiangaodu;
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

        et0Qianhoushijuleijicha.setText(mCurrentData.getQianhoushijuleijicha());
        et1Shixianchangdu.setText(mCurrentData.getShixianchangdu());
        et2Qianhoushijucha.setText(mCurrentData.getQianhoushijucha());
        et3Liangcidushucha.setText(mCurrentData.getLiangcidushucha());
        et4Liangcigaochazhicha.setText(mCurrentData.getLiangcigaochazhicha());
        et5Shixiangaodu.setText(mCurrentData.getShixiangaodu());

        et0Qianhoushijuleijicha.addTextChangedListener(new BaseTextWatcher(tv0Qianhoushijuleijicha, view0Qianhoushijuleijicha, "0", "6"));
        et1Shixianchangdu.addTextChangedListener(new BaseTextWatcher(tv1Shixianchangdu, view1Shixianchangdu, "3", "50"));
        et2Qianhoushijucha.addTextChangedListener(new BaseTextWatcher(tv2Qianhoushijucha, view2Qianhoushijucha, "0", "1.5"));
        et3Liangcidushucha.addTextChangedListener(new BaseTextWatcher(tv3Liangcidushucha, view3liangcidushucha, "0", "0.4"));
        et4Liangcigaochazhicha.addTextChangedListener(new BaseTextWatcher(tv4Liangcigaochazhicha, view4Liangcigaochazhicha, "0", "0.6"));
        et5Shixiangaodu.addTextChangedListener(new BaseTextWatcher(tv5Shixiangaodu, view5Shixiangaodu, "0.55", "2.80"));
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
            mCurrentData.setQianhoushijuleijicha(et0Qianhoushijuleijicha.getText().toString().trim());
            mCurrentData.setShixianchangdu(et1Shixianchangdu.getText().toString().trim());
            mCurrentData.setQianhoushijucha(et2Qianhoushijucha.getText().toString().trim());
            mCurrentData.setLiangcidushucha(et3Liangcidushucha.getText().toString().trim());
            mCurrentData.setLiangcigaochazhicha(et4Liangcigaochazhicha.getText().toString().trim());
            mCurrentData.setShixiangaodu(et5Shixiangaodu.getText().toString().trim());

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
        mStandardData.setQianhoushijuleijicha(6.0f + "");
        mStandardData.setShixianchangdu(50 + "");
        mStandardData.setQianhoushijucha(1.5f + "");
        mStandardData.setLiangcidushucha(0.4f + "");
        mStandardData.setLiangcigaochazhicha(0.6f + "");
        mStandardData.setShixiangaodu(0.55f + "");

        mCurrentData = new MeasureSpecificationData();
        mCurrentData.setQianhoushijuleijicha(6.0f + "");
        mCurrentData.setShixianchangdu(50 + "");
        mCurrentData.setQianhoushijucha(1.5f + "");
        mCurrentData.setLiangcidushucha(0.4f + "");
        mCurrentData.setLiangcigaochazhicha(0.6f + "");
        mCurrentData.setShixiangaodu(0.55f + "");

        if (mStandardData.save() && mCurrentData.save()) {
            DialogHelper.successSnackbar(rootView, "重置成功", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
            et0Qianhoushijuleijicha.setText(mStandardData.getQianhoushijuleijicha());
            et1Shixianchangdu.setText(mStandardData.getShixianchangdu());
            et2Qianhoushijucha.setText(mStandardData.getQianhoushijucha());
            et3Liangcidushucha.setText(mStandardData.getLiangcidushucha());
            et4Liangcigaochazhicha.setText(mStandardData.getLiangcigaochazhicha());
            et5Shixiangaodu.setText(mStandardData.getShixiangaodu());

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
            this.min = max;
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
