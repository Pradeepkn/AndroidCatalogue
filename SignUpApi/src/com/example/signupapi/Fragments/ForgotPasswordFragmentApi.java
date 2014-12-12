package com.example.signupapi.Fragments;

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

import com.example.signupapi.PasswordRecovery;
import com.example.signupapi.R;
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

	private static final String url = "http://brinvents.com/jewel/Apis/forgetpwdclass.php?email=";

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
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
		submitButton.setOnClickListener(this);
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

		System.out.println("when getpassword button is clicked......................");
		new SendPassword().execute();
	}
	class SendPassword extends AsyncTask<String, String, String> {

		// Before starting background thread Show Progress Dialog
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(mForgotPassword);
			pDialog.setMessage("sending password...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			String email1 = emailEditText.getText().toString();
			Log.d(email1, "email====================");

			JSONObject jsonObj = new JSONObject();
			String url1 = emailEditText.getText().toString();
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
				int TAG_STATUSCODE = result.getInt("statusCode");
				System.out.println("statusCode->"+TAG_STATUSCODE);
				int TAG_ERRORCODE = result.getInt("errorCode");
				System.out.println("errorCode->"+TAG_ERRORCODE);
				String TAG_ERRORMESSAGE = result.getString("errorMessage");
				System.out.println("errorMessage->"+TAG_ERRORMESSAGE);

				/*Intent inent = new Intent(mForgotPassword, PasswordRecovery.class);
				startActivity(inent);
				 */
				//success condition 
				if (TAG_ERRORCODE == 0) {
					Log.d("password got from mail!", json.toString());
					Intent i = new Intent(mForgotPassword, PasswordRecovery.class);
					startActivity(i);
				}else{
					Log.d("please check your mail", json.getString(TAG_ERRORMESSAGE));
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
		//After completing background task Dismiss the progress dialog
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null){
				Toast.makeText(mForgotPassword, file_url, Toast.LENGTH_LONG).show();
			}
		}
	}
}
