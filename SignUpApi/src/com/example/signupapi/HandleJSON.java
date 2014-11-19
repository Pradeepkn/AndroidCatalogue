package com.example.signupapi;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.annotation.SuppressLint;

public class HandleJSON {
	private String errorCode = "errorcode";
	private String errorMessage = "errormessage";
	private String statusCode = "statuscode";

	private String urlString = null;

	public volatile boolean parsingComplete = true;
	public HandleJSON(String url){
		this.urlString = url;
	}
	public String getErrorcode() {
		return errorCode;
	}
	public void setErrorcode(String errorcode) {
		this.errorCode = errorcode;
	}
	public String getErrormessage() {
		return errorMessage;
	}
	public void setErrormessage(String errormessage) {
		this.errorMessage = errormessage;
	}
	public String getStatuscode() {
		return statusCode;
	}
	public void setStatuscode(String statuscode) {
		this.statusCode = statuscode;
	}

	@SuppressLint("NewApi")
	public void readAndParseJSON(String in) {
		System.out.println("readAndParseJSON()----------------------------------------------");
		try {
			JSONObject reader = new JSONObject(in);
			System.out.println(reader+"reader.................................");
			JSONObject Result  = reader.getJSONObject("Result");
			System.out.println(Result+"Result----------------------------------");
			errorCode = Result.getString("errorCode");
			errorMessage = Result.getString("errorMessage");
			statusCode = Result.getString("statusCode");

			parsingComplete = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void fetchJSON(){
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					URL url = new URL(urlString);
					System.out.println(url+"url------------------------------------");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					System.out.println(conn+"conn===============================");
					conn.setReadTimeout(10000 /* milliseconds */);
					conn.setConnectTimeout(15000 /* milliseconds */);
					conn.setRequestMethod("POST");
					conn.setDoInput(true);
					// Starts the query
					conn.connect();
					InputStream stream = conn.getInputStream();

					String data = convertStreamToString(stream);
					System.out.println(data+"data==================================");

					readAndParseJSON(data);
					stream.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		thread.start(); 		
	}
	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		System.out.println(s+"s-----------------------------------------");
		return s.hasNext() ? s.next() : "";
	}
}
