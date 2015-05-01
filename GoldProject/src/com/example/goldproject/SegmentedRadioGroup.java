package com.example.goldproject;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

public class SegmentedRadioGroup extends RadioGroup {

	public SegmentedRadioGroup(Context context) {

		super(context);
	}

	public SegmentedRadioGroup(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {

		super.onFinishInflate();

		changeButtonsImages();
	}

	private void changeButtonsImages(){

		int count = super.getChildCount();

		if(count > 1)
		{
			super.getChildAt(0).setBackgroundResource(com.example.goldproject.R.drawable.segment_radio_left);

			super.getChildAt(1).setBackgroundResource(com.example.goldproject.R.drawable.segment_radio_middle);

			super.getChildAt(count-1).setBackgroundResource(com.example.goldproject.R.drawable.segment_right_radio);
		}
	}
}
