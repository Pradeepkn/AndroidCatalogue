package com.example.signupapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class SignUpActivity extends Activity implements OnClickListener{

	private EditText userName, passWord, eMail, mNumber;
	private Button  signup;
	//final Context context = this;
	//int errorCode;

	String responseText = null;
	// JSON Response node names
	private static int TAG_STATUSCODE;
	private static int TAG_ERRORCODE ;
	private static String TAG_ERRORMESSAGE = "errorMessage";

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	HandleJSON jsonParser = new HandleJSON();

	//testing on device:
	private static final String url = "http://brinvents.com/jewel/Apis/signupclass.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("on create() started.......................");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		userName = (EditText)findViewById(R.id.editText1);
		eMail = (EditText)findViewById(R.id.editText2);
		mNumber = (EditText)findViewById(R.id.editText3);
		passWord = (EditText)findViewById(R.id.editText4);

		signup = (Button)findViewById(R.id.button1);
		signup.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		System.out.println("when signup button is clicked......................");
		new CreateUser().execute();
	}
	class CreateUser extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SignUpActivity.this);
			pDialog.setMessage("Creating User...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			// Check for success tag
			String username = userName.getText().toString();
			Log.d(username, "username====================");
			String password = passWord.getText().toString();
			Log.d(password, "password====================");

			String email = eMail.getText().toString();
			Log.d(email, "email====================");

			String mnumber = mNumber.getText().toString();
			Log.d(mnumber, "mnumber====================");

			try {
				// Building Parameters
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("username", username);
				jsonObj.put("password", password);
				jsonObj.put("email", email);
				jsonObj.put("mnumber", mnumber);

				System.out.println(jsonObj+"------------");

				// request method is POST
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPostreq = new HttpPost(url);

				//Create a String entity. String entity is appended to the url in a format that is required in HTTP POST.
				StringEntity se = new StringEntity(jsonObj.toString());

				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));

				//Set entitiy in post request.
				httpPostreq.setEntity(se);

				//Execute POST request.
				HttpResponse httpResponse = httpClient.execute(httpPostreq);

				//To receive the response from the Server after HTTP POST execution. Write in trycatch block.

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
				TAG_STATUSCODE = result.getInt("statusCode");
				System.out.println("statusCode->"+TAG_STATUSCODE);
				TAG_ERRORCODE = result.getInt("errorCode");
				System.out.println("errorCode->"+TAG_ERRORCODE);
				TAG_ERRORMESSAGE = result.getString("errorMessage");
				System.out.println("errorMessage->"+TAG_ERRORMESSAGE);

				//success condition 
				if (TAG_ERRORCODE == 0) {
					Log.d("signup Successful!", json.toString());
					Intent i = new Intent(SignUpActivity.this, SignUpSuccess.class);
					finish();
					startActivity(i);
				}else{
					//Toast.makeText(SignUpActivity.this, "failure..", Toast.LENGTH_LONG).show();
					Log.d("failure..", json.getString(TAG_ERRORMESSAGE));
				}
			}
			catch (JSONException e) {
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
				Toast.makeText(SignUpActivity.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
	}
}
