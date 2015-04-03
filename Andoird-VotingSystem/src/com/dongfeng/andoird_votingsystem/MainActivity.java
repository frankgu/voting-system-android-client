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

		// get the username and password from the view
		EditText username_editText = (EditText) findViewById(R.id.Username);
		EditText password_editText = (EditText) findViewById(R.id.Password);

		String username = username_editText.getText().toString();
		String password = password_editText.getText().toString();

		if (username.isEmpty() || password.isEmpty()) {

			MessageDialog.getInstance().showErrorMessage(this,
					"Please input the valid username/password");

		} else {

			// user login function
			Intent intent = new Intent(this, Voting.class);
			startActivity(intent);
		}
	}

	public void regist_button_click_event(View view) {

		// redirect to user registration page
		Intent intent = new Intent(this, Register.class);
		startActivity(intent);

	}

	public void login_setting_click_event(View view) {

		// pop up the message box and show the
		MessageDialog.getInstance().showSettingMessageDialog(this);
	}

}
