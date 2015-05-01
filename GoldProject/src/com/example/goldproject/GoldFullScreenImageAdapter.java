package com.example.goldproject;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example.goldproject.TouchImageView.OnTouchImageViewListener;
import com.example.goldproject.jewellerymodels.GoldItems;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class GoldFullScreenImageAdapter extends PagerAdapter implements OnClickListener {

	private Activity gActivity;

	private TouchImageView imageDisplay;

//	private TextView itemName;
//
//	private TextView jewellery_type_name;
//
//	private TextView gender_name;
//
//	private TextView wearing_style_name;
//
//	private TextView design_type_name;
//
//	//	private TextView clarity_name;
//
//	private TextView color_name;
//
//	//	private TextView ring_size_name;
//
//	private TextView item_price;

	private TextView currentZoomTextView;

	private DecimalFormat df;

	private TextView scrollPositionTextView;

	private ArrayList<GoldItems> mGoldItem = new ArrayList<GoldItems>();

	private LayoutInflater layoutInflater;

	private ViewFlipper fullScreenFlipper;

	private Animation inFromRightAnimation() {

		Animation inFromRight = new TranslateAnimation(

				Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,

				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
				);
		inFromRight.setDuration(500);

		inFromRight.setInterpolator(new AccelerateInterpolator());

		return inFromRight;
	}
	private Animation inFromLeftAnimation() {

		Animation inFromLeft = new TranslateAnimation(

				Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,

				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
				);
		inFromLeft.setDuration(500);

		inFromLeft.setInterpolator(new AccelerateInterpolator());

		return inFromLeft;
	}

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

		return view == ((ViewFlipper) object);
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

//				itemName = (TextView) viewLayout.findViewById(R.id.itemName);
//
//				jewellery_type_name = (TextView) viewLayout.findViewById(R.id.jewellery_type_name);
//
//				gender_name = (TextView) viewLayout.findViewById(R.id.gender_name);
//
//				wearing_style_name = (TextView) viewLayout.findViewById(R.id.wearing_style_name);
//
//				design_type_name = (TextView) viewLayout.findViewById(R.id.design_type_name);
//
//				//				clarity_name = (TextView) viewLayout.findViewById(R.id.clarity_name);
//
//				color_name = (TextView) viewLayout.findViewById(R.id.color_name);
//
//				//				ring_size_name = (TextView) viewLayout.findViewById(R.id.ring_size_name);
//
//				item_price = (TextView) viewLayout.findViewById(R.id.item_price);

				scrollPositionTextView = (TextView) viewLayout.findViewById(R.id.scroll_position);

				currentZoomTextView = (TextView) viewLayout.findViewById(R.id.current_zoom);

				fullScreenFlipper = (ViewFlipper) viewLayout.findViewById(R.id.goldFullScreenFlipper);

//				Button fullScreenImageDetails = (Button) viewLayout.findViewById(R.id.fullScreenImageDetails);

//				ImageView goldItemsBackView = (ImageView) viewLayout.findViewById(R.id.goldItemsBackImage);

				imageDisplay.setImageBitmap(myBitmap);

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

//				fullScreenImageDetails.setOnClickListener(this);
//
//				itemName.setText(mGoldItem.get(position).name);
//
//				jewellery_type_name.setText("Jewellery Type : " + mGoldItem.get(position).jewelleryType);
//
//				gender_name.setText("Gender : " + mGoldItem.get(position).gender);
//
//				wearing_style_name.setText("Wearing Style : " + mGoldItem.get(position).style);
//
//				design_type_name.setText("Design Type : " + mGoldItem.get(position).designType);
//
//				//				clarity_name.setText("Clarity : " + mGoldItem.get(position).clarity);
//
//				color_name.setText("Color : " +mGoldItem.get(position).color);
//
//				//				ring_size_name.setText("Ring Size : " + mGoldItem.get(position).size);
//
//				item_price.setText("Price : " + mGoldItem.get(position).price);

				/*goldItemsBackView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						fullScreenFlipper.setInAnimation(inFromLeftAnimation());

						fullScreenFlipper.showPrevious();
					}
				});*/
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

		((ViewPager) container).removeView((ViewFlipper) object);
	}

	@Override
	public void onClick(View v) {

		fullScreenFlipper.setInAnimation(inFromRightAnimation());

		fullScreenFlipper.showNext();
	}
}
