package com.example.jewelleryproject.fragments;

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

import com.example.jewelleryproject.Gold;
import com.example.jewelleryproject.GoldAdapter;
import com.example.jewelleryproject.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GoldApiFragment2 extends Fragment implements OnClickListener{

	ArrayList<Gold> goldList;

	GoldAdapter gAdapter;

	GridView gridView;

	private Activity mGoldApi2;

	@Override
	public void onAttach(Activity activity) {

		mGoldApi2 = activity;

		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.gold_row1, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		goldList = new ArrayList<Gold>();

		new JSONAsyncGoldTask().execute("http://brinvents.com/jew/api/ListOfProducts/retrive.json?type=Gold");

		gridView = (GridView) getActivity().findViewById(R.id.goldGridView);

		gAdapter = new GoldAdapter(mGoldApi2, R.layout.gold_row, goldList);

		gridView.setAdapter(gAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Toast.makeText(mGoldApi2, goldList.get(position).getUri(), Toast.LENGTH_LONG).show();	
			}
		});
	}

	class JSONAsyncGoldTask extends AsyncTask<String, Void, Boolean> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			dialog = new ProgressDialog(mGoldApi2);

			dialog.setMessage("LoadingImages, please wait");

			dialog.setTitle("Connecting server");

			dialog.show();

			dialog.setCancelable(false);
		}

		@Override
		protected Boolean doInBackground(String... urls) {

			try {

				HttpGet httpGet = new HttpGet(urls[0]);

				HttpClient httpclient = new DefaultHttpClient();

				HttpResponse response = httpclient.execute(httpGet);

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

								Gold gold = new Gold();

								/*	gold.setCT(jsonObj.getString("CT"));
								gold.setPT(jsonObj1.getString("PT"));
								gold.setName(jsonObj2.getString("name"));
								gold.setJewellery_type_name(jsonObj2.getString("jewellery_type_name"));
								gold.setGender_name(jsonObj2.getString("gender_name"));
								gold.setWearing_style_name(jsonObj2.getString("wearing_style_name"));
								gold.setDesign_type_name(jsonObj2.getString("design_type_name"));
								gold.setClarity_name(jsonObj2.getString("clarity_name"));
								gold.setColor_name(jsonObj2.getString("color_name"));
								gold.setRing_size_name(jsonObj2.getString("ring_size_name"));*/
								gold.setUri(jsonObj2.getString("uri"));
								//gold.setPrice(jsonObj2.getString("price"));

								goldList.add(gold);
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

			dialog.dismiss();

			gAdapter.notifyDataSetChanged();

			if(result == false)
				//shows a quick little msg for User
				Toast.makeText(mGoldApi2, "Unable to fetch data from server", Toast.LENGTH_LONG).show();
		}
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

	@Override
	public void onClick(View v) {

	}
}
