package com.wspa.app.notification.notifications;

import com.google.analytics.tracking.android.EasyTracker;
import com.wspa.app.notification.R;
import com.wspa.app.notification.ServiceWSPA;
import com.wspa.app.notification.adapters.AdapterKeyListener;
import com.wspa.app.notification.adapters.AdapterMediaPlayer;
import com.wspa.app.notification.adapters.SharedPref;
import com.wspa.app.notification.adapters.TypeSet;
import com.wspa.app.notification.content.ContentManager;
import com.wspa.app.notification.content.NetworkManager;
import com.wspa.app.notification.content.NetworkQueue.NetworkQueueListener;
import com.wspa.app.notification.extras.ControlActivity;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.extras.SettingsConsts;
import com.wspa.app.notification.menu.BottomMenu;
import com.wspa.app.notification.menu.TopMenu;
import com.wspa.app.notification.models.NotifyModel;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.KeyCharacterMap.KeyData;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ActivityNotifications extends Activity implements
		OnCheckedChangeListener, OnClickListener {

	Context context;
	TypeSet ts;
	SharedPref sp;
	LinearLayout ll_bot_menu, // layout для нижнего меню
			ll_top_menu; // layout для верхнего меню
	BottomMenu bm; // нижнее меню
	TopMenu tm; // верхнее меню
	RelativeLayout rl_check_1, rl_check_2, rl_check_3, rl_check_4, rl_check_5,
			rl_check_6;
	TextView tw1, tw2, tw3, tw4, tw5, tw6, // название настроек
			twr1, twr2, twr3, twr4, twr5, twr6, twSounds;
	ToggleButton tg1, tg2, tg3, tg4, tg5, tg6; // переключател настроек
	Boolean in_load, EnableStatus;
	AdapterMediaPlayer aPlayer; // адаптер звуков
	ImageView rb1, rb2, rb3, rb4, rb5, rb6;
	Button btnAlpha;
	int selected_radio; // идентификатор выбранного
						// RadioBtn из R (ресурсов)
	ControlActivity CActivity;
	AdapterKeyListener AKeyListener;
	ContentManager cm;
	NotifyModel nm;
	Boolean PushState = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.notifications_screen);
		EasyTracker.getInstance().setContext(getApplicationContext());
		EasyTracker.getTracker().trackEvent(getString(R.string.A_A_LADEN),
				getString(R.string.A_L_NOTIFY),
				getString(R.string.A_L_NOTIFY),
				null);
		// Инициализация компонентов
		context = getApplicationContext();
		CActivity = new ControlActivity(DataConsts.ACT_NOTOFICATION, this);
		in_load = true;
		EnableStatus = true;
		ts = new TypeSet(context);
		sp = new SharedPref(context, getString(R.string.APP_SET));
		ll_bot_menu = (LinearLayout) findViewById(R.id.ll_notification_bot_menu);
		ll_top_menu = (LinearLayout) findViewById(R.id.ll_notification_top_menu);
		bm = new BottomMenu(this, ll_bot_menu,
				DataConsts.BOT_MENU_TYPE_NOTIFICATION, CActivity);
		tm = new TopMenu(this, ll_top_menu, R.string.TITLE_NOTIFICATIONS,
				DataConsts.TOP_MENU_NO_BUTTON_INFO,
				DataConsts.TOP_MENU_BUTTON_SHARING,
				DataConsts.TOP_MENU_CAN_BACK, bm, CActivity, null);
		bm.setOnClickListener(this);
		rl_check_1 = (RelativeLayout) findViewById(R.id.rl_check_lay1);
		rl_check_2 = (RelativeLayout) findViewById(R.id.rl_check_lay2);
		rl_check_3 = (RelativeLayout) findViewById(R.id.rl_check_lay3);
		rl_check_4 = (RelativeLayout) findViewById(R.id.rl_check_lay4);
		rl_check_5 = (RelativeLayout) findViewById(R.id.rl_check_lay5);
		rl_check_6 = (RelativeLayout) findViewById(R.id.rl_check_lay6);
		rl_check_1.setOnClickListener(this);
		rl_check_2.setOnClickListener(this);
		rl_check_3.setOnClickListener(this);
		rl_check_4.setOnClickListener(this);
		rl_check_5.setOnClickListener(this);
		rl_check_6.setOnClickListener(this);
		tw1 = (TextView) findViewById(R.id.twPush1);
		tw1 = ts.SetTF(tw1, DataConsts.ARIAL_BOLD);
		tw2 = (TextView) findViewById(R.id.twPush2);
		tw2 = ts.SetTF(tw2, DataConsts.ARIAL_REGULAR);
		tw3 = (TextView) findViewById(R.id.twPush3);
		tw3 = ts.SetTF(tw3, DataConsts.ARIAL_REGULAR);
		tw4 = (TextView) findViewById(R.id.twPush4);
		tw4 = ts.SetTF(tw4, DataConsts.ARIAL_REGULAR);
		tw5 = (TextView) findViewById(R.id.twPush5);
		tw5 = ts.SetTF(tw5, DataConsts.ARIAL_REGULAR);
		tw6 = (TextView) findViewById(R.id.twPush6);
		tw6 = ts.SetTF(tw6, DataConsts.ARIAL_REGULAR);
		twr1 = (TextView) findViewById(R.id.twRadio1);
		twr1.setText(R.string.SET_PUSH_STANDART);
		twr1 = ts.SetTF(twr1, DataConsts.ARIAL_REGULAR);
		twr2 = (TextView) findViewById(R.id.twRadio2);
		twr2.setText(R.string.SOUND_NAME_1);
		twr2 = ts.SetTF(twr2, DataConsts.ARIAL_REGULAR);
		twr3 = (TextView) findViewById(R.id.twRadio3);
		twr3.setText(R.string.SOUND_NAME_2);
		twr3 = ts.SetTF(twr3, DataConsts.ARIAL_REGULAR);
		twr4 = (TextView) findViewById(R.id.twRadio4);
		twr4.setText(R.string.SOUND_NAME_3);
		twr4 = ts.SetTF(twr4, DataConsts.ARIAL_REGULAR);
		twr5 = (TextView) findViewById(R.id.twRadio5);
		twr5.setText(R.string.SOUND_NAME_4);
		twr5 = ts.SetTF(twr5, DataConsts.ARIAL_REGULAR);
		twr6 = (TextView) findViewById(R.id.twRadio6);
		twr6.setText(R.string.SOUND_NAME_5);
		twr6 = ts.SetTF(twr6, DataConsts.ARIAL_REGULAR);
		twSounds = (TextView) findViewById(R.id.twSounds);
		twSounds = ts.SetTF(twSounds, DataConsts.ARIAL_BOLD);
		tg1 = (ToggleButton) findViewById(R.id.tbtnPush1);
		PushState = sp.ReadBoolValue(SettingsConsts.PUSH_STATUS);
		tg1.setChecked(PushState);
		tg1.setOnCheckedChangeListener(this);
		tg2 = (ToggleButton) findViewById(R.id.tbtnPush2);
		tg2.setChecked(sp.ReadBoolValue(SettingsConsts.PUSH_BEER));
		tg2.setOnCheckedChangeListener(this);
		tg3 = (ToggleButton) findViewById(R.id.tbtnPush3);
		tg3.setChecked(sp.ReadBoolValue(SettingsConsts.PUSH_HUISDIEREN));
		tg3.setOnCheckedChangeListener(this);
		tg4 = (ToggleButton) findViewById(R.id.tbtnPush4);
		tg4.setChecked(sp.ReadBoolValue(SettingsConsts.PUSH_KIPPEN));
		tg4.setOnCheckedChangeListener(this);
		tg5 = (ToggleButton) findViewById(R.id.tbtnPush5);
		tg5.setOnCheckedChangeListener(this);
		tg5.setChecked(sp.ReadBoolValue(SettingsConsts.PUSH_OLIFANT));
		tg6 = (ToggleButton) findViewById(R.id.tbtnPush6);
		tg6.setChecked(sp.ReadBoolValue(SettingsConsts.PUSH_VEE));
		tg6.setOnCheckedChangeListener(this);
		aPlayer = new AdapterMediaPlayer(context);
		in_load = false;
		rb1 = (ImageView) findViewById(R.id.radio1);
		rb1.setOnClickListener(this);
		rb2 = (ImageView) findViewById(R.id.radio2);
		rb2.setOnClickListener(this);
		rb3 = (ImageView) findViewById(R.id.radio3);
		rb3.setOnClickListener(this);
		rb4 = (ImageView) findViewById(R.id.radio4);
		rb4.setOnClickListener(this);
		rb5 = (ImageView) findViewById(R.id.radio5);
		rb5.setOnClickListener(this);
		rb6 = (ImageView) findViewById(R.id.radio6);
		rb6.setOnClickListener(this);
		btnAlpha = (Button) findViewById(R.id.btn_alpha_notification);
		btnAlpha.setOnClickListener(this);
		btnAlpha.setVisibility(Button.INVISIBLE);
		AKeyListener = new AdapterKeyListener(bm, CActivity);
		selected_radio = SetRadioFromSettings();
		if(!PushState) LockNewsCat();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		AKeyListener.KeyDown(keyCode);
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
		}
		return true;
	}

	public void ReadSetiingsNewsCat(){
		tg2.setEnabled(true);
		tg3.setEnabled(true);
		tg4.setEnabled(true);
		tg5.setEnabled(true);
		tg6.setEnabled(true);
		tg2.setChecked(sp.ReadBoolValue(SettingsConsts.PUSH_BEER));
		tg3.setChecked(sp.ReadBoolValue(SettingsConsts.PUSH_HUISDIEREN));
		tg4.setChecked(sp.ReadBoolValue(SettingsConsts.PUSH_KIPPEN));
		tg5.setChecked(sp.ReadBoolValue(SettingsConsts.PUSH_OLIFANT));
		tg6.setChecked(sp.ReadBoolValue(SettingsConsts.PUSH_VEE));
	}
	
	public void LockNewsCat(){
		tg2.setChecked(false);
		tg3.setChecked(false);
		tg4.setChecked(false);
		tg5.setChecked(false);
		tg6.setChecked(false);
		tg2.setEnabled(false);
		tg3.setEnabled(false);
		tg4.setEnabled(false);
		tg5.setEnabled(false);
		tg6.setEnabled(false);
	}
	
	public int SetRadioFromSettings() {
		rb1.setImageResource(R.drawable.radio_off);
		rb2.setImageResource(R.drawable.radio_off);
		rb3.setImageResource(R.drawable.radio_off);
		rb4.setImageResource(R.drawable.radio_off);
		rb5.setImageResource(R.drawable.radio_off);
		rb6.setImageResource(R.drawable.radio_off);
		switch (sp.ReadIntValue(SettingsConsts.PUSH_SOUND_TYPE)) {
		case DataConsts.PUSH_STANDART: {
			rb1.setImageResource(R.drawable.radio_on);
			return rb1.getId();
		}
		case DataConsts.PUSH_BEER: {
			rb2.setImageResource(R.drawable.radio_on);
			return rb2.getId();
		}
		case DataConsts.PUSH_HUISDIEREN: {
			rb3.setImageResource(R.drawable.radio_on);
			return rb3.getId();
		}
		case DataConsts.PUSH_KIPPEN: {
			rb4.setImageResource(R.drawable.radio_on);
			return rb4.getId();
		}
		case DataConsts.PUSH_OLIFANT: {
			rb5.setImageResource(R.drawable.radio_on);
			return rb5.getId();
		}
		case DataConsts.PUSH_VEE: {
			rb6.setImageResource(R.drawable.radio_on);
			return rb6.getId();
		}
		}
		return rb1.getId();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (buttonView.equals(tg1)) {
			sp.SetBoolValue(SettingsConsts.PUSH_STATUS, buttonView.isChecked());
			PushState = buttonView.isChecked();
			if (PushState){
				startService(new Intent(ActivityNotifications.this,
						ServiceWSPA.class));
				ReadSetiingsNewsCat();
			}
			else {
				stopService(new Intent(ActivityNotifications.this,
						ServiceWSPA.class));
				LockNewsCat();
			}
		} else if (PushState) {
			if (buttonView.equals(tg2)) {
				sp.SetBoolValue(SettingsConsts.PUSH_BEER,
						buttonView.isChecked());
			} else if (buttonView.equals(tg3)) {
				sp.SetBoolValue(SettingsConsts.PUSH_HUISDIEREN,
						buttonView.isChecked());
			} else if (buttonView.equals(tg4)) {
				sp.SetBoolValue(SettingsConsts.PUSH_KIPPEN,
						buttonView.isChecked());
			} else if (buttonView.equals(tg5)) {
				sp.SetBoolValue(SettingsConsts.PUSH_OLIFANT,
						buttonView.isChecked());
			} else if (buttonView.equals(tg6)) {
				sp.SetBoolValue(SettingsConsts.PUSH_VEE, buttonView.isChecked());
			}
		}
		UpdateNot();
	}
	
	public void ChangeEnableStatus() {
		EnableStatus = !EnableStatus;
		if (EnableStatus) {
			btnAlpha.setVisibility(Button.INVISIBLE);
		} else {
			btnAlpha.setVisibility(Button.VISIBLE);
		}
	}

	private void UpdateNot() {
		cm = new ContentManager(this);
		cm.updateNotify(new NetworkQueueListener() {
			@Override
			public void onNext(NetworkManager manager) {
			}

			@Override
			public void onFinish() {
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		/*
		 * if(bm.present_visibility) { bm.Invisible(); return; }
		 */
		if (v.equals(btnAlpha)) {
			bm.ChangeVisibility();
			return;
		} else {
			switch (v.getId()) {
			case R.id.radio1: {
				ImageView rb = (ImageView) v;
				rb = (ImageView) findViewById(selected_radio);
				rb.setImageResource(R.drawable.radio_off);
				selected_radio = R.id.radio1;
				aPlayer.PlaySound(0);
				sp.SetIntValue(SettingsConsts.PUSH_SOUND_TYPE,
						DataConsts.PUSH_STANDART);
				rb1.setImageResource(R.drawable.radio_on);
				break;
			}
			case R.id.radio2: {
				ImageView rb = (ImageView) v;
				rb = (ImageView) findViewById(selected_radio);
				rb.setImageResource(R.drawable.radio_off);
				selected_radio = R.id.radio2;
				aPlayer.PlaySound(1);
				sp.SetIntValue(SettingsConsts.PUSH_SOUND_TYPE,
						DataConsts.PUSH_BEER);
				rb2.setImageResource(R.drawable.radio_on);
				break;
			}
			case R.id.radio3: {
				ImageView rb = (ImageView) v;
				rb = (ImageView) findViewById(selected_radio);
				rb.setImageResource(R.drawable.radio_off);
				selected_radio = R.id.radio3;
				aPlayer.PlaySound(2);
				sp.SetIntValue(SettingsConsts.PUSH_SOUND_TYPE,
						DataConsts.PUSH_HUISDIEREN);
				rb3.setImageResource(R.drawable.radio_on);
				break;
			}
			case R.id.radio4: {
				ImageView rb = (ImageView) v;
				rb = (ImageView) findViewById(selected_radio);
				rb.setImageResource(R.drawable.radio_off);
				selected_radio = R.id.radio4;
				aPlayer.PlaySound(3);
				sp.SetIntValue(SettingsConsts.PUSH_SOUND_TYPE,
						DataConsts.PUSH_KIPPEN);
				rb4.setImageResource(R.drawable.radio_on);
				break;
			}
			case R.id.radio5: {
				ImageView rb = (ImageView) v;
				rb = (ImageView) findViewById(selected_radio);
				rb.setImageResource(R.drawable.radio_off);
				selected_radio = R.id.radio5;
				aPlayer.PlaySound(4);
				sp.SetIntValue(SettingsConsts.PUSH_SOUND_TYPE,
						DataConsts.PUSH_OLIFANT);
				rb5.setImageResource(R.drawable.radio_on);
				break;
			}
			case R.id.radio6: {
				ImageView rb = (ImageView) v;
				rb = (ImageView) findViewById(selected_radio);
				rb.setImageResource(R.drawable.radio_off);
				selected_radio = R.id.radio6;
				aPlayer.PlaySound(5);
				sp.SetIntValue(SettingsConsts.PUSH_SOUND_TYPE,
						DataConsts.PUSH_VEE);
				rb6.setImageResource(R.drawable.radio_on);
				break;
			}
			case R.id.rl_check_lay1: {
				ImageView rb = (ImageView)findViewById(R.id.radio1);
				rb = (ImageView) findViewById(selected_radio);
				rb.setImageResource(R.drawable.radio_off);
				selected_radio = R.id.radio1;
				aPlayer.PlaySound(0);
				sp.SetIntValue(SettingsConsts.PUSH_SOUND_TYPE,
						DataConsts.PUSH_STANDART);
				rb1.setImageResource(R.drawable.radio_on);
				break;
			}
			case R.id.rl_check_lay2: {
				ImageView rb = (ImageView)findViewById(R.id.radio2);
				rb = (ImageView) findViewById(selected_radio);
				rb.setImageResource(R.drawable.radio_off);
				selected_radio = R.id.radio2;
				aPlayer.PlaySound(1);
				sp.SetIntValue(SettingsConsts.PUSH_SOUND_TYPE,
						DataConsts.PUSH_BEER);
				rb2.setImageResource(R.drawable.radio_on);
				break;
			}
			case R.id.rl_check_lay3: {
				ImageView rb = (ImageView)findViewById(R.id.radio3);
				rb = (ImageView) findViewById(selected_radio);
				rb.setImageResource(R.drawable.radio_off);
				selected_radio = R.id.radio3;
				aPlayer.PlaySound(2);
				sp.SetIntValue(SettingsConsts.PUSH_SOUND_TYPE,
						DataConsts.PUSH_HUISDIEREN);
				rb3.setImageResource(R.drawable.radio_on);
				break;
			}
			case R.id.rl_check_lay4: {
				ImageView rb = (ImageView)findViewById(R.id.radio4);
				rb = (ImageView) findViewById(selected_radio);
				rb.setImageResource(R.drawable.radio_off);
				selected_radio = R.id.radio4;
				aPlayer.PlaySound(3);
				sp.SetIntValue(SettingsConsts.PUSH_SOUND_TYPE,
						DataConsts.PUSH_KIPPEN);
				rb4.setImageResource(R.drawable.radio_on);
				break;
			}
			case R.id.rl_check_lay5: {
				ImageView rb = (ImageView)findViewById(R.id.radio5);
				rb = (ImageView) findViewById(selected_radio);
				rb.setImageResource(R.drawable.radio_off);
				selected_radio = R.id.radio5;
				aPlayer.PlaySound(4);
				sp.SetIntValue(SettingsConsts.PUSH_SOUND_TYPE,
						DataConsts.PUSH_OLIFANT);
				rb5.setImageResource(R.drawable.radio_on);
				break;
			}
			case R.id.rl_check_lay6: {
				ImageView rb = (ImageView)findViewById(R.id.radio6);
				rb = (ImageView) findViewById(selected_radio);
				rb.setImageResource(R.drawable.radio_off);
				selected_radio = R.id.radio6;
				aPlayer.PlaySound(5);
				sp.SetIntValue(SettingsConsts.PUSH_SOUND_TYPE,
						DataConsts.PUSH_VEE);
				rb6.setImageResource(R.drawable.radio_on);
				break;
			}
			default:
				break;
			}
			UpdateNot();
		}// if else
	}
}