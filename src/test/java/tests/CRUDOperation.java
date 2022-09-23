package tests;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.FileNotFoundException;
import java.io.IOException;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restassured.HttpController;
import restassured.JsonParser;

public class CRUDOperation {
	
	String entityID = null;
	SoftAssert sf;
	HttpController http;
	JsonParser jsparser;
	
	String postFilePath = "src/test/resources/post.json";
	String putFilePath = "src/test/resources/put.json";
	String resURI = "/api/users";
	String getPutDeleteResURI = null;
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://reqres.in";
	}
	
	@BeforeMethod
	public void beforeMethod() {
		sf = new SoftAssert();
		http = new HttpController();
		jsparser = new JsonParser();
	}
	
	@Test(priority = 1)
	public void createEntity() throws FileNotFoundException, IOException, ParseException {
		JSONObject expected =  jsparser.returnJsonObject(postFilePath);
		Response resp = http.post(resURI, expected.toString());
		
		JsonPath actual = jsparser.respToJson(resp);
		sf.assertEquals(resp.statusCode(), 201);
		sf.assertEquals(actual.get("name"), expected.get("name"));
		sf.assertEquals(actual.get("job"), expected.get("job"));
		entityID = actual.get("id");
		sf.assertTrue(entityID.length() > 0, "FAIL | ID is not greater than 0 and is " +entityID);
		sf.assertTrue(actual.get("createdAt").toString().length() > 0, "FAIL | Created at value is " +actual.get("createdAt").toString());
		sf.assertAll();
	}
	
	@Test(priority = 2, dependsOnMethods = "createEntity")
	public void readEntity() {
		entityID = "2";
		getPutDeleteResURI = resURI +"/"+ entityID;
		
		Response resp = http.get(getPutDeleteResURI);
		 JsonPath actual = jsparser.respToJson(resp);
		 
		 sf.assertEquals(resp.statusCode(), 200);
		 //You need to add assertion for id, email, first name, last name. These values should be read from JSON file
		 sf.assertAll();
	}
	
	@Test(priority = 3, dependsOnMethods ="readEntity" )
	public void updateEntity() throws FileNotFoundException, IOException, ParseException {
		JSONObject expected =  jsparser.returnJsonObject(putFilePath);
		Response resp =  http.put(getPutDeleteResURI, expected.toString());

		JsonPath actual =  jsparser.respToJson(resp);
		sf.assertEquals(resp.statusCode(), 200);
		sf.assertEquals(actual.get("name"), expected.get("name"));
		sf.assertEquals(actual.get("job"), expected.get("job"));
		sf.assertTrue(actual.getString("updatedAt").length() > 0, "FAIL MESSAGE TO COME HERE");
		sf.assertAll();
	}
	
	@Test(priority = 4, dependsOnMethods ="readEntity" )
	public void deleteEntity() {
		Response resp = http.delete(getPutDeleteResURI);
		sf.assertEquals(resp.statusCode(), 204);
		//Add assert - resp or body should be empty
		sf.assertAll();
	}

}
