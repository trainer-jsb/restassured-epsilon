package restassured;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonParser {
	
	/***
	 * This method accepts response and returns the JSON path that can be used for validation
	 * @param resp		API response returned by REST-Assured
	 * @return				JSON path
	 */
	public JsonPath respToJson(Response resp) {
		return resp.jsonPath();
	}
	
	public String respToString(Response resp) {
		return resp.asString();
	}
	
	public JSONObject returnJsonObject(String filePath) throws FileNotFoundException, IOException, ParseException {
		JSONParser jsparser = new JSONParser();
		Object object = jsparser.parse(new FileReader(filePath));
		JSONObject jsobject = (JSONObject) object;
		return jsobject;
	}

}
