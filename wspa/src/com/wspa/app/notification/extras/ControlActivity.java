package com.wspa.app.notification.extras;

import android.app.Activity;
import android.app.backup.RestoreObserver;
import android.content.Intent;

import com.google.analytics.tracking.android.EasyTracker;
import com.wspa.app.notification.R;
import com.wspa.app.notification.info.ActivityWspaInfo;
import com.wspa.app.notification.memory.ActivityGameMainMenu;
import com.wspa.app.notification.memory.ActivityGameProcess;
import com.wspa.app.notification.memory.ActivityGameResult;
import com.wspa.app.notification.news.ActivityNews;
import com.wspa.app.notification.news.ActivityNewsDetails;
import com.wspa.app.notification.notifications.ActivityNotifications;

public class ControlActivity {

	public ActivityNews aNews;
	public ActivityNewsDetails aNewsDetails;
	public ActivityGameMainMenu aGameMainMenu;
	public ActivityGameProcess aGameProcess;
	public ActivityGameResult aGameResult;
	public ActivityWspaInfo aWspaInfo;
	public ActivityNotifications aNotification;
	int PresentActivity;

	public ControlActivity(int _PresentActivity, ActivityNews _aNews) {
		ClearData();
		PresentActivity = _PresentActivity;
		aNews = _aNews;
	}

	public ControlActivity(int _PresentActivity,
			ActivityNewsDetails _aNewsDetails) {
		ClearData();
		PresentActivity = _PresentActivity;
		aNewsDetails = _aNewsDetails;
	}

	public ControlActivity(int _PresentActivity,
			ActivityGameMainMenu _aGameMainMenu) {
		ClearData();
		PresentActivity = _PresentActivity;
		aGameMainMenu = _aGameMainMenu;
	}

	public ControlActivity(int _PresentActivity,
			ActivityGameProcess _aGameProcess) {
		ClearData();
		PresentActivity = _PresentActivity;
		aGameProcess = _aGameProcess;
	}

	public ControlActivity(int _PresentActivity, ActivityGameResult _aGameResult) {
		ClearData();
		PresentActivity = _PresentActivity;
		aGameResult = _aGameResult;
	}

	public ControlActivity(int _PresentActivity, ActivityWspaInfo _aWspaInfo) {
		ClearData();
		PresentActivity = _PresentActivity;
		aWspaInfo = _aWspaInfo;
	}

	public ControlActivity(int _PresentActivity,
			ActivityNotifications _aNotification) {
		ClearData();
		PresentActivity = _PresentActivity;
		aNotification = _aNotification;
	}

	public void ChangeEnableActivity() {
		switch (PresentActivity) {
		case DataConsts.ACT_GAME_MAIN_MENU: {
			if (aGameMainMenu != null)
				aGameMainMenu.ChangeEnableStatus();
			break;
		}
		case DataConsts.ACT_GAME_RESULT: {
			if (aGameResult != null)
				aGameResult.ChangeEnableStatus();
			break;
		}
		case DataConsts.ACT_GAME_PROCESS: {
			if (aGameProcess != null)
				aGameProcess.ChangeEnableStatus();
			break;
		}
		case DataConsts.ACT_NOTOFICATION: {
			if (aNotification != null)
				aNotification.ChangeEnableStatus();
			break;
		}
		case DataConsts.ACT_WSPA_INFO: {
			if (aWspaInfo != null)
				aWspaInfo.ChangeEnableStatus();
			break;
		}
		case DataConsts.ACT_NEWS: {
			if (aNews != null)
				aNews.ChangeEnableStatus();
			break;
		}
		case DataConsts.ACT_NEWS_DETAILS: {
			if (aNewsDetails != null)
				aNewsDetails.ChangeEnableStatus();
			break;
		}
		}// switch(PresentActivity)
	}// public void ChangeEnableActivity()

	private void ClearData() {
		aNews = null;
		aNewsDetails = null;
		aGameMainMenu = null;
		aGameProcess = null;
		aGameResult = null;
		aWspaInfo = null;
		aNotification = null;
	}

