package stepDefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.runner.RunWith;
import com.util.actions.app.APIResources;
import com.util.actions.app.ApplicationLibrary;
import com.util.actions.init.Initializer;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import java.util.*;

@RunWith(Cucumber.class)
public class StepDefinitionsAPI extends Initializer {

	RequestSpecification reqSpec;
	Response response;
	ApplicationLibrary appLib;

	@Given("^The Stack overflow \"([^\"]*)\" API with paramaters (.+) (.+) (.+) (.+) (.+) (.+) (.+) (.+) (.+)$")
	public void the_Stack_overflow_API_with_paramaters(String type, String page, String pageSize, String fromDate, String toDate, String inname, String min, String max, 
			String id, String site ) throws Throwable {
		appLib = new ApplicationLibrary();
		if(type!=null && type.equalsIgnoreCase("badges"))
			appLib.setAndGenerateParams(page, pageSize, fromDate, toDate, inname, min, max, id, site);
		reqSpec = given().spec(requestSpecsification());
	}

	@When("^user calls \"([^\"]*)\" with \"([^\"]*)\" http request$")
	public void user_calls_with_http_request(String resource, String method) throws Throwable {
		appLib = new ApplicationLibrary();
		APIResources resourceAPI = APIResources.valueOf(resource);
		if(method.equalsIgnoreCase("POST")) {
			response = reqSpec.when().post(getBaseURL()+resourceAPI.getResource());
		}
		else if(method.equalsIgnoreCase("GET")) {
			appLib.updateParams(resource);
			if(resource.equalsIgnoreCase("GetBadgesAPI") || resource.equalsIgnoreCase("GetBadgesByTagAPI") || resource.equalsIgnoreCase("GetBadgesByRecipientsAPI")) {
				String params = appLib.getParams();
				System.out.println("url="+(getBaseURL()+resourceAPI.getResource()+params));
				response = reqSpec.when().get(getBaseURL()+resourceAPI.getResource()+params);
			}else if(resource.equalsIgnoreCase("GetBadgesByIdAPI") || resource.equalsIgnoreCase("GetBadgesByRecipientsIdAPI")) {
				String params = appLib.getParams();
				System.out.println("params is="+params);
				String resourcePart = resourceAPI.getResource().replaceAll("\\{ID\\}", appLib.getBadgeId());
				String url = getBaseURL()+resourcePart+params;
				System.out.println("url="+url);
				response = reqSpec.when().get(url);
			}
			else
				response = reqSpec.when().get(getBaseURL()+resourceAPI.getResource());
		}
	}

	@Then("^the API call get successfull with status code (\\d+)$")
	public void the_API_call_get_successfull_with_status_code(int statusCode) throws Throwable {
		response.then().statusCode(statusCode);
	}

	@Then("^\"(.+)\" in the response body is of type (.+)$")
	public void in_response_body_is_of_type(String keyValue, String expectedType) throws Throwable {
		appLib = new ApplicationLibrary();
		System.out.println("Response Body:\n"+String.valueOf(response.getBody().asString()));

		List<Object> retObjList = appLib.parseJson(response.getBody().asString(), "items", keyValue);
		System.out.println("Pared Object List="+retObjList);

		boolean flag = false;

		if(expectedType.contains("\""))
			expectedType = expectedType.replaceAll("\"", "").trim();

		String regex = null;
		switch (expectedType) {
		case "number":
			regex = "[0-9]+";
			break;
		case "string":
			regex = "[a-zA-Z]+";
			break;
		case "alphanumeric_string":
			regex = "[a-zA-Z0-9]+";
			break;
		case "string_with_special_characters":
			regex = "[a-zA-Z\\$&\\+,:;=\\?@#|'<>.^\\*\\(\\)%!-]+";
			break;
		case "link":
			regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
			break;
		default:
			regex = null;
			break;
		}
		if (regex!=null) {
			for(Object obj : retObjList) {
				String result = String.valueOf(obj);
				if(result.matches(regex)) { 
					flag = true;
				} else {
					flag=false;
					break;
				}
			}
		}
		assertEquals(true, flag);
	}

	@Then("^\"(.+)\" in response body is (.+)$")
	public void in_response_body_is(String keyValue, String expectedvalue) throws Throwable {
		appLib = new ApplicationLibrary();
		System.out.println("Response Body:\n"+String.valueOf(response.getBody().asString()));
		if(expectedvalue.contains("\""))
			expectedvalue = expectedvalue.replaceAll("\"", "").trim();
		if(keyValue.contains("\""))
			keyValue = keyValue.replaceAll("\"", "").trim();
		
		String parentKey = null;
		if(!keyValue.equalsIgnoreCase("has_more") && !keyValue.equalsIgnoreCase("quota_max") && !keyValue.equalsIgnoreCase("quota_remaining")  && 
				!keyValue.startsWith("error_") && !keyValue.contains("error"))
			parentKey = "items";

		List<Object> retObjList = appLib.parseJson(response.getBody().asString(), parentKey, keyValue);
		System.out.println("Parsed Object List="+retObjList);

		boolean flag = false;
		if(expectedvalue.equalsIgnoreCase("null"))
			flag = true;
		else if(retObjList.contains(expectedvalue))
			flag = true;

		assertEquals(true, flag);
	}

	@Then("^\"(.+)\" not in response body is (.+)$")
	public void not_in_response_body_is(String keyValue, String expectedvalue) throws Throwable {
		appLib = new ApplicationLibrary();
		System.out.println("Response Body:\n"+String.valueOf(response.getBody().asString()));
		if(expectedvalue.contains("\""))
			expectedvalue = expectedvalue.replaceAll("\"", "").trim();
		if(keyValue.contains("\""))
			keyValue = keyValue.replaceAll("\"", "").trim();
		String parentKey = null;
		if(!keyValue.equalsIgnoreCase("has_more") && !keyValue.equalsIgnoreCase("quota_max") && !keyValue.equalsIgnoreCase("quota_remaining")  && 
				!keyValue.startsWith("error_") && !keyValue.contains("error"))
			parentKey = "items";
		
		List<Object> retObjList = appLib.parseJson(response.getBody().asString(), parentKey, keyValue);
		System.out.println("Parsed Object List="+retObjList);

		boolean flag = false;
		if(expectedvalue.equalsIgnoreCase("null"))
			flag = true;
		else if(retObjList.size() < 1)
			flag = true;
		else if(!retObjList.contains(expectedvalue))
			flag = true;

		assertEquals(true, flag);
	}

	@Then("^user details are present in response body$")
	public void user_details_are_present_in_response_body() throws Throwable {
		appLib = new ApplicationLibrary();
		System.out.println("Response Body:\n"+String.valueOf(response.getBody().asString()));
		String parentKey = "items";
		
		List<Object> retObjList = appLib.parseJsonAsObject(response.getBody().asString(), parentKey, "user");
		System.out.println("Parsed Object List="+retObjList);

		boolean flag = false;
		for(Object obj : retObjList) {
			try{
				JSONObject jObj = (JSONObject)obj;
				String tempStr = String.valueOf(jObj.get("user_id"));
				if(tempStr.equalsIgnoreCase("null")) {
					flag = false;
					break;
				}else {
					flag = true;
				}
				
			}catch (Exception e) {
				System.out.println("Exception while parsing user details:"+e.getMessage());
				flag = false;
				break;
			}
		}
		assertEquals(true, flag);
	}

}