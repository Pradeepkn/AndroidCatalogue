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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HomeScreenApi extends Activity{

	ArrayList<HomeScreen> homeList;
	HomeScreenAdapter adapter;
	JSONObject jObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabHostProvider tabProvider = new MyTabHostProvider(HomeScreenApi.this);
		TabView tabView = tabProvider.getTabHost("Home");
		tabView.setCurrentView(R.layout.activity_home_screen_api);
		setContentView(tabView.render(0));
		//setContentView(R.layout.activity_home_screen_api);

		homeList = new ArrayList<HomeScreen>();
		new JSONAsyncTask().execute("http://brinvents.com/jew/api/ListOfProducts/retrive.json?type=homeScreens");

		ListView listView = (ListView) findViewById(R.id.homescreenview);
		adapter = new HomeScreenAdapter(getApplicationContext(), R.layout.row, homeList);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Toast.makeText(getApplicationContext(), homeList.get(position).getUri(), Toast.LENGTH_LONG).show();
			}
		});
	}

	class JSONAsyncTask extends AsyncTask<String, Void, Boolean>{

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(HomeScreenApi.this);
			dialog.setMessage("LoadingImages, please Wait...");
			dialog.setTitle("connecting server..");
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

				// StatusLine stat = response.getStatusLine();
				int status = response.getStatusLine().getStatusCode();

				if (status == 200) {
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);

					JSONObject jsono = new JSONObject(data);
					System.out.println("response from the server-----------"+jsono);

					JSONObject result = jsono.getJSONObject("Result");
					int errorCode = result.getInt("errorCode");
					System.out.println("ERROR_CODE->"+errorCode);
					String errorMessage = result.getString("errorMessage");
					System.out.println("ERROR_MESSAGE->"+errorMessage);
					int statusCode = result.getInt("statusCode");
					System.out.println("STATUS_CODE->"+statusCode);

					JSONArray jarray = result.getJSONArray("listOfItems");

					for (int i = 0; i < jarray.length(); i++) {
						jObj = jarray.getJSONObject(i);
					}

					HomeScreen homeScreen = new HomeScreen();
					homeScreen.setUri(jObj.getString("uri"));
					homeList.add(homeScreen);
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
			adapter.notifyDataSetChanged();
			if(result == false)
				Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
		}
	}
}
