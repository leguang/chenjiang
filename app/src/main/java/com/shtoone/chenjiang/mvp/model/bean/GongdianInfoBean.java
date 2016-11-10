package com.shtoone.chenjiang.mvp.model.bean;

import java.util.List;

/**
 * Author：leguang on 2016/11/8 0008 13:24
 * Email：langmanleguang@qq.com
 */
public class GongdianInfoBean {

    /**
     * status : 0
     * gdxxs : [{"ssgzw":"小竹坑大桥","gdpx":"","kslc":"DK357+121.27 ,,","departid":"40288bf457f5cadd0157f5e4f304001b","zxlc":"DK357+258.91","bz":"","gdlx":"2","jldepartid":"40288bee57fae67c0157fae9997b0001","gdbm":"GD1002","id":"f89aff0758186c3701581895a13e001b","gdxx":"","jslc":"DK357+395.32,,","gdmc":"小竹坑大桥","jlbd":"","cd":"274.05,,GD1002"}]
     * description : 工点数据下载成功
     */

    private int status;
    private String description;
    /**
     * ssgzw : 小竹坑大桥
     * gdpx :
     * kslc : DK357+121.27 ,,
     * departid : 40288bf457f5cadd0157f5e4f304001b
     * zxlc : DK357+258.91
     * bz :
     * gdlx : 2
     * jldepartid : 40288bee57fae67c0157fae9997b0001
     * gdbm : GD1002
     * id : f89aff0758186c3701581895a13e001b
     * gdxx :
     * jslc : DK357+395.32,,
     * gdmc : 小竹坑大桥
     * jlbd :
     * cd : 274.05,,GD1002
     */

    private List<GdxxsBean> gdxxs;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GdxxsBean> getGdxxs() {
        return gdxxs;
    }

    public void setGdxxs(List<GdxxsBean> gdxxs) {
        this.gdxxs = gdxxs;
    }

    public static class GdxxsBean {
        private String ssgzw;
        private String gdpx;
        private String kslc;
        private String departid;
        private String zxlc;
        private String bz;
        private String gdlx;
        private String jldepartid;
        private String gdbm;
        private String id;
        private String gdxx;
        private String jslc;
        private String gdmc;
        private String jlbd;
        private String cd;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
}
