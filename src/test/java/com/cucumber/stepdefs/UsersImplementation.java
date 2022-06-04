package com.cucumber.stepdefs;

import static io.restassured.RestAssured.given;

import static org.junit.Assert.assertEquals;

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
    private Response postUserList = null;
    private Response deleteUser= null;
    private Response getUser = null;
    private Response getLoginUser = null;
    private Response getLogoutUser = null;

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

    @And("the response is {int} for the post user")
    public void validateResponsePostUser(int status) {
        assertEquals("The response is not" + status, 200, postUser.statusCode());
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

    /*@And("the body response contains the {string}")
    public Response emailGetUser(String username) {
        getUser = given().log().all().get("/user/"+username);
        return getUser;
    }*/
    @Then("the response is {int} for the get user")
    public void statusCodeGetUser(int status) {
        assertEquals("The response is not" + status, 200, getUser.statusCode());
    }

    // Get userLogin @userlogin
    @Given("the user login with {string} and {string}")
    public Response userLogin(String username, String password) {

        getLoginUser =  given().log().all().param("username", username).param("password",password)
                .get("/user/login");
        return getLoginUser;
    }

    @Then("the response is {int} for login")
    public void statusCodeUserLogin(int status) {
        assertEquals("The response is not " + status, 200, getLoginUser.statusCode());
    }

    @Given("the user logout the current session")
    public Response userLogout() {
        getLogoutUser = given().get("/user/logout");
        return getLogoutUser;
    }

    @Then("the response is {int} and message is ok")
    public void statusCodeUserLogout(int status) {
        assertEquals("The response is not " + status, 200, getLogoutUser.statusCode());
    }
}