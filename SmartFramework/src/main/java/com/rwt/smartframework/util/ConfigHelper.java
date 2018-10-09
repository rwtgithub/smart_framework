package com.rwt.smartframework.util;

import java.util.Properties;
import com.rwt.smartframework.util.ConfigConstant;
/*
* 属性文件帮助类
*
*@author rwt
* @since 1.0.0
* */

public final class ConfigHelper {

    public static final Properties properties=PropsUtil.loadProps(ConfigConstant.CONFIG_FILENAME);

    /*
    * 获取JDBC-DRIVER
    * */
    public static  String getJdbcDriver(){
        return PropsUtil.getString(properties,ConfigConstant.JDBC_DRIVER);
    }
    /*
    * 获取JDBC-URL
    * */
    public static String getJdbcUrl(){
        return PropsUtil.getString(properties,ConfigConstant.JDBC_URL);

    }
    /*
    * 获取JDBC用户名
    * */
    public static String getJdbcUsername(){
        return PropsUtil.getString(properties,ConfigConstant.JDBC_USERNAME);
    }
    /*
    * 获取JDBC密码
    * */
    public static String getJdbcPassword(){
       return PropsUtil.getString(properties,ConfigConstant.JDBC_PASSWORD);

    }
    /*
    * 获取JSP资源路径（默认/WEB-INF/view/）
    * */
    public static String getAppJspPath(){
        return PropsUtil.getString(properties,ConfigConstant.APP_JSP_PATH,"/WEB-INF/view");
    }
    /*
    * 获取静态资源路径（默认/asset/）
    * */
    public static String getAppAssetPath(){
        return PropsUtil.getString(properties,ConfigConstant.APP_ASSET_PATH,"/asset/");

    }
    /*
    * 获取应用基础包
    * */
    public static String getAppBasePackage(){
        return PropsUtil.getString(properties,ConfigConstant.APP_BASE_PACKAGE);
    }



}
