package com.wspa.app.notification.adapters;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogPublic {
	
	private static int Uses = 0;
	private static ProgressDialog mProgressDialog = null;
	
	public static ProgressDialog ShowProgressDialog(Context con){
		if(mProgressDialog == null){
			mProgressDialog = new ProgressDialog(con);
			mProgressDialog.setMessage("Laden...");
			mProgressDialog.setCancelable(false);
			if(!mProgressDialog.isShowing()) mProgressDialog.show();
		}
		
		Uses++;
		return mProgressDialog;
	}
	
	public static void RemoveProgressDialog(){
		if(mProgressDialog == null) return;
		
		Uses--;
		
		if(Uses == 0){
			if(mProgressDialog.isShowing()) mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
}
