package com.wspa.app.notification.splash;

import com.google.analytics.tracking.android.EasyTracker;
import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.TypeSet;
import com.wspa.app.notification.extras.DataConsts;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityRegCodeNew extends Activity implements OnClickListener {

	ImageView btnClose;		// Кнопка "Закрыть" (приложение)
	TextView  title1,		// Заголовок 1
			  title2,		// Заголовок 2
			  text1,		// Текст 1
			  text2,		// Текст 2
			  twNewCode;	// Гиперссылка, открывающаю почтовую программу
	TypeSet   ts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registration_content_code_error);
		// Инициалзация компонентов
		ts = new TypeSet(getApplicationContext());
		btnClose = (ImageView) findViewById(R.id.btn_close_new_code);
		btnClose.setOnClickListener(this);
		title1 = (TextView) findViewById(R.id.twRegCodeNewTitle1);
		title1 = ts.SetTF(title1, DataConsts.ARIAL_BOLD);
		title2 = (TextView) findViewById(R.id.twRegCodeNewTitle2);
		title2 = ts.SetTF(title2, DataConsts.ARIAL_BOLD);
		text1 = (TextView) findViewById(R.id.twRegCodeNewText1);
		text1 = ts.SetTF(text1, DataConsts.ARIAL_REGULAR);
		text1.setMovementMethod(LinkMovementMethod.getInstance());
		text1.setText(Html.fromHtml(getString(R.string.TEXT_REG_CODE_NEW_1)));
		text2 = (TextView) findViewById(R.id.twRegCodeNewText2);
		text2.setText(Html.fromHtml(getString(R.string.TEXT_REG_CODE_NEW_2)));
		text2 = ts.SetTF(text2, DataConsts.ARIAL_REGULAR);
		text2.setMovementMethod(LinkMovementMethod.getInstance());
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
		twNewCode = (TextView) findViewById(R.id.twHyperLinkNewCode);
		twNewCode = ts.SetTF(twNewCode, DataConsts.ARIAL_BOLD);
		twNewCode.setMovementMethod(LinkMovementMethod.getInstance());
		twNewCode.setText(Html.fromHtml(getString(R.string.TEXT_NIEUWE_CODE_AANVRAGEN)));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			// Закрыть приложение
		if(v.equals(btnClose)){
			finish();
		}
	}
}