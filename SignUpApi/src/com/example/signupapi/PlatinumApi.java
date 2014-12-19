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

import com.example.signupapi.Fragments.tabpannel.MyTabHostProvider;
import com.example.signupapi.Fragments.tabpannel.TabHostProvider;
import com.example.signupapi.Fragments.tabpannel.TabView;

import android.app.Activity;
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

public class PlatinumApi extends Activity {

	ArrayList<Platinum> platinumList;
	PlatinumAdapter pAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabHostProvider tabProvider = new MyTabHostProvider(PlatinumApi.this);
		TabView tabView = tabProvider.getTabHost("Platinum");
		tabView.setCurrentView(R.layout.activity_platinum_api);
		setContentView(tabView.render(4));
		//setContentView(R.layout.activity_platinum_api);

		platinumList = new ArrayList<Platinum>();
		new JSONAsyncPlatinumTask().execute("http://brinvents.com/jew/api/ListOfProducts/retrive.json?type=Platinum");

		ListView listView = (ListView) findViewById(R.id.platinumView);
		pAdapter = new PlatinumAdapter(getApplicationContext(), R.layout.platinumrow, platinumList);

		listView.setAdapter(pAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Toast.makeText(getApplicationContext(), platinumList.get(position).getPT(), Toast.LENGTH_LONG).show();	
			}
		});
	}

	class JSONAsyncPlatinumTask extends AsyncTask<String, Void, Boolean>{

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(PlatinumApi.this);
			dialog.setMessage("LoadingImages, please wait");
			dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
		}


		@Override
		protected Boolean doInBackground(String... urls) {

			try {

				//------------------>>
				HttpGet httppost = new HttpGet(urls[0]);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httppost);

				int status = response.getStatusLine().getStatusCode();

				if (status == 200) {
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);

					JSONObject json = new JSONObject(data);
					System.out.println("response from the server-----------"+json);

					JSONObject result = json.getJSONObject("Result");
					int errorCode = result.getInt("errorCode");
					System.out.println("ERROR_CODE->"+errorCode);
					String errorMessage = result.getString("errorMessage");
					System.out.println("ERROR_MESSAGE->"+errorMessage);
					int statusCode = result.getInt("statusCode");
					System.out.println("STATUS_CODE->"+statusCode);

					//jsonarray parse for listOfItems
					JSONArray jarray = result.getJSONArray("listOfItems");

					for (int i = 0; i < jarray.length(); i++) {
						JSONObject jsonObj = jarray.getJSONObject(i);

						//jsonarray parse for products
						JSONArray jarray1 = jsonObj.getJSONArray("products");

						for (int j = 0; j < jarray1.length(); j++) {
							JSONObject jsonObj1 = jarray1.getJSONObject(j);

							//jsonarray parse for items
							JSONArray jarray2 = jsonObj1.getJSONArray("items");

							for (int k = 0; k < jarray2.length(); k++) {
								JSONObject jsonObj2 = jarray2.getJSONObject(k);

								Platinum platinum = new Platinum();

								platinum.setCT(jsonObj.getString("CT"));
								platinum.setPT(jsonObj1.getString("PT"));
								platinum.setName(jsonObj2.getString("name"));
								platinum.setJewellery_type_name(jsonObj2.getString("jewellery_type_name"));
								platinum.setGender_name(jsonObj2.getString("gender_name"));
								platinum.setWearing_style_name(jsonObj2.getString("wearing_style_name"));
								platinum.setDesign_type_name(jsonObj2.getString("design_type_name"));
								platinum.setClarity_name(jsonObj2.getString("clarity_name"));
								platinum.setColor_name(jsonObj2.getString("color_name"));
								platinum.setRing_size_name(jsonObj2.getString("ring_size_name"));
								platinum.setUri(jsonObj2.getString("uri"));
								platinum.setPrice(jsonObj2.getString("price"));

								platinumList.add(platinum);
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

		protected void onPostExecute(Boolean result) {
			dialog.cancel();
			pAdapter.notifyDataSetChanged();
			if(result == false)
				Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.platinum_api, menu);
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