package com.example.goldproject.fragments;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.goldproject.PasswordRecovery;
import com.example.goldproject.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordFragmentApi extends Fragment implements OnClickListener{

	String responseText = null;

	private static final String url = "http://brinvents.com/jewellery/api/forgetpwdclass.php?email=";

	//json parse fields
	int TAG_ERRORCODE; 

	// Progress Dialog
	private ProgressDialog pDialog;
	/**
	 * email edit filed 
	 */
	private EditText emailEditText;
	/**
	 * forgot activity context  
	 */
	private Activity mForgotPassword;

	@Override
	public void onAttach(Activity activity) {

		mForgotPassword = activity;

		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.forgot_password_fragment_api, container, false);

		initForgotScreenComponents(view);

		return view;
	}

	/**
	 * init forgot screen componenets
	 * @param view
	 */
	private void initForgotScreenComponents(View view) {

		emailEditText = (EditText)view.findViewById(R.id.forgot_editText);

		Button submitButton = (Button)view.findViewById(R.id.forgot_button);

		//ImageView imageView1 = (ImageView) view.findViewById(R.id.closeImage1);

		submitButton.setOnClickListener(this);

		//imageView1.setOnClickListener(this);
	}

	private boolean isValidEmail(String email) {

		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);

		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.forgot_button:

			System.out.println("when getpassword button is clicked......................");

			String email = emailEditText.getText().toString();

			if(email.equals("")){

				Toast.makeText(mForgotPassword, "email id should not be empty", Toast.LENGTH_LONG).show();
			}
			else if (!isValidEmail(email)) {

				emailEditText.setError("Invalid Email");

				Toast.makeText(mForgotPassword, "Enter a valid EmailID", Toast.LENGTH_LONG).show();
			} else {

				new SendPassword().execute();
			}

			break;

			/*case R.id.closeImage1:

			Intent intent1 = new Intent(mForgotPassword, LoginApi.class);

			startActivity(intent1);
			 */
		default:

			break;
		}
	}
	public class SendPassword extends AsyncTask<String, String, String> {

		// Before starting background thread Show Progress Dialog
		boolean failure = false;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			pDialog = new ProgressDialog(mForgotPassword);

			pDialog.setMessage("Authenticating email address...");

			pDialog.setIndeterminate(false);

			pDialog.setCancelable(true);

			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			String email1 = emailEditText.getText().toString();

			Log.d(email1, "email====================");

			//JSONObject jsonObj = new JSONObject();
			String url1 = emailEditText.getText().toString();

			String finalUrl = url + url1;
			//eMail.setText(finalUrl);
			System.out.println(finalUrl+"---------------");

			//jsonParser = new HandleJSON1(finalUrl);
			//System.out.println(jsonObj+"------------");

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

				int TAG_STATUSCODE = result.getInt("statusCode");

				System.out.println("statusCode->"+TAG_STATUSCODE);

				TAG_ERRORCODE = result.getInt("errorCode");

				System.out.println("errorCode->"+TAG_ERRORCODE);

				String TAG_ERRORMESSAGE = result.getString("errorMessage");

				System.out.println("errorMessage->"+TAG_ERRORMESSAGE);

				/*Intent inent = new Intent(mForgotPassword, PasswordRecovery.class);
				startActivity(inent);
				 */

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
		//After completing background task Dismiss the progress dialog
		protected void onPostExecute(String file_url) {

			// dismiss the dialog once product deleted
			pDialog.dismiss();

			//success condition 
			if (TAG_ERRORCODE == 0) {

				Toast.makeText(mForgotPassword, "enter recovery password to login", Toast.LENGTH_LONG).show();

				Intent i = new Intent(mForgotPassword, PasswordRecovery.class);

				startActivity(i);
			}else{

				Toast.makeText(mForgotPassword, "Entered EmailId was wrong please check", Toast.LENGTH_LONG).show();
			}
		}
	}
}
