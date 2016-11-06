package com.shtoone.chenjiang.mvp.model.bean;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/11/2 0002 13:54
 * Email：langmanleguang@qq.com
 */
public class StaffData extends DataSupport {
    private int id;
    private String name;
    private String type;
    private String phtoneNumber;

    public String getPhtoneNumber() {
        return phtoneNumber;
    }

    public void setPhtoneNumber(String phtoneNumber) {
        this.phtoneNumber = phtoneNumber;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
