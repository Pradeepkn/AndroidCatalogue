package com.example.goldproject;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.brickred.socialauth.Photo;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;

import com.example.goldproject.TouchImageView.OnTouchImageViewListener;
import com.example.goldproject.jewellerymodels.GoldItems;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

public class GoldFullScreenImage extends ActionBarActivity {

	private GoldFullScreenImageAdapter goldFullScreenAdapter;

	private ViewPager viewPager;

	private ViewFlipper fullScreenFlipper;

	private TextView itemName;

	private TextView jewellery_type_name;

	private TextView gender_name;

	private TextView wearing_style_name;

	private TextView design_type_name;

	private TextView color_name;

	private TextView item_price;

	private Button fullScreenImageDetails;

	// SocialAuth Component for share button
	SocialAuthAdapter adapter;
	Profile profileMap;
	List<Photo> photosList;

	private Animation inFromRightAnimation() {

		Animation inFromRight = new TranslateAnimation(

				Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,

				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
				);
		inFromRight.setDuration(30);

		inFromRight.setInterpolator(new AccelerateInterpolator());

		return inFromRight;
	}
	private Animation inFromLeftAnimation() {

		Animation inFromLeft = new TranslateAnimation(

				Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,

				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
				);
		inFromLeft.setDuration(30);

		inFromLeft.setInterpolator(new AccelerateInterpolator());

		return inFromLeft;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		getSupportActionBar().hide();

		setContentView(R.layout.fullscreen_image_viewflipper);

		viewPager = (ViewPager) findViewById(R.id.goldSinglePager1);

		fullScreenImageDetails = (Button) findViewById(R.id.fullScreenImageDetails);

		ImageView goldItemsBackView = (ImageView) findViewById(R.id.goldItemsBackImage);

		fullScreenFlipper = (ViewFlipper) findViewById(R.id.goldFullScreenFlipper);

		itemName = (TextView) findViewById(R.id.itemName);

		jewellery_type_name = (TextView) findViewById(R.id.jewellery_type_name);

		gender_name = (TextView) findViewById(R.id.gender_name);

		wearing_style_name = (TextView) findViewById(R.id.wearing_style_name);

		design_type_name = (TextView) findViewById(R.id.design_type_name);

		color_name = (TextView) findViewById(R.id.color_name);

		item_price = (TextView) findViewById(R.id.item_price);

		// Get intent data
		Intent i = getIntent();

		// Selected image id
		String position = i.getStringExtra("goldimgposition");

		// the value of an item that previously added with putExtra() or null if no Serializable value was found.
		ArrayList<GoldItems> items = (ArrayList<GoldItems>) i.getSerializableExtra("golditems");

		System.out.println("Position>>>" + position);

		goldFullScreenAdapter = new GoldFullScreenImageAdapter(GoldFullScreenImage.this, position);

		goldFullScreenAdapter.setItem(items);

		viewPager.setAdapter(goldFullScreenAdapter);

		//fullScreenImageDetails.setVisibility(1);

		goldItemsBackView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				fullScreenImageDetails.setVisibility(View.VISIBLE);

				fullScreenFlipper.setInAnimation(inFromLeftAnimation());

				fullScreenFlipper.showPrevious();
			}
		});

		// Create Your Own Share Button
		Button share = (Button) findViewById(R.id.sharebutton);
		share.setText("Share");
		share.setTextColor(Color.WHITE);
		share.setBackgroundResource(R.drawable.button_gradient);

		// Add it to Library
		adapter = new SocialAuthAdapter(new ResponseListener());

		// Add providers
		adapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
		adapter.addProvider(Provider.TWITTER, R.drawable.twitter);
		adapter.addProvider(Provider.LINKEDIN, R.drawable.linkedin);
		adapter.addProvider(Provider.YAHOO, R.drawable.yahoo);
		adapter.addProvider(Provider.YAMMER, R.drawable.yammer);
		adapter.addProvider(Provider.EMAIL, R.drawable.email);
		adapter.addProvider(Provider.MMS, R.drawable.mms);

		// Providers require setting user call Back url
		adapter.addCallBack(Provider.TWITTER, "http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");
		adapter.addCallBack(Provider.YAMMER, "http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");

		// Enable Provider
		adapter.enable(share);
	}

	public class GoldFullScreenImageAdapter extends PagerAdapter {

		private Activity gActivity;

		private TouchImageView imageDisplay;

		private TextView currentZoomTextView;

		private DecimalFormat df;

		private TextView scrollPositionTextView;

		private ArrayList<GoldItems> mGoldItem = new ArrayList<GoldItems>();

		private LayoutInflater layoutInflater;

		public void setItem(ArrayList<GoldItems> item) {

			mGoldItem.clear();

			if (item != null) {

				mGoldItem.addAll(item);
			}
			notifyDataSetChanged();
		}

		// constructor
		public GoldFullScreenImageAdapter(Activity activity, String imgList) {

			System.out.println("entering Goldfullscreenimage adapter");

			this.gActivity = activity;
		}

		@Override
		public int getCount() {

			return this.mGoldItem.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {

			return view == ((LinearLayout) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			//Returns the string representation of the given int.
			String filename=String.valueOf(mGoldItem.get(position).url.hashCode());

			System.out.println("filename2 hashcode---"+filename);

			layoutInflater = (LayoutInflater) gActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View viewLayout = layoutInflater.inflate(R.layout.full_screen_image, container, false);

			File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();

			if (filename != null && !filename.isEmpty()) {

				File imgFile = new File(SDCardRoot, filename);

				System.out.println("imgFile---" + imgFile);

				System.out.println("fullImageListUrl---" + filename);

				if (imgFile.exists()) {

					Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

					imageDisplay = (TouchImageView) viewLayout.findViewById(R.id.goldGridimgDisplay);

					scrollPositionTextView = (TextView) viewLayout.findViewById(R.id.scroll_position);

					currentZoomTextView = (TextView) viewLayout.findViewById(R.id.current_zoom);

					imageDisplay.setImageBitmap(myBitmap);

					itemName.setText(mGoldItem.get(position).name);

					jewellery_type_name.setText("Jewellery Type : " + mGoldItem.get(position).jewelleryType);

					gender_name.setText("Gender : " + mGoldItem.get(position).gender);

					wearing_style_name.setText("Wearing Style : " + mGoldItem.get(position).style);

					design_type_name.setText("Design Type : " + mGoldItem.get(position).designType);

					color_name.setText("Color : " +mGoldItem.get(position).color);

					item_price.setText("Price : " + mGoldItem.get(position).price);

					fullScreenImageDetails.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							fullScreenImageDetails.setVisibility(View.GONE);

							fullScreenFlipper.setInAnimation(inFromRightAnimation());

							fullScreenFlipper.showNext();
						}
					});

					df = new DecimalFormat("#.##");

					imageDisplay.setOnTouchImageViewListener(new OnTouchImageViewListener() {

						@Override
						public void onMove() {

							PointF point = imageDisplay.getScrollPosition();

							float currentZoom = imageDisplay.getCurrentZoom();

							boolean isZoomed = imageDisplay.isZoomed();

							scrollPositionTextView.setText("x: " + df.format(point.x) + " y: " + df.format(point.y));

							currentZoomTextView.setText("getCurrentZoom(): " + currentZoom + " isZoomed(): " + isZoomed);
						}
					});
				}

				/*
				 * imageDisplay = (ImageView)
				 * viewLayout.findViewById(R.id.goldGridimgDisplay);
				 * 
				 * BitmapFactory.Options options = new BitmapFactory.Options();
				 * 
				 * options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				 * 
				 * Bitmap bitmap =
				 * BitmapFactory.decodeFile(fullImageListUrl.get(position),
				 * options);
				 * 
				 * imageDisplay.setImageBitmap(bitmap);
				 */
			}
			/*
			 * goldItemsView = (TextView)
			 * gActivity.findViewById(R.id.goldItemDetails);
			 * 
			 * goldItemsView.setText(filename);
			 */

			((ViewPager) container).addView(viewLayout);

			return viewLayout;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			((ViewPager) container).removeView((LinearLayout) object);
		}
	}

	/**
	 * Listens Response from Library
	 * 
	 */

	private final class ResponseListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {

			Log.d("ShareButton", "Authentication Successful");

			// Get name of provider after authentication
			final String providerName = values.getString(SocialAuthAdapter.PROVIDER);
			Log.d("ShareButton", "Provider Name = " + providerName);
			Toast.makeText(GoldFullScreenImage.this, providerName + " connected", Toast.LENGTH_LONG).show();

			// Please avoid sending duplicate message. Social Media Providers
			// block duplicate messages.

			// Share via Email Intent
			if (providerName.equalsIgnoreCase("share_mail")) {
				// Use your own code here
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "harish.cosmos@gmail.com", null));
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test");
				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "image5964402.png");
				Uri uri = Uri.fromFile(file);
				emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
				startActivity(Intent.createChooser(emailIntent, "Test"));
			}

			// Share via mms intent
			if (providerName.equalsIgnoreCase("share_mms")) {

				// Use your own code here
				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"image5964402.png");
				Uri uri = Uri.fromFile(file);

				Intent mmsIntent = new Intent(Intent.ACTION_SEND, uri);
				mmsIntent.putExtra("sms_body", "Test");
				mmsIntent.putExtra(Intent.EXTRA_STREAM, uri);
				mmsIntent.setType("image/png");
				startActivity(mmsIntent);
			}
		}

		@Override
		public void onError(SocialAuthError error) {
			Log.d("ShareButton", "Authentication Error: " + error.getMessage());
		}

		@Override
		public void onCancel() {
			Log.d("ShareButton", "Authentication Cancelled");
		}

		@Override
		public void onBack() {
			Log.d("Share-Button", "Dialog Closed by pressing Back Key");
		}
	}

	// To get status of message after authentication
	private final class MessageListener implements SocialAuthListener<Integer> {
		@Override
		public void onExecute(String provider, Integer t) {
			Integer status = t;
			if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
				Toast.makeText(GoldFullScreenImage.this, "Message posted on " + provider, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(GoldFullScreenImage.this, "Message not posted on " + provider, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}
}
