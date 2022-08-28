package com.api.controller;

import com.api.rest.WebServiceConsumer;

import java.util.Map;

public class ApiController {
    WebServiceConsumer api;
    public void init(){
        api  = new WebServiceConsumer();
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
    public void GetConsumer() {
       api.consumeRestGet();
       api.obtainResponse();
    }

    public void VerifyApiData(Map<String, String> datatable) {
        for (Map.Entry<String,String> entry : datatable.entrySet()){
            api.single_entry_contains(entry);
        }
    }
}
