package com.example.goldproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class LoginApi extends ActionBarActivity implements OnClickListener {

	private ViewFlipper mViewFlipper1;

	private ViewFlipper mViewFlipper2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login_api);
		
		getSupportActionBar().hide();

		/*ActionBar actionBar = getSupportActionBar(); 
		// As you said you are using support library
		LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View v = inflator.inflate(R.layout.back_textview, null);

		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		actionBar.setCustomView(v);*/
		ActionBar actionBar = getSupportActionBar();

		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(false);

		actionBar.setDisplayShowHomeEnabled(false);

		actionBar.setDisplayShowTitleEnabled(false);

		LayoutInflater mInflater = LayoutInflater.from(this);

		View mCustomView = mInflater.inflate(R.layout.back_textview, null);

		// add the custom view to the action bar
		actionBar.setCustomView(mCustomView);

		TextView LoginBackText = (TextView) mCustomView.findViewById(R.id.loginBackTextView);

		ImageView LoginBackImage = (ImageView) mCustomView.findViewById(R.id.loginBackImageView);

		LoginBackImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(LoginApi.this, HomePage.class);

				startActivity(intent);
			}
		});

		LoginBackText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(LoginApi.this, HomePage.class);

				startActivity(intent);
			}
		});

		actionBar.setCustomView(mCustomView);

		actionBar.setDisplayShowCustomEnabled(true);

		mViewFlipper1 = (ViewFlipper) this.findViewById(R.id.view_flipper_1);

		mViewFlipper1.setAutoStart(true);

		mViewFlipper1.setFlipInterval(5000);

		mViewFlipper2 = (ViewFlipper) this.findViewById(R.id.view_flipper_2);

		TextView signupView = (TextView) findViewById(R.id.forgot_textview);

		signupView.setOnClickListener(this);

		TextView forgotpwdView = (TextView) findViewById(R.id.signup_textview);

		forgotpwdView.setOnClickListener(this);

		ImageView registerclose = (ImageView) findViewById(R.id.closeView);

		registerclose.setOnClickListener(this);

		ImageView closeimageView = (ImageView) findViewById(R.id.closeImage1);

		closeimageView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.forgot_textview:

			mViewFlipper2.setDisplayedChild(1);

			break;
		case R.id.signup_textview:

			mViewFlipper2.setDisplayedChild(2);

			break;
		case R.id.closeView:

			mViewFlipper2.setDisplayedChild(0);

			break;
		case R.id.closeImage1:

			mViewFlipper2.setDisplayedChild(0);

			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_api, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button 
		case android.R.id.home: 
			NavUtils.navigateUpFromSameTask(this); 
			return true; 
		}*/
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/*@Override
	public void onBackPressed() {
		moveTaskToBack(true);
		LoginApi.this.finish();
	}*/
}
