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
    private Response putUser = null;
    private Response postUser = null;
    private Response deleteUser= null;
    private Response getUser = null;

    @Before("@users")
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    // POST create a new user @user-post
    @Given("the following post request that add one user")
    public void postUser(){
        HashMap<String, String> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("id", "100511");
        bodyRequestMap.put("username", "georgeLucas");
        bodyRequestMap.put("firstName", "George");
        bodyRequestMap.put("lastName", "Lucas");
        bodyRequestMap.put("email", "glucas@petshop.com");
        bodyRequestMap.put("password", "wookiee");
        bodyRequestMap.put("phone", "909090909");
        bodyRequestMap.put("userStatus", "2");

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

    // GET users
    @Given("the following get request which brings us {string}")
    public Response getUser(String username) {
        getUser = given().log().all().get("/user/"+username);
        return getUser;
    }

    @And("the response is 200 for the get user")
    public void statusCodeGetUser() {
        assertEquals("The response is not 200", 200, getUser.statusCode());
    }

    @Then("the body response contains the {string}")
    public Response emailGetUser(String email) {
        getUser = given().log().all().get("/user/"+email);
        return getUser;
    }
}