package com.example.goldproject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HomePage extends Activity {

	//Displays text to the user and optionally allows them to edit it
	TextView textView;

	//Called when the activity is starting
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		//java.lang.System
		System.out.println("entering to the jwelery home page");

		//Set the activity content from a layout resource.
		setContentView(R.layout.activity_home_page);

		//Finds a view that was identified by the id attribute from the XML that was processed in onCreate.
		textView = (TextView) findViewById(R.id.clickhereView);

		//Register a callback to be invoked when this view is clicked.
		textView.setOnClickListener(new View.OnClickListener() {

			//Called when a view has been clicked.
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(HomePage.this, LoginApi.class);

				startActivity(intent);

				overridePendingTransition(R.animator.home_anim, 0);

				finish();
			}
		}); 
	}

	/*@Override
	protected void onResume() {
		super.onResume();

		// Logs 'install' and 'app activate' App Events.
		AppEventsLogger.activateApp(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Logs 'app deactivate' App Event.
		AppEventsLogger.deactivateApp(this);
	}*/

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
