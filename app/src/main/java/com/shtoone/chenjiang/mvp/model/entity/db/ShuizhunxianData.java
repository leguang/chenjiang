package com.shtoone.chenjiang.mvp.model.entity.db;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2017/01/10 0025 10:30
 * Email：langmanleguang@qq.com
 */
public class ShuizhunxianData extends DataSupport {
    private long id;
    private String biaoshi;
    private String ysszxid;
    private String xianlubianhao;
    private String cedianshu;
    private String leixing;
    private String shezhiren;
    private String xianlumingcheng;
    private String chuangjianshijian;
    private String xiugaishijian;
    private String departId;
    private String jidianshu;
    private String xianluxinxi;
    private String routeType;
    private String observeType;
    private String weather;
    private String pressure;
    private String temperature;
    private String staff;
    private long yusheshuizhunxianID;

    public String getYsszxid() {
        return ysszxid;
    }

    public String getXiugaishijian() {
        return xiugaishijian;
    }

    public void setXiugaishijian(String xiugaishijian) {
        this.xiugaishijian = xiugaishijian;
    }

    public String getChuangjianshijian() {
        return chuangjianshijian;
    }

    public void setChuangjianshijian(String chuangjianshijian) {
        this.chuangjianshijian = chuangjianshijian;
    }

    public void setYsszxid(String ysszxid) {
        this.ysszxid = ysszxid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getYusheshuizhunxianID() {
        return yusheshuizhunxianID;
    }

    public void setYusheshuizhunxianID(long yusheshuizhunxianID) {
        this.yusheshuizhunxianID = yusheshuizhunxianID;
    }
}
