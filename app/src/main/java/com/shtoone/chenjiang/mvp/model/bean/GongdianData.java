package com.shtoone.chenjiang.mvp.model.bean;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/10/25 0025 14:50
 * Email：langmanleguang@qq.com
 */
public class GongdianData extends DataSupport {
    private long id;
    private String ssgzw;
    private String gdpx;
    private String kslc;
    private String departid;
    private String zxlc;
    private String bz;
    private String gdlx;
    private String jldepartid;
    private String gdbm;
    private String gdid;
    private String gdxx;
    private String jslc;
    private String gdmc;
    private String jlbd;
    private String cd;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGdid() {
        return gdid;
    }

    public void setGdid(String gdid) {
        this.gdid = gdid;
    }

    public String getSsgzw() {
        return ssgzw;
    }

    public void setSsgzw(String ssgzw) {
        this.ssgzw = ssgzw;
    }

    public String getGdpx() {
        return gdpx;
    }

    public void setGdpx(String gdpx) {
        this.gdpx = gdpx;
    }

    public String getKslc() {
        return kslc;
    }

    public void setKslc(String kslc) {
        this.kslc = kslc;
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid;
    }

    public String getZxlc() {
        return zxlc;
    }

    public void setZxlc(String zxlc) {
        this.zxlc = zxlc;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getGdlx() {
        return gdlx;
    }

    public void setGdlx(String gdlx) {
        this.gdlx = gdlx;
    }

    public String getJldepartid() {
        return jldepartid;
    }

    public void setJldepartid(String jldepartid) {
        this.jldepartid = jldepartid;
    }

    public String getGdbm() {
        return gdbm;
    }

    public void setGdbm(String gdbm) {
        this.gdbm = gdbm;
    }

    public String getGdxx() {
        return gdxx;
    }

    public void setGdxx(String gdxx) {
        this.gdxx = gdxx;
    }

    public String getJslc() {
        return jslc;
    }

    public void setJslc(String jslc) {
        this.jslc = jslc;
    }

    public String getGdmc() {
        return gdmc;
    }

    public void setGdmc(String gdmc) {
        this.gdmc = gdmc;
    }

    public String getJlbd() {
        return jlbd;
    }

    public void setJlbd(String jlbd) {
        this.jlbd = jlbd;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }
}
