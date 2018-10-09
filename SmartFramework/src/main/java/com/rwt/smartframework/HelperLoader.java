package com.rwt.smartframework;

import com.rwt.smartframework.helper.BeanHelper;
import com.rwt.smartframework.helper.ClassHelper;
import com.rwt.smartframework.helper.ControllerHelper;
import com.rwt.smartframework.helper.IocHelper;
import com.rwt.smartframework.util.ClassUtil;

/*
* 加载相应的Helper类
* */
public final class HelperLoader {
    public static void init(){
        Class<?>[] classList={
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        } ;
        for (Class<?> cls:classList){
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
