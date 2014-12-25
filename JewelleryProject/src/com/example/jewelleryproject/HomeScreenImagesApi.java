package com.example.jewelleryproject;

import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeScreenImagesApi extends Fragment {

	/*ArrayList<HomeScreen> homeList;
	HomeScreenAdapter adapter;*/
	JSONObject jObj;

	private Activity mHomeScreen;

	//private SQLiteDatabase db=null;
	String uri;

	ImageView imageView;

	@Override
	public void onAttach(Activity activity) {
		mHomeScreen = activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_row, container, false);

		initSignUpComponents(view);
		return view;
	}

	private void initSignUpComponents(View view) {

		imageView = (ImageView) view.findViewById(R.id.homeRowLogo);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		//homeList = new ArrayList<HomeScreen>();
		new JSONAsyncTask().execute("http://brinvents.com/jew/api/ListOfProducts/retrive.json?type=homeScreens");

		/*ListView listView = (ListView) getActivity().findViewById(R.id.homescreenview);
		adapter = new HomeScreenAdapter(mHomeScreen, R.layout.home_row, homeList);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Toast.makeText(mHomeScreen, homeList.get(position).getUri(), Toast.LENGTH_LONG).show();
			}
		});*/
	}

	// DownloadJSON AsyncTask
	private class JSONAsyncTask extends AsyncTask<String, Void, Boolean>{

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(mHomeScreen);
			dialog.setMessage("LoadingImages, please Wait...");
			dialog.setTitle("connecting server..");
			dialog.show();
			dialog.setCancelable(false);
		}

		@Override
		protected Boolean doInBackground(String... urls) {

			try {
				HttpGet httpGet = new HttpGet(urls[0]);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httpGet);

				// StatusLine stat = response.getStatusLine();
				int status = response.getStatusLine().getStatusCode();

				if (status == 200) {
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);

					JSONObject jsono = new JSONObject(data);
					System.out.println("response from the server-----------"+jsono);

					// Retrieve JSON Objects from the given website URL in JSONfunctions
					JSONObject result = jsono.getJSONObject("Result");

					// Retrieve JSON Objects
					// Storing each json item in variable
					int errorCode = result.getInt("errorCode");
					System.out.println("ERROR_CODE->"+errorCode);
					String errorMessage = result.getString("errorMessage");
					System.out.println("ERROR_MESSAGE->"+errorMessage);
					int statusCode = result.getInt("statusCode");
					System.out.println("STATUS_CODE->"+statusCode);

					// Locate the array name
					JSONArray jarray = result.getJSONArray("listOfItems");

					for (int i = 0; i < jarray.length(); i++) 
					{
						jObj = jarray.getJSONObject(i);

						uri = jObj.getString("uri");
						System.out.println("uri----"+uri);

						//imageView.setImageResource(R.drawable.ic_launcher);
					}
					new DownloadImageTask1(imageView).execute(uri);
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
			//adapter.notifyDataSetChanged();
			if(result == false)
				Toast.makeText(mHomeScreen, "Unable to fetch data from server", Toast.LENGTH_LONG).show();
		}
	}
	private class DownloadImageTask1 extends AsyncTask<String, Void, Bitmap> {

		ImageView bmImage;

		public DownloadImageTask1(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		@Override
		protected Bitmap doInBackground(String... urls) {

			String urldisplay = urls[0];
			Bitmap bitmap = null;
			try {
				// Download Image from URL
				InputStream in = new java.net.URL(urldisplay).openStream();
				bitmap = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return bitmap;
		}
		// Sets the Bitmap returned by doInBackground
		@Override
		protected void onPostExecute(Bitmap result) {
			imageView.setImageBitmap(result);
		}
	}
}
