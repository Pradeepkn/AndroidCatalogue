package com.example.jewellersapp.jewelleryfragments;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.example.jewellersapp.ForgotPassword;
import com.example.jewellersapp.HomePage;
import com.example.jewellersapp.R;
import com.example.jewellersapp.SignUpAPI;

public class LogInAPIFormFragment extends Fragment implements OnClickListener {

	// fetching ids of all the views

	public EditText unameEditText;

	public EditText pwdEditText;

	public TextView fgtpwdTextView;

	public TextView signUpAtLoginTextView;

	private Activity LogInActivity;

	// onAttach creates form Activity
	@Override
	public void onAttach(Activity activity) {
		LogInActivity = activity;
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	// calling the layout
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login_api_form_fragment,
				container, false);

		initLogInComponents(view);
		return view;
	}

	// initializing the components
	private void initLogInComponents(View view) {

		unameEditText = (EditText) view.findViewById(R.id.uname_editText);

		pwdEditText = (EditText) view.findViewById(R.id.pwd_editText);

		Button loginButton = (Button) view.findViewById(R.id.login_button);

		loginButton.setOnClickListener(this);

		fgtpwdTextView = (TextView) view.findViewById(R.id.forgotPassword_textView);
		fgtpwdTextView.setOnClickListener(this);

		signUpAtLoginTextView = (TextView) view.findViewById(R.id.signupAtLogin_textView);
		signUpAtLoginTextView.setOnClickListener(this);
	}

	// performs action based on the button clicked
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			new LoginAttempt()
					.execute("http://brinvents.com/jew/api/loginclass.php");
			break;
		case R.id.forgotPassword_textView:
			Intent forgotPwd = new Intent(LogInActivity,ForgotPassword.class);
			startActivity(forgotPwd);
			break;
		case R.id.signupAtLogin_textView:
			Intent signup = new Intent(LogInActivity,SignUpAPI.class);
			startActivity(signup);
			break;
		default:
			break;
		}
	}

	//
	class LoginAttempt extends AsyncTask<String, String, String> {

		// process dialog or wait symbol
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LogInActivity);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		// performs json parsing
		@Override
		protected String doInBackground(String... urls) {

			// fetching values
			String uname = unameEditText.getText().toString();

			String pwd = pwdEditText.getText().toString();

			try {
				JSONObject jsonobj = new JSONObject();

				// placing values into the key
				jsonobj.put("username", uname);
				jsonobj.put("password", pwd);

				// calling httpclient
				DefaultHttpClient httpclient = new DefaultHttpClient();

				// calling httpPost
				HttpPost httppostreq = new HttpPost(urls[0]);

				// using StringEntity object for setting contentType and
				// encoding
				StringEntity se = new StringEntity(jsonobj.toString());
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				// getting httpResponse by calling execute
				HttpResponse httpresponse = httpclient.execute(httppostreq);

				// getting the json reponse in string using EntityUtils
				String responseText = null;
				try {
					responseText = EntityUtils.toString(httpresponse
							.getEntity());

				} catch (ParseException | IOException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				}
				System.out.println(responseText + "response from the user..");

				// parsing the json response
				JSONObject json = new JSONObject(responseText);
				JSONObject result = json.getJSONObject("Result");
				int STATUSCODE = result.getInt("statusCode");
				System.out.println("statusCode:" + STATUSCODE);
				int ERRORCODE = result.getInt("errorCode");
				System.out.println("errorCode:" + ERRORCODE);
				String ERRORMESSAGE = result.getString("errorMessage");
				System.out.println("errorMessage:" + ERRORMESSAGE);

				// verifying the response
				if (ERRORCODE == 0) {
					Intent i = new Intent(LogInActivity,HomePage.class);
					startActivity(i);
				} else {
					Log.d("failure", "Invalid Email ID");
					
				}
			} catch (JSONException | IOException e1) {
				e1.printStackTrace();
			}
			return null;

		}

		// closing the process dialog or wait symbol
		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();
		}

	}
}
