package com.example.goldproject;

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
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

public class OurCollectionsAPI extends ActionBarActivity {

	static {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy);
	}

	String uri;

	//	private ViewFlipper mViewFlipper;

	private FancyCoverFlow cflow;

	private OurCollectionAdapter cAdapter;

	private ArrayList<String> urlValues = new ArrayList<String>();

	private Activity activity;

	/*private TextView descText;

	private ImageButton goldShow, goldHide;

	private ImageButton diamondShow, diamondHide;

	private ImageButton silverShow, silverHide;

	private ImageButton platinumShow, platinumHide;*/

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		System.out.println("entering to our collections API");

		this.setContentView(R.layout.activity_our_collections_api);

		ActionBar actionBar = getSupportActionBar();

		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(false);

		actionBar.setDisplayShowHomeEnabled(false);

		actionBar.setDisplayShowTitleEnabled(false);

		LayoutInflater mInflater = LayoutInflater.from(this);

		View mCustomView = mInflater.inflate(R.layout.ourcoll_logout, null);

		// add the custom view to the action bar
		actionBar.setCustomView(mCustomView);

		TextView logOutView = (TextView) mCustomView.findViewById(R.id.logoutView);

		logOutView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(OurCollectionsAPI.this, LoginApi.class);

				startActivity(intent);
			}
		});

		actionBar.setCustomView(mCustomView);

		actionBar.setDisplayShowCustomEnabled(true);

		this.cflow= (FancyCoverFlow) findViewById(R.id.fancyCoverFlowInCollections);

		this.cflow.setUnselectedAlpha(.3f);

		this.cflow.setUnselectedSaturation(0.0f);

		this.cflow.setUnselectedScale(0.4f);

		this.cflow.setReflectionEnabled(true);

		// reflectionRatio may only be in the interval (0, 0.5]
		this.cflow.setReflectionRatio(0.35f);

		// shows reflection gap for items
		this.cflow.setReflectionGap(0);

		// Sets the spacing between items in a Gallery
		this.cflow.setSpacing(-150);

		// Sets the maximum rotation that is applied to items left and right of the center of the coverflow.
		this.cflow.setMaxRotation(60);

		this.cflow.setScaleDownGravity(0.1f);

		cAdapter = new OurCollectionAdapter(this);

		this.cflow.setAdapter(cAdapter);

		this.cflow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);

		//bannerView = (TextView) findViewById(R.id.bannerTextView);

		new JSONAsyncTask().execute("http://brinvents.com/jewellery/api/ListOfProducts/retrive.json?type=homeScreens");

		/*cflow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Toast.makeText(OurCollectionsAPI.this, urlValues.get(position), Toast.LENGTH_LONG).show();

				bannerView.setText(uri);
			}
		});*/

		//		mViewFlipper = (ViewFlipper) this.findViewById(R.id.viewFlipper);

		//		Button yes = (Button) findViewById(R.id.yes);

		//		yes.setOnClickListener(this);

		/*descText = (TextView) findViewById(R.id.gold_description_text);

		descText = (TextView) findViewById(R.id.diamond_description_text);

		descText = (TextView) findViewById(R.id.silver_description_text);

		descText = (TextView) findViewById(R.id.platinum_description_text);

		goldShow = (ImageButton) findViewById(R.id.gold_show);

		diamondShow = (ImageButton) findViewById(R.id.diamond_show);

		silverShow = (ImageButton) findViewById(R.id.silver_show);

		platinumShow = (ImageButton) findViewById(R.id.platinum_show);

		goldShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				System.out.println("Show button");

				goldShow.setVisibility(View.INVISIBLE);

				goldHide.setVisibility(View.VISIBLE);

				descText.setMaxLines(Integer.MAX_VALUE);
			}
		});
		goldHide = (ImageButton) findViewById(R.id.gold_hide);

		goldHide.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				System.out.println("Hide button");

				goldHide.setVisibility(View.INVISIBLE);

				goldShow.setVisibility(View.VISIBLE);

				descText.setMaxLines(1);
			}
		});

		diamondShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				System.out.println("Show button");

				diamondShow.setVisibility(View.INVISIBLE);

				diamondHide.setVisibility(View.VISIBLE);

				descText.setMaxLines(Integer.MAX_VALUE);
			}
		});
		diamondHide = (ImageButton) findViewById(R.id.diamond_hide);

		diamondHide.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				System.out.println("Hide button");

				diamondHide.setVisibility(View.INVISIBLE);

				diamondShow.setVisibility(View.VISIBLE);

				descText.setMaxLines(1);
			}
		});

		silverShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				System.out.println("Show button");

				silverShow.setVisibility(View.INVISIBLE);

				silverHide.setVisibility(View.VISIBLE);

				descText.setMaxLines(Integer.MAX_VALUE);
			}
		});
		silverHide = (ImageButton) findViewById(R.id.silver_hide);

		silverHide.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				System.out.println("Hide button");

				silverHide.setVisibility(View.INVISIBLE);

				silverShow.setVisibility(View.VISIBLE);

				descText.setMaxLines(1);
			}
		});

		platinumShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				System.out.println("Show button");

				platinumShow.setVisibility(View.INVISIBLE);

				platinumHide.setVisibility(View.VISIBLE);

				descText.setMaxLines(Integer.MAX_VALUE);
			}
		});
		platinumHide = (ImageButton) findViewById(R.id.platinum_hide);

		platinumHide.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				System.out.println("Hide button");

				platinumHide.setVisibility(View.INVISIBLE);

				platinumShow.setVisibility(View.VISIBLE);

				descText.setMaxLines(1);
			}
		});*/
	}

	//	@Override
	//	public void onClick(View v) {
	//
	//		switch (v.getId()) {
	//
	//		case R.id.yes:
	//
	//			mViewFlipper.setDisplayedChild(1);
	//
	//			break;
	//
	//		default:
	//			break;
	//		}
	//	}

	class JSONAsyncTask extends AsyncTask<String, Void, ArrayList<String>>{

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			dialog = new ProgressDialog(OurCollectionsAPI.this);

			dialog.setMessage("LoadingImages, please Wait...");

			dialog.setTitle("connecting server..");

			dialog.show();

			dialog.setCancelable(false);
		}

		// Supertype overrides

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

					for (int i = 0; i < jarray.length(); i++) 
					{
						JSONObject jObj = jarray.getJSONObject(i);
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

				cAdapter.setItems(urls);
			}
		}
	}
}

