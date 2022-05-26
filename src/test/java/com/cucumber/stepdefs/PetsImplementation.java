package com.cucumber.stepdefs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.Serializable;


public class PetsImplementation implements Serializable {

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


}