package com.example.jewelleryproject;

import android.os.Bundle;
import android.view.MenuItem;

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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NewCollectionsApi extends Fragment {

	ArrayList<Collections> collectionsList;
	collectionsAdapter cAdapter;

	private Activity mColl;

	@Override
	public void onAttach(Activity activity) {
		mColl = activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_new_collections_api, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		collectionsList = new ArrayList<Collections>();
		new JSONAsynscCollTask().execute("http://brinvents.com/jewel/Apis/new_collections.php");
		ListView listView = (ListView) getActivity().findViewById(R.id.collectionview);

		cAdapter = new collectionsAdapter(mColl, R.layout.collections_row, collectionsList);

		listView.setAdapter(cAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Toast.makeText(mColl, collectionsList.get(position).getItem_type(), Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_collections_api);
	}

	public class JSONAsynscCollTask extends AsyncTask<String, Void, Boolean>{

		ProgressDialog cDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			cDialog = new ProgressDialog(mColl);
			cDialog.setMessage("Loading images...");
			cDialog.setTitle("Connecting Server..");
			cDialog.show();
			cDialog.setCancelable(false);
		}
		@Override
		protected Boolean doInBackground(String... urls) {

			try{

				HttpGet httpGet = new HttpGet(urls[0]);
				HttpClient httpClient = new DefaultHttpClient();
				HttpResponse httpResponse = httpClient.execute(httpGet);

				int status = httpResponse.getStatusLine().getStatusCode();
				if(status == 200){
					HttpEntity entity = httpResponse.getEntity();
					String responseText = EntityUtils.toString(entity);

					JSONObject json = new JSONObject(responseText);
					System.out.println("response from url..."+responseText);

					JSONObject result = json.getJSONObject("Result");
					String ERRORCODE = result.getString("errorCode");
					System.out.println("errorCode->"+ERRORCODE);
					String ERRORMESSAGE = result.getString("errorMessage");
					System.out.println("errorMessage->"+ERRORMESSAGE);
					String STATUSCODE = result.getString("statusCode");
					System.out.println("statusCode->"+STATUSCODE);

					//parse jsonObject from result obj
					JSONObject jsonObj = result.getJSONObject("newCollections");
					System.out.println("jsonObj-------"+jsonObj);

					//parse jsonArray from newCollections obj
					JSONArray jsonArray = jsonObj.getJSONArray("goldItems");
					System.out.println("goldItems array--"+jsonArray);

					for (int i = 0; i < jsonArray.length(); i++) {

						JSONObject jsonObj1 = jsonArray.getJSONObject(i);

						Collections collections = new Collections();

						collections.setItem_type(jsonObj1.getString("item_type"));
						collections.setPurity(jsonObj1.getString("purity"));
						collections.setImage_path(jsonObj1.getString("image_path"));
						collections.setPrice(jsonObj1.getString("price"));
						collections.setOccassion(jsonObj1.getString("occassion"));
						collections.setWeight(jsonObj1.getString("weight"));
						collections.setMaking_charge(jsonObj1.getString("making_charge"));
						collections.setWastage(jsonObj1.getString("wastage"));
						collections.setModel(jsonObj1.getString("model"));

						collectionsList.add(collections);
						//parse jsonArray from goldItemsArray
					}
					JSONArray jsonArray1 = jsonObj.getJSONArray("diamondItems");
					System.out.println("diamondItems array--"+jsonArray1);

					for (int j = 0; j < jsonArray1.length(); j++) {

						JSONObject jsonObj2 = jsonArray1.getJSONObject(j);

						Collections collections = new Collections();

						collections.setItem_type(jsonObj2.getString("item_type"));
						collections.setPurity(jsonObj2.getString("purity"));
						collections.setImage_path(jsonObj2.getString("image_path"));
						collections.setPrice(jsonObj2.getString("price"));
						collections.setOccassion(jsonObj2.getString("occassion"));
						collections.setWeight(jsonObj2.getString("weight"));
						collections.setMaking_charge(jsonObj2.getString("making_charge"));
						collections.setWastage(jsonObj2.getString("wastage"));
						collections.setModel(jsonObj2.getString("model"));

						collectionsList.add(collections);
					}
					//parse jsonArray from diamondItemsArray

					JSONArray jsonArray2 = jsonObj.getJSONArray("silverItems");
					System.out.println("silverItems array--"+jsonArray2);

					for (int k = 0; k < jsonArray2.length(); k++) {

						JSONObject jsonObj3 = jsonArray2.getJSONObject(k);

						Collections collections = new Collections();

						collections.setItem_type(jsonObj3.getString("item_type"));
						collections.setPurity(jsonObj3.getString("purity"));
						collections.setImage_path(jsonObj3.getString("image_path"));
						collections.setPrice(jsonObj3.getString("price"));
						collections.setOccassion(jsonObj3.getString("occassion"));
						collections.setWeight(jsonObj3.getString("weight"));
						collections.setMaking_charge(jsonObj3.getString("making_charge"));
						collections.setWastage(jsonObj3.getString("wastage"));
						collections.setModel(jsonObj3.getString("model"));

						collectionsList.add(collections);
					}
					//parse jsonArray from silverItemsArray

					JSONArray jsonArray3 = jsonObj.getJSONArray("platinumItems");
					System.out.println("platinumItems array--"+jsonArray3);

					for (int l = 0; l < jsonArray3.length(); l++) {

						JSONObject jsonObj4 = jsonArray3.getJSONObject(l);

						Collections collections = new Collections();

						collections.setItem_type(jsonObj4.getString("item_type"));
						collections.setPurity(jsonObj4.getString("purity"));
						collections.setImage_path(jsonObj4.getString("image_path"));
						collections.setPrice(jsonObj4.getString("price"));
						collections.setOccassion(jsonObj4.getString("occassion"));
						collections.setWeight(jsonObj4.getString("weight"));
						collections.setMaking_charge(jsonObj4.getString("making_charge"));
						collections.setWastage(jsonObj4.getString("wastage"));
						collections.setModel(jsonObj4.getString("model"));

						collectionsList.add(collections);
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

			cDialog.cancel();
			cAdapter.notifyDataSetChanged();
			if(result == false)
				Toast.makeText(mColl, "Unable to fetch data from server", Toast.LENGTH_LONG).show();
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
}
