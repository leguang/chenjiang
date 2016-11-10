package com.shtoone.chenjiang.mvp.model.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Author：leguang on 2016/11/2 0002 13:54
 * Email：langmanleguang@qq.com
 */
public class JidianBean extends DataSupport {

    /**
     * status : 0
     * description : 工作基点数据下载成功
     * gzjds : [{"chushigaocheng":"1.00000","nzuobiao":"1","banbenhao":"0","cishu":"0","beizhu":"2","licheng":"1","bencixiuzheng":"","id":"4028ca81584716cf0158472014280007","changduanlian":"1","ezuobiao":"1","name":"test","bianhao":"jd1","guanhao":"1","xiuzhenghou":"1.00000"},{"chushigaocheng":"2.00000","nzuobiao":"2","banbenhao":"0","cishu":"0","beizhu":"2","licheng":"2","bencixiuzheng":"","id":"4028ca81584716cf015847202a100009","changduanlian":"1","ezuobiao":"2","name":"2","bianhao":"jd2","guanhao":"2","xiuzhenghou":"2.00000"}]
     */

    private int status;
    private String description;
    /**
     * chushigaocheng : 1.00000
     * nzuobiao : 1
     * banbenhao : 0
     * cishu : 0
     * beizhu : 2
     * licheng : 1
     * bencixiuzheng :
     * id : 4028ca81584716cf0158472014280007
     * changduanlian : 1
     * ezuobiao : 1
     * name : test
     * bianhao : jd1
     * guanhao : 1
     * xiuzhenghou : 1.00000
     */

    private List<GzjdsBean> gzjds;

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

    public List<GzjdsBean> getGzjds() {
        return gzjds;
    }

    public void setGzjds(List<GzjdsBean> gzjds) {
        this.gzjds = gzjds;
    }

    public static class GzjdsBean {
        private String chushigaocheng;
        private String nzuobiao;
        private String banbenhao;
        private String cishu;
        private String beizhu;
        private String licheng;
        private String bencixiuzheng;
        private String id;
        private String changduanlian;
        private String ezuobiao;
        private String name;
        private String bianhao;
        private String guanhao;
        private String xiuzhenghou;

        public String getChushigaocheng() {
            return chushigaocheng;
        }

        public void setChushigaocheng(String chushigaocheng) {
            this.chushigaocheng = chushigaocheng;
        }

        public String getNzuobiao() {
            return nzuobiao;
        }

        public void setNzuobiao(String nzuobiao) {
            this.nzuobiao = nzuobiao;
        }

        public String getBanbenhao() {
            return banbenhao;
        }

        public void setBanbenhao(String banbenhao) {
            this.banbenhao = banbenhao;
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

        public String getLicheng() {
            return licheng;
        }

        public void setLicheng(String licheng) {
            this.licheng = licheng;
        }

        public String getBencixiuzheng() {
            return bencixiuzheng;
        }

        public void setBencixiuzheng(String bencixiuzheng) {
            this.bencixiuzheng = bencixiuzheng;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getChangduanlian() {
            return changduanlian;
        }

        public void setChangduanlian(String changduanlian) {
            this.changduanlian = changduanlian;
        }

        public String getEzuobiao() {
            return ezuobiao;
        }

        public void setEzuobiao(String ezuobiao) {
            this.ezuobiao = ezuobiao;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBianhao() {
            return bianhao;
        }

        public void setBianhao(String bianhao) {
            this.bianhao = bianhao;
        }

        public String getGuanhao() {
            return guanhao;
        }

        public void setGuanhao(String guanhao) {
            this.guanhao = guanhao;
        }

        public String getXiuzhenghou() {
            return xiuzhenghou;
        }

        public void setXiuzhenghou(String xiuzhenghou) {
            this.xiuzhenghou = xiuzhenghou;
        }
    }
}
