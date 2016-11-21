package com.shtoone.chenjiang.mvp.model.entity.db;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/11/2 0002 13:54
 * Email：langmanleguang@qq.com
 */
public class JidianData extends DataSupport {
    private long id;
    private String chushigaocheng;
    private String nzuobiao;
    private String banbenhao;
    private String cishu;
    private String beizhu;
    private String licheng;
    private String bencixiuzheng;
    private String jdid;
    private String changduanlian;
    private String ezuobiao;
    private String name;
    private String bianhao;
    private String guanhao;
    private String xiuzhenghou;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJdid() {
        return jdid;
    }

    public void setJdid(String jdid) {
        this.jdid = jdid;
    }

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
