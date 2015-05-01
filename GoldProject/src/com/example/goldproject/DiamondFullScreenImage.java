package com.example.goldproject;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example.goldproject.TouchImageView.OnTouchImageViewListener;
import com.example.goldproject.jewellerymodels.DiamondItems;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.ViewFlipper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;

public class DiamondFullScreenImage extends ActionBarActivity {

	private DiamondFullScreenImageAdapter dAdapter;

	private ViewPager viewPager;

	private ViewFlipper diamondFullScreenFlipper;

	private TextView itemName;

	private TextView jewellery_type_name;

	private TextView gender_name;

	private TextView wearing_style_name;

	private TextView design_type_name;

	private TextView color_name;

	private TextView item_price;

	private Button diamondFullImageDetails;

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
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		getSupportActionBar().hide();

		setContentView(R.layout.diamond_fragment2_viewflipper);

		viewPager = (ViewPager) findViewById(R.id.diamondSinglePager1);

		diamondFullImageDetails = (Button) findViewById(R.id.diamondFullScreenImageDetails);

		ImageView diamondBackImage = (ImageView) findViewById(R.id.diamondItemsBackImage);

		diamondFullScreenFlipper = (ViewFlipper) findViewById(R.id.diamondFlipper1);

		itemName = (TextView) findViewById(R.id.diamond_item_name);

		jewellery_type_name = (TextView) findViewById(R.id.djewellery_type_name);

		gender_name = (TextView) findViewById(R.id.dgender_name);

		wearing_style_name = (TextView) findViewById(R.id.dwearing_style_name);

		design_type_name = (TextView) findViewById(R.id.ddesign_type_name);

		color_name = (TextView) findViewById(R.id.dcolor_name);

		item_price = (TextView) findViewById(R.id.ditem_price);

		// Get intent data
		Intent i = getIntent();

		// Selected image id
		String position = i.getStringExtra("diamondImgPostion");

		// the value of an item that previously added with putExtra() or null if no Serializable value was found.
		ArrayList<DiamondItems> items=(ArrayList<DiamondItems>) i.getSerializableExtra("diamonditems");

		System.out.println("Position>>>" + position);

		dAdapter = new DiamondFullScreenImageAdapter(DiamondFullScreenImage.this, position);

		dAdapter.setItem(items);

		viewPager.setAdapter(dAdapter);

		diamondBackImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				diamondFullImageDetails.setVisibility(View.VISIBLE);

				diamondFullScreenFlipper.setInAnimation(inFromLeftAnimation());

				diamondFullScreenFlipper.showPrevious();
			}
		});
	}

	public class DiamondFullScreenImageAdapter extends PagerAdapter {

		private Activity dActivity;

		private TouchImageView imageDisplay;

		private ArrayList<DiamondItems> mDiamondItem = new ArrayList<DiamondItems>();

		private LayoutInflater layoutInflater;

		private TextView currentZoomTextView;

		private DecimalFormat df;

		private TextView scrollPositionTextView;

		public void setItem(ArrayList<DiamondItems> item) {

			mDiamondItem.clear();

			if (item != null) {

				mDiamondItem.addAll(item);
			}
			notifyDataSetChanged();
		}

		// constructor
		public DiamondFullScreenImageAdapter(Activity activity, String imgList) {

			System.out.println("entering Goldfullscreenimage adapter");

			this.dActivity = activity;
		}

		@Override
		public int getCount() {

			return this.mDiamondItem.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {

			return view == ((LinearLayout) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			//Returns the string representation of the given int.
			String filename=String.valueOf(mDiamondItem.get(position).url.hashCode());

			System.out.println("filename hashcode---"+filename);

			layoutInflater = (LayoutInflater) dActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View viewLayout = layoutInflater.inflate(R.layout.diamondfullscreenimage, container, false);

			File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();

			if (filename != null && !filename.isEmpty()) {

				File imgFile = new File(SDCardRoot, filename);

				System.out.println("imgFile---" + imgFile);

				System.out.println("fullImageListUrl---" + filename);

				if (imgFile.exists()) {

					Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

					imageDisplay = (TouchImageView) viewLayout.findViewById(R.id.diamondGridimgDisplay);

					scrollPositionTextView = (TextView) viewLayout.findViewById(R.id.diamond_Scroll_position);

					currentZoomTextView = (TextView) viewLayout.findViewById(R.id.diamond_Current_zoom);

					imageDisplay.setImageBitmap(myBitmap);

					itemName.setText(mDiamondItem.get(position).name);

					jewellery_type_name.setText("Jewellery Type : " + mDiamondItem.get(position).jewelleryType);

					gender_name.setText("Gender : " + mDiamondItem.get(position).gender);

					wearing_style_name.setText("Wearing Style : " + mDiamondItem.get(position).style);

					design_type_name.setText("Design Type : " + mDiamondItem.get(position).designType);

					color_name.setText("Color : " +mDiamondItem.get(position).color);

					item_price.setText("Price : " + mDiamondItem.get(position).price);

					diamondFullImageDetails.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							diamondFullImageDetails.setVisibility(View.GONE);

							diamondFullScreenFlipper.setInAnimation(inFromRightAnimation());

							diamondFullScreenFlipper.showNext();
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
}
