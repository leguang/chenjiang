package com.shtoone.chenjiang.common;

import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.socks.library.KLog;

import java.text.DecimalFormat;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 * <p>
 * 后视距和：从第1测站开始，到当前测站，所有后视距离累计之和  ∑[(B1HD+B2HD)/2]
 * 前视距和：从第1测站开始，到当前测站，所有前视距离累计之和  ∑[(F1HD+F2HD)/2]
 * 高差1：当前测站第一次后尺读数减前尺读数，也就是图上的 B1R-F1R
 * 高差2：当前测站第二次后尺读数减前尺读数，也就是图上的 B2R-F2R
 * 测站高差：两次后视的平均值减去两次前视的平均值，(B1R+B2R)/2-(F1R+F2R)/2 = ∆h 化简一下：(b1r - f1r + b2r - f2r) / 2 = ∆h
 * 高程值：测站高差加工作基点高程  ∆h + H
 * FR读数差：两次前视读数之差  F1R - F2R
 * BR读数差：两次后视读数之差  B1R - B2R
 */
public class Formula {
    private static final String TAG = Formula.class.getSimpleName();
    private static Formula INSTANCE;
    private float b1hd;
    private float b2hd;
    private float b1r;
    private float b2r;
    private float f1hd;
    private float f2hd;
    private float f1r;
    private float f2r;
    private DecimalFormat mDecimalFormat;

    private Formula() {
        mDecimalFormat = new DecimalFormat("0.00000");
    }

    public static Formula getInstance(CezhanData mCezhanData) {
        if (INSTANCE == null) {
            synchronized (Formula.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Formula();
                }
            }
        }
        INSTANCE.initData(mCezhanData);
        return INSTANCE;
    }

    private void initData(CezhanData mCezhanData) {
        try {
            this.b1hd = Float.parseFloat(mCezhanData.getB1hd());
            this.b2hd = Float.parseFloat(mCezhanData.getB2hd());
            this.b1r = Float.parseFloat(mCezhanData.getB1r());
            this.b2r = Float.parseFloat(mCezhanData.getB2r());
            this.f1hd = Float.parseFloat(mCezhanData.getF1hd());
            this.f2hd = Float.parseFloat(mCezhanData.getF2hd());
            this.f1r = Float.parseFloat(mCezhanData.getF1r());
            this.f2r = Float.parseFloat(mCezhanData.getF2r());
        }catch (Exception e){
        }
        KLog.e("init：：" + b1hd);
        KLog.e("init：：" + b2hd);
        KLog.e("init：：" + b1r);
        KLog.e("init：：" + b2r);
        KLog.e("init：：" + f1hd);
        KLog.e("init：：" + f2hd);
        KLog.e("init：：" + f1r);
    }

    //后视距和
    public String houshijuhe(float houshijuhe) {
        return mDecimalFormat.format(houshijuhe + ((b1hd + b2hd) / 2));
    }

    //前视距和
    public String qianshijuhe(float qianshijuhe) {
        return mDecimalFormat.format(qianshijuhe + ((f1hd + f2hd) / 2));
    }

    //高差1
    public String gaocha1() {
        return mDecimalFormat.format(b1r - f1r);
    }

    //高差2
    public String gaocha2() {
        return mDecimalFormat.format(b2r - f2r);
    }

    //测站高差
    public String cezhangaocha() {
//        return mDecimalFormat.format((gaocha1() + gaocha2()) / 2);
        return mDecimalFormat.format((b1r - f1r + b2r - f2r) / 2);
    }

    //高程值
    public String gaochengzhi(float gaochengzhi) {
        return mDecimalFormat.format(gaochengzhi + ((b1r - f1r + b2r - f2r) / 2));
    }

    //FR读数差
    public String frdushucha() {
        return mDecimalFormat.format(f1r - f2r);
    }

    //BR读数差
    public String brdushucha() {
        return mDecimalFormat.format(b1r - b2r);
    }
}
