package com.shtoone.chenjiang.mvp.model.entity.db;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/11/2 0002 13:54
 * Email：langmanleguang@qq.com
 */
public class DeviceData extends DataSupport {
    private long id;
    private String brand;
    private String xinghao;
    private String shebeihao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getXinghao() {
        return xinghao;
    }

    public void setXinghao(String xinghao) {
        this.xinghao = xinghao;
    }

    public String getShebeihao() {
        return shebeihao;
    }

    public void setShebeihao(String shebeihao) {
        this.shebeihao = shebeihao;
    }
}
