package com.example.goldproject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class PlatinumApi extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_platinum_api);
		
		System.out.println("entering to platinum API");

		getSupportActionBar().hide();
	}
}
