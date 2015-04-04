package com.dongfeng.function;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

	public void showErrorMessage(Context main, String message) {

		new AlertDialog.Builder(main).setTitle("Error").setMessage(message)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton("OK", null).create().show();
	}

	public void showSuccessMessage(Context main, String message) {

		new AlertDialog.Builder(main).setTitle("Success").setMessage(message)
				.setPositiveButton("OK", null).create().show();

	}

	public void showSettingMessageDialog(final Activity main) {

		AlertDialog.Builder builder = new AlertDialog.Builder(main);
		LayoutInflater inflater = main.getLayoutInflater();
		final View main_view = inflater.inflate(R.layout.dialog_setting, null);
		builder.setView(main_view);
		builder.setTitle("Input").setIcon(android.R.drawable.ic_dialog_info)
				.setNegativeButton("Cancel", null);
		builder.setPositiveButton("confirm",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						EditText port_edittext = (EditText) main_view
								.findViewById(R.id.dialog_port_input);
						EditText host_edittext = (EditText) main_view
								.findViewById(R.id.dialog_host_input);

						if (port_edittext.getText().toString().isEmpty()
								|| host_edittext.getText().toString().isEmpty()) {
							showErrorMessage(main,
									"Please input the port and host");
						} else {
							int port = Integer.parseInt(port_edittext.getText()
									.toString());
							String host = host_edittext.getText().toString();

							// change the transmission file (port , host) and
							// get
							// the district info
							if (!Transmission.getInstance().initialization(
									port, host)) {
								showErrorMessage(main,
										"Fail to connect the server");
							}
						}

					}
				});
		builder.create().show();
	}
}
