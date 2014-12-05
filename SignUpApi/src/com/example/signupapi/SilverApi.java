package com.example.signupapi;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SilverApi extends ActionBarActivity {

	ArrayList<Silver> silverList;
	SilverAdapter sAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_silver_api);

		//creating list of silver items in an array
		silverList = new ArrayList<Silver>();

		//execute the silver uri by background process
		new JSONAsyncSilverTask().execute("http://brinvents.com/jew/api/ListOfProducts/retrive.json?type=Silver");

		//create a listview object for scrolllist of items
		ListView listView = (ListView) findViewById(R.id.silverView);

		//adapt the silver list items from layout
		sAdapter = new SilverAdapter(getApplicationContext(), R.layout.silverrow, silverList);
		listView.setAdapter(sAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				//when each item is clicked it displays its item name
				Toast.makeText(getApplicationContext(), silverList.get(position).getPT(), Toast.LENGTH_LONG).show();
			}
		});
	}

	//for async background process
	class JSONAsyncSilverTask extends AsyncTask<String, Void, Boolean>{

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(SilverApi.this);
			pDialog.setMessage("loading imagess, please wait");
			pDialog.setTitle("ConnectingServer...");
			pDialog.show();
			pDialog.setCancelable(false);
		}

		@Override
		protected Boolean doInBackground(String... urls) {

			try{
				// request method is GET
				// defaultHttpClient
				HttpGet httpGetReq = new HttpGet(urls[0]);
				HttpClient httpClient = new DefaultHttpClient();
				//Execute GET request.
				HttpResponse httpResponse = httpClient.execute(httpGetReq);

				//success response
				int status = httpResponse.getStatusLine().getStatusCode();

				if(status == 200){

					//To receive the response from the Server after HTTP POST execution
					HttpEntity entity = httpResponse.getEntity();
					String responseText = EntityUtils.toString(entity);

					//storing the response got from url in JSONObject
					JSONObject json = new JSONObject(responseText);
					System.out.println("response from url..."+json);

					//parsing the JSON result object
					JSONObject result = json.getJSONObject("Result");
					int TAG_ERRORCODE = result.getInt("errorCode");
					System.out.println("errorCode->"+TAG_ERRORCODE);
					String TAG_ERROMESSAGE = result.getString("errorMessage");
					System.out.println("errorMessage->"+TAG_ERROMESSAGE);
					int TAG_STATUSCODE = result.getInt("statusCode");
					System.out.println("errorMessage->"+TAG_STATUSCODE);

					//parsing JSON listOfItems array
					JSONArray jarray = result.getJSONArray("listOfItems");
					System.out.println("listOfItems array.."+jarray);

					for (int i = 0; i < jarray.length(); i++) {

						JSONObject jsonObj = jarray.getJSONObject(i);

						//parsing JSON products array
						JSONArray jarray1 = jsonObj.getJSONArray("products");

						for (int j = 0; j < jarray1.length(); j++) {

							JSONObject jsonObj1 = jarray1.getJSONObject(j);

							//parsing JSON items arrat
							JSONArray jarray2 = jsonObj1.getJSONArray("items");

							for (int k = 0; k < jarray2.length(); k++) {

								JSONObject jsonObj2 = jarray2.getJSONObject(k);

								//create silver object
								Silver silver = new Silver();

								//populate the json response values fron json objects
								silver.setCT(jsonObj.getString("CT"));
								silver.setPT(jsonObj1.getString("PT"));
								silver.setName(jsonObj2.getString("name"));
								silver.setJewellery_type_name(jsonObj2.getString("jewellery_type_name"));
								silver.setGender_name(jsonObj2.getString("gender_name"));
								silver.setWearing_style_name(jsonObj2.getString("wearing_style_name"));
								silver.setDesign_type_name(jsonObj2.getString("design_type_name"));
								silver.setClarity_name(jsonObj2.getString("clarity_name"));
								silver.setColor_name(jsonObj2.getString("color_name"));
								silver.setRing_size_name(jsonObj2.getString("ring_size_name"));
								silver.setUri(jsonObj2.getString("uri"));
								silver.setPrice(jsonObj2.getString("price"));

								silverList.add(silver);
							}
						}
					}
					return true;
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {

			pDialog.cancel();
			sAdapter.notifyDataSetChanged();
			if(result == false)
				Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.silver_api, menu);
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
}
