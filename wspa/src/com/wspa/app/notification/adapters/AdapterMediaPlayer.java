package com.wspa.app.notification.adapters;

import java.io.IOException;

import com.wspa.app.notification.R;
import com.wspa.app.notification.notifications.ActivityNotifications;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class AdapterMediaPlayer {

	Context context;

	public AdapterMediaPlayer(Context con) {
		this.context = con;
	}

	public AdapterMediaPlayer(ActivityNotifications act) {
		// TODO Auto-generated constructor stub
	}

	public void PlaySound(int id) {
		MediaPlayer mp = new MediaPlayer();
		AssetFileDescriptor afd = null;
		try {
			switch (id) {
			case 0: {
				/*
				Uri defaultRingtoneUri = RingtoneManager
						.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				mp.setDataSource(context, defaultRingtoneUri);
				mp.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
				mp.prepare();
				mp.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						mp.release();
					}
				});
				mp.start();*/
				return;}
			case 1:{
				afd = context.getResources().openRawResourceFd(R.raw.s1);
				break;
			}
			case 2:{
				afd = context.getResources().openRawResourceFd(R.raw.s2);
				break;
			}
			case 3:{
				afd = context.getResources().openRawResourceFd(R.raw.s3);
				break;
			}
			case 4:{
				afd = context.getResources().openRawResourceFd(R.raw.s4);
				break;
			}
			case 5:{
				afd = context.getResources().openRawResourceFd(R.raw.s5);
				break;
			}
			} // switch (id)
			mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
	        mp.prepare();
	        mp.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
			mp.start();
			afd.close();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
