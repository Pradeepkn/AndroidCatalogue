package com.example.goldproject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class GoldApi extends ActionBarActivity {

	//private ViewFlipper mViewFlipper1;
	/*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.activity_gold_api, container, false);
	}*/

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_gold_api);

		System.out.println("entering to gold API");

		getSupportActionBar().hide();

		/*mViewFlipper1 = (ViewFlipper) findViewById(R.id.viewFlipperFullImage);

		ImageView fullImageView = (ImageView) findViewById(R.id.GoldImageLogo1);

		fullImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mViewFlipper1.setDisplayedChild(1);

				overridePendingTransition(R.animator.home_anim, 0);

				finish();
			}
		});*/
		/*ActionBar actionBar = getSupportActionBar();

		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);

		actionBar.setDisplayShowHomeEnabled(false);

		actionBar.setDisplayShowTitleEnabled(false);

		LayoutInflater mInflater = LayoutInflater.from(this);

		View mCustomView = mInflater.inflate(R.layout.golditems_textview, null);
		// add the custom view to the action bar
		//actionBar.setCustomView(R.layout.back_textview);

		TextView backView = (TextView) mCustomView.findViewById(R.id.goldItemsTextView);

		ImageView goldBackView = (ImageView) mCustomView.findViewById(R.id.goldBackImageView);

		TextView goldBacktextView = (TextView) mCustomView.findViewById(R.id.goldBackView);

		actionBar.setCustomView(mCustomView);

		actionBar.setDisplayShowCustomEnabled(true);*/
	}
	//	public void setActionBarTitle(String title){
	//		
	//		getSupportActionBar().setTitle(title);
	//	}
}
