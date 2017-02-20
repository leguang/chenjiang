package com.shtoone.chenjiang.common;


import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.utils.DirectoryUtils;

import java.io.File;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class Constants {
    private static final String TAG = Constants.class.getSimpleName();

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
    public static final String BASE_URL = "http://192.168.11.113:8081/ljcjqms/";

    //登录地址
    public static final String LOGIN_URL = BASE_URL + "app.do?AppLogin&userName=%1&userPwd=%2&OSType=2";
    public static final String DOMAIN_1 = "shtoone.com";
    public static final String DOMAIN_2 = "sh-toone";
    public static final String ISFIRSTENTRY = "is_first_entry";
    public static final String IS_INITIALIZE = "is_initialize";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String REGISTER_CODE = "register_code";
    public static final String USER_ID = "user_id";
    public static final String USER_INFO_BEAN = "user_info_bean";

    //网络链接超时时间
    public static final int DEFAULT_TIMEOUT = 1;

    //作为登录的参数，固定这个写法
    public static final String OSTYPE = "2";
    public static final String PRESS_AGAIN = "再按一次退出";
    public static final String ENCRYPT_KEY = "leguang";

    public static final String ABOUTAPP = "http://note.youdao.com/share/?id=37e5d8602c49af15d7589d7f91bd548b&type=note";
    public static final String ABOUTCOMPANY = "http://en.ccccltd.cn/ccccltd/";

    //分页查询
    public static final int PAGE_SIZE = 30;

    //下载页面
    public static final String UPDATA_TIME = "updata_time";

    //预设水准线
    public static final String YUSHESHUIZHUNXIAN = "yusheshuizhunxian";
    //工点
    public static final String GONGDIAN = "gongdian";
    //基点
    public static final String JIDIAN = "JIDIAN";
    //人员
    public static final String STAFF = "STAFF";

    //EventBus系列,值是随便取，只要不相同即可。**********************************************************************
    public static final int EVENT_FINISH_LAUNCH = 0;
    //刷新主界面的列表
    public static final int EVENT_REFRESH = 1;
    //保存测量参数规范
    public static final int EVENT_SAVE_PARAM = 2;
    //重置二等水准测量规范
    public static final int EVENT_RESET_SECOND_CLASS = 3;
    //查看水准线历史记录
    public static final int EVENT_SHUIZHUNXIAN = 4;

    //EventBus系列,值是随便取，只要不相同即可。**********************************************************************

    //上传模块
    public static final int UPLAND_SUCCESS = 30;
    public static final int UPLAND_FAIL = 31;
    public static final String FROMTO_MEASURED_DATA_FRAGMENT = "measured_data_fragment";
    public static final String FROMTO_CHECK_MEASURE_RIGHT_FRAGMENT = "check_measure_right_fragment";
    public static final String FROMTO_X = "x";
    public static final String FROMTO_Y = "y";

    public static final String FROM_TO = "from_to";

    public static final int FROM_SPLASH = 0;
    public static final int FROM_MAIN = 1;

    //测量状态
    public static final String measure_state_daiceliang = "0";
    public static final String measure_state_daipingcha = "1";
    public static final String measure_state_deleted = "2";

    //水准线的状态:下载下来默认是待编辑
    //            -->编辑完了之后就是待测量（此时就可以点进测量页面）
    //            -->测量完了之后就是待平差（此时点平差就不需要弹框提示）
    //            -->平差完了之后就是待上传（待上传的水准线才会出现在上传页面，同时未上传的水准线也就无法进入待编辑状态，也就无法继续第二次测量了）
    //            -->上传完了之后就又回到待编辑状态，如此周而复始。
    public static final String status_daibianji = "0";
    public static final String status_daiceliang = "1";
    public static final String status_daipingcha = "2";
    public static final String status_daishanchu = "3";

    //标示：0为平台，1为App
    public static final String biaoshi_pingtai = "0";
    public static final String biaoshi_app = "1";

    //蓝牙模块
    public static final String BLUETOOTH_TYPE = "bluetooth_type";
    public static final int BLUETOOTH_CLASSIC = 0;
    public static final int BLUETOOTH_LE = 1;
    public static final String BLUETOOTH_ADDRESS = "bluetooth_address";

    //音频
    public static final int AUDIO_CONNECT = 2;
    public static final int AUDIO_NEXTB = 3;
    public static final int AUDIO_NEXTF = 4;
    public static final int AUDIO_DISCONNECT = 7;
    public static final int AUDIO_WARNING = 8;

    //奇偶站顺序
    public static final int b1 = 1;
    public static final int b2 = 2;
    public static final int f1 = 3;
    public static final int f2 = 4;

    //观测类型
    public static final String BFFB = "BFFB";
    public static final String FBBF = "FBBF";

    //观测方向
    public static final String wangce = "往测";
    public static final String fance = "反测";

}
