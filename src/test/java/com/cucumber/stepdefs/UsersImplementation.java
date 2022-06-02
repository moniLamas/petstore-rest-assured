package com.cucumber.stepdefs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


import java.io.Serializable;
import java.util.HashMap;

public class UsersImplementation implements Serializable {
    private Response putUsers = null;
    private Response postUser = null;
    private Response deleteUsers = null;

    @Before("@users")
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    // POST crear un nuevo usuario
    @Given("the following post request that add one user")
    public void postUser(){
        HashMap<String, String> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("id", "100511");
        bodyRequestMap.put("username", "kateMiles");
        bodyRequestMap.put("firstName", "Kate");
        bodyRequestMap.put("lastName", "Miles");
        bodyRequestMap.put("email", "kmiles@petshop.com");
        bodyRequestMap.put("password", "phillie");
        bodyRequestMap.put("phone", "909090909");
        bodyRequestMap.put("userStatus", "0");

        postUser =
                given().contentType(ContentType.JSON).body(bodyRequestMap).post("/user");
    }

    @And("the response is 200 for the post user")
    public void validateResponsePostUser() {
        assertEquals("The response is not 200", 200, postUser.statusCode());
    }

    @And("the body response contains key {string}")
    public void validateResponsePostKeyBody(String id){
        JsonPath jsonPathUser = new JsonPath(postUser.body().asString());
        String jsonUser = jsonPathUser.getString("message");
        System.out.println("id: " + jsonUser);
        assertEquals("The value of the id field is not what is expected",id,jsonUser);
    }

    @Then("the body response contains the {string} of the user created")
    public void validateResponsePostBodyValueName(String message) {
        JsonPath jsonPathUsers = new JsonPath(postUser.body().asString());
        String jsonUsers=jsonPathUsers.getString("message");
        assertEquals("The value of the name field is not what is expected",message,jsonUsers);
    }

}