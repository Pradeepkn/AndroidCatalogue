package com.example.goldproject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class SilverApi extends ActionBarActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_silver_api);

		System.out.println("entering to gold API");

		getSupportActionBar().hide();
	}
}
