package com.bvmit.star_0733.minor_project;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {

	// Custom Toast Method
	public void Show_Toast(Context context, View view, String error) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(com.bvmit.star_0733.minor_project.R.layout.custom_toast,
                (ViewGroup) view.findViewById(com.bvmit.star_0733.minor_project.R.id.toast_root));
		TextView text = layout.findViewById(com.bvmit.star_0733.minor_project.R.id.toast_error);
		text.setText(error);

		Toast toast = new Toast(context);
		toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);// Set
																		// Toast
																		// gravity
																		// and
																		// Fill
																		// Horizoontal

		toast.setDuration(Toast.LENGTH_SHORT);// Set Duration
		toast.setView(layout); // Set Custom View over toast

		toast.show();// Finally show toast
	}

}
