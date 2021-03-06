package com.example.signupapi.Fragments;

import java.io.IOException;

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

import com.example.signupapi.ForgotPassword;
import com.example.signupapi.HomeScreenApi;
import com.example.signupapi.R;
import com.example.signupapi.SignUpActivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragmentApi extends Fragment implements OnClickListener {

	/**
	 * activity login context
	 */
	private Activity mLoginApi;
	/**
	 * username edit text field 
	 */
	private EditText usernameEditText;
	/**
	 * password edit field 
	 */
	private EditText passwordEditText;

	// Progress Dialog
	private ProgressDialog pDialog;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mLoginApi = activity;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login_fragment_api, container, false);

		initLoginComponents(view);
		return view;
	}

	/**
	 * Initializing login view components
	 * 
	 * @param view
	 */
	private void initLoginComponents(View view) {
		usernameEditText = (EditText) view
				.findViewById(R.id.username_login_editText);
		passwordEditText = (EditText) view
				.findViewById(R.id.password_login_editText);

		Button loginButton = (Button) view.findViewById(R.id.login_button);
		loginButton.setOnClickListener(this);

		TextView forgotpwdTextview = (TextView) view
				.findViewById(R.id.forgot_textview);
		forgotpwdTextview.setOnClickListener(this);

		TextView signupTextview = (TextView) view
				.findViewById(R.id.signup_textview);
		signupTextview.setOnClickListener(this);
	}

	/*@Override
	public void onActivityCreated(Bundle savedInstanceState) {
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
		case R.id.login_button:
			new AttemptLogin().execute("your url.do");
			break;
		case R.id.signup_textview:
			Intent signUpIntent = new Intent(mLoginApi, SignUpActivity.class);
			startActivity(signUpIntent);
			break;
		case R.id.forgot_textview:
			Intent forgotIntent = new Intent(mLoginApi, ForgotPassword.class);
			startActivity(forgotIntent);
			break;
		default:
			break;
		}
	}
	class AttemptLogin extends AsyncTask<String, String, String>{

		// Before starting background thread Show Progress Dialog

		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(mLoginApi);
			pDialog.setMessage("user login...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {

			// Check for success tag
			//   int success;
			String responseText = null;
			String username = usernameEditText.getText().toString();
			String password = passwordEditText.getText().toString();
			try {
				// Building Parameters
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("username", username);
				jsonObj.put("password", password);

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPostreq = new HttpPost(urls[0]);

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
				} catch (IOException e) {
					e.printStackTrace();
				}

				JSONObject json = new JSONObject(responseText);
				System.out.println(json+"response from json object...............");

				Log.d("register atempt", json.toString());

				JSONObject result = json.getJSONObject("Result");
				int TAG_STATUSCODE = result.getInt("statusCode");
				System.out.println("statusCode->"+TAG_STATUSCODE);
				int TAG_ERRORCODE = result.getInt("errorCode");
				System.out.println("errorCode->"+TAG_ERRORCODE);
				String TAG_ERRORMESSAGE = result.getString("errorMessage");
				System.out.println("errorMessage->"+TAG_ERRORMESSAGE);
				// json success tag
				//  success = json.getInt(TAG_SUCCESS);
				if (TAG_ERRORCODE == 0) {
					Log.d("Login Successful!", json.toString());
					Intent i = new Intent(mLoginApi, HomeScreenApi.class);
					startActivity(i);
					//	return json.getString(TAG_MESSAGE);
				}else{
					Log.d("Login Failure!", json.getString(TAG_ERRORMESSAGE));
					//	return json.getString(TAG_MESSAGE);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			return null;
		}

		// After completing background task Dismiss the progress dialog
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null){
				Toast.makeText(mLoginApi, file_url, Toast.LENGTH_LONG).show();
			}
		}
	}

}
