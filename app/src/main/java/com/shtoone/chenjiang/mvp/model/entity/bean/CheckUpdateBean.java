package com.shtoone.chenjiang.mvp.model.entity.bean;

/**
 * Author：leguang on 2016/10/27 0027 11:32
 * Email：langmanleguang@qq.com
 */
public class CheckUpdateBean {

    /**
     * status : 0
     * description : 检测更新成功
     * updateInfo : {"newAppUpdateDesc":"升级","appName":"shtoone","newAppUrl":"dataFile/apk/4028ca8158992362015899547e200012.apk","isForceUpdate":0,"newAppVersionName":"1.0","newAppReleaseTime":"2016-11-25 10:33:21","newAppSize":"2173270","newAppVersionCode":1}
     */

    private int status;
    private String description;
    /**
     * newAppUpdateDesc : 升级
     * appName : shtoone
     * newAppUrl : dataFile/apk/4028ca8158992362015899547e200012.apk
     * isForceUpdate : 0
     * newAppVersionName : 1.0
     * newAppReleaseTime : 2016-11-25 10:33:21
     * newAppSize : 2173270
     * newAppVersionCode : 1
     */

    private UpdateInfoBean updateInfo;

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

    public UpdateInfoBean getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(UpdateInfoBean updateInfo) {
        this.updateInfo = updateInfo;
    }

    public static class UpdateInfoBean {
        private String newAppUpdateDesc;
        private String appName;
        private String newAppUrl;
        private int isForceUpdate;
        private String newAppVersionName;
        private String newAppReleaseTime;
        private String newAppSize;
        private int newAppVersionCode;

        public String getNewAppUpdateDesc() {
            return newAppUpdateDesc;
        }

        public void setNewAppUpdateDesc(String newAppUpdateDesc) {
            this.newAppUpdateDesc = newAppUpdateDesc;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getNewAppUrl() {
            return newAppUrl;
        }

        public void setNewAppUrl(String newAppUrl) {
            this.newAppUrl = newAppUrl;
        }

        public int getIsForceUpdate() {
            return isForceUpdate;
        }

        public void setIsForceUpdate(int isForceUpdate) {
            this.isForceUpdate = isForceUpdate;
        }

        public String getNewAppVersionName() {
            return newAppVersionName;
        }

        public void setNewAppVersionName(String newAppVersionName) {
            this.newAppVersionName = newAppVersionName;
        }

        public String getNewAppReleaseTime() {
            return newAppReleaseTime;
        }

        public void setNewAppReleaseTime(String newAppReleaseTime) {
            this.newAppReleaseTime = newAppReleaseTime;
        }

        public String getNewAppSize() {
            return newAppSize;
        }

        public void setNewAppSize(String newAppSize) {
            this.newAppSize = newAppSize;
        }

        public int getNewAppVersionCode() {
            return newAppVersionCode;
        }

        public void setNewAppVersionCode(int newAppVersionCode) {
            this.newAppVersionCode = newAppVersionCode;
        }
    }
}
