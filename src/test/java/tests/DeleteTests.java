package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteTests {
	
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
	public void deleteResReqTest_usingTestNG() {
		Response resp = given().when().delete("/api/users/2").thenReturn();
		sf.assertEquals(resp.statusCode(), 204);
		sf.assertEquals(resp.asString().length(), 0);
		sf.assertAll();
	}
	
	@Test
	public void deleteResReqTest() {		
		given().when().delete("/api/users/2")
		.then().assertThat()
		.statusCode(204);
	}

}
