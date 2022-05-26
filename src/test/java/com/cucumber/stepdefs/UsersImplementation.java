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

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.Serializable;
import java.util.HashMap;

public class UsersImplementation implements Serializable {
    private Response putUsers = null;
    private Response postUsers = null;
    private Response deleteUsers = null;

    @Before
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    // POST crear un nuevo usuario
    @Given("the following post request that add one user")
    public void postUsers(){
// given()
// .accept(ContentType.JSON)
// . body("{\"name\":\"juan\", \"job\":\"developer\"}")
// .post("/users");
        HashMap<String, String> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("id", "101");
        bodyRequestMap.put("username", "kateMiles");
        bodyRequestMap.put("firstName", "Kate");
        bodyRequestMap.put("lastName", "Miles");
        bodyRequestMap.put("email", "kmiles@petshop.com");
        bodyRequestMap.put("password", "phillie");
        bodyRequestMap.put("phone", "909090909");
        bodyRequestMap.put("userStatus", "0");

        postUsers =
                given().contentType(ContentType.JSON).body(bodyRequestMap).post("/user");
    }

    @And("the response is 201 for the post")
    public void validateResponsePost() {
        assertTrue("The response is not 200",postUsers.statusCode()==200);
    }

    @And("the body response contains key id")
    public void validateResponsePostKeyBody(){
        postUsers.then().body("$",hasKey("id"));
    }

    @Then("the body response contains the {string} of the user created")
    public void validateResponsePostBodyValueName(String message) {
        JsonPath jsonPathUsers = new JsonPath(postUsers.body().asString());
        String jsonUsers=jsonPathUsers.getString("message");
        assertEquals("The value of the name field is not what is expected",message,jsonUsers);
    }

}