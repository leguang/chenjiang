package com.shtoone.chenjiang.mvp.model.bean;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/10/25 0025 14:50
 * Email：langmanleguang@qq.com
 */
public class GongdianData extends DataSupport {
    private int id;
    private String name;

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
}
