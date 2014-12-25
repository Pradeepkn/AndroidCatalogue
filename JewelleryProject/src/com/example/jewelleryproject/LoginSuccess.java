package com.example.jewelleryproject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LoginSuccess extends ActionBarActivity implements OnClickListener{

	TextView homeText, offersText, updateOffersText, goldText, DiamondText, silverText, platinumText, settingsText, collectionsText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_success);

		homeText = (TextView) findViewById(R.id.homeView);
		offersText = (TextView) findViewById(R.id.offersView);
		updateOffersText = (TextView) findViewById(R.id.updateOffersView);
		goldText = (TextView) findViewById(R.id.goldView1);
		DiamondText = (TextView) findViewById(R.id.diamondView1);
		silverText = (TextView) findViewById(R.id.silverView1);
		platinumText = (TextView) findViewById(R.id.platinumView1);
		settingsText = (TextView) findViewById(R.id.settingsView);
		collectionsText = (TextView) findViewById(R.id.collectionsView);

		homeText.setOnClickListener(this);
		offersText.setOnClickListener(this);
		updateOffersText.setOnClickListener(this);
		goldText.setOnClickListener(this);
		DiamondText.setOnClickListener(this);
		silverText.setOnClickListener(this);
		platinumText.setOnClickListener(this);
		settingsText.setOnClickListener(this);
		collectionsText.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.homeView:
			Intent homeIntent = new Intent(LoginSuccess.this, HomeScreenImagesApi.class);
			startActivity(homeIntent);
			break;
		case R.id.offersView:
			Intent offersIntent = new Intent(LoginSuccess.this, OffersApi.class);
			startActivity(offersIntent);
			break;
		case R.id.updateOffersView:
			Intent updateIntent = new Intent(LoginSuccess.this, UpdateOffersApi.class);
			startActivity(updateIntent);
			break;
		case R.id.goldView1:
			Intent goldIntent = new Intent(LoginSuccess.this, GoldApi.class);
			startActivity(goldIntent);
			break;
		case R.id.diamondView1:
			Intent diamondIntent = new Intent(LoginSuccess.this, DiamondApi.class);
			startActivity(diamondIntent);
			break;
		case R.id.platinumView1:
			Intent platinumIntent = new Intent(LoginSuccess.this, PlatinumApi.class);
			startActivity(platinumIntent);
			break;
		case R.id.silverView1:
			Intent silverIntent = new Intent(LoginSuccess.this, SilverApi.class);
			startActivity(silverIntent);
			break;
		case R.id.collectionsView:
			Intent collIntent = new Intent(LoginSuccess.this, NewCollectionsApi.class);
			startActivity(collIntent);
			break;

		default:
			break;
		}
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_success, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
