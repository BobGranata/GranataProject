package com.wspa.app.notification.memory;

import java.util.Timer;
import java.util.TimerTask;

import com.wspa.app.notification.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MemoryCard extends ImageView {
	
	boolean sideOpen = false;
	boolean cardOnTable = true;
	int xPosCard;
	int yPosCard;
	int indexResurse;
	Bitmap openSideImage;

	private TimerTask mTimerTask;
	private final Handler handler = new Handler();
	private Timer t = new Timer();
	private int nCounter = 0;	
	Animation animation; 
	boolean mChangeImage = false;
	int imageRes;

	public MemoryCard(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		animation = AnimationUtils.loadAnimation(context, R.anim.scale);
	}

	public void setOpenCard(boolean changeImage) {
		this.mChangeImage = changeImage;
		this.startAnimation(animation);
		doTimerTask();
		//this.setImageBitmap(openSideImage);
	}

	public void doTimerTask() {

		mTimerTask = new TimerTask() {
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						nCounter++;
						if (nCounter == 2) {							
							if (mChangeImage == true) {
								MemoryCard.this.setImageBitmap(openSideImage);								
							} else {
								MemoryCard.this.setImageResource(R.drawable.blank_side_card);
							}							
							nCounter = 0;
							stopTask();
						}
					}
				});
			}
		};

		t.schedule(mTimerTask, 0, 100); //

	}

	public void stopTask() {

		if (mTimerTask != null) {
			mTimerTask.cancel();
		}

	}
}
