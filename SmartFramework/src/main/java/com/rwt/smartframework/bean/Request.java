package com.rwt.smartframework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

/*
* 封装请求信息
* */
public class Request {

    private  String requestMethod;

    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    @Override
    public boolean equals(Object o) {

        return EqualsBuilder.reflectionEquals(this,o);
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }
}
