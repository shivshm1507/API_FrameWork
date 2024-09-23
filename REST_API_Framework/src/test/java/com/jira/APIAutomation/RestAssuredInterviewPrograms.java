//There is no fixed limit on how large a JSON data block is or any of the fields.

package com.jira.APIAutomation;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredInterviewPrograms {
	
	RequestSpecification reqspec=null;
	
	@BeforeTest
	public void requestSpecificationExample() {
		
		 reqspec = RestAssured.given();
		
		reqspec.baseUri("http://localhost:8080");
		reqspec.basePath("/employees");
	}

	@Test
	public void methodChainingExample() {
		
	 RestAssured
				.given()
				.spec(reqspec)
				.queryParam("parameterName","parameterValues")
				.header("X-REGION", "NAM")
				.accept("ContentType")
				.log().all()
				.when()
				.get("/get")
				.then()
				.log().all()
				.assertThat()
				.statusCode(200);
	}
	
	@Test
	public void logOnlyIfTestFailsExample() {
		
	 RestAssured.given()
	 .baseUri("http://localhost:8080")
	 .contentType(ContentType.JSON)
	 .queryParam("")
	 .headers("","")
	 .log().all()
	 .when()
	 .get("/employees/get")
	 .then()
	 .log().ifValidationFails()
	 .assertThat()
	 .statusCode(200);
	}
	
	@Test
	public void testGetEmployeeWithPathParam() {
		
		//Considering id as path variable in GET Rest end point url - http://localhost:8080/employee/{id}.

		//	Example : http://localhost:8080/employee/33
		
		Set<String> headers = new HashSet<String>();
		headers.add("X-REGION");
		headers.add("content-type");
		
		Response res = RestAssured.given()
		.baseUri("http://localhost:8080")
		.basePath("/employees")
		.pathParam("id", "33")
		.header("X-REGION", "NAM")
		.// blacklist headers
		config(
				config.logConfig(LogConfig.logConfig().blacklistHeaders(headers)))
		.contentType(ContentType.JSON)
		.log().ifValidationFails()
		.when()
		.get("/getEmployees")
		.then()
		.log().ifValidationFails()
		.assertThat().statusCode(200)
		.extract().response();
	}
	
	/*
	 { "company": {
   "employee": [
    { "id": 1,
      "name": "TechGeekNextUser1",
      "role": "Admin"
    },
    { "id": 2,
      "name": "TechGeekNextUser2",
      "role": "User"
    },
    { "id": 3,
      "name": "TechGeekNextUser3",
      "role": "User"
    }
  ]
  }
 }*/
	
	@Test
	public void employeesIdsFrom_15_to_300() {
		
		Response res = given().request(Method.GET,"/all");	
		
		JsonPath jsonpath = res.jsonPath();		
		jsonpath.get("company.employee.findAll{employee -> employee.id>=15 && employee.id <=300}");
	}
	
	
	
	@Test
	public void POST_Request_In_RestAssured() {
		
		JSONObject jobj = new JSONObject();
		jobj.put("name", "Arjun");
		jobj.put("Role", "Software Engineer");
		
		Response response = given()
		.baseUri("http://localhost:8080")
		.headers("X-REGION", "NAM")
		.contentType(ContentType.JSON)
		.body(jobj)
		.log().all()
		.when()
		.post("/get/employees")
		.then()
		.log().ifValidationFails()
		.assertThat()
		.statusCode(200)
		
		.body("Name", equalTo("Arjun")) // This method is coming from hamcrest package
		.body("Role", equalTo("Software Engineer"))
		.extract().response();
		System.out.println(response.asPrettyString());
		
	}
	
	@Test
	public void getJSONArraySize() {
		
		RestAssured.baseURI="http://localhost:8080";
		
		Response res = RestAssured.given().request(Method.GET,"/employee/all");
		
		List<Object> list = res.jsonPath().getList("Employee.ID");
		System.out.println(list.size());
		
	}
	
	    @Test
	    public void testLogIfError() {
	      Response res =   given().
	        baseUri("http://localhost:8080").
	        header("X-REGION", "NAM").
	        log().all().
	        when().
	        get("/employees").
	        then().
	        log().ifError().
	        assertThat().
	        statusCode(200).extract().response();
	    }
}
