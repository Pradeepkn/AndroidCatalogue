package com.example.goldproject.fragments;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldproject.R;
import com.example.goldproject.TabBarTab;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class LoginFragmentsApi extends Fragment implements OnClickListener {

	/*private static final Pattern EMAIL_PATTERN = Pattern
			.compile("[a-zA-Z0-9+._%-+]{1,100}" + "@"
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,10}" + "(" + "."
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,20}"+
					")+");*/
	//private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9]{1,250}");

	//private static final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9+_.]{4,16}");

	// Your Facebook APP ID
	private static String APP_ID = "1629163970652814"; 

	// Instance of Facebook Class
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;

	// Buttons
	Button btnFbLogin;

	Button btnFbGetProfile;

	//	private LoginButton loginButton;

	//The CallbackManager manages the callbacks into the FacebookSdk from an Activity's or Fragment's onActivityResult() method.
	//	private CallbackManager callbackManager;

	//	private AccessTokenTracker accessTokenTracker;

	int TAG_ERRORCODE;
	/**
	 * activity login context
	 */
	private Activity mLoginApi;
	/**
	 * username edit text field 
	 */
	private TextView usernameTextView;

	//	private EditText usernameEditView;
	/**
	 * password edit field 
	 */
	private TextView passwordTextView;

	//	private EditText passwordEditView;

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		mLoginApi = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		//to initialize the SDK
		//		FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

		//create a callback manager to handle login responses
		//		callbackManager = CallbackManager.Factory.create();

		//		accessTokenTracker = new AccessTokenTracker() {
		//
		//			@Override
		//			protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
		//				// App code
		//			}
		//		};

		//		LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
	}

	/*@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		//		callbackManager.onActivityResult(requestCode, resultCode, data);
	}*/

	//	@Override
	//	public void onDestroy() {
	//
	//		super.onDestroy();
	//
	//		accessTokenTracker.stopTracking();
	//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.login_fragment_api, container, false);

		//		loginButton = (LoginButton) view.findViewById(R.id.fb_login_button);

		//		loginButton.setReadPermissions("email");

		//		loginButton.setFragment(this);   

		// Callback registration
		//		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

		//			@Override
		//			public void onSuccess(LoginResult loginResult) {

		/*final String username = usernameTextView.getText().toString();

				final String password = passwordTextView.getText().toString();

				if (username.equals("") || password.equals("")) {

					if (username.equals("")) {

						Toast.makeText(mLoginApi, "UserName shouldnot be empty", Toast.LENGTH_LONG).show();
					}
					else if (password.equals("")) {

						Toast.makeText(mLoginApi, "Password shouldnot be empty", Toast.LENGTH_LONG).show();
					}
				}
				else{
					new AttemptLogin().execute("http://brinvents.com/jewellery/api/loginclass.php");
				}*/
		/*}

			@Override
			public void onCancel() {
				// App code
			}

			@Override
			public void onError(FacebookException exception) {
				// App code
			}
		});  */  

		initLoginComponents(view);

		return view;
	}

	/**
	 * Initializing login view components
	 * 
	 * @param view
	 */
	private void initLoginComponents(View view) {

		//		usernameEditView = (EditText) view.findViewById(R.id.username_login_edittext);

		btnFbLogin = (Button) view.findViewById(R.id.btn_fblogin);

		btnFbGetProfile = (Button) view.findViewById(R.id.btn_get_profile);

		usernameTextView = (TextView) view.findViewById(R.id.username_login_TextView);

		passwordTextView = (TextView) view.findViewById(R.id.password_login_TextView);

		//		passwordEditView = (EditText) view.findViewById(R.id.password_login_edittext);

		Button loginButton = (Button) view.findViewById(R.id.login_button);

		loginButton.setOnClickListener(this);

		/*TextView forgotpwdTextview = (TextView) view.findViewById(R.id.forgot_textview);

		forgotpwdTextview.setOnClickListener(this);

		TextView signupTextview = (TextView) view.findViewById(R.id.signup_textview);

		signupTextview.setOnClickListener(this);*/
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		mAsyncRunner = new AsyncFacebookRunner(facebook);

		/**
		 * Login button Click event
		 * */
		btnFbLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("Image Button", "button Clicked");
				loginToFacebook();
			}
		});

		/**
		 * Getting facebook Profile info
		 * */
		btnFbGetProfile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getProfileInformation();
			}
		});
	}

	/**
	 * Function to login into facebook
	 * */
	protected void loginToFacebook() {

		mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			facebook.setAccessToken(access_token);

			btnFbLogin.setVisibility(View.INVISIBLE);

			// Making get profile button visible
			btnFbGetProfile.setVisibility(View.VISIBLE);

			Log.d("FB Sessions", "" + facebook.isSessionValid());
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {

			facebook.authorize(getActivity(), new String[] { "email", "publish_stream" }, new DialogListener() {

				@Override
				public void onCancel() {
					// Function to handle cancel event
				}

				@Override
				public void onComplete(Bundle values) {
					// Function to handle complete event
					// Edit Preferences and update facebook acess_token
					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token", facebook.getAccessToken());
					editor.putLong("access_expires", facebook.getAccessExpires());
					editor.commit();

					// Making Login button invisible
					btnFbLogin.setVisibility(View.INVISIBLE);

					// Making logout Button visible
					btnFbGetProfile.setVisibility(View.VISIBLE);
				}

				@Override
				public void onError(DialogError error) {
					// Function to handle error

				}

				@Override
				public void onFacebookError(FacebookError fberror) {
					// Function to handle Facebook errors

				}
			});
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	/**
	 * Get Profile information by making request to Facebook Graph API
	 * */
	public void getProfileInformation() {
		mAsyncRunner.request("me", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Profile", response);
				String json = response;
				try {
					// Facebook Profile JSON data
					JSONObject profile = new JSONObject(json);

					// getting name of the user
					final String name = profile.getString("name");

					// getting email of the user
					final String email = profile.getString("email");

					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(getActivity().getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
						}

					});


				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}

	/**
	 * Function to Logout user from Facebook
	 * */
	public void logoutFromFacebook() {
		mAsyncRunner.logout(getActivity().getApplicationContext(), new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Logout from Facebook", response);
				if (Boolean.parseBoolean(response) == true) {
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// make Login button visible
							btnFbLogin.setVisibility(View.VISIBLE);

							// making all remaining buttons invisible
							btnFbGetProfile.setVisibility(View.INVISIBLE);
						}

					});
				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e, Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e, Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}

	@Override
	public void onClick(View v) {

		System.out.println("entering onclick method");

		switch (v.getId()) {

		case R.id.login_button:

			final String username = usernameTextView.getText().toString();

			final String password = passwordTextView.getText().toString();

			if (username.equals("") || password.equals("")) {

				if (username.equals("")) {

					Toast.makeText(mLoginApi, "UserName shouldnot be empty", Toast.LENGTH_LONG).show();
				}
				else if (password.equals("")) {

					Toast.makeText(mLoginApi, "Password shouldnot be empty", Toast.LENGTH_LONG).show();
				}
			}
			/*else if (!isValidUserName(username)) {

				usernameEditText.setError("UserName must contains atleast alphabets and numbers like a-zA-Z0-9");
			}

			else if (!isValidPassword(password)) {

				passwordEditText.setError("password length should be max 6");
			}*/
			else{
				new AttemptLogin().execute("http://brinvents.com/jewellery/api/loginclass.php");
			}
			break;

			/*if (username.equals("") || password.equals("")) {

				if (username.equals("")) {

					Toast.makeText(mLoginApi, "ENTER USERNAME", Toast.LENGTH_LONG).show();
				}
				if (password.equals("")) {

					Toast.makeText(mLoginApi, "ENTER PASSWORD", Toast.LENGTH_LONG).show();
				}
			} else {
				if (!CheckUsername(username)) {

					Toast.makeText(mLoginApi, "ENTER VALID USERNAME", Toast.LENGTH_LONG).show();
				}
				if (!CheckPassword(password)) {

					Toast.makeText(mLoginApi, "ENTER VALID PASSWORD", Toast.LENGTH_LONG).show();
				}
			}*/

			/*new AttemptLogin().execute("http://brinvents.com/jewel/Apis/loginclass.php");

			break;

		case R.id.signup_textview:

			Intent signUpIntent = new Intent(mLoginApi, SignUpApi.class);

			startActivity(signUpIntent);

			break;

		case R.id.forgot_textview:

			Intent forgotIntent = new Intent(mLoginApi, ForgotPasswordApi.class);

			startActivity(forgotIntent);

			break;*/

		default:
			break;
		}
	}
	/*private boolean CheckPassword(String password) {

		return PASSWORD_PATTERN.matcher(password).matches();
	}

	private boolean CheckUsername(String username) {

		return USERNAME_PATTERN.matcher(username).matches();
	}*/
	public class AttemptLogin extends AsyncTask<String, String, String> {

		// Progress Dialog
		public ProgressDialog pDialog;
		// Before starting background thread Show Progress Dialog

		//boolean failure = false;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			pDialog = new ProgressDialog(mLoginApi);

			pDialog.setMessage("user login...");

			pDialog.setCancelable(false);

			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {

			System.out.println("background process strated");

			// Check for success tag
			//   int success;
			String responseText = null;

			String username = usernameTextView.getText().toString();

			String password = passwordTextView.getText().toString();

			try {
				// Building Parameters
				JSONObject jsonObj = new JSONObject();

				jsonObj.put("username", username);

				jsonObj.put("password", password);

				Log.d("request!", "starting");

				HttpPost httpPostreq = new HttpPost(urls[0]);

				// getting product details by making HTTP request
				HttpClient httpClient = new DefaultHttpClient();
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

				//				value database of type java.lang.String cannot be converted to JSONObject
				JSONObject json = new JSONObject(responseText);

				System.out.println(json+"response from json object...............");

				Log.d("register atempt", json.toString());

				JSONObject result = json.getJSONObject("Result");

				int TAG_STATUSCODE = result.getInt("statusCode");

				System.out.println("statusCode->"+TAG_STATUSCODE);

				TAG_ERRORCODE = result.getInt("errorCode");

				System.out.println("errorCode->"+TAG_ERRORCODE);

				String TAG_ERRORMESSAGE = result.getString("errorMessage");

				System.out.println("errorMessage->"+TAG_ERRORMESSAGE);

				// json success tag
				//  success = json.getInt(TAG_SUCCESS);

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

			if (TAG_ERRORCODE == 0) {

				Toast.makeText(getActivity().getApplicationContext(), "Login sucessfull...", Toast.LENGTH_LONG).show();

				Intent i = new Intent(mLoginApi, TabBarTab.class);

				startActivity(i);
			} 
			else {

				Toast.makeText(mLoginApi, "Login failed check username and password", Toast.LENGTH_LONG).show();	

				Log.d("failure", "Invalid username/Email ID");
			}
			/*if (file_url != null){


				Toast.makeText(mLoginApi, file_url, Toast.LENGTH_LONG).show();
			}*/
		}
	}
}
