package restassured;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class HttpController {
	
	public Response get(String resURI) {
		return given().when().get(resURI).thenReturn();
	}
	
	public Response post(String resURI, String strBody) {
		return given().body(strBody).contentType(ContentType.JSON)
		.when().post(resURI).thenReturn();
	}
	
	public Response put(String resURI, String strBody) {
		return given().body(strBody).contentType(ContentType.JSON)
		.when().put(resURI).thenReturn();
	}
	
	public Response delete(String resURI) {
		return given().when().delete(resURI).thenReturn();
	}

}
