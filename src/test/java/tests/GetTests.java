package tests;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restassured.HttpController;
import restassured.JsonParser;

import static org.hamcrest.Matchers.*;

public class GetTests {
	
	SoftAssert sf;
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://reqres.in";  
	}
	
	@BeforeMethod
	public void beforeMethod() {
		sf = new SoftAssert();
	}
	
	@Test
	public void getResReqTest_UsingFramework() {
		//Comment here
		Reporter.log("Calling get method");
		HttpController http = new HttpController();
		Response resp = http.get("/api/users/2");
		
		Reporter.log("Converting response to JSON path");
		JsonParser jsparser = new JsonParser();
		JsonPath jspath =jsparser.respToJson(resp);
		
		Reporter.log("Comparing expected and actual result");
		sf.assertEquals(jspath.get("data.id"), 1);
		sf.assertEquals(jspath.get("data.email"), "janet.weaver@reqres.in");
		sf.assertEquals(jspath.get("data.first_name"), "Janet");
		sf.assertEquals(jspath.get("data.last_name"), "Weaver");
		sf.assertAll();
	}
	
	@Test
	public void getReqResTest_usingTestNG() {
		Response resp = given().when().get("/api/users/2").thenReturn();
		sf.assertEquals(resp.statusCode(), 200);
		//System.out.println(resp.jsonPath().get("data.id"));
		sf.assertEquals(resp.body().jsonPath().get("data.id"), 2);
		sf.assertEquals(resp.body().jsonPath().get("data.email"), "janet.weaver@reqres.in");
		sf.assertEquals(resp.body().jsonPath().get("data.first_name"), "Janet");
		sf.assertEquals(resp.body().jsonPath().get("data.last_name"), "Weaver");
		sf.assertAll();
	}

	
	@Test
	public void getReqResTest() {
		
		given().when().get("/api/users/2")
		.then().assertThat()
		.statusCode(200)
		.and().body("data.id", equalTo(2))
		.and().body("data.email", equalTo("janet.weaver@reqres.in"));
	}
	

}
