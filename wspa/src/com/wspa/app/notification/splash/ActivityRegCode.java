package com.wspa.app.notification.splash;

import com.google.analytics.tracking.android.EasyTracker;
import com.wspa.app.notification.R;
import com.wspa.app.notification.models.AuthentModel;
import com.wspa.app.notification.content.ContentManager;
import com.wspa.app.notification.content.NetworkManager;
import com.wspa.app.notification.adapters.SharedPref;
import com.wspa.app.notification.adapters.TypeSet;
import com.wspa.app.notification.content.NetworkQueue.NetworkQueueListener;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.extras.SettingsConsts;
import com.wspa.app.notification.news.ActivityNews;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class ActivityRegCode extends Activity implements OnClickListener {

	Button 	 btnActiveren;		// Кнопка "Активировать"
	TextView text1, 			// Текст 1
			 text2, 			// Текст 2
			 twHyperlink, 		// Гиперссылка для запуска превью
			 twError, 			// Сообщение о неверно введенном коде
			 title1, 			// Заголовок 1
			 title2;			// Заголовок 2
	EditText et;				// Поле вводка кода
	TypeSet  ts;				// Установка шрифтов
	Intent   intent;
	ContentManager tc;
	AuthentModel Auth;
	SharedPref sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registration_content_code);
		// Инициализация компонентов
		ts = new TypeSet(getApplicationContext());
		btnActiveren = (Button) findViewById(R.id.btnActiveren);
		btnActiveren.setOnClickListener(this);
		title1 = (TextView)findViewById(R.id.twRegCodeTitle1);
		title1 = ts.SetTF(title1, DataConsts.ARIAL_BOLD);
		title2 = (TextView)findViewById(R.id.twRegCodeTitle2);
		title2 = ts.SetTF(title2, DataConsts.ARIAL_BOLD);
		text1 = (TextView) findViewById(R.id.twRegCodeText1);
		text1.setMovementMethod(LinkMovementMethod.getInstance());
		text1 = ts.SetTF(text1, DataConsts.ARIAL_REGULAR);
		text1.setText(Html.fromHtml(getString(R.string.TEXT_REG_CODE_1)));
		text2 = (TextView) findViewById(R.id.twRegCodeText2);
		text2.setMovementMethod(LinkMovementMethod.getInstance());
		text2 = ts.SetTF(text2, DataConsts.ARIAL_REGULAR);
		text2.setText(Html.fromHtml(getString(R.string.TEXT_REG_CODE_2)));
		text2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EasyTracker.getInstance().setContext(getApplicationContext());
				EasyTracker.getTracker().trackEvent(getString(R.string.A_A_CLICK),
						getString(R.string.A_L_DONATE),
						getString(R.string.A_L_DONATE),
						null);
			}
		});
		twHyperlink = (TextView) findViewById(R.id.twRegCodeHyperlink);
		twHyperlink = ts.SetTF(twHyperlink, DataConsts.ARIAL_REGULAR);
		twHyperlink.setText(Html.fromHtml(getString(R.string.TEXT_REG_CODE_1_PRIVIEW)));
		twHyperlink.setOnClickListener(this);
		twError = (TextView) findViewById(R.id.twErrorCode);
		twError.setVisibility(TextView.INVISIBLE);
		twError = ts.SetTF(twError, DataConsts.ARIAL_ITALIC);
		et = (EditText) findViewById(R.id.etCode);
		et = ts.SetTF(et, DataConsts.ARIAL_REGULAR);
		et.setOnClickListener(this);
		et.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
	            if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
	                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	                in.hideSoftInputFromWindow(et.getWindowToken(), 0);
	               return true;
	            }
				return false;
			}
	    });
	}

	@Override
	public void onClick(View v) {
		// Проверка кода, переход к подписке на новости
		if (v.equals(btnActiveren)) {			
			tc = new ContentManager(this);
			Toast.makeText(this, getString(R.string.TOAST_CODE_CHECKING), Toast.LENGTH_SHORT).show();
			tc.updateAuthenticatie(et.getText().toString(),
					new NetworkQueueListener() {
						@Override
						public void onNext(NetworkManager manager) { }
						@Override
						public void onFinish() {
							CheckCode();
						}// public void onFinish()
					});// tc.updateAuthenticatie(new NetworkQueueListener()*/
			// Запуск preview приложения
		} else if (v.equals(twHyperlink)) {
			intent = new Intent(getApplicationContext(), ActivityHello.class);
			startActivity(intent);
			// Обратно поменять цвет текста, после неправильного ввода кода
		}else if(v.equals(et)){
			et.setTextColor(Color.BLACK);
		}
	}
	
	private Boolean CheckCode(){
		Auth = tc.getAuth();
		if(Auth != null){
			// Если код верный
			if(Auth.getAuth_outcome().equals(DataConsts.SUCCESS)){
				// Сохраняем код в файле настроек
				SharedPref sp = new SharedPref(getApplicationContext(), getString(R.string.APP_SET));
				sp.SetBoolValue(SettingsConsts.IS_REGISTERED, true);
				sp.SetStringValue(SettingsConsts.PASSWORD, et.getText().toString());
				UpdateNot();
			} else {
				// Если количество мобильных устройств "на коде" = 4
				if(Auth.getTries() == 0){
					intent = new Intent(this, ActivityRegCodeNew.class);
					//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				// Если ошибка (Заголовок ошибки не пустой)
				} else if (!Auth.getCaption().equals("")){
					twError.setText(Auth.getCaption());
					twError.setVisibility(TextView.VISIBLE);
					et.setTextColor(Color.RED);
				}
			}
		}// if(Auth != null)
		return false;
	}
	
	private void UpdateNot(){
		ContentManager cm = new ContentManager(this);
		cm.updateNotify(new NetworkQueueListener() {
			@Override
			public void onNext(NetworkManager manager) {
			}
			@Override
			public void onFinish() {
				intent = new Intent(ActivityRegCode.this, ActivityHello.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
	}
	
}