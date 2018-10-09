package com.rwt.smartframework.bean;

import java.util.Map;

/*
* 封装请求参数信息
* */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
    /*
    * 获取所有字段信息
    * */
    public Map<String,Object> getParamMap(){
        return this.paramMap;
    }


}
