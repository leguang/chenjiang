package com.shtoone.chenjiang.mvp.model.bean;

import org.litepal.crud.DataSupport;

/**
 * Author：leguang on 2016/11/2 0002 13:54
 * Email：langmanleguang@qq.com
 */
public class StaffData extends DataSupport {
    private long id;
    private String staffid;
    private String userName;
    private String userPhone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