	public Activity GetActivity() {
		switch (PresentActivity) {
		case DataConsts.ACT_GAME_MAIN_MENU:
			return aGameMainMenu;
		case DataConsts.ACT_GAME_RESULT:
			return aGameResult;
		case DataConsts.ACT_GAME_PROCESS:
			return aGameProcess;
		case DataConsts.ACT_NOTOFICATION:
			return aNotification;
		case DataConsts.ACT_WSPA_INFO:
			return aWspaInfo;
		case DataConsts.ACT_NEWS:
			return aNews;
		case DataConsts.ACT_NEWS_DETAILS:
			return aNewsDetails;
		}// switch(PresentActivity)
		return null;
	}// public Activity GetActivity()

	public int GetShareType() {
		switch (PresentActivity) {
		case DataConsts.ACT_GAME_MAIN_MENU:
			return 0;
		case DataConsts.ACT_GAME_RESULT:{
			EasyTracker.getInstance().setContext(GetActivity().getApplicationContext());
			EasyTracker.getTracker().trackEvent(GetActivity().getString(R.string.A_A_CLICK),
					GetActivity().getString(R.string.A_S_HIGHSCORE),
					GetActivity().getString(R.string.A_S_HIGHSCORE),
					null);
			return DataConsts.SHARE_GAME_SCORE;
		}
		case DataConsts.ACT_GAME_PROCESS:
			return 0;
		case DataConsts.ACT_NOTOFICATION:{
			EasyTracker.getInstance().setContext(GetActivity().getApplicationContext());
			EasyTracker.getTracker().trackEvent(GetActivity().getString(R.string.A_A_CLICK),
					GetActivity().getString(R.string.A_S_APP),
					GetActivity().getString(R.string.A_S_APP),
					null);
			return DataConsts.SHARE_INFO;
		}
		case DataConsts.ACT_WSPA_INFO:{
			EasyTracker.getInstance().setContext(GetActivity().getApplicationContext());
			EasyTracker.getTracker().trackEvent(GetActivity().getString(R.string.A_A_CLICK),
					GetActivity().getString(R.string.A_S_APP),
					GetActivity().getString(R.string.A_S_APP),
					null);
			return DataConsts.SHARE_INFO;
		}
		case DataConsts.ACT_NEWS:{
			EasyTracker.getInstance().setContext(GetActivity().getApplicationContext());
			EasyTracker.getTracker().trackEvent(GetActivity().getString(R.string.A_A_CLICK),
					GetActivity().getString(R.string.A_S_APP),
					GetActivity().getString(R.string.A_S_APP),
					null);
			return DataConsts.SHARE_INFO;
		}
		case DataConsts.ACT_NEWS_DETAILS:{
			EasyTracker.getInstance().setContext(GetActivity().getApplicationContext());
			EasyTracker.getTracker().trackEvent(GetActivity().getString(R.string.A_A_CLICK),
					GetActivity().getString(R.string.A_S_NEWS_DETAILS),
					GetActivity().getString(R.string.A_S_NEWS_DETAILS),
					null);			
			return DataConsts.SHARE_NEWS_DETAILS;
		}
		}// switch(PresentActivity)
		return 0;
	}// public int GetShareType
	
	public void GoBack(){
		switch(PresentActivity){
		case DataConsts.ACT_NEWS:{
			GetActivity().finish();
		}
		case DataConsts.ACT_NEWS_DETAILS:{
			GetActivity().finish();
			break;
		}
		case DataConsts.ACT_GAME_MAIN_MENU:{
			Intent intent = new Intent(GetActivity(), ActivityNews.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			GetActivity().startActivity(intent);
			GetActivity().finish();
			break;
		}
		case DataConsts.ACT_GAME_PROCESS:{
			GetActivity().finish();
			break;
		}
		case DataConsts.ACT_GAME_RESULT:{
			GetActivity().finish();
			break;
		}
		case DataConsts.ACT_WSPA_INFO:{
			Intent intent = new Intent(GetActivity(), ActivityNews.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			GetActivity().startActivity(intent);
			GetActivity().finish();
			break;
		}
		case DataConsts.ACT_NOTOFICATION:{
			Intent intent = new Intent(GetActivity(), ActivityNews.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			GetActivity().startActivity(intent);
			GetActivity().finish();
			break;
		}
		} // switch(PresentActivity)
	}
	
	public String GetGameResult() {
		if(PresentActivity == DataConsts.ACT_GAME_RESULT) return aGameResult.GetGameResult();
		else return "";
	}
	
	public void ShowAboutGame(){
		if(PresentActivity == DataConsts.ACT_GAME_MAIN_MENU) aGameMainMenu.ShowAboutGame();
	}
}
