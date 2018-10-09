package com.rwt.smartframework.helper;

import com.rwt.smartframework.util.RefelectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
* Bean操作帮助类
* */

public class BeanHelper {

    private static final Map<Class<?>,Object> BEAN_MAP=new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> classSet=ClassHelper.getBeanClassSet();
        for (Class<?> cls:classSet){
            Object object=RefelectionUtil.newInstance(cls);
            BEAN_MAP.put(cls,object);
        }
    }

    /*
    * 获取Bean映射
    * */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }
    /*
    * 获取Bean实例
    * */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls)){
           throw new RuntimeException("can not get bean by class"+cls);
        }
        return (T) BEAN_MAP.get(cls);

    }
}
