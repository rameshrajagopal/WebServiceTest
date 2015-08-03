package com.mycomp.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {
	private String hostName;
	
	public HttpClient() {
		
	}
	public HttpClient(String hostName) {
		this.hostName = hostName;
	}
	public String handleHttpGETMessage(String uri, String email) {
		
	    String url = "http://" + this.hostName + "/" + uri;
	    String[] msgInfo = {null, null};
		
		URL urlobj = null;
		try {
			urlobj = new URL(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			HttpURLConnection con = (HttpURLConnection) urlobj.openConnection();
			con.setRequestMethod("GET");
			con.addRequestProperty("X-Surya-Email-Id", email);
			int responseCode = con.getResponseCode();
			if (responseCode >= 400 && responseCode < 500) {
				return "4XX";
			} else if(responseCode >= 500 && responseCode < 600) {
				return "5XX";
			} //FIXME yet to handle other error codes
			//System.out.println("Response Code: " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			JsonParser.decodeMessage(response.toString(), msgInfo);
			return msgInfo[1];
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String handleHttpPOSTMessage(String uri, String email, String uuid) {
		String url = "http://" + this.hostName + "/" + uri;
		String urlParameters = JsonParser.encodeMessage(email, uuid);
		
		URL urlobj = null;
		try {
			urlobj = new URL(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			HttpURLConnection con = (HttpURLConnection) urlobj.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			if (responseCode >= 400 && responseCode < 500) {
				return "4XX";
			} else if(responseCode >= 500 && responseCode < 600) {
				return "5XX";
			} //FIXME yet to handle other error codes
			//System.out.println("Response Code: " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			//System.out.println(response.toString());
			return response.toString();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
