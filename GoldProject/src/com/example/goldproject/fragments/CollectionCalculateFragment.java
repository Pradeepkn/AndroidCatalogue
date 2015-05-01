package com.example.goldproject.fragments;

import com.example.goldproject.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CollectionCalculateFragment extends Fragment {

	public TextView calculate;

	public TextView check;

	private Activity collectionscalculateActivity;

	// onAttach creates form Activity
	@Override
	public void onAttach(Activity activity) {

		collectionscalculateActivity = activity;

		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	// calling the layout
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.collection_cal_frag, container, false);

		return view;
	}
}
