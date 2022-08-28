package com.app.stepdefinitions;

import com.app.controller.PokemonApi;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;

public class PokemonSteps {
    PokemonApi pokemon = new PokemonApi();
    @Given("params {string} and Headers {string}")
    public void paramsAndHeaders(String param, String param2){
        pokemon.init();
        pokemon.setParamGetHost(param, param2);
        pokemon.setHeaderGetHost();
    }
    @When("consume {string}")
    public void consumeEndpointAsA(String endpoint) {
        pokemon.GetConsumer(endpoint);
    }
    @Then("the service response with status {int}")
    public void theServiceResponseWithStatus(int statusCode) {
        pokemon.verifyStatus(statusCode);
    }
    @And("verify the response with the data")
    public void verifyTheResponseWithTheData(Map<String,String> datatable) {
        pokemon.VerifyApiData(datatable);
    }

    @Given("restassured example")
    public void restassuredExample() {
        pokemon.restAssuredExample();
    }
}
