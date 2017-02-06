package com.shtoone.chenjiang.mvp.view.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.event.EventData;
import com.shtoone.chenjiang.mvp.contract.AddYusheshuizhunxianContract;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.AddYusheshuizhunxianPresenter;
import com.shtoone.chenjiang.mvp.view.adapter.Decoration;
import com.shtoone.chenjiang.mvp.view.adapter.ItemDragAdapter;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.utils.DensityUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class AddShuizhunxianFragment extends BaseFragment<AddYusheshuizhunxianContract.Presenter> implements AddYusheshuizhunxianContract.View {
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
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.bt_jidian_addyusheshuizhunxian_fragment)
    Button btJidian;
    @BindView(R.id.bt_cedian_addyusheshuizhunxian_fragment)
    Button btCedian;
    private ImageView ivSave;
    private String[] arrayRouteType;
    private String[] arrayObserveType;
    private String[] arrayWeather;
    private Integer[] cedianIndices = new Integer[0];
    private Integer[] jidianIndices = new Integer[0];
    private List<CharSequence> selectedJidian;
    private List<CharSequence> selectedCedian;
    private MaterialDialog jidianDialog;
    private MaterialDialog cedianDialog;
    private ViewGroup viewGroup;
    private ItemDragAdapter mAdapter;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    private ItemTouchHelper mItemTouchHelper;

    public static AddShuizhunxianFragment newInstance() {
        return new AddShuizhunxianFragment();
    }

    @NonNull
    @Override
    protected AddYusheshuizhunxianContract.Presenter createPresenter() {
        return new AddYusheshuizhunxianPresenter(this);
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
        initRecyclerView();
        initData();
        viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                KLog.e(TAG, "drag start");
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                KLog.e(TAG, "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                KLog.e(TAG, "drag end");
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
                mAdapter.notifyDataSetChanged();
            }
        };
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                KLog.e(TAG, "view swiped start: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                KLog.e(TAG, "View reset: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                }, 500);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                KLog.e(TAG, "View Swiped: " + pos);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                KLog.e(TAG, "onItemSwipeMoving: dX:" + dX + "dY:" + dY);
                KLog.e(TAG, isCurrentlyActive);

            }
        };

        mAdapter = new ItemDragAdapter();
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        //mItemDragAndSwipeCallback.setDragMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);
        mAdapter.enableDragItem(mItemTouchHelper);
        mAdapter.setOnItemDragListener(listener);
//        mRecyclerView.addItemDecoration(new GridItemDecoration(this ,R.drawable.list_divider));
        mRecyclerView.addItemDecoration(new Decoration(_mActivity, Decoration.VERTICAL_LIST));

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {

            }
        });
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

                        if (TextUtils.isEmpty(etTemperature.getText())) {
                            etTemperature.setBackgroundResource(R.drawable.rect_bg_red_stroke_table);
                            DialogHelper.warningSnackbar(viewGroup, "温度不能为空", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
                            return true;
                        } else {
                            if (Integer.parseInt(etTemperature.getText().toString()) > 40
                                    || Integer.parseInt(etTemperature.getText().toString()) < -10) {
                                etTemperature.setBackgroundResource(R.drawable.rect_bg_red_stroke_table);
                                DialogHelper.warningSnackbar(viewGroup, "温度值应在-10~40之间", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
                                return true;
                            }
                        }

                        if (TextUtils.isEmpty(etPressure.getText())) {
                            etPressure.setBackgroundResource(R.drawable.rect_bg_red_stroke_table);
                            DialogHelper.warningSnackbar(viewGroup, "气压不能为空", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
                            return true;
                        } else {
                            if (Integer.parseInt(etPressure.getText().toString()) > 1200
                                    || Integer.parseInt(etPressure.getText().toString()) < 700) {
                                etPressure.setBackgroundResource(R.drawable.rect_bg_red_stroke_table);
                                DialogHelper.warningSnackbar(viewGroup, "气压值应在700hPa~1200hPa之间", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
                                return true;
                            }
                        }

                        String strStaff = (String) spinnerStaff.getItems().get(spinnerStaff.getSelectedIndex());
                        if (strStaff.equals("请先下载人员数据")) {
                            spinnerStaff.setBackgroundResource(R.drawable.rect_bg_red_stroke_table);
                            DialogHelper.warningSnackbar(viewGroup, "司镜人员不能为空，请先下载", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
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
    public void responseJidian(String[] arrayJidianName) {
        if (arrayJidianName.length == 0) {
            DialogHelper.warningSnackbar(viewGroup, "未找到基点数据", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
            return;
        }
        jidianDialog = new MaterialDialog.Builder(_mActivity)
                .title(R.string.dialog_select_jidian)
                .items(arrayJidianName)
                .itemsCallbackMultiChoice(jidianIndices, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        jidianIndices = which;
                        selectedJidian = Arrays.asList(text);

                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < which.length; i++) {
                            sb.append(text[i]);
                            if (i != which.length - 1) {
                                sb.append("/");
                            }
                        }


                        mAdapter.addData(selectedJidian);

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
    public void responseCedian(String[] arrayCedianName) {
        if (arrayCedianName.length == 0) {
            DialogHelper.warningSnackbar(viewGroup, "未找到基点数据", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
            return;
        }
        cedianDialog = new MaterialDialog.Builder(_mActivity)
                .title(R.string.dialog_select_cedian)
                .items(arrayCedianName)
                .itemsCallbackMultiChoice(cedianIndices, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        cedianIndices = which;
                        selectedJidian = Arrays.asList(text);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < which.length; i++) {
                            sb.append(text[i]);
                            if (i != which.length - 1) {
                                sb.append("/");
                            }
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

    //响应司镜人员数据
    @Override
    public void responseStaff(List<String> mStaffData) {
        if (mStaffData == null || mStaffData.size() == 0) {
            spinnerStaff.setItems("请先下载人员数据");
        } else {
            spinnerStaff.setItems(mStaffData);
        }
    }


    @Override
    public void responseSave(int rowsAffected) {
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
        DialogHelper.errorSnackbar(viewGroup, "查询数据出错", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
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
                int finalRadius = (int) Math.hypot(w, h);

                Animator anim = ViewAnimationUtils.createCircularReveal(ll, cx, cy, 0, finalRadius);
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

    @OnClick({R.id.bt_jidian_addyusheshuizhunxian_fragment, R.id.bt_cedian_addyusheshuizhunxian_fragment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_jidian_addyusheshuizhunxian_fragment:
                if (jidianDialog == null) {
                    DialogHelper.warningSnackbar(viewGroup, "未找到基点数据", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
                    return;
                }
                jidianDialog.setSelectedIndices(jidianIndices);
                jidianDialog.show();
                break;
            case R.id.bt_cedian_addyusheshuizhunxian_fragment:

                for (CharSequence charSequence : mAdapter.getData()) {
                    KLog.e("charSequence::" + charSequence);
                }

//                if (cedianDialog == null) {
//                    DialogHelper.warningSnackbar(viewGroup, "未找到测点数据", DialogHelper.APPEAR_FROM_TOP_TO_DOWN);
//                    return;
//                }
//                cedianDialog.setSelectedIndices(cedianIndices);
//                cedianDialog.show();
                break;
        }
    }
}
