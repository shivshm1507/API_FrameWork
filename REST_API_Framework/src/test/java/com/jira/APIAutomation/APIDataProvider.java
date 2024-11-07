package com.jira.APIAutomation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class APIDataProvider {

	
	@DataProvider(name = "userData")
	public Object[][] createUserData() {
	    return new Object[][] {
	        {"user1", "password1"},
	        {"user2", "password2"}
	    };
	}
	
	@Test(dataProvider = "userData")
	public void testLogin(String username, String password) {
	    given()
	        .param("username", username)
	        .param("password", password)
	        .when()
	        .post("/login")
	        .then()
	        .statusCode(200);
	}
}
