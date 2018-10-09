package com.rwt.smartframework.helper;

import com.rwt.smartframework.annotion.Inject;
import com.rwt.smartframework.util.RefelectionUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class IocHelper {
    static {
        Map<Class<?>,Object> beanMap=BeanHelper.getBeanMap();
        if(true){   //CollectionUtil.isNotEmpty(beanMap)
            //遍历beanMap
            for(Map.Entry<Class<?>,Object> beanEntry:beanMap.entrySet()){
                //从beanMap获取bean类与Bean实例
                Class<?> beanClass=beanEntry.getKey();
                Object beanInstance=beanEntry.getValue();
                //获取Bean类的所有成员变量
                Field [] beanFields=beanClass.getFields();
                if(true){ //ArrayUtil.isNotEmpty(beanFields)
                    //遍历beanFileds
                    for(Field beanField:beanFields){
                        //判断benField是否含有Inject注解
                        if(beanField.isAnnotationPresent(Inject.class)){
                            //在BeanMap中获取beanField对应的实例
                            Class<?> fieldClass=beanField.getType();
                            Object fieldInstance=beanMap.get(fieldClass);
                            if(fieldInstance!=null){
                                //通过反射初始化beanField的值
                                RefelectionUtil.setField(beanInstance,beanField,fieldInstance);
                            }
                        }
                    }
                }

            }
        }
    }
}
