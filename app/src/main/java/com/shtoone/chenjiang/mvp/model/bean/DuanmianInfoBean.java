package com.shtoone.chenjiang.mvp.model.bean;

import java.util.List;

/**
 * Author：leguang on 2016/11/8 0008 13:24
 * Email：langmanleguang@qq.com
 */
public class DuanmianInfoBean {

    /**
     * status : 0
     * description : 断面数据下载成功
     * ljdms : [{"chulishendu":"8","duanmianname":"8","guancezhuangtai":"","dingmiantiantuhigh":"","gouzhuwuname":"8","gongdianid":"f89aff0758186c3701581895a13e001b","beizhu":"","shifouguoduduan":"0","id":"f89aff0758193ea50158194a45610006","yasuocenghoudu":"","dijichulifangshi":"8","gouzhuwutype":"2","suoshuqiaoduntaibianhao":"8","qiaoduntaihigh":"8","shigonglicheng":"8","shigonglichengguanhao":"DK","shejiyuyatuhigh":"","tianwahigh":"","shigongchangduanlianjie":"无"}]
     */

    private int status;
    private String description;
    /**
     * chulishendu : 8
     * duanmianname : 8
     * guancezhuangtai :
     * dingmiantiantuhigh :
     * gouzhuwuname : 8
     * gongdianid : f89aff0758186c3701581895a13e001b
     * beizhu :
     * shifouguoduduan : 0
     * id : f89aff0758193ea50158194a45610006
     * yasuocenghoudu :
     * dijichulifangshi : 8
     * gouzhuwutype : 2
     * suoshuqiaoduntaibianhao : 8
     * qiaoduntaihigh : 8
     * shigonglicheng : 8
     * shigonglichengguanhao : DK
     * shejiyuyatuhigh :
     * tianwahigh :
     * shigongchangduanlianjie : 无
     */

    private List<LjdmsBean> ljdms;

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

    public List<LjdmsBean> getLjdms() {
        return ljdms;
    }

    public void setLjdms(List<LjdmsBean> ljdms) {
        this.ljdms = ljdms;
    }

    public static class LjdmsBean {
        private String chulishendu;
        private String duanmianname;
        private String guancezhuangtai;
        private String dingmiantiantuhigh;
        private String gouzhuwuname;
        private String gongdianid;
        private String beizhu;
        private String shifouguoduduan;
        private String id;
        private String yasuocenghoudu;
        private String dijichulifangshi;
        private String gouzhuwutype;
        private String suoshuqiaoduntaibianhao;
        private String qiaoduntaihigh;
        private String shigonglicheng;
        private String shigonglichengguanhao;
        private String shejiyuyatuhigh;
        private String tianwahigh;
        private String shigongchangduanlianjie;

        public String getChulishendu() {
            return chulishendu;
        }

        public void setChulishendu(String chulishendu) {
            this.chulishendu = chulishendu;
        }

        public String getDuanmianname() {
            return duanmianname;
        }

        public void setDuanmianname(String duanmianname) {
            this.duanmianname = duanmianname;
        }

        public String getGuancezhuangtai() {
            return guancezhuangtai;
        }

        public void setGuancezhuangtai(String guancezhuangtai) {
            this.guancezhuangtai = guancezhuangtai;
        }

        public String getDingmiantiantuhigh() {
            return dingmiantiantuhigh;
        }

        public void setDingmiantiantuhigh(String dingmiantiantuhigh) {
            this.dingmiantiantuhigh = dingmiantiantuhigh;
        }

        public String getGouzhuwuname() {
            return gouzhuwuname;
        }

        public void setGouzhuwuname(String gouzhuwuname) {
            this.gouzhuwuname = gouzhuwuname;
        }

        public String getGongdianid() {
            return gongdianid;
        }

        public void setGongdianid(String gongdianid) {
            this.gongdianid = gongdianid;
        }

        public String getBeizhu() {
            return beizhu;
        }

        public void setBeizhu(String beizhu) {
            this.beizhu = beizhu;
        }

        public String getShifouguoduduan() {
            return shifouguoduduan;
        }

        public void setShifouguoduduan(String shifouguoduduan) {
            this.shifouguoduduan = shifouguoduduan;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getYasuocenghoudu() {
            return yasuocenghoudu;
        }

        public void setYasuocenghoudu(String yasuocenghoudu) {
            this.yasuocenghoudu = yasuocenghoudu;
        }

        public String getDijichulifangshi() {
            return dijichulifangshi;
        }

        public void setDijichulifangshi(String dijichulifangshi) {
            this.dijichulifangshi = dijichulifangshi;
        }

        public String getGouzhuwutype() {
            return gouzhuwutype;
        }

        public void setGouzhuwutype(String gouzhuwutype) {
            this.gouzhuwutype = gouzhuwutype;
        }

        public String getSuoshuqiaoduntaibianhao() {
            return suoshuqiaoduntaibianhao;
        }

        public void setSuoshuqiaoduntaibianhao(String suoshuqiaoduntaibianhao) {
            this.suoshuqiaoduntaibianhao = suoshuqiaoduntaibianhao;
        }

        public String getQiaoduntaihigh() {
            return qiaoduntaihigh;
        }

        public void setQiaoduntaihigh(String qiaoduntaihigh) {
            this.qiaoduntaihigh = qiaoduntaihigh;
        }

        public String getShigonglicheng() {
            return shigonglicheng;
        }

        public void setShigonglicheng(String shigonglicheng) {
            this.shigonglicheng = shigonglicheng;
        }

        public String getShigonglichengguanhao() {
            return shigonglichengguanhao;
        }

        public void setShigonglichengguanhao(String shigonglichengguanhao) {
            this.shigonglichengguanhao = shigonglichengguanhao;
        }

        public String getShejiyuyatuhigh() {
            return shejiyuyatuhigh;
        }

        public void setShejiyuyatuhigh(String shejiyuyatuhigh) {
            this.shejiyuyatuhigh = shejiyuyatuhigh;
        }

        public String getTianwahigh() {
            return tianwahigh;
        }

        public void setTianwahigh(String tianwahigh) {
            this.tianwahigh = tianwahigh;
        }

        public String getShigongchangduanlianjie() {
            return shigongchangduanlianjie;
        }

        public void setShigongchangduanlianjie(String shigongchangduanlianjie) {
            this.shigongchangduanlianjie = shigongchangduanlianjie;
        }
    }
}
