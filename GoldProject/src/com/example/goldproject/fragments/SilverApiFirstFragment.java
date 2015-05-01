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

public class SilverApiFirstFragment  extends Fragment implements OnClickListener{

	private Activity mSilverApi;

	private ViewFlipper silverFlipper;

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

		mSilverApi = activity;

		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		System.out.println("silver API on create() started.......................");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.silver_fragment_viewflipper, container, false);

		initSignUpComponenets(view);

		return view;
	}

	private void initSignUpComponenets(View view) {

		silverFlipper = (ViewFlipper) view.findViewById(R.id.silverFlipper);

		TextView womenView= (TextView) view.findViewById(R.id.SilverWomenId);

		TextView banglesView = (TextView) view.findViewById(R.id.SilverBanglesId);

		ImageView silverCategoryBackImage = (ImageView) view.findViewById(R.id.silverItemsCategoryBackImageView);

		TextView silverCategoryBackText = (TextView) view.findViewById(R.id.silverItemsCategoryBackTextView);

		ImageView silverDetailsBackImage = (ImageView) view.findViewById(R.id.silverItemsDetailsBackImageView);

		TextView silverDetailsBackText = (TextView) view.findViewById(R.id.silverItemsDetailsBackTextView);

		womenView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				silverFlipper.setInAnimation(inFromRightAnimation());

				silverFlipper.showNext();
			}
		});

		banglesView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				silverFlipper.setInAnimation(inFromRightAnimation());

				silverFlipper.showNext();
			}
		});

		silverCategoryBackImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				silverFlipper.setInAnimation(inFromLeftAnimation());

				silverFlipper.showPrevious();
			}
		});

		silverCategoryBackText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				silverFlipper.setInAnimation(inFromLeftAnimation());

				silverFlipper.showPrevious();
			}
		});

		silverDetailsBackImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				silverFlipper.setInAnimation(inFromLeftAnimation());

				silverFlipper.showPrevious();
			}
		});

		silverDetailsBackText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				silverFlipper.setInAnimation(inFromLeftAnimation());

				silverFlipper.showPrevious();
			}
		});
	}

	@Override
	public void onClick(View v) {

	}
}
