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

public class DiamondApiFirstFragment extends Fragment {

	private Activity mDiamondApi;

	ViewFlipper diamondFlipper;

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

		mDiamondApi = activity;

		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		System.out.println("diamond on create() started.......................");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.diamond_fragment_viewflipper, container, false);

		initSignUpComponenets(view);

		return view;
	}

	private void initSignUpComponenets(View view) {

		diamondFlipper = (ViewFlipper) view.findViewById(R.id.diamondFlipper);

		TextView diamondWomenView= (TextView) view.findViewById(R.id.diamondWomenId);

		TextView bracelateView = (TextView) view.findViewById(R.id.diamondBracelateId);

		ImageView diamondCategoryBackImage = (ImageView) view.findViewById(R.id.diamondCategoryBackImageView);

		TextView diamondCategoryBackText = (TextView) view.findViewById(R.id.diamondCategoryBackTextView);

		ImageView diamondDetailsBackImage = (ImageView) view.findViewById(R.id.diamondDetailsBackImage);

		TextView diamondDetailsBackText = (TextView) view.findViewById(R.id.diamondDetailsBackTextView);

		diamondWomenView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				diamondFlipper.setInAnimation(inFromRightAnimation());

				diamondFlipper.showNext();
			}
		});

		bracelateView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				diamondFlipper.setInAnimation(inFromRightAnimation());

				diamondFlipper.showNext();
			}
		});

		diamondCategoryBackImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				diamondFlipper.setInAnimation(inFromLeftAnimation());

				diamondFlipper.showPrevious();
			}
		});

		diamondCategoryBackText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				diamondFlipper.setInAnimation(inFromLeftAnimation());

				diamondFlipper.showPrevious();
			}
		});

		diamondDetailsBackImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				diamondFlipper.setInAnimation(inFromLeftAnimation());

				diamondFlipper.showPrevious();
			}
		});

		diamondDetailsBackText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				diamondFlipper.setInAnimation(inFromLeftAnimation());

				diamondFlipper.showPrevious();
			}
		});
	}
}
