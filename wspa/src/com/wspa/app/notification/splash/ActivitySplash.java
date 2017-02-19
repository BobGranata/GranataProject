package com.wspa.app.notification.splash;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.google.analytics.tracking.android.EasyTracker;
import com.wspa.app.notification.R;
import com.wspa.app.notification.ServiceWSPA;
import com.wspa.app.notification.adapters.SharedPref;
import com.wspa.app.notification.content.NetworkManager;
import com.wspa.app.notification.extras.SettingsConsts;
import com.wspa.app.notification.news.ActivityNews;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.view.Window;
import android.widget.Toast;

public class ActivitySplash extends Activity {

	Timer SplashTimer;
	SharedPref sp;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		context = getApplicationContext();
		SplashTimer = new Timer();
		if (NetworkManager.isOnline(this)) {
			SplashTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					MainScreen();
				}
			}, 4000);
		} else {
			Toast.makeText(this, getString(R.string.ERROR_OFFLINE),
					Toast.LENGTH_SHORT).show();
			SplashTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					finish();
				}
			}, 4000);
		}
	}

	private void MainScreen() {
		// SplashTimer.cancel();
		Intent intent;
		sp = new SharedPref(context, getString(R.string.APP_SET));

		if (sp.IsRegistered()) {
			if (sp.ReadBoolValue(SettingsConsts.PUSH_STATUS)) {
				if (!CheckService()) {
					startService(new Intent(ActivitySplash.this,
							ServiceWSPA.class));
				}
			}
			// startService(new Intent(ActivitySplash.this,
			// ServiceWSPA.class));
			EasyTracker.getInstance().setContext(getApplicationContext());
			EasyTracker.getTracker().trackEvent(getString(R.string.A_A_LADEN),
					getString(R.string.A_L_APP), getString(R.string.A_L_APP),
					null);
			intent = new Intent(context, ActivityNews.class);
		} else {
			sp.SetBoolValue(SettingsConsts.PUSH_STATUS, false);
			sp.SetBoolValue(SettingsConsts.PUSH_BEER, true);
			sp.SetBoolValue(SettingsConsts.PUSH_HUISDIEREN, true);
			sp.SetBoolValue(SettingsConsts.PUSH_KIPPEN, true);
			sp.SetBoolValue(SettingsConsts.PUSH_OLIFANT, true);
			sp.SetBoolValue(SettingsConsts.PUSH_VEE, true);
			sp.SetIntValue(SettingsConsts.PUSH_SOUND_TYPE, 1);
			sp.SetBoolValue(SettingsConsts.NEWS_SORT_TYPE, true);
			intent = new Intent(context, ActivityRegCode.class);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	private Boolean CheckService() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (ServiceWSPA.class.getName().equals(
					service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
}
