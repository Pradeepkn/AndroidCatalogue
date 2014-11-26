package com.example.signupapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class HomeScreenApi extends Activity{

	//ArrayList<ListOfItems> arrayList;

	ArrayList<HashMap<String, Object>> arrayList;
	String responseText = null;
	// JSON Response node names
	private static int TAG_STATUSCODE;
	private static int TAG_ERRORCODE ;
	private static String TAG_ERRORMESSAGE = "errorMessage";
	//private static String TAG_LISTOFITEMS = "listOfItems";
	public String TAG_URI = "uri";
	public String TAG_URI1 = "uri";
	public String TAG_URI2 = "uri";

	private static String TAG_LISTOFITEMS = "listOfItems";
	JSONArray listOfItems;
	//ListViewAdapter adapter;s
	ListView listView;
	ImageView imageview1;
	ImageView imageview2;
	ImageView imageview3;

	public String uri1;
	public String uri2;
	public String uri3;
	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	HandleJSON1 jsonParser = new HandleJSON1();

	//testing on device:
	public static final String url = "http://brinvents.com/jew/api/ListOfProducts/retrive.json?type=homeScreens";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen_api);
		listView = (ListView)findViewById(R.id.listview);
		/*imageview1 = (ImageView) findViewById(R.id.ivImage);
		imageview2 = (ImageView) findViewById(R.id.ivImage2);
		imageview3 = (ImageView) findViewById(R.id.ivImage3);*/
		new DisplayImages().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen_api, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class DisplayImages extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(HomeScreenApi.this);
			pDialog.setMessage("displaying images...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			JSONObject jsonObj = new JSONObject();
			System.out.println(jsonObj+"------------");

			// request method is GET
			// defaultHttpClient
			try{
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGetreq = new HttpGet(url);

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
				TAG_STATUSCODE = result.getInt("statusCode");
				System.out.println("statusCode->"+TAG_STATUSCODE);
				TAG_ERRORCODE = result.getInt("errorCode");
				System.out.println("errorCode->"+TAG_ERRORCODE);
				TAG_ERRORMESSAGE = result.getString("errorMessage");
				System.out.println("errorMessage->"+TAG_ERRORMESSAGE);

				//locate the array name in JSON
				//listOfItems = new JSONArray(responseText);
				listOfItems = result.getJSONArray(TAG_LISTOFITEMS);
				System.out.println(listOfItems.toString()+"=======================");
				for (int i = 0; i < listOfItems.length(); i++) {

					System.out.println(listOfItems.length()+"------------------");//array length 3
					JSONObject jsonObj1 = listOfItems.getJSONObject(i);//first array object
					System.out.println(jsonObj1+"----------------------");
					uri1 = jsonObj1.getString(TAG_URI);
					System.out.println("TAG_URI->"+uri1);
					JSONObject jsonObj2 = listOfItems.getJSONObject(1);
					System.out.println(jsonObj2+"----------------------");
					uri2 = jsonObj2.getString(TAG_URI1);
					System.out.println("TAG_URI1->"+uri2);
					JSONObject jsonObj3 = listOfItems.getJSONObject(2);
					System.out.println(jsonObj3+"----------------------");
					uri3 = jsonObj3.getString(TAG_URI2);
					System.out.println("TAG_URI2->"+uri3);
					// Hashmap for ListView
					arrayList = new ArrayList<HashMap<String, Object>>();
					/*//ListView listView = getListView();                                  // Get listview
							listView.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
								}
							});*/
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put(TAG_URI, uri1);
					map.put(TAG_URI1, uri2);
					map.put(TAG_URI2, uri3);
					arrayList.add(map);
				}

				//success condition 
				/*if (TAG_ERRORCODE == 0) {
				Log.d("signup Successful!", json.toString());
				Intent i = new Intent(SignUpActivity.this, SignUpSuccess.class);
				finish();
				startActivity(i);
			}else{
				Log.d("user already exists", json.getString(TAG_ERRORMESSAGE));
			}*/

			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
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
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {

			// dismiss the dialog once product deleted
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					// Updating parsed JSON data into ListView
					//here u can add as many uri as u want
					MySimpleAdapter adapter = new MySimpleAdapter(HomeScreenApi.this, arrayList, R.layout.row, new String[] {TAG_URI, TAG_URI1, TAG_URI2}, new int[] {});
					listView.setAdapter(adapter);
				}
			});
			if (file_url != null){
				Toast.makeText(HomeScreenApi.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
	}
}
