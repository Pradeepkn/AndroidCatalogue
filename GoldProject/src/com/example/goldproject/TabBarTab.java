package com.example.goldproject;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class TabBarTab extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		//Enable extended window features.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//Set the activity content from a layout resource.
		setContentView(R.layout.activity_tab_bar_tab);

		// Your Tab Titles
		String tab_title[] = {"Home","Gold","Diamond","Platinum","Silver","Settings"};

		// Your Tab Drawables for their states
		int tab_drawables[] = {R.drawable.selector_for_hometab,R.drawable.selector_for_goldtab,R.drawable.selector_for_diamondtab,R.drawable.selector_for_platinumtab,R.drawable.selector_for_silvertab,R.drawable.selector_for_settingstab};

		// Your Tab Activities
		Object tab_act[] = { HomeScreenImagesApi.class, GoldApi.class, DiamondApi.class, PlatinumApi.class , SilverApi.class ,OurCollectionsAPI.class };

		// TabHost setup
		final TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

		// Call setup() before adding tabs if loading TabHost using findViewById().
		tabHost.setup();

		for (int i = 0; i < tab_act.length; i++) {

			//Get a new TabSpec associated with this tab host.
			TabSpec spec = this.getTabHost().newTabSpec(tab_title[i]);

			// A tab has a tab indicator, content, and a tag that is used to keep track of it. 
			View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);

			// Look for a child view with the given id. If this view has the given id, return this view.
			((TextView) tabIndicator.findViewById(R.id.tabText)).setText(tab_title[i]);

			((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource(tab_drawables[i]);

			// Specify a view as the tab indicator.
			spec.setIndicator(tabIndicator);

			// Specify an intent to use to launch an activity as the tab content.
			spec.setContent(new Intent(this, (Class<?>) tab_act[i]));

			// Add a tab.
			tabHost.addTab(spec);
		}
		tabHost.setCurrentTab(0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			TabBarTab.this.finish();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}