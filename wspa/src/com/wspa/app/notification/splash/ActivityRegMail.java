package com.wspa.app.notification.splash;

import com.google.analytics.tracking.android.EasyTracker;
import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.TypeSet;
import com.wspa.app.notification.content.ContentManager;
import com.wspa.app.notification.content.NetworkManager;
import com.wspa.app.notification.content.NetworkQueue.NetworkQueueListener;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.news.ActivityNews;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class ActivityRegMail extends Activity implements OnClickListener {

	Button btnInschrijven; // Кнопка "подписаться"
	ImageView btnClose; // Кнопка "закрыть"
	TextView text1, // Текст 1
			text2, // Текст 2
			title1, // Заголовок 1
			title2; // Заголовок 2
	EditText et; // Поле ввода почтового ящика
	TypeSet ts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registration_content_mail);
		EasyTracker.getInstance().setContext(getApplicationContext());
		// Инициализация компонентов
		ts = new TypeSet(getApplicationContext());
		btnInschrijven = (Button) findViewById(R.id.btnInschrijven);
		btnInschrijven.setOnClickListener(this);
		text1 = (TextView) findViewById(R.id.twRegMailText1);
		text1.setText(Html.fromHtml(getString(R.string.TEXT_REG_MAIL_1)));
		text1 = ts.SetTF(text1, DataConsts.ARIAL_REGULAR);
		text2 = (TextView) findViewById(R.id.twRegMailText2);
		text2.setText(Html.fromHtml(getString(R.string.TEXT_REG_MAIL_2)));
		text2 = ts.SetTF(text2, DataConsts.ARIAL_REGULAR);
		text2.setOnClickListener(this);
		title1 = (TextView) findViewById(R.id.twRegMailTitle1);
		title1 = ts.SetTF(title1, DataConsts.ARIAL_BOLD);
		title2 = (TextView) findViewById(R.id.twRegMailTitle2);
		title2 = ts.SetTF(title2, DataConsts.ARIAL_BOLD);
		et = (EditText) findViewById(R.id.etMail);
		et.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (event != null
						&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					in.hideSoftInputFromWindow(et.getWindowToken(), 0);
					return true;
				}
				return false;
			}
		});
		btnClose = (ImageView) findViewById(R.id.btn_close_mail);
		btnClose.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// Подписаться на новости
		if (v.equals(btnInschrijven)) {
			ContentManager tc = new ContentManager(this);
			Toast.makeText(this, getString(R.string.TOAST_MAIL_CONG),
					Toast.LENGTH_SHORT).show();
			if (!(et.getText().toString().equals(""))) {
				tc.updateAbonnement(et.getText().toString(),
						new NetworkQueueListener() {
							@Override
							public void onNext(NetworkManager manager) {
							}

							@Override
							public void onFinish() {
								EasyTracker.getTracker().trackEvent(
										getString(R.string.A_A_CLICK),
										getString(R.string.A_MAIL_SUB),
										getString(R.string.A_MAIL_SUB), null);
								AbonentUpdated(true);
							}
						});
			} else
				AbonentUpdated(false);
			// Отказ от подписки на новости
		} else if (v.equals(text2) || v.equals(btnClose)) {
			EasyTracker.getTracker().trackEvent(getString(R.string.A_A_CLICK),
					getString(R.string.A_MAIL_NOSUB),
					getString(R.string.A_MAIL_NOSUB), null);
			Intent intent = new Intent(getApplicationContext(),
					ActivityNews.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	}

	private void AbonentUpdated(Boolean show_toast) {
		if(show_toast) Toast.makeText(this, getApplicationContext().getString(R.string.TOAST_MAIL_CONG), Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(getApplicationContext(), ActivityNews.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
		startActivity(intent);
	}
}