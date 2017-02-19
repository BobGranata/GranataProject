// Интерактивная инструкция демонстрирующая возможности приложения
package com.wspa.app.notification.splash;

import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.SharedPref;
import com.wspa.app.notification.adapters.TypeSet;
import com.wspa.app.notification.extras.DataConsts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

public class ActivityHello extends Activity implements OnTouchListener, OnClickListener{

	ViewFlipper flipper;
	ImageView image,		// Индикатор активного экрана 
			  imageBtnClose;// Кнопка "закрыть"
	Button btnToApp;		// Кнопка "To App" - возвращение к разделу ввода кода
	float fromPosition, 	// Координаты начала движения
		  toPosition;		// Координаты окончания движения
	int PresentLayout;		// Номер активного экрана
	TypeSet ts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hello_screen);
		super.onCreate(savedInstanceState);
		ts = new TypeSet(getApplicationContext());
		PresentLayout = 1;
		imageBtnClose = (ImageView) findViewById(R.id.imageViewBtnClose);
		btnToApp = (Button) findViewById(R.id.btnToApp);
		btnToApp.setVisibility(ImageView.INVISIBLE);
		btnToApp = ts.SetTF(btnToApp, DataConsts.ARIAL_REGULAR);
		image = (ImageView) findViewById(R.id.imageViewIndicator);
		image.setImageResource(R.drawable.screen_indicator1);
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.hello_screen_rl);
		rl.setOnTouchListener(this);
		imageBtnClose.setOnClickListener(this);
		btnToApp.setOnClickListener(this);
		btnToApp.setTypeface(Typeface.createFromAsset(getAssets(), DataConsts.ARIAL_REGULAR));
		flipper = (ViewFlipper) findViewById(R.id.flipper);
		// Инциализация флиппера
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		int layouts[] = new int[] { R.layout.hello_content_nieuws,
				R.layout.hello_content_memory,
				R.layout.hello_content_notificaties };
		for (int layout : layouts) flipper.addView(inflater.inflate(layout, null));
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
	    // Экран нажат - начало движения
		case MotionEvent.ACTION_DOWN: 
	        fromPosition = event.getX();
	        break;
	    // Экран отпущен - окончание движения
	    case MotionEvent.ACTION_UP:
	        float toPosition = event.getX();
	        // Движение влево
	        if ((fromPosition > toPosition) && PresentLayout < 3)
	        {
	        	PresentLayout++;
	            flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.go_next_in));
	            flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.go_next_out));
	            btnToApp.setVisibility(ImageView.INVISIBLE);
				ScreenIndAnim(PresentLayout, image);
				BtnCloseAnimation();
	            flipper.showNext();
	        }
	        // Движение вправо
	        else if ((fromPosition < toPosition) && PresentLayout > 1)
	        {
	        	PresentLayout--;
	            flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_in));
	            flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_out));
				ScreenIndAnim(PresentLayout, image);
				btnToApp.setVisibility(ImageView.INVISIBLE);
				BtnCloseAnimation();
	            flipper.showPrevious();
	        }
	        break;
	    default:
	        break;
	    }
		return true;
	}

	// Анимация индикаторов экрана
	public void ScreenIndAnim(int indicator, ImageView image) {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.invisible);
		image.startAnimation(anim);
		switch (indicator) {
		case 1:
			image.setImageResource(R.drawable.screen_indicator1);
			break;
		case 2:
			image.setImageResource(R.drawable.screen_indicator2);
			break;
		case 3:
			image.setImageResource(R.drawable.screen_indicator3);
			anim = AnimationUtils.loadAnimation(this, R.anim.visible);
			btnToApp.startAnimation(anim);
			btnToApp.setVisibility(ImageView.VISIBLE);
			break;
		default:
			break;
		}
		anim = AnimationUtils.loadAnimation(this, R.anim.visible);
		image.startAnimation(anim);
	}
	
	// Анимация кнопки "закрыть"
	public void BtnCloseAnimation(){
		imageBtnClose.setVisibility(ImageView.INVISIBLE);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.visible);
		imageBtnClose.startAnimation(anim);
		imageBtnClose.setVisibility(ImageView.VISIBLE);
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// Возвращение на страницу активации кода
		if(v.equals(btnToApp) || v.equals(imageBtnClose)) {
			Animation anim = AnimationUtils.loadAnimation(this, R.anim.invisible);
			flipper.startAnimation(anim);
			flipper.setVisibility(ViewFlipper.INVISIBLE);
			imageBtnClose.setVisibility(ImageView.INVISIBLE);
			btnToApp.setVisibility(ImageView.INVISIBLE);
			image.setVisibility(ImageView.INVISIBLE);
			flipper.setEnabled(false);
			image.setEnabled(false);
			imageBtnClose.setEnabled(false);
			btnToApp.setEnabled(false);
			FunctionWithoutName();
			finish();
		}// if(v.equals(btnToApp) && v.equals(imageBtnClose))
	}
	
	private void FunctionWithoutName(){
		Intent intent;
		SharedPref sp = new SharedPref(getApplicationContext(), getString(R.string.APP_SET));
		if(sp.IsRegistered()) {
			intent = new Intent(ActivityHello.this, ActivityRegMail.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) FunctionWithoutName();
		return true;
	}
}