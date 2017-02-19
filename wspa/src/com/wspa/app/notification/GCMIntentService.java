/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wspa.app.notification;

import static com.wspa.app.notification.CommonUtilities.SENDER_ID;
import static com.wspa.app.notification.CommonUtilities.displayMessage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.wspa.app.notification.adapters.AdapterMediaPlayer;
import com.wspa.app.notification.adapters.SharedPref;
import com.wspa.app.notification.content.ContentManager;
import com.wspa.app.notification.content.ParserJSON;
import com.wspa.app.notification.extras.SettingsConsts;
import com.wspa.app.notification.models.MessageModel;
import com.wspa.app.notification.news.NewsModel;
import com.wspa.app.notification.splash.ActivitySplash;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";
	private static SharedPref sp;
	private static int cx;

	// public static AdapterMediaPlayer mp;

	public GCMIntentService() {
		super(SENDER_ID);
		cx = 0;
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);
		sp = new SharedPref(getApplicationContext(),
				getString(R.string.APP_SET));
		sp.SetStringValue("PUSH_ACCESS_TOKEN", registrationId);
		displayMessage(context, getString(R.string.gcm_registered));
		ContentManager cm = new ContentManager(getApplicationContext());
		ServerUtilities.register(context, registrationId, cm.GetIMEI(),
				cm.GetCode(), cm.GetNotificationsGroup(), cm.GetSoundType());
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		displayMessage(context, getString(R.string.gcm_unregistered));
		if (GCMRegistrar.isRegisteredOnServer(context)) {
			ServerUtilities.unregister(context, registrationId);
		} else {
			// This callback results from the call to unregister made on
			// ServerUtilities when the registration to the server failed.
			Log.i(TAG, "Ignoring unregister callback");
		}
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received message");
		//
		String message = intent.getStringExtra("aps");
		displayMessage(context, message);
		// notifies user
		generateNotification(context, message);
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		displayMessage(context, message);
		// notifies user
		generateNotification(context, message);
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
		displayMessage(context, getString(R.string.gcm_error, errorId));
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		displayMessage(context,
				getString(R.string.gcm_recoverable_error, errorId));
		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	private static void generateNotification(Context context, String message) {
		// ѕарсинг текста сообщени€
		MessageModel messageParsed = new ParserJSON(null, false, context).parseMessage(message);
		Resources res = context.getResources();
		//Intent notificationIntent = new Intent(context, ActivitySplash.class);
		// —оздание уникального идентификатора сообщени€
		int id = new Random().nextInt(1000);
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update((messageParsed.getAlert() + String.valueOf(id)).getBytes());
			byte messageDigest[] = digest.digest();
			StringBuffer octString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) octString.append(Integer.toOctalString(0x77 & messageDigest[i]));
			// In some cases, the Indian code - the right way
			String temp = octString.toString();
			String temp1 = temp.substring(0, 7);
			id = Integer.valueOf(temp1);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// —оздание уведомлени€
		PendingIntent contentIntent = PendingIntent.getActivity(context, id, new Intent(context, ActivitySplash.class), PendingIntent.FLAG_CANCEL_CURRENT);
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		// sp = new SharedPref(context, context.getString(R.string.APP_SET));
		// int SoundState = sp.ReadIntValue(SettingsConsts.PUSH_SOUND_TYPE);
		// mp.PlaySound(messageParsed.GetSound());
		builder.setContentIntent(contentIntent)
				.setSmallIcon(R.drawable.ic_launcher)
				.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher))		// больша€ иконка
				.setTicker(messageParsed.getAlert())											// бегущий текст
				.setWhen(System.currentTimeMillis()).setAutoCancel(true)						// событие при нажатии
				.setContentTitle(context.getString(R.string.APP_NAME))							// заголовок уведомлени€
				.setContentText(messageParsed.getAlert());										// текст уведомлени€
		Notification nDetails = builder.build();
		nm.notify(id, nDetails);
		// ¬спроизведение звука уведомлени€
		try {
			MediaPlayer mMediaPlayer = new MediaPlayer();
			int Sound = messageParsed.GetSound();
			Boolean isStandart = false;
			switch (Sound) {
			case 0: {
				Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				mMediaPlayer.setDataSource(context, defaultRingtoneUri);
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
				mMediaPlayer.prepare();
				mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						//mMediaPlayer.release();
						//isStandart = true;
					}
				});
				isStandart = true;
				break;
			}
			case 1: { mMediaPlayer = MediaPlayer.create(context, R.raw.s1); break; }
			case 2: { mMediaPlayer = MediaPlayer.create(context, R.raw.s2); break; }
			case 3: { mMediaPlayer = MediaPlayer.create(context, R.raw.s3); break; }
			case 4: { mMediaPlayer = MediaPlayer.create(context, R.raw.s4); break; }
			case 5: { mMediaPlayer = MediaPlayer.create(context, R.raw.s5); break; }
			} // switch (Sound)
			if (!isStandart) mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setLooping(false);
			mMediaPlayer.start();
		} catch (Exception e) { }
	}

}
