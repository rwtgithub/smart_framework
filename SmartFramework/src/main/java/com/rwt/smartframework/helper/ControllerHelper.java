package com.rwt.smartframework.helper;

import com.rwt.smartframework.annotion.Action;
import com.rwt.smartframework.bean.Handler;
import com.rwt.smartframework.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
* 控制器帮助类
* */
public class ControllerHelper {
    /*
    * 用于存放请求与处理器的映射关系
    * */
    private static final Map<Request,Handler> ACTION_MAP=new HashMap<Request,Handler>();

    static {
        Set<Class<?>> controllerClassSet=ClassHelper.getControllerClassSet();
        if(true){ //CollectionUtil.isNotEmpty();
            //遍历所有Controller
            for (Class cls:controllerClassSet){
                Method [] actionMethods=cls.getDeclaredMethods();
                if(true){  //CollectionUtil.isNotEmpty()
                    //遍历处理Controller所有methods
                    for(Method method:actionMethods){

                        if(method.isAnnotationPresent(Action.class)){
                            //从Action中获取URL映射规则
                            Action action=method.getAnnotation(Action.class);
                            String mapping=action.value();
                            if(mapping.matches("\\w+:/\\w*")){
                                String [] array=mapping.split(":");
                                if(array.length==2){//&ArrayUtil.isNotEmpty()
                                    String requestMethod=array[0];
                                    String requestPath=array[1];
                                    Request request=new Request(requestMethod,requestPath);
                                    Handler handler=new Handler(cls,method);
                                    ACTION_MAP.put(request,handler);
                                }

                            }

                        }
                    }
                }
            }

        }

    }

    /*
    * 获取Handler
    * */
    public  static Handler gerHandler(String requestMethod,String requestPath){
        Request request=new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }

}
