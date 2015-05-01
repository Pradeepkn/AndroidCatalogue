package com.example.goldproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

public class HomeScreenImagesApi extends ActionBarActivity implements OnClickListener {

	static {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy);
	}

	private Dialog cDialog;

	private String finalGoldPrice;

	TextView priceEditText;

	EditText weightEditText;

	TextView wastageEditText;

	TextView makingEditText;

	TextView discountEditText;

	TextView vatEditText;

	public int currentimageindex = 0;

	ImageView slidingimage;

	private int[] IMAGE_IDS = { R.drawable.slide1, R.drawable.slide2, R.drawable.slide3, R.drawable.slide4 };

	private JSONObject jObj;

	private String uri;

	private FancyCoverFlow fancyCoverFlow;

	private HomeScreenImagesAdapter mAdapter;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home_screen_images_api);

		getSupportActionBar().hide();

		priceEditText = (TextView) findViewById(R.id.todaysPrice);

		weightEditText = (EditText) findViewById(R.id.WeightInGrams);

		wastageEditText = (TextView) findViewById(R.id.WastageCharges);

		makingEditText = (TextView) findViewById(R.id.MakingCharges);

		discountEditText = (TextView) findViewById(R.id.Discount);

		vatEditText = (TextView) findViewById(R.id.VAT);

		Button calculateButton = (Button) findViewById(R.id.CalculateId);

		//add calculate button listener
		calculateButton.setOnClickListener(this);

		slidingimage = (ImageView) findViewById(R.id.imageView_slide);

		final Handler mHandler = new Handler();

		// Create runnable for posting
		final Runnable mUpdateResults = new Runnable()
		{
			public void run()
			{
				//VM screen budget exceeds limit
				AnimateandSlideShow();
			}
		};

		int delay = 2000; // delay for 1 sec.

		int period = 5000; // repeat every 3 sec.

		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask()
		{
			public void run()
			{
				mHandler.post(mUpdateResults);
			}
		}, delay, period);

		this.fancyCoverFlow = (FancyCoverFlow) findViewById(R.id.homeScreenImageFancyCoverFlow);

		mAdapter = new HomeScreenImagesAdapter();

		this.fancyCoverFlow.setAdapter(mAdapter);

		this.fancyCoverFlow.setUnselectedAlpha(.3f);

		this.fancyCoverFlow.setUnselectedSaturation(0.0f);

		this.fancyCoverFlow.setUnselectedScale(0.4f);

		this.fancyCoverFlow.setReflectionEnabled(true);

		// reflectionRatio may only be in the interval (0, 0.5]
		this.fancyCoverFlow.setReflectionRatio(0.3f);

		// shows reflection gap for items
		this.fancyCoverFlow.setReflectionGap(0);

		// Sets the spacing between items in a Gallery
		this.fancyCoverFlow.setSpacing(-150);

		// Sets the maximum rotation that is applied to items left and right of the center of the coverflow.
		this.fancyCoverFlow.setMaxRotation(60);

		this.fancyCoverFlow.setScaleDownGravity(0.1f);

		this.fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);

		//homeList = new ArrayList<HomeScreen>();
		new JSONAsyncTask().execute("http://brinvents.com/jewellery/api/ListOfProducts/retrive.json?type=homeScreens");
	}

	//run UI thread in background
	private class JSONAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			dialog = new ProgressDialog(HomeScreenImagesApi.this);

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

			dialog.dismiss();
			//adapter.notifyDataSetChanged();
			/*if(result == false)
				Toast.makeText(mHomeScreen, "Unable to fetch data from server", Toast.LENGTH_LONG).show();*/
			if(urls != null) {

				mAdapter.setItems(urls);
			}
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.CalculateId:

			//String goldPrice = priceEditText.getText().toString();

			String weightInGrams = weightEditText.getText().toString();

			//String wastageCharges = wastageEditText.getText().toString();

			//String makingCharges = makingEditText.getText().toString();

			//String discount = discountEditText.getText().toString();

			//String vat = vatEditText.getText().toString();
			/*if(weightInGrams!=null)
			{
			 */
			Double actualPrice =  1000.00 * Double.parseDouble(weightInGrams); 

			Double totalGoldPrice = (((actualPrice/1.6) + (actualPrice/0.5)) - 0.3) + 0.5;

			String totalGoldPrice1 = totalGoldPrice.toString();

			finalGoldPrice = totalGoldPrice1 + "/-";
			//}

			System.out.println("finalGoldPrice----"+ finalGoldPrice);

			openAlertSucess();
			/*}
			else
			{
				openAlertFailure();
			}
			 */
			break;

		case R.id.okText:

//			Intent positveActivity = new Intent(HomeScreenImagesApi.this,TabBarTab.class);
//
//			startActivity(positveActivity);

		default:
			break;
		}
	}

	protected void openAlertSucess() {

		cDialog = new Dialog(this, android.R.style.Theme_Translucent);

		cDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		cDialog.setCancelable(true);

		cDialog.setContentView(R.layout.dialog);

		TextView finalGoldPriceView = (TextView) cDialog.findViewById(R.id.finalPriceCalc);

		finalGoldPriceView.setText(finalGoldPrice);

		TextView okTextView = (TextView) cDialog.findViewById(R.id.okText);

		okTextView.setOnClickListener(this);

		cDialog.show();
	}
	/*protected void openAlertFailure() {

		cDialog = new Dialog(this, android.R.style.Theme_Translucent);

		cDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		cDialog.setCancelable(true);

		cDialog.setContentView(R.layout.dialog);

		TextView finalGoldPriceView = (TextView) cDialog.findViewById(R.id.finalPriceCalc);

		finalGoldPriceView.setText(finalGoldPrice);

		TextView okTextView = (TextView) cDialog.findViewById(R.id.okText);

		okTextView.setOnClickListener(this);

		cDialog.show();
	}*/


	private void AnimateandSlideShow() {

		//VM screen budget exceeds limit
		slidingimage.setImageResource(IMAGE_IDS[currentimageindex % IMAGE_IDS.length]);

		currentimageindex++;

		Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.fade_in);

		slidingimage.startAnimation(rotateimage);
	}
}
