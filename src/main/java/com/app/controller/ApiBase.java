package com.app.controller;

import com.app.actions.RestActions;

import java.util.Map;

public class ApiBase {
    protected RestActions api;
    public void init(){
        api  = new RestActions();
    }

    public void setEndPoint(String endPoint){
        api.setEndpoint(endPoint);
    }

    public void setParam(String tag, String value){
        api.setParams(tag,value);
    }

    public void setHeader(String tag, String value){
        api.setHeader(tag,value);
    }

    public void verifyStatus(int status){
        api.response_status_should_be(status);
    }
    public void GetConsumer(String endpoint) {
       setEndPoint(endpoint);
       api.consumeRestGet();
       api.obtainResponse();
    }

    public void setNormalHeaders(){
        setHeader("content-type","application/json");
        setHeader("postman-token", "3eb89b06-9aaf-4392-b92a-995784b9d054");
    }

    public void setHeaderGetHost(){
        setNormalHeaders();
        setHeader("host", "postman-echo.com");
    }



    public void setParamGetHost(String param, String param2){
        setParam("user",param);
        setParam("text",param2);
    }


    public void restAssuredExample() {
        init();
        api.restAssuredExample();
    }
}
