package com.wspa.app.notification.adapters;

import com.wspa.app.notification.extras.DataConsts;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TypeSet {

	Typeface regular, 
			 bold,
			 italic,
			 italic_bold;
	
	public TypeSet(Context context) {
		regular = Typeface.createFromAsset(context.getAssets(), DataConsts.ARIAL_REGULAR);
		bold = Typeface.createFromAsset(context.getAssets(), DataConsts.ARIAL_BOLD);
		italic = Typeface.createFromAsset(context.getAssets(), DataConsts.ARIAL_ITALIC);
		italic_bold = Typeface.createFromAsset(context.getAssets(), DataConsts.ARIAL_ITALIC_BOLD);
	}
	
	public TextView SetTF(TextView tw, String TypeFont){
		if (TypeFont == DataConsts.ARIAL_REGULAR) {
			tw.setTypeface(regular);
		} else if (TypeFont == DataConsts.ARIAL_BOLD) {
			tw.setTypeface(bold);
		} else if (TypeFont == DataConsts.ARIAL_ITALIC) {
			tw.setTypeface(italic);
		} else if (TypeFont == DataConsts.ARIAL_ITALIC_BOLD) {
			tw.setTypeface(italic_bold);
		}
		return tw;
	}
	
	public EditText SetTF(EditText et, String TypeFont) {
		if (TypeFont == DataConsts.ARIAL_REGULAR) {
			et.setTypeface(regular);
		} else if (TypeFont == DataConsts.ARIAL_BOLD) {
			et.setTypeface(bold);
		} else if (TypeFont == DataConsts.ARIAL_ITALIC) {
			et.setTypeface(italic);
		} else if (TypeFont == DataConsts.ARIAL_ITALIC_BOLD) {
			et.setTypeface(italic_bold);
		}
		return et;
	}

	public Button SetTF(Button btn, String TypeFont) {
		if (TypeFont == DataConsts.ARIAL_REGULAR) {
			btn.setTypeface(regular);
		} else if (TypeFont == DataConsts.ARIAL_BOLD) {
			btn.setTypeface(bold);
		} else if (TypeFont == DataConsts.ARIAL_ITALIC) {
			btn.setTypeface(italic);
		} else if (TypeFont == DataConsts.ARIAL_ITALIC_BOLD) {
			btn.setTypeface(italic_bold);
		}
		return btn;
	}
}
