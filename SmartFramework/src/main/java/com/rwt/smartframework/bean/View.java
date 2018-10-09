package com.rwt.smartframework.bean;

import java.util.HashMap;
import java.util.Map;

/*
* 返回视图对象View
* */
public class View {

    private String path;

    private Map<String,Object> modelMap;

    public View(String path) {
        this.path = path;
        modelMap=new HashMap<String, Object>();
    }

    public View addModel(String key,String value){
        modelMap.put(key,value);
        return this;

    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModelMap() {
        return modelMap;
    }
}
