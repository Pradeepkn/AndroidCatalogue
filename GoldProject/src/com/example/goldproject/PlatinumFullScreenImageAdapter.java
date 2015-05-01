package com.example.goldproject;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.goldproject.R;
import com.example.goldproject.TouchImageView.OnTouchImageViewListener;
import com.example.goldproject.jewellerymodels.PlatinumItems;

public class PlatinumFullScreenImageAdapter extends PagerAdapter {

	private Activity pActivity;

	private TouchImageView imageDisplay;

	private TextView item_name;

	private TextView jewellery_type_name;

	private TextView gender_name;

	private TextView wearing_style_name;

	private TextView design_type_name;

	private TextView color_name;

	private TextView item_price;

	private ArrayList<PlatinumItems> mPlatinumItem = new ArrayList<PlatinumItems>();

	private LayoutInflater layoutInflater;

	private TextView currentZoomTextView;

	private DecimalFormat df;

	private TextView scrollPositionTextView;

	public void setItem(ArrayList<PlatinumItems> item) {

		mPlatinumItem.clear();

		if (item != null) {

			mPlatinumItem.addAll(item);
		}
		notifyDataSetChanged();
	}

	//constructor
	public PlatinumFullScreenImageAdapter(Activity activity, String imgList){

		System.out.println("entering fullscreenimage adapter");

		this.pActivity = activity;
	}

	@Override
	public int getCount() {

		return this.mPlatinumItem.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {

		return view == ((LinearLayout)object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		//Returns the string representation of the given int.
		String filename = String.valueOf(mPlatinumItem.get(position).url.hashCode());

		layoutInflater = (LayoutInflater) pActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View viewLayout = layoutInflater.inflate(R.layout.platinumfullscreenimage, container, false);

		File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();

		if (filename != null && !filename.isEmpty()) {

			File imgFile = new File(SDCardRoot, filename);

			System.out.println("imgFile---" + imgFile);

			System.out.println("fullImageListUrl---" + filename);

			if (imgFile.exists()) {

				Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

				imageDisplay = (TouchImageView) viewLayout.findViewById(R.id.platinumGridimgDisplay);

				item_name = (TextView) viewLayout.findViewById(R.id.pitem_name);

				jewellery_type_name = (TextView) viewLayout.findViewById(R.id.pjewellery_type_name);

				gender_name = (TextView) viewLayout.findViewById(R.id.pgender_name);

				wearing_style_name = (TextView) viewLayout.findViewById(R.id.pwearing_style_name);

				design_type_name = (TextView) viewLayout.findViewById(R.id.pdesign_type_name);

				color_name = (TextView) viewLayout.findViewById(R.id.pcolor_name);

				item_price = (TextView) viewLayout.findViewById(R.id.pitem_price);

				scrollPositionTextView = (TextView) viewLayout.findViewById(R.id.platinum_scroll_position);

				currentZoomTextView = (TextView) viewLayout.findViewById(R.id.platinum_current_zoom);

				imageDisplay.setImageBitmap(myBitmap);

				item_name.setText(mPlatinumItem.get(position).name);

				jewellery_type_name.setText("Jewellery Type : " + mPlatinumItem.get(position).jewelleryType);

				gender_name.setText("Gender : " + mPlatinumItem.get(position).gender);

				wearing_style_name.setText("Wearing Style : " + mPlatinumItem.get(position).style);

				design_type_name.setText("Design Type : " + mPlatinumItem.get(position).designType);

				color_name.setText("Color : " +mPlatinumItem.get(position).color);

				item_price.setText("Price : " + mPlatinumItem.get(position).price);

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
