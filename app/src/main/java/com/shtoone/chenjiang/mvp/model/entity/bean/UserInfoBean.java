package com.shtoone.chenjiang.mvp.model.entity.bean;


/**
 * 用户实体类
 * Created by leguang on 2016/5/11 0031.
 */
public class UserInfoBean {

    /**
     * userPhoneNum : 13888888888
     * status : 0
     * description : 登录成功
     * userId : 4028ca815848a903015849383a1d002b
     * dept : {"orgId":"402880e447e99cf10147e9a03b320003","orgName":"昌吉赣客专"}
     * userFullName : test
     */

    private String userPhoneNum;
    private int status;
    private String description;
    private String userId;
    /**
     * orgId : 402880e447e99cf10147e9a03b320003
     * orgName : 昌吉赣客专
     */

    private DeptBean dept;
    private String userFullName;

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public DeptBean getDept() {
        return dept;
    }

    public void setDept(DeptBean dept) {
        this.dept = dept;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public static class DeptBean {
        private String orgId;
        private String orgName;

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }
    }
}
