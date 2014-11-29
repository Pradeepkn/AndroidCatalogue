package com.example.signupapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener{

	TextView textView1;
	TextView textView2;
	TextView textView3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		textView1 = (TextView) findViewById(R.id.homeview);
		textView2 = (TextView) findViewById(R.id.offersview1);
		textView3 = (TextView) findViewById(R.id.updateoffersapi);
		textView1.setOnClickListener(this);
		textView2.setOnClickListener(this);
		textView3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.homeview:
			Intent intent = new Intent(this, HomeScreenApi.class);
			startActivity(intent);
			break;
		case R.id.offersview1:
			Intent intent1 = new Intent(this, OffersApi.class);
			startActivity(intent1);
			break;
		case R.id.updateoffersapi:
			Intent intent2 = new Intent(this, UpdateOffersApi.class);
			startActivity(intent2);
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
