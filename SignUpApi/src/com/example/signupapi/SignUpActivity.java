package com.example.signupapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends Activity {

	private String url1 = "http://brinvents.com/jewel/Apis/signupclass.php";
	private EditText username,email,mnumber,password,errorCode,errorMessage,statusCode;
	private HandleJSON obj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("on create() started.......................");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		username = (EditText)findViewById(R.id.editText1);
		email = (EditText)findViewById(R.id.editText2);
		mnumber = (EditText)findViewById(R.id.editText3);
		password = (EditText)findViewById(R.id.editText4);
		errorCode = (EditText)findViewById(R.id.editText5);
		errorMessage = (EditText)findViewById(R.id.editText6);
		statusCode = (EditText)findViewById(R.id.editText7);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}
	public void open(View view) {
		
		System.out.println("open() invoking..................................");
		
		System.out.println(url1+"url====================================");

		obj = new HandleJSON(url1);
		obj.fetchJSON();
		System.out.println(obj+"obj..................................");

		while(obj.parsingComplete);
		errorCode.setText(obj.getErrorcode());
		errorMessage.setText(obj.getErrormessage());
		statusCode.setText(obj.getStatuscode());
	}
}
