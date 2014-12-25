package com.example.jewelleryproject;

import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TabBar extends FragmentActivity {

	private static final String TAB_1_TAG = "tab_1";
	private static final String TAB_2_TAG = "tab_2";
	private static final String TAB_3_TAG = "tab_3";
	private static final String TAB_4_TAG = "tab_4";
	private static final String TAB_5_TAG = "tab_5";
	private static final String TAB_6_TAG = "tab_6";

	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_bar);
		InitView();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void InitView() {

		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		mTabHost.addTab(setIndicator(TabBar.this,mTabHost.newTabSpec(TAB_1_TAG), R.drawable.tab_indicator_gen,"Home",R.drawable.b1),Tab1Container.class,null);

		mTabHost.addTab(setIndicator(TabBar.this,mTabHost.newTabSpec(TAB_2_TAG), R.drawable.tab_indicator_gen,"Gold",R.drawable.b1),Tab2Container.class,null);

		mTabHost.addTab(setIndicator(TabBar.this,mTabHost.newTabSpec(TAB_3_TAG), R.drawable.tab_indicator_gen,"Diamond",R.drawable.b1),Tab3Container.class,null);

		mTabHost.addTab(setIndicator(TabBar.this,mTabHost.newTabSpec(TAB_4_TAG), R.drawable.tab_indicator_gen,"Silver",R.drawable.b1),Tab4Container.class,null);

		mTabHost.addTab(setIndicator(TabBar.this,mTabHost.newTabSpec(TAB_5_TAG), R.drawable.tab_indicator_gen,"Platinum",R.drawable.b1),Tab5Container.class,null);

		mTabHost.addTab(setIndicator(TabBar.this,mTabHost.newTabSpec(TAB_6_TAG), R.drawable.tab_indicator_gen,"Settings",R.drawable.b1),Tab6Container.class,null);
	}

	@Override
	public void onBackPressed() {

		boolean isPopFragment = false;
		String currentTabTag = mTabHost.getCurrentTabTag();

		if (currentTabTag.equals(TAB_1_TAG)) {
			isPopFragment = ((BaseContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_1_TAG)).popFragment();
		} else if (currentTabTag.equals(TAB_2_TAG)) {
			isPopFragment = ((BaseContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_2_TAG)).popFragment();
		}
		else if (currentTabTag.equals(TAB_3_TAG)) {
			isPopFragment = ((BaseContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_3_TAG)).popFragment();
		}

		if (!isPopFragment) {
			finish();
		}
	}

	private TabSpec setIndicator(Context ctx, TabSpec spec, int resid, String string, int genresIcon) {
		View v = LayoutInflater.from(ctx).inflate(R.layout.tab_item, null);
		v.setBackgroundResource(resid);
		TextView tv = (TextView)v.findViewById(R.id.txt_tabtxt);
		ImageView img = (ImageView)v.findViewById(R.id.img_tabtxt);

		tv.setText(string);
		img.setBackgroundResource(genresIcon);
		return spec.setIndicator(v);
	}
}
