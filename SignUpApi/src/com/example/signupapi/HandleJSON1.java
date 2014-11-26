package com.example.signupapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HandleJSON1 {
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	String responseText = null;
	//String urlString = null;

	public HandleJSON1() {
		System.out.println("conctructor calling..............");
		//this.urlString = url;
	}
	public JSONObject getJSONFromUrl(String url) {
		// Making HTTP request
		try {
			// Construct the client and the HTTP request.
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			// Execute the GET request and store the response locally.
			HttpResponse httpResponse = httpClient.execute(httpGet);
			// Extract data from the response.
			HttpEntity httpEntity = httpResponse.getEntity();
			// Open an inputStream with the data content.
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("[GET REQUEST]", "Network exception", e);
			e.printStackTrace();
		}

		try {
			// Create a BufferedReader to parse through the inputStream.
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			// Declare a string builder to help with the parsing.
			StringBuilder sb = new StringBuilder();
			// Declare a string to store the JSON object data in string form.
			String line = null;

			// Build the string until null.
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			// Close the input stream.
			is.close();
			// Convert the string builder data to an actual string.
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// Try to parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// Return the JSON Object.
		return jObj;
	}

}
