package com.example.jewellersapp.jewelleryfragments;

import com.example.jewellersapp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommonImageFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.image_fragment, container,false);
		return v;
	}
}
