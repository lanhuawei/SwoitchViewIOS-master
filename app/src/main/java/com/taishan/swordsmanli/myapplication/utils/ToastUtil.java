package com.taishan.swordsmanli.myapplication.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	private static Toast mToast;

	public static void showToast(Context context, String info) {
		if (mToast == null) {
			/*
			 * When the Toast object is in the state, no longer creates the
			 * Toast object, and the object is to prevent multiple Toast's
			 * continuous pop up.
			 */
			mToast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(info);
		}
		mToast.show();
	}

	public static void showToast(Context context, int stringId) {
		showToast(context, context.getResources().getString(stringId));
	}

}
