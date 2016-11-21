package com.shtoone.chenjiang.mvp.model.entity.db;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/10/25 0025 11:00
 * Email：langmanleguang@qq.com
 */
public class LevelLineData extends DataSupport {
    private int id;
    private String biaoDuan;
    private String routeType;
    private String observeType;
    private String observeDate;
    private String measureType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBiaoDuan() {
        return biaoDuan;
    }

    public void setBiaoDuan(String biaoDuan) {
        this.biaoDuan = biaoDuan;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getObserveType() {
        return observeType;
    }

    public void setObserveType(String observeType) {
        this.observeType = observeType;
    }

    public String getObserveDate() {
        return observeDate;
    }

    public void setObserveDate(String observeDate) {
        this.observeDate = observeDate;
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }
}
