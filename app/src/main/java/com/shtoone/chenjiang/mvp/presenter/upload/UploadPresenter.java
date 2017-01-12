package com.shtoone.chenjiang.mvp.presenter.upload;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.upload.UploadContract;
import com.shtoone.chenjiang.mvp.model.HttpHelper;
import com.shtoone.chenjiang.mvp.model.entity.bean.CedianInfoBean;
import com.shtoone.chenjiang.mvp.model.entity.bean.ShuizhunxianBean;
import com.shtoone.chenjiang.mvp.model.entity.bean.YusheshuizhunxianInfoBean;
import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.shtoone.chenjiang.mvp.model.entity.db.DuanmianData;
import com.shtoone.chenjiang.mvp.model.entity.db.GongdianData;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;
import com.socks.library.KLog;

import org.json.JSONException;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class UploadPresenter extends BasePresenter<UploadContract.View> implements UploadContract.Presenter {

    private static final String TAG = UploadPresenter.class.getSimpleName();
    private Gson mGson;

    public UploadPresenter(UploadContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
        requestShuizhunxianData(0);
    }

    @Override
    public void requestShuizhunxianData(final int pagination) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<YusheshuizhunxianData>>() {

                    @Override
                    public void call(Subscriber<? super List<YusheshuizhunxianData>> subscriber) {
                        List<YusheshuizhunxianData> mYusheshuizhunxianData = null;
                        try {
                            //分页查询每次查询PAGE_SIZE条，从0开始。
                            mYusheshuizhunxianData = DataSupport.where("status = ? ", Constants.status_daishanchu)
                                    .order("id").limit(Constants.PAGE_SIZE)
                                    .offset(pagination * Constants.PAGE_SIZE)
                                    .find(YusheshuizhunxianData.class);

                            subscriber.onNext(mYusheshuizhunxianData);
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                        //做此判断的目的是为了不让最后onCompleted()调用的showContent()遮住了showEmpty()的显示。
                        if (mYusheshuizhunxianData != null && mYusheshuizhunxianData.size() > 0) {
                            subscriber.onCompleted();
                        }
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<List<YusheshuizhunxianData>>() {
                            @Override
                            public void _onNext(List<YusheshuizhunxianData> mYusheshuizhunxianData) {
                                getView().responseShuizhunxianData(mYusheshuizhunxianData, pagination);
                            }
                        })
        );
    }

    @Override
    public void upload(List<YusheshuizhunxianData> listShuizhunxian) {
        mRxManager.add(
                Observable.from(listShuizhunxian).map(new Func1<YusheshuizhunxianData, ShuizhunxianBean>() {
                    @Override
                    public ShuizhunxianBean call(YusheshuizhunxianData mYusheshuizhunxianData) {
                        ShuizhunxianData mShuizhunxianData = DataSupport.where("yusheshuizhunxianID = ? and chuangjianshijian = ? "
                                , String.valueOf(mYusheshuizhunxianData.getId()), mYusheshuizhunxianData.getXiugaishijian())
                                .findFirst(ShuizhunxianData.class);

                        List<CezhanData> listCezhan = DataSupport.where("shuizhunxianID = ? ", String.valueOf(mShuizhunxianData.getId()))
                                .order("number").find(CezhanData.class);


                        return new ShuizhunxianBean(mShuizhunxianData, listCezhan);
                    }
                }).toList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<ShuizhunxianBean>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<ShuizhunxianBean> shuizhunxianBeen) {
                                KLog.e("shuizhunxianBeen::" + shuizhunxianBeen.size());

                                if (mGson == null) {
                                    mGson = new Gson();
                                }
                                String str = mGson.toJson(shuizhunxianBeen, ArrayList.class);
                                KLog.e("shuizhunxianBeen::" + str);


                                //**************************************************************************************************************************************

                                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);

                                try {
                                    long timestamp = System.currentTimeMillis();
                                    String time = formatter.format(new Date());
                                    String fileName = time + "-" + timestamp + ".log";
                                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                        String path = Environment.getExternalStorageDirectory().getPath() + "/SHTW/";
                                        Log.d(TAG, "path=" + path);
                                        File dir = new File(path);
                                        if (!dir.exists()) {
                                            dir.mkdirs();
                                        }
                                        FileOutputStream fos = new FileOutputStream(path + fileName);
                                        fos.write(str.getBytes());
                                        fos.close();
                                    }
                                } catch (Exception e) {
                                    Log.e(TAG, "an error occured while writing file...", e);
                                }


                                //*******************************************************************************************************************************************************


                                HttpHelper.getInstance().initService().upload(str).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                KLog.e("responseBody:" + response.body().string());
                                                getView().onUploaded(Constants.UPLAND_SUCCESS, "恭喜，上传成功");


                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            getView().onUploaded(Constants.UPLAND_FAIL, "上传失败，请重试");
                                        }


                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        t.printStackTrace();
                                        KLog.e(t);

                                        if (t instanceof ConnectException) {
                                            getView().onUploaded(Constants.UPLAND_FAIL, "网络异常，请重试");
                                        } else if (t instanceof HttpException) {
                                            getView().onUploaded(Constants.UPLAND_FAIL, "服务器异常");
                                        } else if (t instanceof SocketTimeoutException) {
                                            getView().onUploaded(Constants.UPLAND_FAIL, "连接超时，请重试");
                                        } else if (t instanceof JSONException) {
                                            getView().onUploaded(Constants.UPLAND_FAIL, "解析异常，请重试");
                                        } else {
                                            getView().onUploaded(Constants.UPLAND_FAIL, "数据异常");
                                        }
                                    }
                                });

                            }
                        })
        );
    }
}
