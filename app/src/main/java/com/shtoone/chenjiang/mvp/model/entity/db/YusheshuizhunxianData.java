package com.shtoone.chenjiang.mvp.model.entity.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Author：leguang on 2016/10/25 0025 14:50
 * Email：langmanleguang@qq.com
 */
public class YusheshuizhunxianData extends DataSupport implements Serializable {
    private long id;
    private String biaoshi;
    private String ysszxid;
    private String xianlubianhao;
    private String cedianshu;
    private String leixing;
    private String xiugaishijian;
    private String shezhiren;
    private String xianlumingcheng;
    private String chuangjianshijian;
    private String departId;
    private String jidianshu;
    private String xianluxinxi;
    private boolean edit;
    private String routeType;
    private String observeType;
    private String weather;
    private String pressure;
    private String temperature;
    private String staff;

    public String getMeasureState() {
        return measureState;
    }

    public void setMeasureState(String measureState) {
        this.measureState = measureState;
    }

    private String measureState;

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

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getYsszxid() {
        return ysszxid;
    }

    public void setYsszxid(String ysszxid) {
        this.ysszxid = ysszxid;
    }

    public String getBiaoshi() {
        return biaoshi;
    }

    public void setBiaoshi(String biaoshi) {
        this.biaoshi = biaoshi;
    }

    public String getXianlubianhao() {
        return xianlubianhao;
    }

    public void setXianlubianhao(String xianlubianhao) {
        this.xianlubianhao = xianlubianhao;
    }

    public String getCedianshu() {
        return cedianshu;
    }

    public void setCedianshu(String cedianshu) {
        this.cedianshu = cedianshu;
    }

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }

    public String getXiugaishijian() {
        return xiugaishijian;
    }

    public void setXiugaishijian(String xiugaishijian) {
        this.xiugaishijian = xiugaishijian;
    }

    public String getShezhiren() {
        return shezhiren;
    }

    public void setShezhiren(String shezhiren) {
        this.shezhiren = shezhiren;
    }

    public String getXianlumingcheng() {
        return xianlumingcheng;
    }

    public void setXianlumingcheng(String xianlumingcheng) {
        this.xianlumingcheng = xianlumingcheng;
    }

    public String getChuangjianshijian() {
        return chuangjianshijian;
    }

    public void setChuangjianshijian(String chuangjianshijian) {
        this.chuangjianshijian = chuangjianshijian;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getJidianshu() {
        return jidianshu;
    }

    public void setJidianshu(String jidianshu) {
        this.jidianshu = jidianshu;
    }

    public String getXianluxinxi() {
        return xianluxinxi;
    }

    public void setXianluxinxi(String xianluxinxi) {
        this.xianluxinxi = xianluxinxi;
    }
}

