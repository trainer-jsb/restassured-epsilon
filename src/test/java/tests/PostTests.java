package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restassured.HttpController;
import restassured.JsonParser;

import static org.hamcrest.Matchers.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class PostTests {
	
	SoftAssert sf;
	String strBody = "{\r\n"
			+ "    \"name\": \"jaskirat\",\r\n"
			+ "    \"job\": \"testing\"\r\n"
			+ "}";
	String filepath = "src/test/resources/post.json";
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://reqres.in";  
	}
	
	@BeforeMethod
	public void beforeMethod() {
		sf = new SoftAssert();
	}
	
	@Test
	public void posrResReqTest_UsingFramework() throws FileNotFoundException, IOException, ParseException {
		HttpController http = new HttpController();
		JsonParser jsparse = new JsonParser();
		JSONObject expected =  jsparse.returnJsonObject(filepath);
		
		Response resp = http.post("/api/users", expected.toString());

		JsonPath actual = jsparse.respToJson(resp);
		
		sf.assertEquals(resp.statusCode(), 201);
		sf.assertEquals(expected.get("name"), expected.get("name"));
		sf.assertEquals(expected.get("job"), expected.get("job"));
		sf.assertTrue(actual.get("id").toString().length()>0, "FAIL | ID was " +actual.get("id").toString());
		sf.assertAll();
	}
	
	@Test
	public void postResReqTest_UsingTestNG() {
		Response resp = given().body(strBody).contentType(ContentType.JSON)
		.when().post("/api/users").thenReturn();
		sf.assertEquals(resp.statusCode(), 201);
		sf.assertEquals(resp.body().jsonPath().get("name"), "jaskirat");
		sf.assertEquals(resp.body().jsonPath().get("job"), "testing");
		sf.assertTrue(resp.body().jsonPath().get("id").toString().length()>0, "FAIL | ID was " +resp.body().jsonPath().get("id").toString());
		sf.assertAll();
	}
	
	@Test
	public void postReqResTest() {
		given().body(strBody).contentType(ContentType.JSON)
		.when().post("/api/users")
		.then().assertThat()
		.statusCode(201)
		.and().body("name", equalTo("jaskirat"))
		.and().body("job", equalTo("testing"))
		.and().body("id", notNullValue());
	}
	

}
