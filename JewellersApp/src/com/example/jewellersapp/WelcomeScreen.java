package com.example.jewellersapp;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.TextView;

public class WelcomeScreen extends ActionBarActivity implements OnClickListener {
	TextView forclick;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_screen);
		forclick = (TextView) findViewById(R.id.ClickHere);
		forclick.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		Intent in = new Intent(WelcomeScreen.this, LoginAPI.class);
		startActivity(in);

	}
}
