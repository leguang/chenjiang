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
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.model.entity.db.MeasureSpecificationData;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.socks.library.KLog;

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
        initData();
    }

    private void initData() {

        MeasureSpecificationData mMeasureSpecificationData = new MeasureSpecificationData();
        mMeasureSpecificationData.setQianhoushijuleijicha(6.0f);
        mMeasureSpecificationData.setShixianchangdu(50);
        mMeasureSpecificationData.setQianhoushijucha(1.5f);
        mMeasureSpecificationData.setLiangcidushucha(0.4f);
        mMeasureSpecificationData.setLiangcigaochazhicha(0.6f);
        mMeasureSpecificationData.setShixiangaodu(0.55f);
        boolean isis = mMeasureSpecificationData.save();

        KLog.e(isis);

        et0Qianhoushijuleijicha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    view0Qianhoushijuleijicha.setVisibility(View.VISIBLE);
                    tv0Qianhoushijuleijicha.setVisibility(View.VISIBLE);
                    tv0Qianhoushijuleijicha.setText("不能为空");
                } else if (Float.parseFloat(s.toString()) >= 0 && Float.parseFloat(s.toString()) <= 6.0) {
                    view0Qianhoushijuleijicha.setVisibility(View.GONE);
                    tv0Qianhoushijuleijicha.setVisibility(View.GONE);
                } else if (Float.parseFloat(s.toString()) < 0) {
                    view0Qianhoushijuleijicha.setVisibility(View.VISIBLE);
                    tv0Qianhoushijuleijicha.setVisibility(View.VISIBLE);
                    tv0Qianhoushijuleijicha.setText("不能小于0");
                } else if (Float.parseFloat(s.toString()) > 6.0) {
                    view0Qianhoushijuleijicha.setVisibility(View.VISIBLE);
                    tv0Qianhoushijuleijicha.setVisibility(View.VISIBLE);
                    tv0Qianhoushijuleijicha.setText("不能小于6.0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et1Shixianchangdu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    view1Shixianchangdu.setVisibility(View.VISIBLE);
                    tv1Shixianchangdu.setVisibility(View.VISIBLE);
                    tv1Shixianchangdu.setText("不能为空");
                } else if (Float.parseFloat(s.toString()) >= 0 && Float.parseFloat(s.toString()) <= 6.0) {
                    view1Shixianchangdu.setVisibility(View.GONE);
                    tv1Shixianchangdu.setVisibility(View.GONE);
                } else if (Float.parseFloat(s.toString()) < 0) {
                    view1Shixianchangdu.setVisibility(View.VISIBLE);
                    tv1Shixianchangdu.setVisibility(View.VISIBLE);
                    tv1Shixianchangdu.setText("不能小于0");
                } else if (Float.parseFloat(s.toString()) > 6.0) {
                    view1Shixianchangdu.setVisibility(View.VISIBLE);
                    tv1Shixianchangdu.setVisibility(View.VISIBLE);
                    tv1Shixianchangdu.setText("不能小于6.0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et2Qianhoushijucha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    view2Qianhoushijucha.setVisibility(View.VISIBLE);
                    tv2Qianhoushijucha.setVisibility(View.VISIBLE);
                    tv2Qianhoushijucha.setText("不能为空");
                } else if (Float.parseFloat(s.toString()) >= 0 && Float.parseFloat(s.toString()) <= 6.0) {
                    view2Qianhoushijucha.setVisibility(View.GONE);
                    tv2Qianhoushijucha.setVisibility(View.GONE);
                } else if (Float.parseFloat(s.toString()) < 0) {
                    view2Qianhoushijucha.setVisibility(View.VISIBLE);
                    tv2Qianhoushijucha.setVisibility(View.VISIBLE);
                    tv2Qianhoushijucha.setText("不能小于0");
                } else if (Float.parseFloat(s.toString()) > 6.0) {
                    view2Qianhoushijucha.setVisibility(View.VISIBLE);
                    tv2Qianhoushijucha.setVisibility(View.VISIBLE);
                    tv2Qianhoushijucha.setText("不能小于6.0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et3Liangcidushucha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    view3liangcidushucha.setVisibility(View.VISIBLE);
                    tv3Liangcidushucha.setVisibility(View.VISIBLE);
                    tv3Liangcidushucha.setText("不能为空");
                } else if (Float.parseFloat(s.toString()) >= 0 && Float.parseFloat(s.toString()) <= 6.0) {
                    view3liangcidushucha.setVisibility(View.GONE);
                    tv3Liangcidushucha.setVisibility(View.GONE);
                } else if (Float.parseFloat(s.toString()) < 0) {
                    view3liangcidushucha.setVisibility(View.VISIBLE);
                    tv3Liangcidushucha.setVisibility(View.VISIBLE);
                    tv3Liangcidushucha.setText("不能小于0");
                } else if (Float.parseFloat(s.toString()) > 6.0) {
                    view3liangcidushucha.setVisibility(View.VISIBLE);
                    tv3Liangcidushucha.setVisibility(View.VISIBLE);
                    tv3Liangcidushucha.setText("不能小于6.0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et4Liangcigaochazhicha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    view4Liangcigaochazhicha.setVisibility(View.VISIBLE);
                    tv4Liangcigaochazhicha.setVisibility(View.VISIBLE);
                    tv4Liangcigaochazhicha.setText("不能为空");
                } else if (Float.parseFloat(s.toString()) >= 0 && Float.parseFloat(s.toString()) <= 6.0) {
                    view4Liangcigaochazhicha.setVisibility(View.GONE);
                    tv4Liangcigaochazhicha.setVisibility(View.GONE);
                } else if (Float.parseFloat(s.toString()) < 0) {
                    view4Liangcigaochazhicha.setVisibility(View.VISIBLE);
                    tv4Liangcigaochazhicha.setVisibility(View.VISIBLE);
                    tv4Liangcigaochazhicha.setText("不能小于0");
                } else if (Float.parseFloat(s.toString()) > 6.0) {
                    view4Liangcigaochazhicha.setVisibility(View.VISIBLE);
                    tv4Liangcigaochazhicha.setVisibility(View.VISIBLE);
                    tv4Liangcigaochazhicha.setText("不能小于6.0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et5Shixiangaodu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    view5Shixiangaodu.setVisibility(View.VISIBLE);
                    tv5Shixiangaodu.setVisibility(View.VISIBLE);
                    tv5Shixiangaodu.setText("不能为空");
                } else if (Float.parseFloat(s.toString()) >= 0 && Float.parseFloat(s.toString()) <= 6.0) {
                    view5Shixiangaodu.setVisibility(View.GONE);
                    tv5Shixiangaodu.setVisibility(View.GONE);
                } else if (Float.parseFloat(s.toString()) < 0) {
                    view5Shixiangaodu.setVisibility(View.VISIBLE);
                    tv5Shixiangaodu.setVisibility(View.VISIBLE);
                    tv5Shixiangaodu.setText("不能小于0");
                } else if (Float.parseFloat(s.toString()) > 6.0) {
                    view5Shixiangaodu.setVisibility(View.VISIBLE);
                    tv5Shixiangaodu.setVisibility(View.VISIBLE);
                    tv5Shixiangaodu.setText("不能小于6.0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }
}
