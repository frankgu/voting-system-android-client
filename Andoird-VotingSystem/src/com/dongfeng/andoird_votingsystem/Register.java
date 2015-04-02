package com.dongfeng.andoird_votingsystem;

import com.dongfeng.function.MessageDialog;
import com.dongfeng.function.Transmission;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Register extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void register_regist_button_click_event(View view) {

		// regist the account
		EditText username_editext = (EditText) findViewById(R.id.register_username);
		EditText password_editext = (EditText) findViewById(R.id.register_password);
		EditText firstname_editext = (EditText) findViewById(R.id.register_FLName);
		EditText lastname_editext = (EditText) findViewById(R.id.register_lastname);
		EditText address_editext = (EditText) findViewById(R.id.register_address);

		if (username_editext.toString().isEmpty()
				|| password_editext.toString().isEmpty()
				|| firstname_editext.toString().isEmpty()
				|| lastname_editext.toString().isEmpty()
				|| address_editext.toString().isEmpty()) {

			// if the user fail to input some value, pop up the error message
			MessageDialog.getInstance().showErrorMessage(this,
					"Please fill in all the information");
			
		} else {

			String data = "1:1:" + username_editext.toString() + ":"
					+ lastname_editext.toString() + ":"
					+ firstname_editext.toString() + ":"
					+ address_editext.toString() + ":"
					+ password_editext.toString();
			
			Transmission.getInstance().sendData(data);

		}

	}
}
