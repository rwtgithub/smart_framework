package com.rwt.smartframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RefelectionUtil {

    private static  final Logger logger=LoggerFactory.getLogger(RefelectionUtil.class);

    /*
    * 创建实例
    * */
    public static Object newInstance(Class<?> cls){
        Object obj=null;
        try {
            obj=cls.newInstance();
        } catch (Exception e) {
            logger.error("new instance failure",e);
            throw new  RuntimeException(e);
        }
        return obj;
    }

    /*
    * 调用方法
    *
    * */
    public static Object invokeMethod(Object object, Method method,Object param){
        //TODO
       return null;
    }
    /*
    * 修改成员变量的值
    * */
    public static void setField(Object object, Field field,Object value){
        //TODO
    }
}
