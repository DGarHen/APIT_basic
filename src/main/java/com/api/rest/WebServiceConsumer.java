package com.api.rest;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonArray;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matcher;
import org.junit.Assert;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class WebServiceConsumer {
    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request = given();
    private String endpoint;
    private JsonObject requestBody = new JsonObject();
    private JsonArray requestBodyArray = new JsonArray();

    public void setEndpoint(String proEndpoint) {
        this.endpoint = "postman-echo.com/"+proEndpoint;
    }

    public void setParams(String tag, Object value) {
        request.param(tag, value);
    }

    public void setParamsWithPost(String tag, Object value) {
        request.queryParam(tag, value);
    }

    public void setParamsWhenBody(String tag, Object value) {
        request.queryParam(tag, value);
    }

    public void setHeader(String tag, Object value) {
        request.header(tag, value);
    }

    public void consumeRestGet() {
        response = request.when().get(endpoint);
    }

    public void consumeRestPost() {
        if (requestBody.size() != 0) {
            request.body(requestBody.toString());
            System.out.println(requestBody.toString());
        }else if(requestBodyArray.size() != 0){
            request.body(requestBodyArray.toString());
            System.out.println(requestBodyArray.toString());
        }
        response = request.when().post(endpoint);
    }

    public void consumeFreeRestPost(String body) {
        request.body(body);
        System.out.println(body);
        response = request.when().post(endpoint);
    }

    public void consumeRestUpdate() {
        if (requestBody.size() != 0) {
            request.body(requestBody.toString());
            System.out.println(requestBody.toString());
        }else if(requestBodyArray.size() != 0){
            request.body(requestBodyArray.toString());
            System.out.println(requestBodyArray.toString());
        }
        response = request.when().patch(endpoint);
    }

    public void consumeRestPostUsingJson(String body) {
        request.body(body);
        System.out.println(body);
        response = request.when().post(endpoint);
    }

    public void obtainResponse() {
        response.prettyPrint();
        json = response.then();
    }

    public void setBodyToPostString(String tag, Object value) {
        switch (value.getClass().toString()) {
            case "class java.lang.String":
                requestBody.add(tag, String.valueOf(value));
                break;
            case "class java.lang.Integer":
                requestBody.add(tag, (Integer) value);
                break;
            case "class com.google.gson.JsonArray":
                if (tag.isEmpty() || tag==null) {
                    requestBodyArray = (JsonArray) value;
                } else {
                    requestBody.add(tag, (JsonArray) value);
                }
                break;
        }
    }

    public void setBodyToPostObject(String tag, JsonObject value) {
        requestBody.add(tag, value);
    }

    public void setBodyToPostObject(String tag, JsonArray value) {
        requestBody.add(tag, value);
    }

    public void setFormDatatoPostString(String tag, String value) {
        request.multiPart(tag, value);
    }

    public void setFormDataPostFile(String tag, String value) {
        File photo = new File("");
        request.multiPart(tag, photo);
    }

    public JsonArray setJsonArray(JsonArray object, JsonObject value) {
        try {
            object.add(value);
            return object;
        } catch (NullPointerException E) {
            JsonArray array = new JsonArray();
            array.add(value);
            return array;
        }
    }

    public void response_status_should_be(int status) {
        try {
            json.assertThat().statusCode(status);
        } catch (Exception e) {
            Assert.fail("Status Response is not the expected" + status);
        }

    }

    public void array_entry_contains(Map.Entry<String, String> field) {
        if (StringUtils.isNumeric(field.getValue())) {
            json.body(field.getKey(), hasItem(Integer.parseInt(field.getValue())));
        } else if (field.getValue().contains("null")) {
            json.body(field.getKey(), hasItem((Collection<Matcher<? super Object>>) null));
        } else if (field.getValue().contains("true") || field.getValue().contains("false")) {
            json.body(field.getKey(), hasItem(Boolean.valueOf(field.getValue())));
        } else {
            json.body(field.getKey(), hasItem(field.getValue()));
        }
    }

    /*public void object_array_contains(Map<String, Object> object, String path) {  VERIFY NEW WAY TO EXPRESS THIS METHOD
        for (Map.Entry<String, Object> field : object.entrySet()) {
            json.content(path + "." + field.getKey(), Matchers.equalTo(field.getValue()));
        }
    }*/

    public void single_entry_contains(Map.Entry<String, String> field) {
        if (StringUtils.isNumeric(field.getValue())) {
            json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
        } else if (field.getValue().contains("null")) {
            json.body(field.getKey(), equalTo((Collection<Matcher<? super Object>>) null));
        } else if (field.getValue().contains("true") || field.getValue().contains("false")) {
            json.body(field.getKey(), equalTo(Boolean.valueOf(field.getValue())));
        } else {
            json.body(field.getKey(), equalTo(field.getValue()));
        }
    }

    public void single_entry_contains_string(Map.Entry<String, String> field) {
        json.body(field.getKey(), equalTo(field.getValue()));
    }

    public void single_entry_may_contains(Map.Entry<String, String> field) {
        json.body(field.getKey(), containsString(field.getValue()));
    }

    public void array_entry_contains_string(Map.Entry<String, String> field) {
        json.body(field.getKey(), hasItem(field.getValue()));
    }

    public void array_entry_no_contains(Map.Entry<String, String> field) {
        json.body(field.getKey(), not(hasItem(field.getValue())));
    }

    public void array_entry_count(Map.Entry<String, String> field) {
        json.body(field.getKey() + ".size()", equalTo(Integer.parseInt(field.getValue())));
    }

    public void array_entry_count(String path, String value) {
        json.body(path + ".size()", equalTo(Integer.parseInt(value)));
    }

    public void array_entry_greater_than(Map.Entry<String, String> field) {
        json.body(field.getKey() + ".size()", greaterThan(Integer.parseInt(field.getValue())));
    }

    public void array_entry_less_than(Map.Entry<String, String> field) {
        json.body(field.getKey() + ".size()", lessThan(Integer.parseInt(field.getValue())));
    }

    public void array_entry_contains_double(Map.Entry<String, String> field) {
        json.body(field.getKey(), hasItem(Double.parseDouble(field.getValue())));
    }

    public void response_is_equals_to(String jsonbase) {
        JsonPath expectedJson = new JsonPath(new File(""));
        json.assertThat().body("", equalTo(expectedJson.getMap("")));
    }

    public void array_entry_contains_float(Map.Entry<String, String> field) {
        json.body(field.getKey(), hasItem(Float.parseFloat(field.getValue())));
    }

    public void single_entry_not_empty(Map.Entry<String, String> field) {
        json.body(field.getKey(), not(empty()));
    }

    public void single_entry_contains_double(Map.Entry<String, String> field) {
        Double val = Double.parseDouble(field.getValue());
        json.body(field.getKey(), equalTo(val.floatValue()));
    }

    public Object single_entry_extract(String path) {
        return json.extract().path(path);
    }

    public void array_entry_not_empty(Map.Entry<String, String> field) {
        json.body(field.getKey(), not(hasItem(empty())));
    }

    public void single_entry_contains_ignore_case(Map.Entry<String, String> field) {
        json.body(field.getKey(), equalToIgnoringCase(field.getValue()));
    }

    public JsonObject getJsonBody() {
        return requestBody;
    }

    public <T> List<T> getExtractMultiFeeList(String data, Class<T> genericType) {
        return json.extract().jsonPath().getList(data, genericType);
    }

    public <T> List<T> getExtractPaymentTemplateFeeList(String data, Class<T> genericType) {
        return json.extract().jsonPath(new JsonPathConfig(JsonPathConfig.NumberReturnType.DOUBLE)).getList(data, genericType);
    }
}
