package com.cucumber.stepdefs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.messages.internal.com.fasterxml.jackson.databind.JsonNode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.Serializable;
import java.util.HashMap;


public class PetsImplementation implements Serializable {

    @Before
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    @Given("the following get request that brings us the pets list")
    public Response getAllPets(){

        Response responseGetAllPets = given().log().all().get("/pet/findByStatus?status=available&status=pending&status=sold");
        return responseGetAllPets;
    }
    @Then("the response is 200")
    public void validateResponse(){
        assertTrue("The response is not 200",getAllPets().statusCode()==200);
    }
}