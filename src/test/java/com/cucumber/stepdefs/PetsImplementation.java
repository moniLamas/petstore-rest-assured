package com.cucumber.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.Serializable;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertTrue;

public class PetsImplementation implements Serializable {
    private Response putUsers = null;
    private Response postUsers = null;
    private Response deleteUsers = null;

    @Before
    public void before(){
        RestAssured.baseURI = "https://reqres.in/api/";
    }

    @Given("the following get request that brings us the users")
    public Response getUsers(){
        //Response responseGetUsers = given().baseUri("https://reqres.in/api/users?page=2").get();
        // given().param("page", 2).baseUri("https://reqres.in/api/users").get();
        Response responseGetUsers = given().log().all().param("page", 2).get("/users");
        return responseGetUsers;
    }

    @And("the response is 200")
    public void validateResponse(){
        assertTrue("The response is not 200",getUsers().statusCode()==200);
    }

    @And("the body response contains the corresponding ids")
    public void validateUserIds(){
        getUsers().then().body("data.id", hasItems(7,8,9,10,11,12));
    }
    @Then("the total page contains {int}")
    public void validateTotalPages(Integer page){
        getUsers().then().body("total_pages", equalTo(page));
    }
}