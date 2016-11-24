package com.shtoone.chenjiang.mvp.model.entity.db;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/11/22 0022 09:58
 * Email：langmanleguang@qq.com
 */
public class OriginalData extends DataSupport {
    private int id;
    private String number;
    private String type;
    private String distance;
    private String value;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;
}
