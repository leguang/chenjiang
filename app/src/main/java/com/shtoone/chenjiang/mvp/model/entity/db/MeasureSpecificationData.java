package com.shtoone.chenjiang.mvp.model.entity.db;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/10/31 0031 11:25
 * Email：langmanleguang@qq.com
 */
public class MeasureSpecificationData extends DataSupport {
    private int id;
    private float qianhoushijuleijicha;
    private float shixianchangdu;
    private float qianhoushijucha;
    private float liangcidushucha;
    private float liangcigaochazhicha;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getQianhoushijuleijicha() {
        return qianhoushijuleijicha;
    }

    public void setQianhoushijuleijicha(float qianhoushijuleijicha) {
        this.qianhoushijuleijicha = qianhoushijuleijicha;
    }

    public float getShixianchangdu() {
        return shixianchangdu;
    }

    public void setShixianchangdu(float shixianchangdu) {
        this.shixianchangdu = shixianchangdu;
    }

    public float getQianhoushijucha() {
        return qianhoushijucha;
    }

    public void setQianhoushijucha(float qianhoushijucha) {
        this.qianhoushijucha = qianhoushijucha;
    }

    public float getLiangcidushucha() {
        return liangcidushucha;
    }

    public void setLiangcidushucha(float liangcidushucha) {
        this.liangcidushucha = liangcidushucha;
    }

    public float getLiangcigaochazhicha() {
        return liangcigaochazhicha;
    }

    public void setLiangcigaochazhicha(float liangcigaochazhicha) {
        this.liangcigaochazhicha = liangcigaochazhicha;
    }

    public float getShixiangaodu() {
        return shixiangaodu;
    }

    public void setShixiangaodu(float shixiangaodu) {
        this.shixiangaodu = shixiangaodu;
    }

    private float shixiangaodu;
}
