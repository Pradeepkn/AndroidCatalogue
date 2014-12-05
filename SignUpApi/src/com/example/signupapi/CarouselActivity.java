package com.example.signupapi;

import java.util.ArrayList;

import com.touchmenotapps.carousel.simple.HorizontalCarouselLayout;
import com.touchmenotapps.carousel.simple.HorizontalCarouselLayout.CarouselInterface;
import com.touchmenotapps.carousel.simple.HorizontalCarouselStyle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CarouselActivity extends Activity implements OnClickListener{

	private HorizontalCarouselStyle mStyle;
	private HorizontalCarouselLayout mCarousel;
	private CarouselAdapter mAdapter;
	private ArrayList<Integer> mData = new ArrayList<Integer>(0);

	Button homeButton, offersButton, updateButton, goldButton, silverButton, diamondButton, platinumButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_carousel);

		mData.add(R.drawable.banner1);
		mData.add(R.drawable.banner2);
		mData.add(R.drawable.banner3);
		mData.add(R.drawable.banner4);
		mData.add(R.drawable.banner5);
		mData.add(R.drawable.banner6);

		mAdapter = new CarouselAdapter(this);
		mAdapter.setData(mData);
		mCarousel = (HorizontalCarouselLayout) findViewById(R.id.carousel_layout);
		mStyle = new HorizontalCarouselStyle(this, HorizontalCarouselStyle.NO_STYLE);		
		mCarousel.setStyle(mStyle);
		mCarousel.setAdapter(mAdapter);

		mCarousel.setOnCarouselViewChangedListener(new CarouselInterface() {
			@Override
			public void onItemChangedListener(View v, int position) {
				Toast.makeText(CarouselActivity.this, "Position " + String.valueOf(position), Toast.LENGTH_SHORT).show();
			}
		});		

		homeButton = (Button) findViewById(R.id.homeButton);
		offersButton = (Button) findViewById(R.id.offersButton);
		updateButton = (Button) findViewById(R.id.updateoffersButton);
		goldButton = (Button) findViewById(R.id.goldButton);
		silverButton = (Button) findViewById(R.id.silverButton);
		diamondButton = (Button) findViewById(R.id.diamondButton);
		platinumButton = (Button) findViewById(R.id.platinumButton);
		homeButton.setOnClickListener(this);
		offersButton.setOnClickListener(this);
		updateButton.setOnClickListener(this);
		goldButton.setOnClickListener(this);
		silverButton.setOnClickListener(this);
		diamondButton.setOnClickListener(this);
		platinumButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.carousel, menu);
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

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.homeButton:
			Intent intent1 = new Intent(this, HomeScreenApi.class);
			startActivity(intent1);
			break;
		case R.id.offersButton:
			Intent intent2 = new Intent(this, OffersApi.class);
			startActivity(intent2);
			break;
		case R.id.updateoffersButton:
			Intent intent3 = new Intent(this, UpdateOffersApi.class);
			startActivity(intent3);
			break;
		case R.id.goldButton:
			Intent intent4 = new Intent(this, GoldApi.class);
			startActivity(intent4);
			break;
		case R.id.diamondButton:
			Intent intent5 = new Intent(this, DiamondApi.class);
			startActivity(intent5);
			break;
		case R.id.silverButton:
			Intent intent6 = new Intent(this, SilverApi.class);
			startActivity(intent6);
			break;
		case R.id.platinumButton:
			Intent intent7 = new Intent(this, PlatinumApi.class);
			startActivity(intent7);
			break;
		default:
			break;
		}
	}
}
