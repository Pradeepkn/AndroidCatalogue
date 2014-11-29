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

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class OffersApi extends Activity {

	ArrayList<Offers> offersList;
	OffersAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offers_api);
		offersList = new ArrayList<Offers>();
		new JSONAsyncTask().execute("http://brinvents.com/jewel/Apis/get_offers.php");

		ListView listView = (ListView) findViewById(R.id.offersview);
		adapter = new OffersAdapter(getApplicationContext(), R.layout.offersrow, offersList);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Toast.makeText(getApplicationContext(), offersList.get(position).getJewellery_type(), Toast.LENGTH_LONG).show();	
			}
		});
	}
	class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(OffersApi.this);
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

					JSONArray jarray = result.getJSONArray("offers");

					for (int i = 0; i < jarray.length(); i++) {
						JSONObject object = jarray.getJSONObject(i);

						Offers offer = new Offers();

						offer.setJewellery_type(object.getString("jewellery_type"));
						offer.setMaking_charge_discount(object.getString("making_charge_discount"));
						offer.setOffer_discount(object.getString("offer_discount"));
						offer.setOffer_image(object.getString("offer_image"));
						offer.setOffer_on_purity(object.getString("offer_on_purity"));
						offer.setOffer_type(object.getString("offer_type"));
						offer.setOffer_validity(object.getString("offer_validity"));
						offer.setWastage_charge(object.getString("wastage_charge"));

						offersList.add(offer);
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
			adapter.notifyDataSetChanged();
			if(result == false)
				Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
		}
	}
}
