package com.shtoone.chenjiang.mvp.model.entity.bean;

import java.util.List;

/**
 * Author：leguang on 2016/11/8 0008 13:24
 * Email：langmanleguang@qq.com
 */
public class YusheshuizhunxianInfoBean {

    /**
     * status : 0
     * description : 预设水准线路下载成功
     * ysszxs : [{"biaoshi":"0","id":"4028ca81584716cf01584726ee5f0011","xianlubianhao":"L1","cedianshu":"1","leixing":"0","xiugaishijian":"2016-11-10 10:24:53","shezhiren":"shtoone","xianlumingcheng":"test","chuangjianshijian":"2016-11-09 11:34:43","departId":"402880e447e99cf10147e9a03b320003","jidianshu":"2","xianluxinxi":"jd1,cd16,jd2,"}]
     */

    private int status;
    private String description;
    /**
     * biaoshi : 0
     * id : 4028ca81584716cf01584726ee5f0011
     * xianlubianhao : L1
     * cedianshu : 1
     * leixing : 0
     * xiugaishijian : 2016-11-10 10:24:53
     * shezhiren : shtoone
     * xianlumingcheng : test
     * chuangjianshijian : 2016-11-09 11:34:43
     * departId : 402880e447e99cf10147e9a03b320003
     * jidianshu : 2
     * xianluxinxi : jd1,cd16,jd2,
     */

    private List<YsszxsBean> ysszxs;

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

    public List<YsszxsBean> getYsszxs() {
        return ysszxs;
    }

    public void setYsszxs(List<YsszxsBean> ysszxs) {
        this.ysszxs = ysszxs;
    }

    public static class YsszxsBean {
        private String biaoshi;
        private String id;
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

        public String getBiaoshi() {
            return biaoshi;
        }

        public void setBiaoshi(String biaoshi) {
            this.biaoshi = biaoshi;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
}
