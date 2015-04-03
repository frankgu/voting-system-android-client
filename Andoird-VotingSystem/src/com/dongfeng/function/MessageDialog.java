package com.dongfeng.function;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.dongfeng.andoird_votingsystem.R;

public class MessageDialog {

	private static MessageDialog amd = new MessageDialog();

	private MessageDialog() {
	}

	public static MessageDialog getInstance() {

		return amd;

	}

	public void showErrorMessage(ActionBarActivity main, String message) {

		new AlertDialog.Builder(main).setTitle("Error").setMessage(message)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton("OK", null).create().show();
	}

	public void showSettingMessageDialog(final ActionBarActivity main) {
		
		// next to fix and continue
		OnClickListener listener = new DialogInterface.OnClickListener() {

			EditText port_edittext = (EditText)main.findViewById(R.id.dialog_port_input);
			EditText host_edittext = (EditText)main.findViewById(R.id.dialog_host_input);
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				int port = Integer.parseInt(port_edittext.toString());
				String host = host_edittext.toString();
				
				System.out.println(port);
				System.out.println(host);
			}
			
		};

		LayoutInflater inflater = main.getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.dialog_setting, null, false);
		new AlertDialog.Builder(main).setTitle("Input")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(dialoglayout).setPositiveButton("Confirm", listener)
				.setNegativeButton("Cancel", null).create().show();
	}
}
