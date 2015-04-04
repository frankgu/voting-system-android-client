package com.dongfeng.andoird_votingsystem;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dongfeng.function.MessageDialog;
import com.dongfeng.function.Transmission;

public class Voting extends ActionBarActivity {

	private String username = null;
	private RadioGroup radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voting);
		
		checkVoterVoteState();
		showCandidateList();
	}

	private void checkVoterVoteState() {
		// initialize the user name and check if the voter has voted or not
		Intent i = getIntent();
		username = i.getExtras().getString("username", "");
		String reply_data = Transmission.getInstance()
				.sendData("6:" + username);
		if (reply_data.compareTo("null") == 0) {
			// fail to connect to the server
			MessageDialog.getInstance().showErrorMessage(this,
					"Fail to connect the server");
			// redirect to the main page
			startActivity(new Intent(this, MainActivity.class));
		} else {
			// check the voter state
			String[] reply_datas = reply_data.split(":");
			if (reply_datas[1].compareTo("1") == 0) {
				// voter hasn't voted, do nothing
			} else if (reply_datas[2].compareTo("2") == 0) {
				// voter already vote, show the candidate name
				String candidateName = reply_datas[1];
				TextView candiate_view = (TextView) findViewById(R.id.voting_candidate_view);
				candiate_view.setText(candidateName);
				candiate_view.setTextColor(Color.GREEN);

				// disable the vote button
				Button vote_button = (Button) findViewById(R.id.voting_button);
				vote_button.setEnabled(false);

			}
		}
	}

	private void showCandidateList() {
		// show the candidate list
		String candidate_list = Transmission.getInstance().sendData("4:");
		if (candidate_list.compareTo("null") == 0) {
			// fail to connect to the server
			MessageDialog.getInstance().showErrorMessage(this,
					"Fail to connect the server");
			// redirect to the main page
			startActivity(new Intent(this, MainActivity.class));
		} else {
			// add the candidate to the radio group
			String[] candidate_lists = candidate_list.split(":");
			final List<String> candidate_name = new ArrayList<String>();
			for (int x = 1; x < candidate_lists.length; x = x + 3) {
				String candidateName = candidate_lists[x + 1] + " "
						+ candidate_lists[x + 2];
				candidate_name.add(candidateName);
			}

			radioGroup = (RadioGroup) findViewById(R.id.voting_radiogroup_candidatelist);
			int count = 0;
			for (String candidate : candidate_name) {
				RadioButton rdbtn = new RadioButton(this);
				rdbtn.setId(count);
				rdbtn.setText(candidate);
				count++;
				radioGroup.addView(rdbtn);
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.voting, menu);
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

	public void voting_button_click_event(View view) {

		// get the candidate choice
		String radiovalue = ((RadioButton) findViewById(radioGroup
				.getCheckedRadioButtonId())).getText().toString();

		// vote for this candidate
		String reply_data = Transmission.getInstance().sendData(
				"2:" + username + ":" + radiovalue);
		if (reply_data.compareTo("null") == 0) {
			// fail to connect to the server
			MessageDialog.getInstance().showErrorMessage(this,
					"Fail to connect the server");
			// redirect to the main page
			startActivity(new Intent(this, MainActivity.class));
		} else {
			String[] reply_datas = reply_data.split(":");
			if (reply_datas[0].compareTo("1") == 0) {
				// error message
				MessageDialog.getInstance().showErrorMessage(this,
						reply_datas[1]);
			} else {

				MessageDialog.getInstance().showSuccessMessage(this,
						"Successfully vote");
				checkVoterVoteState();
			}
		}

	}

	public void voting_logout_button_click_event(View view) {
		// logout the system
		onBackPressed();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		// logout the user
		String reply_data = Transmission.getInstance()
				.sendData("5:" + username);
		if (reply_data.compareTo("null") == 0) {
			// fail to connect to the server
			MessageDialog.getInstance().showErrorMessage(this,
					"Fail to connect the server");
			// redirect to the main page
			startActivity(new Intent(this, MainActivity.class));
		} else {
			String[] reply_datas = reply_data.split(":");
			if (reply_datas[0].compareTo("1") == 0) {
				MessageDialog.getInstance().showErrorMessage(this,
						reply_datas[1]);
			} else {
				MessageDialog.getInstance().showSuccessMessage(this,
						"Successfully logout");
			}
		}
	}
}
