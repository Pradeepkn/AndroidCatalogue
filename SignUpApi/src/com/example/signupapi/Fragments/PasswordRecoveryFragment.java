package com.example.signupapi.Fragments;

import com.example.signupapi.LoginApi;
import com.example.signupapi.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.password_rec_fragment, container, false);

		initForgotScreenComponents(view);
		return view;
	}

	/**
	 * init forgot screen componenets
	 * @param view
	 */
	private void initForgotScreenComponents(View view) {
		passwordEditText = (EditText)view.findViewById(R.id.recovery_editText);
		cPasswordEditText = (EditText)view.findViewById(R.id.crecovery_editText);

		Button submitButton = (Button)view.findViewById(R.id.recovery_button);
		submitButton.setOnClickListener(this);
	}

	/*@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}
	 */
	@Override
	public void onClick(View v) {

		Intent intent = new Intent(mPasswordRecovery,LoginApi.class);
		startActivity(intent);
	}
}
