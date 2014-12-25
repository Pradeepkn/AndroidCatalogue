package com.example.jewelleryproject.fragments;

import com.example.jewelleryproject.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class GoldApiFragment1 extends Fragment implements OnClickListener{

	private Activity mGoldApi1;

	@Override
	public void onAttach(Activity activity) {
		mGoldApi1 = activity;
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("on create() started.......................");
		//mSignUpActivity.setContentView(R.layout.signup_fragment_api);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gold_api_fragment1, container, false);

		initSignUpComponenets(view);
		return view;
	}

	private void initSignUpComponenets(View view) {
	}


	@Override
	public void onClick(View v) {

	}

}
