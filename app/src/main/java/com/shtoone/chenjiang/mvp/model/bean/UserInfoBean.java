package com.shtoone.chenjiang.mvp.model.bean;


/**
 * 用户实体类
 * Created by leguang on 2016/5/11 0031.
 */
public class UserInfoBean {

    /**
     * status : 0
     * description : 登录成功
     * userId : 40288bf15803d149015803d461bf0006
     * dept : {"orgId":"40288bf457f5cadd0157f5e5d341001d","orgName":"一分部-项目分部(工区)"}
     * userFullName : 测试人员1
     */

    private int status;
    private String description;
    private String userId;
    /**
     * orgId : 40288bf457f5cadd0157f5e5d341001d
     * orgName : 一分部-项目分部(工区)
     */

    private DeptBean dept;
    private String userFullName;

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
