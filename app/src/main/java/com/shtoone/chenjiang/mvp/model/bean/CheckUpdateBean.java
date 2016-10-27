package com.shtoone.chenjiang.mvp.model.bean;

/**
 * Author：leguang on 2016/10/27 0027 11:32
 * Email：langmanleguang@qq.com
 */
public class CheckUpdateBean {

    /**
     * success : true
     * updateInfo : {"newAppUpdateDesc":"1,优化下载逻辑 2,修复一些bug 3,完全实现强制更新与非强制更新逻辑 4,非强制更新状态下进行下载,默认在后台进行下载 5,当下载成功时,会在通知栏显示一个通知,点击该通知,进入安装应用界面 6,当下载失败时,会在通知栏显示一个通知,点击该通知,会重新下载该应用 7,当下载中,会在通知栏显示实时下载进度,但前提要dialog.setShowProgress(true).","appName":"android检查更新库","newAppUrl":"http://shouji.360tpcdn.com/160914/c5164dfbbf98a443f72f32da936e1379/com.tencent.mobileqq_410.apk","isForceUpdate":1,"newAppVersionName":"1.0.2","newAppReleaseTime":"2016-10-14 12:37","newAppSize":12.300000190734863,"newAppVersionCode":20}
     */

    private boolean success;
    /**
     * newAppUpdateDesc : 1,优化下载逻辑 2,修复一些bug 3,完全实现强制更新与非强制更新逻辑 4,非强制更新状态下进行下载,默认在后台进行下载 5,当下载成功时,会在通知栏显示一个通知,点击该通知,进入安装应用界面 6,当下载失败时,会在通知栏显示一个通知,点击该通知,会重新下载该应用 7,当下载中,会在通知栏显示实时下载进度,但前提要dialog.setShowProgress(true).
     * appName : android检查更新库
     * newAppUrl : http://shouji.360tpcdn.com/160914/c5164dfbbf98a443f72f32da936e1379/com.tencent.mobileqq_410.apk
     * isForceUpdate : 1
     * newAppVersionName : 1.0.2
     * newAppReleaseTime : 2016-10-14 12:37
     * newAppSize : 12.300000190734863
     * newAppVersionCode : 20
     */

    private UpdateInfoBean updateInfo;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
        private float newAppSize;
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

        public float getNewAppSize() {
            return newAppSize;
        }

        public void setNewAppSize(float newAppSize) {
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
