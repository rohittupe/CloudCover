package com.util.actions.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GenericsLibrary {

	public List<Object> parseJson(String response, String parentKey, String key) {
		List<Object> list = new ArrayList<Object>();
		try {
			JSONParser parse = new JSONParser();
			JSONObject jobj = (JSONObject)parse.parse(response);
			if(parentKey != null) {
				JSONArray jsonarr = (JSONArray) jobj.get(parentKey);
				for(Object obj : jsonarr) {
					JSONObject jsonobj = (JSONObject)obj;
					list.add(String.valueOf(jsonobj.get(key)));
				}
			}else {
				list.add(String.valueOf(jobj.get(key)));
			}
		} catch (Exception e) {
			System.out.println("-------Exception in parseJson start--------");			
		}
		return list;
	}

	public List<Object> parseJsonAsObject(String response, String parentKey, String key) {
		List<Object> list = new ArrayList<Object>();
		try {
			JSONParser parse = new JSONParser();
			JSONObject jobj = (JSONObject)parse.parse(response);
			if(parentKey != null) {
				JSONArray jsonarr = (JSONArray) jobj.get(parentKey);
				for(Object obj : jsonarr) {
					JSONObject jsonobj = (JSONObject)obj;
					list.add(jsonobj.get(key));
				}
			}else {
				list.add(jobj.get(key));
			}
		} catch (Exception e) {
			System.out.println("-------Exception in parseJsonAsObject start--------");
		}
		return list;
	}

	public Object parseJson(Object obj, String key) {
		try {
			JSONObject cob = (JSONObject)obj;
			return cob.get("user_id");
		}catch (Exception e) {
			System.out.println("-------Exception in parseJson with object start--------");
		}
		return null;
	}

	public List<Object> parseJson(List<Object> objList, String key) {
		List<Object> list = new ArrayList<Object>();
		try {
			for(Object obj : objList) {
				JSONObject cob = (JSONObject)obj;
				list.add(cob.get(key));
			}
		}catch (Exception e) {
			System.out.println("-------Exception in parseJson with list object start--------");
		}
		return list;
	}

	public String getDateInMillis(String dateToFormat , String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date;
		try {
			date = formatter.parse(dateToFormat);
			long millis = date.getTime();
			return String.valueOf(millis);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
