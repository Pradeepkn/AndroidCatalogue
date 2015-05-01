package com.example.goldproject.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.goldproject.R;

public class PlatinumApiFirstFragment  extends Fragment {

	private Activity mPlatinumApi;

	ViewFlipper platinumFlipper;

	private Animation inFromRightAnimation() {

		Animation inFromRight = new TranslateAnimation(

				Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,

				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
				);
		inFromRight.setDuration(500);

		inFromRight.setInterpolator(new AccelerateInterpolator());

		return inFromRight;
	}

	private Animation inFromLeftAnimation() {

		Animation inFromLeft = new TranslateAnimation(

				Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,

				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
				);
		inFromLeft.setDuration(500);

		inFromLeft.setInterpolator(new AccelerateInterpolator());

		return inFromLeft;
	}

	@Override
	public void onAttach(Activity activity) {

		mPlatinumApi = activity;

		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		System.out.println("platinum api on create() started.......................");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.platinum_fragment1_viewflipper, container, false);

		initSignUpComponenets(view);

		return view;
	}

	private void initSignUpComponenets(View view) {

		platinumFlipper = (ViewFlipper) view.findViewById(R.id.platinumFlipper);

		TextView platinumWomenView = (TextView) view.findViewById(R.id.platinumWomenId);

		ImageView platinumCategoryBackImage = (ImageView) view.findViewById(R.id.platinumItemsCategoryBackImageView);

		TextView platinumCategoryBackText = (TextView) view.findViewById(R.id.platinumItemsCategoryBackTextView);

		platinumWomenView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				platinumFlipper.setInAnimation(inFromRightAnimation());

				platinumFlipper.showNext();
			}
		});

		platinumCategoryBackImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				platinumFlipper.setInAnimation(inFromLeftAnimation());

				platinumFlipper.showPrevious();
			}
		});

		platinumCategoryBackText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				platinumFlipper.setInAnimation(inFromLeftAnimation());

				platinumFlipper.showPrevious();
			}
		});
	}
}
