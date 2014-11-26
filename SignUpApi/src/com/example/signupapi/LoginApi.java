package com.example.signupapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginApi extends Activity implements OnClickListener{

	private EditText userName, Password;
	private Button login,signup;
	final Context context = this;
	private TextView forgotPassword;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	HandleJSON jsonParser = new HandleJSON();

	//testing on Emulator:
	private static final String LOGIN_URL = "http://brinvents.com/jewel/Apis/loginclass.php";

	public String TAG_STATUSCODE =null;
	public  String TAG_ERRORMESSAGE = null;
	public int TAG_ERRORCODE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_api);

		//setup input fields
		userName = (EditText)findViewById(R.id.editText1);
		Password = (EditText)findViewById(R.id.editText2);
		forgotPassword = (TextView) findViewById(R.id.forgotpassword);

		//setup buttons listners
		login = (Button)findViewById(R.id.LoginButon);
		signup = (Button)findViewById(R.id.signupButton);

		login.setOnClickListener(this);
		signup.setOnClickListener(this);
		forgotPassword.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_api, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.LoginButon:
			new AttemptLogin().execute();
			break;
		case R.id.signupButton:
			Intent i = new Intent(this, SignUpActivity.class);
			startActivity(i);
			break;
		case R.id.forgotpassword:
			Intent intent = new Intent(this, ForgotPassword.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		//new CreateUser().execute();
	}
	class AttemptLogin extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginApi.this);
			pDialog.setMessage("user login...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Check for success tag
			//   int success;
			String responseText = null;
			String username = userName.getText().toString();
			String password = Password.getText().toString();
			try {
				// Building Parameters
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("username", username);
				jsonObj.put("password", password);

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPostreq = new HttpPost(LOGIN_URL);

				// check your log for json response
				//               Log.d("Login attempt", json.toString());

				StringEntity se = new StringEntity(jsonObj.toString());
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));

				httpPostreq.setEntity(se);

				HttpResponse httpResponse = httpClient.execute(httpPostreq);

				try {
					responseText = EntityUtils.toString(httpResponse.getEntity());
				}catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "response got");
				} catch (org.apache.http.ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				JSONObject json = new JSONObject(responseText);
				System.out.println(json+"response from json object...............");

				Log.d("register atempt", json.toString());

				JSONObject result = json.getJSONObject("Result");
				TAG_STATUSCODE = result.getString("statusCode");
				System.out.println("statusCode->"+TAG_STATUSCODE);
				TAG_ERRORCODE = result.getInt("errorCode");
				System.out.println("errorCode->"+TAG_ERRORCODE);
				TAG_ERRORMESSAGE = result.getString("errorMessage");
				System.out.println("errorMessage->"+TAG_ERRORMESSAGE);
				// json success tag
				//  success = json.getInt(TAG_SUCCESS);
				if (TAG_ERRORCODE == 0) {
					Log.d("Login Successful!", json.toString());
					Intent i = new Intent(LoginApi.this, LoginActivity.class);
					finish();
					startActivity(i);
					//	return json.getString(TAG_MESSAGE);
				}else{
					Log.d("Login Failure!", json.getString(TAG_ERRORMESSAGE));
					//	return json.getString(TAG_MESSAGE);
				}

				/*Intent i = new Intent(LoginApi.this, SignUpActivity.class);
				startActivity(i);*/

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
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
				Toast.makeText(LoginApi.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
	}
}
