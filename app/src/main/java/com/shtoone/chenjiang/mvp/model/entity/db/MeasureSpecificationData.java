package com.shtoone.chenjiang.mvp.model.entity.db;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/10/31 0031 11:25
 * Email：langmanleguang@qq.com
 */
public class MeasureSpecificationData extends DataSupport {
    private long id;
    private String qianhoushijuleijicha;
    private String shixianchangdu;
    private String qianhoushijucha;
    private String liangcidushucha;
    private String shixiangaodu;
    private String liangcigaochazhicha;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQianhoushijuleijicha() {
        return qianhoushijuleijicha;
    }

    public void setQianhoushijuleijicha(String qianhoushijuleijicha) {
        this.qianhoushijuleijicha = qianhoushijuleijicha;
    }

    public String getShixianchangdu() {
        return shixianchangdu;
    }

    public void setShixianchangdu(String shixianchangdu) {
        this.shixianchangdu = shixianchangdu;
    }

    public String getQianhoushijucha() {
        return qianhoushijucha;
    }

    public void setQianhoushijucha(String qianhoushijucha) {
        this.qianhoushijucha = qianhoushijucha;
    }

    public String getLiangcidushucha() {
        return liangcidushucha;
    }

    public void setLiangcidushucha(String liangcidushucha) {
        this.liangcidushucha = liangcidushucha;
    }

    public String getShixiangaodu() {
        return shixiangaodu;
    }

    public void setShixiangaodu(String shixiangaodu) {
        this.shixiangaodu = shixiangaodu;
    }

    public String getLiangcigaochazhicha() {
        return liangcigaochazhicha;
    }

    public void setLiangcigaochazhicha(String liangcigaochazhicha) {
        this.liangcigaochazhicha = liangcigaochazhicha;
    }
}
