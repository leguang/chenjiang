package com.shtoone.chenjiang.mvp.model.bean;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/11/2 0002 13:54
 * Email：langmanleguang@qq.com
 */
public class JidianData extends DataSupport {
    private int id;
    private String bianhao;
    private String name;
    private String xiuzhenghougaochengzhi;
    private String lichengguanhao;
    private String lichengzhi;

    public String getBianhao() {
        return bianhao;
    }

    public void setBianhao(String bianhao) {
        this.bianhao = bianhao;
    }

    public String getLichengzhi() {
        return lichengzhi;
    }

    public void setLichengzhi(String lichengzhi) {
        this.lichengzhi = lichengzhi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXiuzhenghougaochengzhi() {
        return xiuzhenghougaochengzhi;
    }

    public void setXiuzhenghougaochengzhi(String xiuzhenghougaochengzhi) {
        this.xiuzhenghougaochengzhi = xiuzhenghougaochengzhi;
    }

    public String getLichengguanhao() {
        return lichengguanhao;
    }

    public void setLichengguanhao(String lichengguanhao) {
        this.lichengguanhao = lichengguanhao;
    }
}
