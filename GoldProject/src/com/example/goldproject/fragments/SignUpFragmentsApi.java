package com.example.goldproject.fragments;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.example.goldproject.R;
import com.example.goldproject.SignUpSuccess;

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

public class SignUpFragmentsApi extends Fragment implements OnClickListener{

	String responseText = null;

	//json parse fields
	int TAG_ERRORCODE;

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

		//ImageView imageView = (ImageView) view.findViewById(R.id.closeView);

		registerButton.setOnClickListener(this);

		//imageView.setOnClickListener(this);
	}

	// validating username/email id
	private boolean isValidUsername(String uname) {

		String USERNAME_PATTERN = "^[A-Za-z0-9 _-]{3,15}$";

		//Patterns are compiled regular expressions
		Pattern pattern = Pattern.compile(USERNAME_PATTERN);

		//The result of applying a Pattern to a given input
		Matcher matcher = pattern.matcher(uname);

		return matcher.matches();
	}

	private boolean isValidEmail(String email) {

		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);

		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	private boolean isValidMobilenumber(CharSequence mobNo) {

		String MOBILENUMBER_PATTERN = "^((\\+91-?)|0)?[0-9]{10}$";

		Pattern pattern = Pattern.compile(MOBILENUMBER_PATTERN);

		Matcher matcher = pattern.matcher(mobNo);

		return matcher.matches();
	}

	// validating password with retype password
	private boolean isValidPassword(String pwd) {

		if (pwd != null && pwd.length() > 6) {

			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.register_button:

			String uname = usernameEditText.getText().toString();

			String email = emailEditText.getText().toString();

			String mobNo = mobileEditText.getText().toString();

			String pwd = passwordEditText.getText().toString();

			if(uname.equals("") || email.equals("") || mobNo.equals("") || pwd.equals("")){

				if(uname.equals("")){

					Toast.makeText(mSignUpActivity, "UserName shouldnot be empty", Toast.LENGTH_LONG).show();
				}
				else if(email.equals("")){

					Toast.makeText(mSignUpActivity, "Email Id shouldnot be empty", Toast.LENGTH_LONG).show();
				}
				else if(mobNo.equals("")){

					Toast.makeText(mSignUpActivity, "MobileNumber shouldnot be empty", Toast.LENGTH_LONG).show();
				}
				else if(pwd.equals("")){

					Toast.makeText(mSignUpActivity, "Password shouldnot be empty", Toast.LENGTH_LONG).show();
				}
			}
			else if (!isValidUsername(uname)) {

				usernameEditText.setError("Invalid Username,must contains atleast alphabets and numbers like a-zA-Z0-9");

				Toast.makeText(mSignUpActivity, "Enter a valid UserName", Toast.LENGTH_LONG).show();
			}
			else if (!isValidEmail(email)) {

				emailEditText.setError("Invalid Email Id");

				Toast.makeText(mSignUpActivity, "Enter a valid EmailId", Toast.LENGTH_LONG).show();
			}
			else if (!isValidMobilenumber(mobNo)) {

				mobileEditText.setError("Invalid MobileNumber");

				Toast.makeText(mSignUpActivity, "Enter a valid MobileNumber", Toast.LENGTH_LONG).show();
			}
			else if (!isValidPassword(pwd)) {

				passwordEditText.setError("Invalid Password,Must be greater than 6 characters");

				Toast.makeText(mSignUpActivity, "Enter a valid Password", Toast.LENGTH_LONG).show();
			}
			else
			{
				new CreateUser().execute("http://brinvents.com/jewellery/api/signupclass.php");
			}
			break;

		default:
			break;
		}

	}
	public class CreateUser extends AsyncTask<String, String, String> {

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

				TAG_ERRORCODE = result.getInt("errorCode");

				System.out.println("errorCode->"+TAG_ERRORCODE);

				String TAG_ERRORMESSAGE = result.getString("errorMessage");

				System.out.println("errorMessage->"+TAG_ERRORMESSAGE);

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

			//success condition 
			if (TAG_ERRORCODE == 0) {

				Toast.makeText(getActivity().getApplicationContext(), "sign up success", Toast.LENGTH_LONG).show();

				Intent i = new Intent(mSignUpActivity, SignUpSuccess.class);

				startActivity(i);

			}else{

				Toast.makeText(getActivity().getApplicationContext(), "user already exists", Toast.LENGTH_LONG).show();

			}
		}
	}
}
