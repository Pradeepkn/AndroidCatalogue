package com.example.jewellersapp.jewelleryfragments;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.jewellersapp.ForgotPassword;
import com.example.jewellersapp.HomePage;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ForgotPasswordFormFragment extends Fragment implements
		OnClickListener {

	public EditText emailEditText;
	
	private String prelim_Url="http://brinvents.com/jew/api/forgetpwdclass.php?email=";
		
	private Activity ForgotPasswordActivity;

	@Override
	public void onAttach(Activity activity) {
		ForgotPasswordActivity = activity;
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.forgot_password_api_form_fragment,
				container, false);

		initLogInComponents(view);
		return view;
	}
	
	private void initLogInComponents(View view) {

		emailEditText = (EditText) view.findViewById(R.id.email_editText);
		
		ImageView closeimageView=(ImageView) view.findViewById(R.id.close_imageView);
		
		closeimageView.setOnClickListener(this);

		Button submitButton = (Button) view.findViewById(R.id.submitForgot_button);

		submitButton.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submitForgot_button:
			new RetrievePassword().execute();
			break;
		case R.id.close_imageView:
			Intent in = new Intent(ForgotPasswordActivity,LoginAPI.class);
			startActivity(in);
			break;
		default:
			break;
		}
	}
	
	class RetrievePassword extends AsyncTask<String, String, String> {

		// process dialog or wait symbol
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ForgotPasswordActivity);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		// performs json parsing
		@Override
		protected String doInBackground(String... urls) {
			String response = null;
			
			String Secondary_url = emailEditText.getText().toString();
			
			String Final_url = prelim_Url+Secondary_url;
			
			try {

				// calling httpclient
				DefaultHttpClient httpclient = new DefaultHttpClient();

				// calling httpPost
				HttpGet httpgetreq = new HttpGet(Final_url);

				/*// using StringEntity object for setting contentType and
				// encoding
				StringEntity se = new StringEntity(jsonobj.toString());
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httpgetreq.getEntity(se);
*/
				// getting httpResponse by calling execute
				HttpResponse httpresponse = httpclient.execute(httpgetreq);

				// getting the json reponse in string using EntityUtils
				
				try {
					response = EntityUtils.toString(httpresponse.getEntity());

				} catch (ParseException | IOException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				}
				System.out.println(response + "response from the user..");

				// parsing the json response
				JSONObject js=new JSONObject(response);
				JSONObject result = js.getJSONObject("Result");
				int STATUSCODE = result.getInt("statusCode");
				System.out.println("statusCode:" + STATUSCODE);
				int ERRORCODE = result.getInt("errorCode");
				System.out.println("errorCode:" + ERRORCODE);
				String ERRORMESSAGE = result.getString("errorMessage");
				System.out.println("errorMessage:" + ERRORMESSAGE);

				// verifying the response
				if (ERRORCODE == 0) {
					Intent i = new Intent(ForgotPasswordActivity,LoginAPI.class);
					startActivity(i);
				} 
				else 
				{
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
