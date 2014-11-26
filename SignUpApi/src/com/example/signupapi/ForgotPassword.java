package com.example.signupapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPassword extends Activity implements OnClickListener{

	private EditText eMail;
	private TextView  passwordRec;

	String responseText = null;
	// JSON Response node names
	private static String TAG_STATUSCODE  = "statusCode";
	private static int TAG_ERRORCODE ;
	private static String TAG_ERRORMESSAGE = "errorMessage";

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	HandleJSON1 jsonParser = new HandleJSON1();

	//testing on device:
	private static final String url = "http://brinvents.com/jewel/Apis/forgetpwdclass.php?email=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("on create() started.......................");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		eMail = (EditText) findViewById(R.id.editText1);
		passwordRec = (TextView) findViewById(R.id.textView3);
		passwordRec.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgot_password, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		System.out.println("when getpassword button is clicked......................");
		new SendPassword().execute();
	}

	class SendPassword extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ForgotPassword.this);
			pDialog.setMessage("sending password...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			String email1 = eMail.getText().toString();
			Log.d(email1, "email====================");

			JSONObject jsonObj = new JSONObject();
			String url1 = eMail.getText().toString();
			String finalUrl = url + url1;
			//eMail.setText(finalUrl);
			System.out.println(finalUrl+"---------------");
			//jsonParser = new HandleJSON1(finalUrl);
			System.out.println(jsonObj+"------------");

			// request method is GET
			// defaultHttpClient
			try{
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGetreq = new HttpGet(finalUrl);

				//Create a String entity. String entity is appended to the url in a format that is required in HTTP GET.
				//StringEntity se = new StringEntity(jsonObj.toString());

				//se.setContentType("application/json;charset=UTF-8");
				//se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));

				//Set entitiy in get request.
				//((HttpResponse) httpGetreq).setEntity(se);

				//Execute GET request.
				HttpResponse httpResponse = httpClient.execute(httpGetreq);

				//To receive the response from the Server after HTTP GET execution. Write in trycatch block.

				try {
					responseText = EntityUtils.toString(httpResponse.getEntity());
				}catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "response got");
				}

				System.out.println(responseText+"response from server-------------");

				Log.d("request!", "starting");

				//Get the response string into a new jSON object and get values from it.
				JSONObject json = new JSONObject(responseText);
				System.out.println(json+"response from json object...............");

				// json success element
				JSONObject result = json.getJSONObject("Result");
				TAG_STATUSCODE = result.getString("statusCode");
				System.out.println("statusCode->"+TAG_STATUSCODE);
				TAG_ERRORCODE = result.getInt("errorCode");
				System.out.println("errorCode->"+TAG_ERRORCODE);
				TAG_ERRORMESSAGE = result.getString("errorMessage");
				System.out.println("errorMessage->"+TAG_ERRORMESSAGE);

				Intent inent = new Intent(ForgotPassword.this, PasswordRecovery.class);
				startActivity(inent);

				//success condition 
				/*if (TAG_ERRORCODE == 0) {
				Log.d("signup Successful!", json.toString());
				Intent i = new Intent(SignUpActivity.this, SignUpSuccess.class);
				finish();
				startActivity(i);
			}else{
				Log.d("user already exists", json.getString(TAG_ERRORMESSAGE));
			}*/
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null){
				Toast.makeText(ForgotPassword.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
	}
}
