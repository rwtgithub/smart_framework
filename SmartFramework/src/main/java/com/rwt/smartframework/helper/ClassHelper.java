package com.rwt.smartframework.helper;

import com.rwt.smartframework.annotion.Controller;
import com.rwt.smartframework.annotion.Service;
import com.rwt.smartframework.util.ClassUtil;
import com.rwt.smartframework.util.ConfigHelper;

import java.util.HashSet;
import java.util.Set;

public class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage=ConfigHelper.getAppBasePackage();
        CLASS_SET=ClassUtil.getClassSet(basePackage);
    }
    /*
    * 获取应用包名下的所有类
    * */
    public static Set<Class<?>> getBeanClassSet(){
        return CLASS_SET;
    }

    /*
    * 获取应用包名下的所有Service类
    * */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> serviceBeanSet=new HashSet<Class<?>>();
        for (Class<?> cls:CLASS_SET){
            if(cls.isAnnotationPresent(Service.class)){
                serviceBeanSet.add(cls);

            }
        }
        return  serviceBeanSet;
    }
    /*
    * 获取应用包名下的所有Controller类
    * */
    public  static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> controllerClassSet =new HashSet<Class<?>>();
        for(Class<?> cls:CLASS_SET){
            if(cls.isAnnotationPresent(Controller.class)){
                controllerClassSet.add(cls);
            }
        }
        return controllerClassSet;
    }
}
