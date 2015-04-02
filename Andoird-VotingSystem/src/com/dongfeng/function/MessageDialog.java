package com.dongfeng.function;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;

public class MessageDialog {

	private static MessageDialog amd = new MessageDialog();

	private MessageDialog() {
	}

	public static MessageDialog getInstance() {

		return amd;

	}

	public void showErrorMessage(ActionBarActivity main, String message) {

		new AlertDialog.Builder(main).setTitle("Error").setMessage(message)
				.setPositiveButton("OK", null).create().show();
	}

}
