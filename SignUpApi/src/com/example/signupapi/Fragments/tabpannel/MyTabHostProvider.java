package com.example.signupapi.Fragments.tabpannel;

import com.example.signupapi.DiamondApi;
import com.example.signupapi.GoldApi;
import com.example.signupapi.HomeScreenApi;
import com.example.signupapi.PlatinumApi;
import com.example.signupapi.R;
import com.example.signupapi.SilverApi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class MyTabHostProvider extends TabHostProvider{

	private Tab homeTab;
	private Tab goldTab;
	private Tab diamondTab;
	private Tab platinumTab;
	private Tab silverTab;
	private Tab settingsTab;

	private TabView tabView;
	private GradientDrawable gradientDrawable, transGradientDrawable;

	public MyTabHostProvider(Activity context) {
		super(context);
	}

	@Override
	public TabView getTabHost(String category) {

		tabView = new TabView(context);
		tabView.setOrientation(TabView.Orientation.BOTTOM);
		tabView.setBackgroundID(R.drawable.tab_background_gradient);

		gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFFB2DA1D, 0xFF85A315});
		gradientDrawable.setCornerRadius(0f);
		gradientDrawable.setDither(true);

		transGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0x00000000, 0x00000000});
		transGradientDrawable.setCornerRadius(0f);
		transGradientDrawable.setDither(true);

		homeTab = new Tab(context, category);
		homeTab.setIcon(R.drawable.home_sel);
		homeTab.setIconSelected(R.drawable.home_sel);
		homeTab.setBtnText("Home");
		homeTab.setBtnTextColor(Color.WHITE);
		homeTab.setSelectedBtnTextColor(Color.BLACK);
		homeTab.setBtnGradient(transGradientDrawable);
		homeTab.setSelectedBtnGradient(gradientDrawable);
		homeTab.setIntent(new Intent(context, HomeScreenApi.class));

		goldTab = new Tab(context, category);
		goldTab.setIcon(R.drawable.menu_sel);
		goldTab.setIconSelected(R.drawable.menu_sel);
		goldTab.setBtnText("Gold");
		goldTab.setBtnTextColor(Color.WHITE);
		goldTab.setSelectedBtnTextColor(Color.BLACK);
		goldTab.setBtnGradient(transGradientDrawable);
		goldTab.setSelectedBtnGradient(gradientDrawable);
		goldTab.setIntent(new Intent(context, GoldApi.class));

		diamondTab = new Tab(context, category);
		diamondTab.setIcon(R.drawable.menu_sel);
		diamondTab.setIconSelected(R.drawable.menu_sel);
		diamondTab.setBtnText("Diamond");
		diamondTab.setBtnTextColor(Color.WHITE);
		diamondTab.setSelectedBtnTextColor(Color.BLACK);
		diamondTab.setBtnGradient(transGradientDrawable);
		diamondTab.setSelectedBtnGradient(gradientDrawable);
		diamondTab.setIntent(new Intent(context, DiamondApi.class));

		platinumTab = new Tab(context, category);
		platinumTab.setIcon(R.drawable.menu_sel);
		platinumTab.setIconSelected(R.drawable.menu_sel);
		platinumTab.setBtnText("Platinum");
		platinumTab.setBtnTextColor(Color.WHITE);
		platinumTab.setSelectedBtnTextColor(Color.BLACK);
		platinumTab.setBtnGradient(transGradientDrawable);
		platinumTab.setSelectedBtnGradient(gradientDrawable);
		platinumTab.setIntent(new Intent(context, PlatinumApi.class));

		silverTab = new Tab(context, category);
		silverTab.setIcon(R.drawable.menu_sel);
		silverTab.setIconSelected(R.drawable.menu_sel);
		silverTab.setBtnText("Silver");
		silverTab.setBtnTextColor(Color.WHITE);
		silverTab.setSelectedBtnTextColor(Color.BLACK);
		silverTab.setBtnGradient(transGradientDrawable);
		silverTab.setSelectedBtnGradient(gradientDrawable);
		silverTab.setIntent(new Intent(context, SilverApi.class));

		settingsTab = new Tab(context, category);
		settingsTab.setIcon(R.drawable.setting_sel);
		settingsTab.setIconSelected(R.drawable.setting_sel);
		settingsTab.setBtnText("Settings");
		settingsTab.setBtnTextColor(Color.WHITE);
		settingsTab.setSelectedBtnTextColor(Color.BLACK);
		settingsTab.setBtnGradient(transGradientDrawable);
		settingsTab.setSelectedBtnGradient(gradientDrawable);
		settingsTab.setIntent(new Intent(context, SilverApi.class));

		tabView.addTab(homeTab);
		tabView.addTab(goldTab);
		tabView.addTab(diamondTab);
		tabView.addTab(silverTab);
		tabView.addTab(platinumTab);
		tabView.addTab(settingsTab);

		return tabView;
	}
}
