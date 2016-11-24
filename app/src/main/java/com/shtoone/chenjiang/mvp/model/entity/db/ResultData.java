package com.shtoone.chenjiang.mvp.model.entity.db;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/11/22 0022 09:58
 * Email：langmanleguang@qq.com
 */
public class ResultData extends DataSupport {
    private int id;
    private String number;
    private String date;
    private String xiuzhengzhi;
    private String chengguozhi;
    private String state;
    private String yuyagaodu;
    private String gongkuang;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getXiuzhengzhi() {
        return xiuzhengzhi;
    }

    public void setXiuzhengzhi(String xiuzhengzhi) {
        this.xiuzhengzhi = xiuzhengzhi;
    }

    public String getChengguozhi() {
        return chengguozhi;
    }

    public void setChengguozhi(String chengguozhi) {
        this.chengguozhi = chengguozhi;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getYuyagaodu() {
        return yuyagaodu;
    }

    public void setYuyagaodu(String yuyagaodu) {
        this.yuyagaodu = yuyagaodu;
    }

    public String getGongkuang() {
        return gongkuang;
    }

    public void setGongkuang(String gongkuang) {
        this.gongkuang = gongkuang;
    }
}
