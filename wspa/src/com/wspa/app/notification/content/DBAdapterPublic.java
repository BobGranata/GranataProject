package com.wspa.app.notification.content;

import android.content.Context;

public class DBAdapterPublic {
	
	private static DBAdapter mDb= null;
	private static int Uses = 0;
	
	public static DBAdapter OpenDb(Context con){
		if(mDb == null){
			mDb = new DBAdapter(con);
			mDb.open();
		}
		
		Uses++;
		return mDb;
	}
	
	public static void CloseDb(){
		if(mDb == null) return;
		
		Uses--;
		if(mDb.isOpened()) mDb.close();
		mDb = null;
	}

}
