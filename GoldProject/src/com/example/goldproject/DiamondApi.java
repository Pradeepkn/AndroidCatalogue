package com.example.goldproject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class DiamondApi extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_diamond_api);

		System.out.println("entering to diamond API");
		
		getSupportActionBar().hide();
	}
}
