package com.shtoone.chenjiang.mvp.model.entity.db;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/10/25 0025 14:50
 * Email：langmanleguang@qq.com
 */
public class CedianData extends DataSupport {
    private long id;
    private String tingceshuoming;
    private String duanmianid;
    private String updatetime;
    private String cedianNo;
    private String tianmaidate;
    private String cedianname;
    private String cishu;
    private String beizhu;
    private String cedianstate;
    private String cediantype;
    private String cdid;
    private String cedianmarginleft;
    private String createtime;
    private String shejizhibeizhu;
    private String shejicengjianliang;
    private String bianhao;
    private String cedianmargintop;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCdid() {
        return cdid;
    }

    public void setCdid(String cdid) {
        this.cdid = cdid;
    }

    public String getTingceshuoming() {
        return tingceshuoming;
    }

    public void setTingceshuoming(String tingceshuoming) {
        this.tingceshuoming = tingceshuoming;
    }

    public String getDuanmianid() {
        return duanmianid;
    }

    public void setDuanmianid(String duanmianid) {
        this.duanmianid = duanmianid;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getCedianNo() {
        return cedianNo;
    }

    public void setCedianNo(String cedianNo) {
        this.cedianNo = cedianNo;
    }

    public String getTianmaidate() {
        return tianmaidate;
    }

    public void setTianmaidate(String tianmaidate) {
        this.tianmaidate = tianmaidate;
    }

    public String getCedianname() {
        return cedianname;
    }

    public void setCedianname(String cedianname) {
        this.cedianname = cedianname;
    }

    public String getCishu() {
        return cishu;
    }

    public void setCishu(String cishu) {
        this.cishu = cishu;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getCedianstate() {
        return cedianstate;
    }

    public void setCedianstate(String cedianstate) {
        this.cedianstate = cedianstate;
    }

    public String getCediantype() {
        return cediantype;
    }

    public void setCediantype(String cediantype) {
        this.cediantype = cediantype;
    }

    public String getCedianmarginleft() {
        return cedianmarginleft;
    }

    public void setCedianmarginleft(String cedianmarginleft) {
        this.cedianmarginleft = cedianmarginleft;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getShejizhibeizhu() {
        return shejizhibeizhu;
    }

    public void setShejizhibeizhu(String shejizhibeizhu) {
        this.shejizhibeizhu = shejizhibeizhu;
    }

    public String getShejicengjianliang() {
        return shejicengjianliang;
    }

    public void setShejicengjianliang(String shejicengjianliang) {
        this.shejicengjianliang = shejicengjianliang;
    }

    public String getBianhao() {
        return bianhao;
    }

    public void setBianhao(String bianhao) {
        this.bianhao = bianhao;
    }

    public String getCedianmargintop() {
        return cedianmargintop;
    }

    public void setCedianmargintop(String cedianmargintop) {
        this.cedianmargintop = cedianmargintop;
    }
}
