package com.example.signupapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HomePage extends Activity {

	TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		System.out.println("entering to the jwelery home page");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		//final Context context = this;

		textView = (TextView) findViewById(R.id.clickhereView);

		textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomePage.this, LoginApi.class);
				startActivity(intent);
				finish();
			}
		}); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_page, menu);
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
