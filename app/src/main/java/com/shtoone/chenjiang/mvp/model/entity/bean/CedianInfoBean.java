package com.shtoone.chenjiang.mvp.model.entity.bean;

import java.util.List;

/**
 * Author：leguang on 2016/11/8 0008 13:24
 * Email：langmanleguang@qq.com
 */
public class CedianInfoBean {

    /**
     * ljcds : [{"tingceshuoming":"","duanmianid":"4028ca81584c0cbb01584c59882d0006","updatetime":"2016-11-10 11:48:05","cedianNo":"1","tianmaidate":"2016-11-07","cedianname":"333","cishu":"0","beizhu":"333","cedianstate":"待测","cediantype":"4","id":"4028ca81584c0cbb01584c59886a0008","cedianmarginleft":"0","createtime":"2016-11-10 11:48:05","shejizhibeizhu":"333","shejicengjianliang":"333","bianhao":"cd19","cedianmargintop":"0"}]
     * status : 0
     * description : 测点数据下载成功
     */

    private int status;
    private String description;
    /**
     * tingceshuoming :
     * duanmianid : 4028ca81584c0cbb01584c59882d0006
     * updatetime : 2016-11-10 11:48:05
     * cedianNo : 1
     * tianmaidate : 2016-11-07
     * cedianname : 333
     * cishu : 0
     * beizhu : 333
     * cedianstate : 待测
     * cediantype : 4
     * id : 4028ca81584c0cbb01584c59886a0008
     * cedianmarginleft : 0
     * createtime : 2016-11-10 11:48:05
     * shejizhibeizhu : 333
     * shejicengjianliang : 333
     * bianhao : cd19
     * cedianmargintop : 0
     */

    private List<LjcdsBean> ljcds;

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

    public List<LjcdsBean> getLjcds() {
        return ljcds;
    }

    public void setLjcds(List<LjcdsBean> ljcds) {
        this.ljcds = ljcds;
    }

    public static class LjcdsBean {
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
        private String id;
        private String cedianmarginleft;
        private String createtime;
        private String shejizhibeizhu;
        private String shejicengjianliang;
        private String bianhao;
        private String cedianmargintop;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
}
