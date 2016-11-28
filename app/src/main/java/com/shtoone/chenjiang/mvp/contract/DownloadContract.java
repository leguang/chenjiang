package com.shtoone.chenjiang.mvp.contract;


import com.shtoone.chenjiang.mvp.contract.base.BaseContract;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface DownloadContract {
    interface View extends BaseContract.View {
        void setGongdianMessage(Boolean mBoolean, int intSum, int intIndex);

        void onGongdianCompleted();

        void setDuanmianMessage(Boolean mBoolean, int intSum, int intIndex);

        void onDuanmianCompleted();

        void setCedianMessage(Boolean mBoolean, int intSum, int intIndex);

        void onCedianCompleted();

        void setYusheshuizhunxianMessage(Boolean mBoolean, int intSum, int intIndex);

        void onYusheshuizhunxianCompleted();

        void setJidianMessage(Boolean mBoolean, int intSum, int intIndex);

        void onJidianCompleted();

        void setStaffMessage(Boolean mBoolean, int intSum, int intIndex);

        void onStaffCompleted();
    }

    interface Presenter extends BaseContract.Presenter {

        void downloadGongdian(String userID);

        void downloadDuanmian();

        void downloadCedian();

        void downloadYusheshuizhunxian(String departId);

        void downloadJidian();

        void downloadStaff();
    }
}
