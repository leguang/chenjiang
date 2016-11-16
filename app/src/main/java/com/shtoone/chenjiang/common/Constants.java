package com.shtoone.chenjiang.common;


import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.utils.DirectoryUtils;

import java.io.File;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class Constants {

    /**
     * 不允许new
     */
    private Constants() {
        throw new Error("Do not need instantiate!");
    }

    //SD卡路径
    public static final String PATH_DATA = DirectoryUtils.getDiskCacheDirectory(BaseApplication.mContext, "data").getAbsolutePath();
    public static final String PATH_NET_CACHE = PATH_DATA + File.separator + "NetCache";
    public static final String PATH_APK_CACHE = PATH_DATA + File.separator + "ApkCache";

    //基地址
    public static final String BASE_URL = "http://192.168.10.35:8081/ljcjqms/";


    //登录地址
    public static final String LOGIN_URL = BASE_URL + "app.do?AppLogin&userName=%1&userPwd=%2&OSType=2";

    public static final String DOMAIN_1 = "shtoone.com";
    public static final String DOMAIN_2 = "sh-toone";
    public static final String ISFIRSTENTRY = "is_first_entry";
    public static final String ISFIRSTGUIDE = "is_first_guide";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String REGISTER_CODE = "register_code";
    public static final String USER_ID = "user_id";

    //网络链接超时时间
    public static final int DEFAULT_TIMEOUT = 3;

    //检测App升级
    public static final int CHECKUPDATE = 0;


    //作为登录的参数，固定这个写法
    public static final String OSTYPE = "2";
    public static final String PRESS_AGAIN = "再按一次退出";
    public static final String ENCRYPT_KEY = "leguang";

    public static final String ABOUTAPP = "http://note.youdao.com/share/?id=37e5d8602c49af15d7589d7f91bd548b&type=note";
    public static final String ABOUTCOMPANY = "http://en.ccccltd.cn/ccccltd/";

    //分页查询
    public static final int PAGE_SIZE = 20;

    //下载页面
    public static final String UPDATA_TIME = "updata_time";

    //工点
    public static final String GONGDIAN = "gongdian";
    //基点
    public static final String JIDIAN = "JIDIAN";
    //人员
    public static final String STAFF = "STAFF";

    //EventBus系列,值是随便取，只要不相同即可
    public static final int EVENT_FINISH_LAUNCH = 10;

}
