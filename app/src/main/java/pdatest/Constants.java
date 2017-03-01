package pdatest;

import static baiduanimation.BuildConfig.BASE_URL;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */

public interface Constants {
    String LOGIN_USER = "user_auth_info_byphone";//登录者信息

    String LOOK_EXCEPTION = "LOOK_EXCEPTION";//是否查看异常
    String LOOK_EXCEPTIONS = "LOOK_EXCEPTIONS";//是否查看异常


    String APP_UPDATE = BASE_URL + "app/getNewest";//检测更新

    String LOGIN = BASE_URL + "common/login";//登录

    String SMS_CODE = BASE_URL + "common/getSmsCode";//登录

    String MAIN_AUTHORIZE = BASE_URL + "common/authorize";//首页（包括一、级菜单内容、权限）
    String GOODS_SHELVES = "1";// 商品上架
    String STORE_REPLENISHMENT = "2";// 卖场补货
    String INVENTORY = "3";// 库存盘点
    String QUERY = "4";// 查询
    String LOCATION_ADJUSTMENT = "5";// 货位调整
    String THE_NEW_LOCATION = "6";// 新增货位

    String  GOODS_SHELVES_URL="";//上架商品的url
}
