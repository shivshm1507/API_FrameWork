package com.jira.APIAutomation;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class AuthenticationInAPI {
	
	@Test
	public void apiAuth() {
		
		given()
		.baseUri("")
		.auth().basic("username", "password")
		.when()
		.get("/emp")
		.then()
		.assertThat().statusCode(200);
		
	}
	
	@Test
	public void bearerTokenAuth() {
		
		given()
		.baseUri("")
		.auth().oauth2("token")
		.when()
		.get("/emp")
		.then()
		.assertThat().statusCode(200);
		
	}

}
