package com.example.signupapi;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

public class ForgotPassword extends ActionBarActivity{

	/*private EditText eMail;
	private Button submit;*/

	/*String responseText = null;
	// JSON Response node names
	private static String TAG_STATUSCODE  = "statusCode";
	private static int TAG_ERRORCODE ;
	private static String TAG_ERRORMESSAGE = "errorMessage";

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	//HandleJSON1 jsonParser = new HandleJSON1();

	//testing on device:
	private static final String url = "http://brinvents.com/jewel/Apis/forgetpwdclass.php?email=";*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("on create() started.......................");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);

		/*ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);*/

		/*eMail = (EditText) findViewById(R.id.eMail1);
		submit = (Button) findViewById(R.id.submitButton);
		submit.setOnClickListener(this);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgot_password, menu);
		return true;
	}

	/*@Override
	public void onClick(View v) {

		System.out.println("when getpassword button is clicked......................");
		new SendPassword().execute();
	}

	class SendPassword extends AsyncTask<String, String, String> {

	 *//**
	 * Before starting background thread Show Progress Dialog
	 * *//*
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
				if (TAG_ERRORCODE == 0) {
				Log.d("signup Successful!", json.toString());
				Intent i = new Intent(SignUpActivity.this, SignUpSuccess.class);
				finish();
				startActivity(i);
			}else{
				Log.d("user already exists", json.getString(TAG_ERRORMESSAGE));
			}
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
	  *//**
	  * After completing background task Dismiss the progress dialog
	  * **//*
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null){
				Toast.makeText(ForgotPassword.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
	}*/
}
