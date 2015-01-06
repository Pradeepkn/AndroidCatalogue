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
import com.example.jewellersapp.LoginAPI;
import com.example.jewellersapp.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

//sign up form
public class SignUPAPIFormFragment extends Fragment implements OnClickListener {

	//fetching ids of all the views
	public ImageView closeImageView;

	public EditText usernameEditText;

	public EditText emailEditText;

	public EditText mobilenumberEditText;

	public EditText passwordEditText;

	private Activity signUpActivity;


	//onAttach creates form Activity
	@Override
	public void onAttach(Activity activity) {
		signUpActivity = activity;
		super.onAttach(activity);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	//calling the layout
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.signup_api_form_fragment, container, false);

		initSignUpComponents(view);
		return view;
	}

	//initializing the components
	private void initSignUpComponents(View view) {

		usernameEditText = (EditText) view.findViewById(R.id.username_editText);

		emailEditText = (EditText) view.findViewById(R.id.email_editText);

		mobilenumberEditText = (EditText) view.findViewById(R.id.mobilenumber_editText);

		passwordEditText = (EditText) view.findViewById(R.id.password_editText);

		Button registerButton = (Button) view.findViewById(R.id.submit_button);

		registerButton.setOnClickListener(this);

		ImageView registerclose = (ImageView) view.findViewById(R.id.close_imageView);
		registerclose.setOnClickListener(this);

	}

	//performs action based on the button clicked
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit_button:
			new CreateNewUser().execute("http://brinvents.com/jew/api/signupclass.php");

			break;
		case R.id.close_imageView:
			Intent in = new Intent(signUpActivity, LoginAPI.class);
			startActivity(in);
		default:
			break;
		}

	}

	//
	class CreateNewUser extends AsyncTask<String, String, String> {

		//process dialog or wait symbol
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(signUpActivity);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		//performs json parsing
		@Override
		protected String doInBackground(String... urls) {

			//fetching values
			String uname = usernameEditText.getText().toString();

			String email = emailEditText.getText().toString();

			String mno = mobilenumberEditText.getText().toString();

			String pwd = passwordEditText.getText().toString();

			try {
				JSONObject jsonobj = new JSONObject();

				//placing values into the key
				jsonobj.put("username", uname);
				jsonobj.put("email", email);
				jsonobj.put("mnumber", mno);
				jsonobj.put("password", pwd);

				//calling httpclient
				DefaultHttpClient httpclient = new DefaultHttpClient();

				//calling httpPost
				HttpPost httppostreq = new HttpPost(urls[0]);

				//using StringEntity object for setting contentType and encoding
				StringEntity se = new StringEntity(jsonobj.toString());
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				//getting httpResponse by calling execute 
				HttpResponse httpresponse = httpclient.execute(httppostreq);


				//getting the json reponse in string using EntityUtils
				String responseText = null;
				try {
					responseText = EntityUtils.toString(httpresponse
							.getEntity());

				} catch (ParseException | IOException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				}
				System.out.println(responseText + "response from the user..");

				//parsing the json response 
				JSONObject json = new JSONObject(responseText);
				JSONObject result=json.getJSONObject("Result"); 
				int STATUSCODE = result.getInt("statusCode");
				System.out.println("statusCode:" + STATUSCODE);
				int ERRORCODE = result.getInt("errorCode");
				System.out.println("errorCode:" + ERRORCODE);
				String ERRORMESSAGE = result.getString("errorMessage");
				System.out.println("errorMessage:" + ERRORMESSAGE);

				//verifying the reponse
				if (ERRORCODE == 0) {
					Intent i = new Intent(signUpActivity, LoginAPI.class);
					startActivity(i);
				} else {
					Log.d("failure", "Invalid Email ID");
				}
			} catch (JSONException | IOException e1) {
				e1.printStackTrace();
			}
			return null;

		}

		//closing the process dialog or wait symbol
		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();
		}

	}
}
