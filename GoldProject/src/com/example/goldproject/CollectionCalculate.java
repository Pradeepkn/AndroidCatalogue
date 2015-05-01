package com.example.goldproject;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CollectionCalculate extends Fragment implements OnClickListener {

	public TextView calculate;

	public TextView check;

	private Activity calculateActivity;

	// onAttach creates form Activity
	@Override
	public void onAttach(Activity activity) {

		calculateActivity = activity;

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

		View view = inflater.inflate(R.layout.activity_collection_calculate, container, false);

		initLogInComponents(view);

		return view;
	}

	// initializing the components
	private void initLogInComponents(View view) {

		Button next = (Button) view.findViewById(R.id.next);

		next.setOnClickListener(this);

		Button reset = (Button) view.findViewById(R.id.reset);

		reset.setOnClickListener(this);
	}

	// performs action based on the button clicked
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.next:

			break;

		case R.id.reset:

			break;

		default:
			break;
		}
	}
}
