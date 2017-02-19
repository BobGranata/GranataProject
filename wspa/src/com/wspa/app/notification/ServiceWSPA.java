package com.wspa.app.notification;

import com.google.android.gcm.GCMRegistrar;
import com.wspa.app.notification.adapters.SharedPref;
import com.wspa.app.notification.content.ContentManager;

import static com.wspa.app.notification.CommonUtilities.SERVER_URL;
import static com.wspa.app.notification.CommonUtilities.SENDER_ID;
import static com.wspa.app.notification.CommonUtilities.EXTRA_MESSAGE;
import static com.wspa.app.notification.CommonUtilities.DISPLAY_MESSAGE_ACTION;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.IBinder;

public class ServiceWSPA extends Service {

	AsyncTask<Void, Void, Void> mRegisterTask;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		checkNotNull(SERVER_URL, "SERVER_URL");
		checkNotNull(SENDER_ID, "SENDER_ID");
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			// Automatically registers application on startup.
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM, check server.
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.
				// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				// mDisplay.append(getString(R.string.already_registered) +
				// "\n");
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						ContentManager cm = new ContentManager(getApplicationContext());
						boolean registered = ServerUtilities.register(context, 
								regId, 
								cm.GetIMEI(), 
								cm.GetCode(), 
								cm.GetNotificationsGroup(), 
								cm.GetSoundType());
						// At this point all attempts to register with the app
						// server failed, so we need to unregister the device
						// from GCM - the app will try to register again when
						// it is restarted. Note that GCM will send an
						// unregistered callback upon completion, but
						// GCMIntentService.onUnregistered() will ignore it.
						if (!registered) {
							GCMRegistrar.unregister(context);
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
	}

	private void checkNotNull(Object reference, String name) {
		if (reference == null) {
			throw new NullPointerException(getString(R.string.error_config,
					name));
		}
	}

	@Override
	public void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		unregisterReceiver(mHandleMessageReceiver);
		try{
			GCMRegistrar.onDestroy(this);
		}catch(Exception e) { }
		super.onDestroy();
	}
	
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// !!!!!!!!!!!!!!!!!!!!!!!!!
			// mDisplay.append(newMessage + "\n");
		}
	};
}
