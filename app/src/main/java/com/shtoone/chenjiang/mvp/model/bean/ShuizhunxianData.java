package com.shtoone.chenjiang.mvp.model.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2016-11-14.
 */
public class ShuizhunxianData extends DataSupport {

    private String bianhao;
    private java.lang.String editDate;
    private java.lang.String observeDate;
    private String departName;
    private String observeType;
    private java.lang.String routeType;
    private java.lang.String beizhu;
    private java.lang.String departId;

    public String getBianhao() {
        return bianhao;
    }

    public void setBianhao(String bianhao) {
        this.bianhao = bianhao;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    public String getObserveDate() {
        return observeDate;
    }

    public void setObserveDate(String observeDate) {
        this.observeDate = observeDate;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getObserveType() {
        return observeType;
    }

    public void setObserveType(String observeType) {
        this.observeType = observeType;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }
}
