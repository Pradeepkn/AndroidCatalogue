package com.example.jewelleryproject.fragments;

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

import com.example.jewelleryproject.LoginApi;
import com.example.jewelleryproject.R;
import com.example.jewelleryproject.SignUpSuccess;

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
import android.widget.ImageView;
import android.widget.Toast;

public class SignUpFragmentApi extends Fragment implements OnClickListener{

	String responseText = null;

	// Progress Dialog
	private ProgressDialog pDialog;
	/**
	 * create username edit field  
	 */
	private EditText usernameEditText;
	/**
	 *  setting password edit field 
	 */
	private EditText passwordEditText;

	/**
	 * creating email edit field  
	 */
	private EditText emailEditText;
	/**
	 *  setting mobilenumber edit field 
	 */
	private EditText mobileEditText;
	/**
	 * activity context 
	 */
	private Activity mSignUpActivity;

	@Override
	public void onAttach(Activity activity) {
		mSignUpActivity = activity;
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("on create() started.......................");
		//mSignUpActivity.setContentView(R.layout.signup_fragment_api);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sign_up_fragment_api, container, false);	
			
		//calling initial components
		initSignUpComponenets(view);
		return view;
	}

	/**
	 *  init sign up componenets
	 * @param view 
	 */
	private void initSignUpComponenets(View view) {

		usernameEditText = (EditText) view.findViewById(R.id.username_editText);
		passwordEditText = (EditText) view.findViewById(R.id.password_editText);
		emailEditText = (EditText) view.findViewById(R.id.eMail);
		mobileEditText = (EditText) view.findViewById(R.id.mobileNumber);

		Button registerButton = (Button) view.findViewById(R.id.register_button);
		ImageView imageView = (ImageView) view.findViewById(R.id.closeView);
		registerButton.setOnClickListener(this);
		imageView.setOnClickListener(this);
	}

	/*@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_button:
			System.out.println("when signup button is clicked......................");
			new CreateUser().execute("http://brinvents.com/jew/api/signupclass.php");
			break;

		case R.id.closeView:
			Intent intent = new Intent(mSignUpActivity, LoginApi.class);
			startActivity(intent);
		default:
			break;
		}

	}
	class CreateUser extends AsyncTask<String, String, String> {

		// Before starting background thread Show Progress Dialog
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(mSignUpActivity);
			pDialog.setMessage("Creating User...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {

			// Check for success tag
			String username = usernameEditText.getText().toString();
			Log.d(username, "username====================");
			String password = passwordEditText.getText().toString();
			Log.d(password, "password====================");

			String email = emailEditText.getText().toString();
			Log.d(email, "email====================");

			String mnumber = mobileEditText.getText().toString();
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
				HttpPost httpPostreq = new HttpPost(urls[0]);

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
				int TAG_STATUSCODE = result.getInt("statusCode");
				System.out.println("statusCode->"+TAG_STATUSCODE);
				int TAG_ERRORCODE = result.getInt("errorCode");
				System.out.println("errorCode->"+TAG_ERRORCODE);
				String TAG_ERRORMESSAGE = result.getString("errorMessage");
				System.out.println("errorMessage->"+TAG_ERRORMESSAGE);

				//success condition 
				if (TAG_ERRORCODE == 0) {
					Log.d("signup Successful!", json.toString());
					Intent i = new Intent(mSignUpActivity, SignUpSuccess.class);
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
		// After completing background task Dismiss the progress dialog
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null){
				Toast.makeText(mSignUpActivity, file_url, Toast.LENGTH_LONG).show();
			}
		}
	}

}