/*class ViewGroupExampleAdapter extends FancyCoverFlowAdapter {

	// =============================================================================
	// Private members
	// =============================================================================

	private int[] images = { R.drawable.splash1, R.drawable.splash2,
			R.drawable.splash3, R.drawable.splash4 };

	// =============================================================================
	// Supertype overrides
	// =============================================================================

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Integer getItem(int i) {
		return images[i];
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
		CustomViewGroup customViewGroup = null;

		if (reuseableView != null) {
			customViewGroup = (CustomViewGroup) reuseableView;
		} else {
			customViewGroup = new CustomViewGroup(viewGroup.getContext());
			customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(
					300, 200));
		}

		customViewGroup.getImageView().setImageResource(this.getItem(i));

		return customViewGroup;
	}
}

class CustomViewGroup extends LinearLayout {

	// =============================================================================
	// Child views
	// =============================================================================

	private ImageView imageView;

	private Button button;

	// =============================================================================
	// Constructor
	// =============================================================================

	CustomViewGroup(Context context) {
		super(context);

		this.setOrientation(HORIZONTAL);

		this.imageView = new ImageView(context);

		LayoutParams layoutParams = new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		this.imageView.setLayoutParams(layoutParams);

		this.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		this.imageView.setAdjustViewBounds(true);

		this.addView(this.imageView);

	}

	// =============================================================================
	// Getters
	// =============================================================================

	ImageView getImageView() {
		return imageView;
	}
}*/
