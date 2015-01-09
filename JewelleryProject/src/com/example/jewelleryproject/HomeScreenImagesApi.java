package com.example.jewelleryproject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;

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
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

public class HomeScreenImagesApi extends Fragment {

	/*ArrayList<HomeScreen> homeList;
	HomeScreenAdapter adapter;*/
	JSONObject jObj;

	//	public static Drawable drawable = null;
	private Activity mHomeScreen;

	String uri;

	ImageView imageView;

	private FancyCoverFlow fancyCoverFlow;

	private MyAdapter mAdapter;

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

		//creation of fancy cover flow.
		this.fancyCoverFlow = (FancyCoverFlow) view.findViewById(R.id.fancyCoverFlow);

		mAdapter = new MyAdapter();

		//        this.fancyCoverFlow.setAdapter(new FancyCoverFlowSampleAdapter());

		this.fancyCoverFlow.setAdapter(mAdapter);

		this.fancyCoverFlow.setUnselectedAlpha(1.0f);

		this.fancyCoverFlow.setUnselectedSaturation(0.0f);

		this.fancyCoverFlow.setUnselectedScale(0.5f);

		this.fancyCoverFlow.setSpacing(50);

		this.fancyCoverFlow.setMaxRotation(0);

		this.fancyCoverFlow.setScaleDownGravity(0.2f);

		this.fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
		//imageView = (ImageView) view.findViewById(R.id.homeRowLogo);
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
	private class JSONAsyncTask extends AsyncTask<String, Void, ArrayList<String>>{

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
		protected ArrayList<String> doInBackground(String... urls) {

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

					ArrayList<String> urlValues = new ArrayList<String>();

					for (int i = 0; i < jarray.length(); i++) 
					{
						jObj = jarray.getJSONObject(i);
						//imageView.setImageResource(R.drawable.ic_launcher);
						uri = jObj.getString("uri");

						System.out.println("uri----"+uri);

						urlValues.add(uri);
						//downloadImage(uri, "sample.png");
					}
					//new DownloadImageTask1(imageView).execute(uri);
					//new myAsyncTask().execute(uri);

					//getImageFromURL(uri);
					return urlValues;
				}
			} catch (ParseException e1) {

				e1.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();

			} catch (JSONException e) {

				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(ArrayList<String> urls) {

			dialog.cancel();
			//adapter.notifyDataSetChanged();
			/*if(result == false)
				Toast.makeText(mHomeScreen, "Unable to fetch data from server", Toast.LENGTH_LONG).show();*/
			if(urls != null) {

				mAdapter.setItems(urls);
			}
		}
	}

	/*private class DownloadImageTask1 extends AsyncTask<String, Void, Bitmap> {

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
				//convert bitmap to drawable
				Drawable d = new BitmapDrawable(getResources(), bitmap);
				System.out.println("drawable images---"+d);
				Bitmap b = BitmapFactory.decodeFile("data/data/com.example.jewelleryproject/files/urls");
				Drawable asset = new BitmapDrawable(b);
				imageView.setImageDrawable(asset);
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
	}*/
	/*public class myAsyncTask extends AsyncTask<String, Void, Bitmap>    
	{

		@Override
		protected Bitmap doInBackground(String... urls) {

			String urldisplay = urls[0];
			Bitmap bitmap = null;*/
	/*private class DownloadImageTask1 extends AsyncTask<String, Void, Bitmap> {

		ImageView bmImage;

		public DownloadImageTask1(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		@Override
		protected Bitmap doInBackground(String... urls) {
			String filepath=null;
			Bitmap bitmap = null;
			String urldisplay = urls[0];

			try {
				//set the download URL, a url that points to a file on the internet
				//this is the file to be downloaded
				InputStream in = new java.net.URL(urldisplay).openStream();
				bitmap = BitmapFactory.decodeStream(in);
				URL url = new URL(urls[0]);

				System.out.println("url..."+url);
				//create the new connection
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

				//set up some things on the connection
				urlConnection.setRequestMethod("GET");
				urlConnection.setDoOutput(true);

				//and connect!
				urlConnection.connect();

				//set the path where we want to save the file
				//in this case, going to save it on the root directory of the
				//sd card.
				//return the path to the root of the external storage(SDcard).
				File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
				//create a new file, specifying the path, and the filename
				//which we want to save the file as.
				String filename1="03_banner.png"; 
				String filename2="02_banner.png"; 
				String filename3="01_banner.png"; 
				Log.i("Local filename1:",""+filename1);
				Log.i("Local filename2:",""+filename2);
				Log.i("Local filename3:",""+filename3);
				File file1 = new File(SDCardRoot,filename1);
				File file2 = new File(SDCardRoot,filename2);
				File file3 = new File(SDCardRoot,filename3);

				if(file1.createNewFile())
				{
					file1.createNewFile();
				}else if (file2.createNewFile()) {
					file2.createNewFile();
				}else {
					file3.createNewFile();
				}

				SDCardRoot.mkdir();
				//this will be used to write the downloaded data into the file we created
				FileOutputStream fileOutput1 = new FileOutputStream(file1);
				FileOutputStream fileOutput2 = new FileOutputStream(file2);
				FileOutputStream fileOutput3 = new FileOutputStream(file3);
				//this will be used in reading the data from the internet
				InputStream inputStream = urlConnection.getInputStream();

				//this is the total size of the file
				int totalSize = urlConnection.getContentLength();
				System.out.println("total size......"+totalSize);
				//variable to store total downloaded bytes
				int downloadedSize = 0;

				//create a buffer...
				byte[] buffer = new byte[1024];
				int bufferLength = 0; //used to store a temporary size of the buffer

				//now, read through the input buffer and write the contents to the file
				while ((bufferLength = inputStream.read(buffer)) > 0 ) {
					//add the data in the buffer to the file in the file output stream (the file on the sd card)
					fileOutput1.write(buffer, 0, bufferLength);
					fileOutput2.write(buffer, 0, bufferLength);
					fileOutput3.write(buffer, 0, bufferLength);
					//add up the size so we know how much is downloaded
					downloadedSize += bufferLength;
					Log.i("Progress:","downloadedSize:"+downloadedSize+"totalSize:"+ totalSize) ;
					//this is where you would do something to report the progress, like this maybe
					//updateProgress(downloadedSize, totalSize);
				}
				//close the output stream when done
				fileOutput1.close();
				fileOutput2.close();
				fileOutput3.close();

				if(downloadedSize==totalSize)   
					filepath=file1.getPath();
				filepath=file2.getPath();
				filepath=file3.getPath();

				//catch some possible errors...
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				filepath=null;
				e.printStackTrace();
			}
			Log.i("filepath:"," "+filepath) ;
			return bitmap;
		}
		// Sets the Bitmap returned by doInBackground
		@Override
		protected void onPostExecute(Bitmap result) {
			imageView.setImageBitmap(result);
		}
	}*/
}
