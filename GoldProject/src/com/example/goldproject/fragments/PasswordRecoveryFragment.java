package com.example.goldproject.fragments;

import com.example.goldproject.LoginApi;
import com.example.goldproject.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PasswordRecoveryFragment extends Fragment implements OnClickListener{

	/**
	 * password edit filed 
	 */
	private EditText passwordEditText;
	/**
	 * confirmpassword edit filed 
	 */
	private EditText cPasswordEditText;

	private Activity mPasswordRecovery;

	@Override
	public void onAttach(Activity activity) {

		mPasswordRecovery = activity;

		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.password_recovery_fragment, container, false);

		initForgotScreenComponents(view);

		return view;
	}

	/**
	 * init forgot screen componenets
	 * @param view
	 */
	private void initForgotScreenComponents(View view) {

		passwordEditText = (EditText)view.findViewById(R.id.recovery_editText);

		//cPasswordEditText = (EditText)view.findViewById(R.id.crecovery_editText);

		Button submitButton = (Button)view.findViewById(R.id.recovery_button);

		submitButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		Intent intent = new Intent(mPasswordRecovery,LoginApi.class);

		startActivity(intent);
	}
}

