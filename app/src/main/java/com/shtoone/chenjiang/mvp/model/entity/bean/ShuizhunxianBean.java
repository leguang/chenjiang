package com.shtoone.chenjiang.mvp.model.entity.bean;

import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.shtoone.chenjiang.mvp.model.entity.db.ShuizhunxianData;

import java.util.List;

/**
 * Author：leguang on 2016/11/8 0008 13:24
 * Email：langmanleguang@qq.com
 */
public class ShuizhunxianBean {

    private ShuizhunxianData shuizhunxiandata;

    private List<CezhanData> listCezhanData;

    public ShuizhunxianBean(ShuizhunxianData shuizhunxiandata, List<CezhanData> listCezhanData) {
        this.shuizhunxiandata = shuizhunxiandata;
        this.listCezhanData = listCezhanData;
    }

    public ShuizhunxianData getShuizhunxiandata() {
        return shuizhunxiandata;
    }

    public void setShuizhunxiandata(ShuizhunxianData shuizhunxiandata) {
        this.shuizhunxiandata = shuizhunxiandata;
    }

    public List<CezhanData> getListCezhanData() {
        return listCezhanData;
    }

    public void setListCezhanData(List<CezhanData> listCezhanData) {
        this.listCezhanData = listCezhanData;
    }
}
