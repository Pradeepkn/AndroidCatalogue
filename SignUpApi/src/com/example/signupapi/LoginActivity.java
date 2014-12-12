package com.example.signupapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener{

	TextView homeText, offersText, updateOffersText, goldText, DiamondText, silverText, platinumText, settingsText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		homeText = (TextView) findViewById(R.id.homeView);
		offersText = (TextView) findViewById(R.id.offersView);
		updateOffersText = (TextView) findViewById(R.id.updateOffersView);
		goldText = (TextView) findViewById(R.id.goldView1);
		DiamondText = (TextView) findViewById(R.id.diamondView1);
		silverText = (TextView) findViewById(R.id.silverView1);
		platinumText = (TextView) findViewById(R.id.platinumView1);
		settingsText = (TextView) findViewById(R.id.settingsView);

		homeText.setOnClickListener(this);
		offersText.setOnClickListener(this);
		updateOffersText.setOnClickListener(this);
		goldText.setOnClickListener(this);
		DiamondText.setOnClickListener(this);
		silverText.setOnClickListener(this);
		platinumText.setOnClickListener(this);
		settingsText.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.homeView:
			Intent homeIntent = new Intent(LoginActivity.this, HomeScreenApi.class);
			startActivity(homeIntent);
			break;
		case R.id.offersView:
			Intent offersIntent = new Intent(LoginActivity.this, OffersApi.class);
			startActivity(offersIntent);
			break;
		case R.id.updateOffersView:
			Intent updateIntent = new Intent(LoginActivity.this, UpdateOffersApi.class);
			startActivity(updateIntent);
			break;
		case R.id.goldView1:
			Intent goldIntent = new Intent(LoginActivity.this, GoldApi.class);
			startActivity(goldIntent);
			break;
		case R.id.diamondView1:
			Intent diamondIntent = new Intent(LoginActivity.this, DiamondApi.class);
			startActivity(diamondIntent);
			break;
		case R.id.platinumView1:
			Intent platinumIntent = new Intent(LoginActivity.this, PlatinumApi.class);
			startActivity(platinumIntent);
			break;
		case R.id.silverView1:
			Intent silverIntent = new Intent(LoginActivity.this, SilverApi.class);
			startActivity(silverIntent);
			break;

		default:
			break;
		}
	}
}
