package com.mycomp.test;

import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParser {
	public static String encodeMessage(String email, String uuid) {
		JSONObject obj = new JSONObject();
		obj.put("emailId", email);
		obj.put("uuid", uuid);
		StringWriter out = new StringWriter();
		try {
			obj.writeJSONString(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String jsonText = out.toString();
		//System.out.println("Encode JsonParser: " + jsonText);
		return jsonText;
	}
	public static void decodeMessage(String response, String[] ret) {
		JSONParser parser = new JSONParser();
		Object object;
		try {
			object = parser.parse(response);
			JSONObject jsonObject = (JSONObject)object;
			ret[0] = (String)jsonObject.get("emailId");
			ret[1] = (String)jsonObject.get("uuid");
			//System.out.println("Decoding: " + ret[0] + " " + ret[1]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
