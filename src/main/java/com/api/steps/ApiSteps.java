package com.api.steps;

import com.api.controller.ApiController;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;

public class ApiSteps {
    ApiController apiControl = new ApiController();
    @Given("params {string} and Headers {string}")
    public void paramsAndHeaders(String param, String header){
        apiControl.init();
        apiControl.setParam("user",param);
        apiControl.setHeader("content-type","application/json");
        apiControl.setHeader("postman-token", "3eb89b06-9aaf-4392-b92a-995784b9d054");
        apiControl.setHeader("host", "postman-echo.com");
    }
    @When("consume {string} endpoint as a {string}")
    public void consumeEndpointAsA(String endpoint, String restType) {
        apiControl.setEndPoint(endpoint);
        apiControl.GetConsumer();
    }
    @Then("the service response with status {int}")
    public void theServiceResponseWithStatus(int statusCode) {
        apiControl.verifyStatus(statusCode);
    }
    @And("verify the response with the data")
    public void verifyTheResponseWithTheData(Map<String,String> datatable) {
        apiControl.VerifyApiData(datatable);
    }
}
