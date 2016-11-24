package com.shtoone.chenjiang.mvp.view.others;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.view.base.BaseFragment;
import com.shtoone.chenjiang.utils.AnimationUtils;
import com.shtoone.chenjiang.utils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class GuideFragment extends BaseFragment {

    private static final String TAG = GuideFragment.class.getSimpleName();
    @BindView(R.id.vp_guide_fragment)
    ViewPager vp;
    @BindView(R.id.bt_guide_fragment)
    Button bt;
    @BindView(R.id.fl_container_guide_fragment)
    FrameLayout fl;
    private int[] arrayResouces = {R.drawable.bg_welcome_0, R.drawable.bg_welcome_1, R.drawable.bg_welcome_2};
    private ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();

    public static GuideFragment newInstance() {
        return new GuideFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getFragmentManager().beginTransaction()
                .show(getPreFragment())
                .commit();
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fl.post(new Runnable() {
            @Override
            public void run() {
                AnimationUtils.show(fl);
            }
        });

        initData();
    }

    private void initData() {
        vp.setAdapter(new GuideViewPagerAdapter());

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int evaluate;
                switch (position) {
                    case 0:
                        bt.setVisibility(View.GONE);
                        evaluate = (Integer) mArgbEvaluator.evaluate(positionOffset, 0XFF76C5F0, 0XFF052CB8);
                        fl.setBackgroundColor(evaluate);
                        break;
                    case 1:
                        bt.setVisibility(View.GONE);
                        evaluate = (Integer) mArgbEvaluator.evaluate(positionOffset, 0XFF052CB8, 0XFFFFFFFF);
                        fl.setBackgroundColor(evaluate);
                        break;
                    case 2:
                        bt.setVisibility(View.VISIBLE);
                        break;
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }

    @OnClick(R.id.bt_guide_fragment)
    public void onClick() {
        SharedPreferencesUtils.put(_mActivity, Constants.ISFIRSTENTRY, false);
        // 页面跳转
        start(LoginFragment.newInstance(Constants.FROM_SPLASH));
    }

    class GuideViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (arrayResouces != null) {
                return arrayResouces.length;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = View.inflate(_mActivity, R.layout.item_vp_guide_fragment, null);
            FrameLayout fl_container = (FrameLayout) view.findViewById(R.id.fl_container_item_vp_guide_fragment);
            fl_container.setBackgroundResource(arrayResouces[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
}
