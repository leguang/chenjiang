package com.shtoone.chenjiang.mvp.presenter.about;


import com.shtoone.chenjiang.mvp.contract.about.AboutFragmentViewPagerContract;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;

/**
 * Created by Administrator on 2016/11/22.
 */
public class AboutFragmentViewPagerPresenter extends BasePresenter<AboutFragmentViewPagerContract.View> implements AboutFragmentViewPagerContract.Presenter {

    public AboutFragmentViewPagerPresenter(AboutFragmentViewPagerContract.View mView) {
        super(mView);
    }


    public void refresh() {
        String URL = createRefreshULR();

        //联网获取数据
        //还没有判断url，用户再判断
//        HttpHelper.getInstance().initService().loadAbout
//        HttpUtils.getRequest(URL, new HttpUtils.HttpListener() {
//            @Override
//            public void onSuccess(String response) {
//                KLog.json(response);
//                onRefreshSuccess(response);
//            }
//
//            @Override
//            public void onFailed(VolleyError error) {
//                KLog.d(error);
//                onRefreshFailed(error);
 //           }
 //       });
    }

    public String createRefreshULR() {
        return null;
    }


    @Override
    public void start() {

    }
}
