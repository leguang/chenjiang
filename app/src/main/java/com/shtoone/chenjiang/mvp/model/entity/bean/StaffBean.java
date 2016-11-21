package com.shtoone.chenjiang.mvp.model.entity.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Author：leguang on 2016/11/2 0002 13:54
 * Email：langmanleguang@qq.com
 */
public class StaffBean extends DataSupport {

    /**
     * obs : [{"id":"40288bf15803d149015803d461bf0006","userName":"13254586549","userPhone":""},{"id":"40288bf3576f8c2801576f8f57d90001","userName":"13111111111","userPhone":""}]
     * status : 0
     * description : 司镜人员数据下载成功
     */

    private int status;
    private String description;
    /**
     * id : 40288bf15803d149015803d461bf0006
     * userName : 13254586549
     * userPhone :
     */

    private List<ObsBean> obs;

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

    public List<ObsBean> getObs() {
        return obs;
    }

    public void setObs(List<ObsBean> obs) {
        this.obs = obs;
    }

    public static class ObsBean {
        private String id;
        private String userName;
        private String userPhone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
}
