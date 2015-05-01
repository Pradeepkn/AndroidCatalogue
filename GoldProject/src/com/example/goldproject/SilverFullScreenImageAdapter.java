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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.goldproject.R;
import com.example.goldproject.TouchImageView.OnTouchImageViewListener;
import com.example.goldproject.jewellerymodels.SilverItems;

public class SilverFullScreenImageAdapter extends PagerAdapter {

	private Activity sActivity;

	private TouchImageView imageDisplay;

	private TextView item_name;

	private TextView jewellery_type_name;

	private TextView gender_name;

	private TextView wearing_style_name;

	private TextView design_type_name;

	private TextView color_name;

	private TextView item_price;

	private ArrayList<SilverItems> mSilverItem = new ArrayList<SilverItems>();

	private LayoutInflater layoutInflater;

	private TextView currentZoomTextView;

	private DecimalFormat df;

	private TextView scrollPositionTextView;

	public void setItems(ArrayList<SilverItems> item) { 

		mSilverItem.clear();

		if (item != null) {

			mSilverItem.addAll(item);
		}
		notifyDataSetChanged();
	}

	//constructor
	public SilverFullScreenImageAdapter(Activity activity, String imgList) {

		System.out.println("entering fullscreenimage adapter");

		this.sActivity = activity;
	}

	@Override
	public int getCount() {

		return this.mSilverItem.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {

		return view == ((LinearLayout) object);	
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		String filename = String.valueOf(mSilverItem.get(position).url.hashCode());

		layoutInflater = (LayoutInflater) sActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View viewLayout = layoutInflater.inflate(R.layout.silver_full_screen_image, container, false);

		File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();

		if (filename != null && !filename.isEmpty()) {

			File imgFile = new File(SDCardRoot, filename);

			System.out.println("imgFile---" + imgFile);

			System.out.println("fullImageListUrl---" + filename);

			if (imgFile.exists()) {

				Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

				imageDisplay = (TouchImageView) viewLayout.findViewById(R.id.silverGridimgDisplay);

				item_name = (TextView) viewLayout.findViewById(R.id.sitem_name);

				jewellery_type_name = (TextView) viewLayout.findViewById(R.id.sjewellery_type_name);

				gender_name = (TextView) viewLayout.findViewById(R.id.sgender_name);

				wearing_style_name = (TextView) viewLayout.findViewById(R.id.swearing_style_name);

				design_type_name = (TextView) viewLayout.findViewById(R.id.sdesign_type_name);

				color_name = (TextView) viewLayout.findViewById(R.id.scolor_name);

				item_price = (TextView) viewLayout.findViewById(R.id.sitem_price);

				scrollPositionTextView = (TextView) viewLayout.findViewById(R.id.silver_scroll_position);

				currentZoomTextView = (TextView) viewLayout.findViewById(R.id.silver_current_zoom);

				imageDisplay.setImageBitmap(myBitmap);

				item_name.setText(mSilverItem.get(position).name);

				jewellery_type_name.setText("Jewellery Type : " + mSilverItem.get(position).jewelleryType);

				gender_name.setText("Gender : " + mSilverItem.get(position).gender);

				wearing_style_name.setText("Wearing Style : " + mSilverItem.get(position).style);

				design_type_name.setText("Design Type : " + mSilverItem.get(position).designType);

				color_name.setText("Color : " +mSilverItem.get(position).color);

				item_price.setText("Price : " + mSilverItem.get(position).price);

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
