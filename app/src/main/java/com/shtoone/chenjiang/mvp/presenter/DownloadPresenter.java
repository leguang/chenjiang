package com.shtoone.chenjiang.mvp.presenter;

import com.shtoone.chenjiang.mvp.contract.DownloadContract;
import com.shtoone.chenjiang.mvp.model.HttpHelper;
import com.shtoone.chenjiang.mvp.model.bean.CedianData;
import com.shtoone.chenjiang.mvp.model.bean.CedianInfoBean;
import com.shtoone.chenjiang.mvp.model.bean.DuanmianData;
import com.shtoone.chenjiang.mvp.model.bean.DuanmianInfoBean;
import com.shtoone.chenjiang.mvp.model.bean.GongdianData;
import com.shtoone.chenjiang.mvp.model.bean.GongdianInfoBean;
import com.shtoone.chenjiang.mvp.model.bean.JidianBean;
import com.shtoone.chenjiang.mvp.model.bean.JidianData;
import com.shtoone.chenjiang.mvp.model.bean.StaffBean;
import com.shtoone.chenjiang.mvp.model.bean.StaffData;
import com.shtoone.chenjiang.mvp.model.bean.YusheshuizhunxianData;
import com.shtoone.chenjiang.mvp.model.bean.YusheshuizhunxianInfoBean;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;
import com.socks.library.KLog;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class DownloadPresenter extends BasePresenter<DownloadContract.View> implements DownloadContract.Presenter {
    private static final String TAG = DownloadPresenter.class.getSimpleName();
    private int intSum;
    private int intIndex;


    public DownloadPresenter(DownloadContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {

    }

    @Override
    public void downloadAll() {

    }

    @Override
    public void downloadGongdian(String userID) {
        DataSupport.deleteAll(GongdianData.class);

        mRxManager.add(
                HttpHelper.getInstance().initService().gongdianInfoDownload(userID)
                        .flatMap(new Func1<GongdianInfoBean, Observable<GongdianInfoBean.GdxxsBean>>() {
                            @Override
                            public Observable<GongdianInfoBean.GdxxsBean> call(GongdianInfoBean gongdianInfoBean) {
                                KLog.e("111111111flatMap::" + Thread.currentThread().getName());
                                intIndex = 0;
                                intSum = gongdianInfoBean.getGdxxs().size();
                                KLog.e("intGongdianSize::" + intSum);
                                return Observable.from(gongdianInfoBean.getGdxxs());
                            }
                        })
                        .map(new Func1<GongdianInfoBean.GdxxsBean, Boolean>() {
                            @Override
                            public Boolean call(GongdianInfoBean.GdxxsBean gdxxsBean) {
                                KLog.e("22222222222map::" + Thread.currentThread().getName());
                                GongdianData mGongdianData = new GongdianData();
                                mGongdianData.setSsgzw(gdxxsBean.getSsgzw());
                                mGongdianData.setGdpx(gdxxsBean.getGdpx());
                                mGongdianData.setKslc(gdxxsBean.getKslc());
                                mGongdianData.setDepartid(gdxxsBean.getDepartid());
                                mGongdianData.setZxlc(gdxxsBean.getZxlc());
                                mGongdianData.setBz(gdxxsBean.getBz());
                                mGongdianData.setGdlx(gdxxsBean.getGdlx());
                                mGongdianData.setJldepartid(gdxxsBean.getJldepartid());
                                mGongdianData.setGdbm(gdxxsBean.getGdbm());
                                mGongdianData.setGdid(gdxxsBean.getId());
                                mGongdianData.setGdxx(gdxxsBean.getGdxx());
                                mGongdianData.setJslc(gdxxsBean.getJslc());
                                mGongdianData.setGdmc(gdxxsBean.getGdmc());
                                mGongdianData.setJlbd(gdxxsBean.getJlbd());
                                mGongdianData.setCd(gdxxsBean.getCd());
                                return mGongdianData.save();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Boolean>() {

                            @Override
                            public void onNext(Boolean aBoolean) {
                                intIndex += 1;
                                getView().setGongdianMessage(aBoolean, intSum, intIndex);
                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().showError(e);
                            }

                            @Override
                            public void onCompleted() {
                                getView().onGongdianCompleted();
                            }
                        })
        );
    }

    @Override
    public void downloadDuanmian() {
        DataSupport.deleteAll(DuanmianData.class);

        List<GongdianData> mGongdianData = DataSupport.findAll(GongdianData.class);
        intSum = mGongdianData.size();
        intIndex = 0;
        mRxManager.add(
                Observable.from(mGongdianData).flatMap(new Func1<GongdianData, Observable<DuanmianInfoBean>>() {
                    @Override
                    public Observable<DuanmianInfoBean> call(GongdianData gongdianData) {
                        KLog.e("gongdianData.getGdid()::" + gongdianData.getGdid());
                        intIndex += 1;
                        KLog.e("intIndex::" + intIndex);
                        return HttpHelper.getInstance().initService().duanmianInfoDownload(gongdianData.getGdid());
                    }
                }).flatMap(new Func1<DuanmianInfoBean, Observable<DuanmianInfoBean.LjdmsBean>>() {
                    @Override
                    public Observable<DuanmianInfoBean.LjdmsBean> call(DuanmianInfoBean duanmianInfoBean) {
                        return Observable.from(duanmianInfoBean.getLjdms());
                    }
                }).map(new Func1<DuanmianInfoBean.LjdmsBean, Boolean>() {

                    @Override
                    public Boolean call(DuanmianInfoBean.LjdmsBean ljdmsBean) {
                        DuanmianData mDuanmianData = new DuanmianData();
                        mDuanmianData.setChulishendu(ljdmsBean.getChulishendu());
                        mDuanmianData.setDuanmianname(ljdmsBean.getDuanmianname());
                        mDuanmianData.setGuancezhuangtai(ljdmsBean.getGuancezhuangtai());
                        mDuanmianData.setDingmiantiantuhigh(ljdmsBean.getDingmiantiantuhigh());
                        mDuanmianData.setGouzhuwuname(ljdmsBean.getGouzhuwuname());
                        mDuanmianData.setGongdianid(ljdmsBean.getGongdianid());
                        mDuanmianData.setBeizhu(ljdmsBean.getBeizhu());
                        mDuanmianData.setShifouguoduduan(ljdmsBean.getShifouguoduduan());
                        mDuanmianData.setDmid(ljdmsBean.getId());
                        mDuanmianData.setYasuocenghoudu(ljdmsBean.getYasuocenghoudu());
                        mDuanmianData.setDijichulifangshi(ljdmsBean.getDijichulifangshi());
                        mDuanmianData.setGouzhuwutype(ljdmsBean.getGouzhuwutype());
                        mDuanmianData.setSuoshuqiaoduntaibianhao(ljdmsBean.getSuoshuqiaoduntaibianhao());
                        mDuanmianData.setQiaoduntaihigh(ljdmsBean.getQiaoduntaihigh());
                        mDuanmianData.setShigonglicheng(ljdmsBean.getShigonglicheng());
                        mDuanmianData.setShigonglichengguanhao(ljdmsBean.getShigonglichengguanhao());
                        mDuanmianData.setShejiyuyatuhigh(ljdmsBean.getShejiyuyatuhigh());
                        mDuanmianData.setTianwahigh(ljdmsBean.getTianwahigh());
                        mDuanmianData.setShigongchangduanlianjie(ljdmsBean.getShigongchangduanlianjie());
                        return mDuanmianData.save();
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Boolean>() {

                            @Override
                            public void onNext(Boolean aBoolean) {
                                getView().setDuanmianMessage(aBoolean, intSum, intIndex);
                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().showError(e);
                            }

                            @Override
                            public void onCompleted() {
                                getView().onDuanmianCompleted();
                            }
                        })
        );

    }

    @Override
    public void downloadCedian() {
        DataSupport.deleteAll(CedianData.class);

        List<DuanmianData> mDuanmianData = DataSupport.findAll(DuanmianData.class);
        intSum = mDuanmianData.size();
        intIndex = 0;
        mRxManager.add(
                Observable.from(mDuanmianData).flatMap(new Func1<DuanmianData, Observable<CedianInfoBean>>() {
                    @Override
                    public Observable<CedianInfoBean> call(DuanmianData DuanmianData) {
                        KLog.e("gongdianData.getGdid()::" + DuanmianData.getDmid());
                        intIndex += 1;
                        KLog.e("intIndex::" + intIndex);
                        return HttpHelper.getInstance().initService().cedianInfoDownload(DuanmianData.getDmid());
                    }
                }).flatMap(new Func1<CedianInfoBean, Observable<CedianInfoBean.LjcdsBean>>() {
                    @Override
                    public Observable<CedianInfoBean.LjcdsBean> call(CedianInfoBean cedianInfoBean) {
                        return Observable.from(cedianInfoBean.getLjcds());
                    }
                }).map(new Func1<CedianInfoBean.LjcdsBean, Boolean>() {
                    @Override
                    public Boolean call(CedianInfoBean.LjcdsBean ljcdsBean) {
                        CedianData mCedianData = new CedianData();
                        mCedianData.setTingceshuoming(ljcdsBean.getTingceshuoming());
                        mCedianData.setDuanmianid(ljcdsBean.getDuanmianid());
                        mCedianData.setUpdatetime(ljcdsBean.getUpdatetime());
                        mCedianData.setCedianNo(ljcdsBean.getCedianNo());
                        mCedianData.setTianmaidate(ljcdsBean.getTianmaidate());
                        mCedianData.setCedianname(ljcdsBean.getCedianname());
                        mCedianData.setCishu(ljcdsBean.getCishu());
                        mCedianData.setBeizhu(ljcdsBean.getBeizhu());
                        mCedianData.setCedianstate(ljcdsBean.getCedianstate());
                        mCedianData.setCediantype(ljcdsBean.getCediantype());
                        mCedianData.setCdid(ljcdsBean.getId());
                        mCedianData.setCedianmarginleft(ljcdsBean.getCedianmarginleft());
                        mCedianData.setCreatetime(ljcdsBean.getCreatetime());
                        mCedianData.setShejizhibeizhu(ljcdsBean.getShejizhibeizhu());
                        mCedianData.setShejicengjianliang(ljcdsBean.getShejicengjianliang());
                        mCedianData.setBianhao(ljcdsBean.getBianhao());
                        mCedianData.setCedianmargintop(ljcdsBean.getCedianmargintop());
                        return mCedianData.save();
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Boolean>() {

                            @Override
                            public void onNext(Boolean aBoolean) {
                                getView().setCedianMessage(aBoolean, intSum, intIndex);
                            }


                            @Override
                            public void onError(Throwable e) {
                                getView().showError(e);
                            }

                            @Override
                            public void onCompleted() {
                                getView().onCedianCompleted();
                            }

                        })
        );
    }

    @Override
    public void downloadYusheshuizhunxian(String departId) {
        DataSupport.deleteAll(YusheshuizhunxianData.class);

        mRxManager.add(
                HttpHelper.getInstance().initService().yusheshuizhunxianInfoDownload(departId)
                        .flatMap(new Func1<YusheshuizhunxianInfoBean, Observable<YusheshuizhunxianInfoBean.YsszxsBean>>() {
                            @Override
                            public Observable<YusheshuizhunxianInfoBean.YsszxsBean> call(YusheshuizhunxianInfoBean yusheshuizhunxianInfoBean) {
                                intIndex = 0;
                                intSum = yusheshuizhunxianInfoBean.getYsszxs().size();
                                return Observable.from(yusheshuizhunxianInfoBean.getYsszxs());
                            }
                        }).map(new Func1<YusheshuizhunxianInfoBean.YsszxsBean, Boolean>() {

                    @Override
                    public Boolean call(YusheshuizhunxianInfoBean.YsszxsBean ysszxsBean) {
                        YusheshuizhunxianData mYusheshuizhunxianData = new YusheshuizhunxianData();
                        mYusheshuizhunxianData.setBiaoshi(ysszxsBean.getBiaoshi());
                        mYusheshuizhunxianData.setYsszxid(ysszxsBean.getId());
                        mYusheshuizhunxianData.setXianlubianhao(ysszxsBean.getXianlubianhao());
                        mYusheshuizhunxianData.setCedianshu(ysszxsBean.getCedianshu());
                        mYusheshuizhunxianData.setLeixing(ysszxsBean.getLeixing());
                        mYusheshuizhunxianData.setXiugaishijian(ysszxsBean.getXiugaishijian());
                        mYusheshuizhunxianData.setShezhiren(ysszxsBean.getShezhiren());
                        mYusheshuizhunxianData.setXianlumingcheng(ysszxsBean.getXianlumingcheng());
                        mYusheshuizhunxianData.setChuangjianshijian(ysszxsBean.getChuangjianshijian());
                        mYusheshuizhunxianData.setDepartId(ysszxsBean.getDepartId());
                        mYusheshuizhunxianData.setJidianshu(ysszxsBean.getJidianshu());
                        mYusheshuizhunxianData.setXianluxinxi(ysszxsBean.getXianluxinxi());

                        return mYusheshuizhunxianData.save();
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Boolean>() {

                            @Override
                            public void onNext(Boolean aBoolean) {
                                intIndex += 1;
                                getView().setYusheshuizhunxianMessage(aBoolean, intSum, intIndex);
                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().showError(e);
                            }

                            @Override
                            public void onCompleted() {
                                getView().onYusheshuizhunxianCompleted();
                            }
                        })
        );
    }

    @Override
    public void downloadJidian() {
        DataSupport.deleteAll(JidianData.class);

        mRxManager.add(
                HttpHelper.getInstance().initService().jidianDownload()
                        .flatMap(new Func1<JidianBean, Observable<JidianBean.GzjdsBean>>() {
                            @Override
                            public Observable<JidianBean.GzjdsBean> call(JidianBean jidianBean) {
                                KLog.e("jidianBean::" + jidianBean);
                                intIndex = 0;
                                intSum = jidianBean.getGzjds().size();
                                return Observable.from(jidianBean.getGzjds());
                            }
                        })
                        .map(new Func1<JidianBean.GzjdsBean, Boolean>() {
                            @Override
                            public Boolean call(JidianBean.GzjdsBean gzjdsBean) {
                                JidianData mJidianData = new JidianData();
                                mJidianData.setChushigaocheng(gzjdsBean.getChushigaocheng());
                                mJidianData.setNzuobiao(gzjdsBean.getNzuobiao());
                                mJidianData.setBanbenhao(gzjdsBean.getBanbenhao());
                                mJidianData.setCishu(gzjdsBean.getCishu());
                                mJidianData.setBeizhu(gzjdsBean.getBeizhu());
                                mJidianData.setLicheng(gzjdsBean.getLicheng());
                                mJidianData.setBencixiuzheng(gzjdsBean.getBencixiuzheng());
                                mJidianData.setJdid(gzjdsBean.getId());
                                mJidianData.setChangduanlian(gzjdsBean.getChangduanlian());
                                mJidianData.setEzuobiao(gzjdsBean.getEzuobiao());
                                mJidianData.setName(gzjdsBean.getName());
                                mJidianData.setBianhao(gzjdsBean.getBianhao());
                                mJidianData.setGuanhao(gzjdsBean.getGuanhao());
                                mJidianData.setXiuzhenghou(gzjdsBean.getXiuzhenghou());
                                return mJidianData.save();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Boolean>() {

                            @Override
                            public void onNext(Boolean aBoolean) {
                                intIndex += 1;
                                getView().setJidianMessage(aBoolean, intSum, intIndex);

                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().showError(e);
                            }

                            @Override
                            public void onCompleted() {
                                getView().onJidianCompleted();
                            }
                        })
        );
    }

    @Override
    public void downloadStaff() {
        DataSupport.deleteAll(StaffData.class);

        mRxManager.add(
                HttpHelper.getInstance().initService().staffDownload()
                        .flatMap(new Func1<StaffBean, Observable<StaffBean.ObsBean>>() {
                            @Override
                            public Observable<StaffBean.ObsBean> call(StaffBean staffBean) {
                                intIndex = 0;
                                intSum = staffBean.getObs().size();
                                return Observable.from(staffBean.getObs());
                            }
                        }).map(new Func1<StaffBean.ObsBean, Boolean>() {
                    @Override
                    public Boolean call(StaffBean.ObsBean obsBean) {
                        StaffData mStaffData = new StaffData();
                        mStaffData.setStaffid(obsBean.getId());
                        mStaffData.setUserName(obsBean.getUserName());
                        mStaffData.setUserPhone(obsBean.getUserPhone());

                        return mStaffData.save();
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Boolean>() {
                            @Override
                            public void onNext(Boolean aBoolean) {
                                intIndex += 1;
                                KLog.e("intIndex::" + intIndex);
                                getView().setStaffMessage(aBoolean, intSum, intIndex);
                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().showError(e);
                            }

                            @Override
                            public void onCompleted() {
                                getView().onStaffCompleted();
                            }
                        })
        );
    }
}
