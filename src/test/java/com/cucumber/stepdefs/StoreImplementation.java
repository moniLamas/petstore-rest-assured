package com.cucumber.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;


import io.cucumber.java.Before;


import java.io.Serializable;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;


public class StoreImplementation implements Serializable {

    @Before("@store")
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/store/";
    }

    //Get inventory
    @Given("the following breeding application that brings us the inventory")
    public Response getInventory() {
        return given().log().all().get("/inventory");
    }

    @Then("the response is 200 for inventory")
    public void validateResponseInventory(){
        assertEquals("The response is not 200", 200, getInventory().statusCode());
    }

}