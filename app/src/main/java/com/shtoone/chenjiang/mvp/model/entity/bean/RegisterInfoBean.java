package com.shtoone.chenjiang.mvp.model.entity.bean;

/**
 * Author：leguang on 2016/10/21 0021 13:54
 * Email：langmanleguang@qq.com
 */
public class RegisterInfoBean {

    /**
     * quanxian :
     * userFullName : 路基
     * success : true
     */

    private String quanxian;
    private String userFullName;
    private boolean success;

    public String getQuanxian() {
        return quanxian;
    }

    public void setQuanxian(String quanxian) {
        this.quanxian = quanxian;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
