package com.shtoone.chenjiang.mvp.model.entity.db;


import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/11/2 0002 13:54
 * Email：langmanleguang@qq.com
 */
public class CezhanData extends DataSupport {
    private long id;
    private int number;
    private String measureDirection;
    private String observeType;
    private String qianshi;
    private String houshi;
    private String b1hd;
    private String b2hd;
    private String b1r;
    private String b2r;
    private String f1hd;
    private String f2hd;
    private String f1r;
    private String f2r;
    private String houshijuhe;
    private String qianshijuhe;
    private String gaocha1;
    private String gaocha2;
    private String cezhangaocha;
    private String gaochengzhi;
    private String chushigaochengzhi;
    private String frdushucha;
    private String brdushucha;
    private String f1time;
    private String f2time;
    private String b1time;
    private String b2time;
    private boolean first;
    private long shuizhunxianID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMeasureDirection() {
        return measureDirection;
    }

    public void setMeasureDirection(String measureDirection) {
        this.measureDirection = measureDirection;
    }

    public String getObserveType() {
        return observeType;
    }

    public void setObserveType(String observeType) {
        this.observeType = observeType;
    }

    public String getQianshi() {
        return qianshi;
    }

    public void setQianshi(String qianshi) {
        this.qianshi = qianshi;
    }

    public String getHoushi() {
        return houshi;
    }

    public void setHoushi(String houshi) {
        this.houshi = houshi;
    }

    public String getB1hd() {
        return b1hd;
    }

    public void setB1hd(String b1hd) {
        this.b1hd = b1hd;
    }

    public String getB2hd() {
        return b2hd;
    }

    public void setB2hd(String b2hd) {
        this.b2hd = b2hd;
    }

    public String getB1r() {
        return b1r;
    }

    public void setB1r(String b1r) {
        this.b1r = b1r;
    }

    public String getB2r() {
        return b2r;
    }

    public void setB2r(String b2r) {
        this.b2r = b2r;
    }

    public String getF1hd() {
        return f1hd;
    }

    public void setF1hd(String f1hd) {
        this.f1hd = f1hd;
    }

    public String getF2hd() {
        return f2hd;
    }

    public void setF2hd(String f2hd) {
        this.f2hd = f2hd;
    }

    public String getF1r() {
        return f1r;
    }

    public void setF1r(String f1r) {
        this.f1r = f1r;
    }

    public String getF2r() {
        return f2r;
    }

    public void setF2r(String f2r) {
        this.f2r = f2r;
    }

    public String getHoushijuhe() {
        return houshijuhe;
    }

    public void setHoushijuhe(String houshijuhe) {
        this.houshijuhe = houshijuhe;
    }

    public String getQianshijuhe() {
        return qianshijuhe;
    }

    public void setQianshijuhe(String qianshijuhe) {
        this.qianshijuhe = qianshijuhe;
    }

    public String getGaocha1() {
        return gaocha1;
    }

    public void setGaocha1(String gaocha1) {
        this.gaocha1 = gaocha1;
    }

    public String getGaocha2() {
        return gaocha2;
    }

    public void setGaocha2(String gaocha2) {
        this.gaocha2 = gaocha2;
    }

    public String getCezhangaocha() {
        return cezhangaocha;
    }

    public void setCezhangaocha(String cezhangaocha) {
        this.cezhangaocha = cezhangaocha;
    }

    public String getGaochengzhi() {
        return gaochengzhi;
    }

    public void setGaochengzhi(String gaochengzhi) {
        this.gaochengzhi = gaochengzhi;
    }

    public String getChushigaochengzhi() {
        return chushigaochengzhi;
    }

    public void setChushigaochengzhi(String chushigaochengzhi) {
        this.chushigaochengzhi = chushigaochengzhi;
    }

    public String getFrdushucha() {
        return frdushucha;
    }

    public void setFrdushucha(String frdushucha) {
        this.frdushucha = frdushucha;
    }

    public String getBrdushucha() {
        return brdushucha;
    }

    public void setBrdushucha(String brdushucha) {
        this.brdushucha = brdushucha;
    }

    public long getShuizhunxianID() {
        return shuizhunxianID;
    }

    public void setShuizhunxianID(long shuizhunxianID) {
        this.shuizhunxianID = shuizhunxianID;
    }

    public String getF1time() {
        return f1time;
    }

    public void setF1time(String f1time) {
        this.f1time = f1time;
    }

    public String getF2time() {
        return f2time;
    }

    public void setF2time(String f2time) {
        this.f2time = f2time;
    }

    public String getB1time() {
        return b1time;
    }

    public void setB1time(String b1time) {
        this.b1time = b1time;
    }

    public String getB2time() {
        return b2time;
    }

    public void setB2time(String b2time) {
        this.b2time = b2time;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public void clean() {
        b1hd = "";
        b2hd = "";
        b1r = "";
        b2r = "";
        f1hd = "";
        f2hd = "";
        f1r = "";
        f2r = "";
        houshijuhe = "";
        qianshijuhe = "";
        gaocha1 = "";
        gaocha2 = "";
        cezhangaocha = "";
        gaochengzhi = "";
        frdushucha = "";
        brdushucha = "";
        f1time = "";
        f2time = "";
        b1time = "";
        b2time = "";
    }
}
