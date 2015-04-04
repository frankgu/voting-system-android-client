package com.dongfeng.andoird_votingsystem;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.dongfeng.function.MessageDialog;
import com.dongfeng.function.Transmission;

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

		if (username_editext.getText().toString().isEmpty()
				|| password_editext.getText().toString().isEmpty()
				|| firstname_editext.getText().toString().isEmpty()
				|| lastname_editext.getText().toString().isEmpty()
				|| address_editext.getText().toString().isEmpty()) {

			// if the user fail to input some value, pop up the error message
			MessageDialog.getInstance().showErrorMessage(this,
					"Please fill in all the information");

		} else {

			String data = "1:1:" + username_editext.getText().toString() + ":"
					+ lastname_editext.getText().toString() + ":"
					+ firstname_editext.getText().toString() + ":"
					+ address_editext.getText().toString() + ":"
					+ password_editext.getText().toString();

			String reply_data = Transmission.getInstance().sendData(data);
			String[] reply_datas = reply_data.split(":");
			if (reply_data.compareTo("null") == 0) {
				// fail to connect to the server
				MessageDialog.getInstance().showErrorMessage(this,
						"Fail to connect the server");

			} else {
				if (reply_datas[0].compareTo("1") == 0) {
					// show the error message
					MessageDialog.getInstance().showErrorMessage(this,
							reply_datas[1]);
				} else {
					// successfully register
					MessageDialog.getInstance().showSuccessMessage(this,
							"Successfully registered");
				}
			}
		}

	}
}
