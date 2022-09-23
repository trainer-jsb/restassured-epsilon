package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

public class PutTests {
	SoftAssert sf; 
	String strBody = "{\r\n"
			+ "    \"name\": \"jaskirat_updated\",\r\n"
			+ "    \"job\": \"developer\"\r\n"
			+ "}";
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://reqres.in";
	}
	
	@BeforeMethod
	public void beforeMethod() {
		sf = new SoftAssert();
	}
	
	@Test
	public void putReqResTest_usingTestNG() {
		Response resp = given().body(strBody).contentType(ContentType.JSON)
		.when().put("/api/users/2")
		.thenReturn();
		sf.assertEquals(resp.statusCode(), 200);
		sf.assertEquals(resp.body().jsonPath().get("name"), "jaskirat_updated");
		sf.assertEquals(resp.body().jsonPath().get("job"), "developer");
		sf.assertTrue(resp.body().jsonPath().get("updatedAt").toString().length()>0, "FAIL | ID was " +resp.body().jsonPath().get("updatedAt").toString());
		sf.assertAll();
	}
	
	@Test
	public void putReqResTest() {
		given().body(strBody).contentType(ContentType.JSON)
		.when().put("/api/users/2")
		.then().assertThat()
		.statusCode(200)
		.and().body("name", equalTo("jaskirat_updated"))
		.and().body("job", equalTo("developer"))
		.and().body("updatedAt", notNullValue());
	}

}
