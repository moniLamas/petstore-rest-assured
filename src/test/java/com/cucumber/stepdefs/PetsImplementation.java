package com.cucumber.stepdefs;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class PetsImplementation implements Serializable {
    private Response postPet = null;
    private Response putPet = null;
    private Response deletePet = null;

    @Before
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    // Get list All pets
    @Given("the following get request that brings us the pets list")
    public Response getAllPets(){

        Response responseGetAllPets = given().log().all().get("/pet/findByStatus?status=available&status=pending&status=sold");
        return responseGetAllPets;
    }
    @Then("the response is 200")
    public void validateResponse(){
        assertTrue("The response is not 200",getAllPets().statusCode()==200);
    }


    // Get list pets availables
    @Given("the following get request that brings us the pets availables")
    public Response listPetAvailable() {
         Response responseListPetAvalible = given().param("status", "availables").get("/pet/findByStatus");
         return responseListPetAvalible;
    }
    /*@And("response message")
    public void validateMessageResponse(){
        listPetAvailable().then().body("data",contains("status", "available") );
    }*/
    @Then("the response is 200 for avalaibles")
    public void validateResponseAvalaibles(){
        assertTrue("The response is not 200",listPetAvailable().statusCode()==200);
    }

    //Create new pet
    @Given("the following post that add pet")
    public void postPet() throws IOException {
        String JSON_SOURCE = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"puppi\"\n" +
                "  },\n" +
                "  \"name\": \"puppi\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";
        Map<String,Object> result =
                new ObjectMapper().readValue(JSON_SOURCE, HashMap.class);
        /*HashMap bodyRequestMap = new HashMap();
        bodyRequestMap.put("id", "5");
        bodyRequestMap.put("category", new HashMap());
        ((Map)bodyRequestMap.get("category")).put("id", 19);
        ((Map)bodyRequestMap.get("category")).put("name", "puppi");
        bodyRequestMap.put("name", "doggie");
        bodyRequestMap.put("photoUrls", new Object[] {"url"});
        bodyRequestMap.put("tags", new ArrayList[] {new HashMap()});
        ((Map)bodyRequestMap.get("tags")).put("id", 0);
        ((Map)bodyRequestMap.get("tags")).put("name", "string");
        bodyRequestMap.put("status", "available");*/
        postPet =
                given().contentType(ContentType.JSON).body(result).post("/pet");
    }

    @And("the response is 200 for the post pet")
    public void validateResponsePost() {
        assertTrue("The response is not 200",postPet.statusCode()==200);
    }

    @Then("the body response contains the {string} of the pet created")
    public void validateResponsePostBodyValueName(String updateName) {
        JsonPath jsonPathPet = new JsonPath(postPet.body().asString());
        String jsonPet = jsonPathPet.getString("name");
        assertEquals("The value of the name field is not what is expected",updateName,jsonPet);
    }


}
