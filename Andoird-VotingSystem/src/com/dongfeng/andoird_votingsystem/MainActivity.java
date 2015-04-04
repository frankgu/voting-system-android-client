package com.dongfeng.andoird_votingsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dongfeng.function.MessageDialog;
import com.dongfeng.function.Transmission;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		// refresh the district
		String district = Transmission.getInstance().getDistrict();
		if (district != null) {
			TextView district_view = (TextView) findViewById(R.id.main_district_view);
			district_view.setText(district);
			district_view.setTextColor(Color.GREEN);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	// the login button onclick handler
	public void login_button_click_event(View view) {
		if (Transmission.getInstance().getDistrict() == null) {
			MessageDialog.getInstance().showErrorMessage(this,
					"Please go to setting and connect to the server first");
		} else {
			// get the username and password from the view
			EditText username_editText = (EditText) findViewById(R.id.Username);
			EditText password_editText = (EditText) findViewById(R.id.Password);

			String username = username_editText.getText().toString();
			String password = password_editText.getText().toString();

			if (username.isEmpty() || password.isEmpty()) {
				MessageDialog.getInstance().showErrorMessage(this,
						"Please input the valid username/password");
			} else {

				if (validateUser(username, password)) {
					// redirect to the user login function, once you
					// successfully login, you can't go back to the login page
					// unless you logout
					Intent intent = new Intent(this, Voting.class);
					String extraData = username;
					intent.putExtra("username", extraData);
					startActivity(intent);
				}
			}
		}
	}

	private boolean validateUser(String username, String password) {

		// send the message to the server
		String request_data = "3:" + username + ":" + password;
		String reply_data = Transmission.getInstance().sendData(request_data);
		String[] reply_datas = reply_data.split(":");
		if (reply_data.compareTo("null") == 0) {
			// fail to connect to the server
			MessageDialog.getInstance().showErrorMessage(this,
					"Fail to connect the server");
			return false;
		} else {
			if (reply_datas[0].compareTo("1") == 0) {
				// show the error message
				MessageDialog.getInstance().showErrorMessage(this,
						reply_datas[1]);
				return false;
			} else {
				// successfully login
				return true;
			}
		}

	}

	public void regist_button_click_event(View view) {
		// check if the client has connect to the server or not
		if (Transmission.getInstance().getDistrict() == null) {
			MessageDialog.getInstance().showErrorMessage(this,
					"Please go to setting and connect to the server first");
		} else {
			// redirect to user registration page
			Intent intent = new Intent(this, Register.class);
			startActivity(intent);
		}

	}

	public void login_setting_click_event(View view) {

		// pop up the message box and show the setting dialog for client
		MessageDialog.getInstance().showSettingMessageDialog(this);

	}

}
