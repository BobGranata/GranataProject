package com.wspa.app.notification.info;

import java.util.ArrayList;

import com.google.analytics.tracking.android.EasyTracker;
import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.AdapterKeyListener;
import com.wspa.app.notification.content.ContentManager;
import com.wspa.app.notification.content.DBAdapter;
import com.wspa.app.notification.content.DBAdapterPublic;
import com.wspa.app.notification.content.ImageGetter;
import com.wspa.app.notification.content.NetworkManager;
import com.wspa.app.notification.adapters.TypeSet;
import com.wspa.app.notification.content.NetworkQueue.NetworkQueueListener;
import com.wspa.app.notification.extras.ControlActivity;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.menu.BottomMenu;
import com.wspa.app.notification.menu.TopMenu;
import com.wspa.app.notification.news.NewsModel;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityWspaInfo extends Activity implements OnClickListener{

	Context 	 	   context;
	Button			   btnAlpha;
	LinearLayout 	   ll_bot_menu,		// layout для нижнего меню приложения
				 	   ll_top_menu;		// layout для верхнего меню приложения
	BottomMenu 		   bm;				// Нижнее меню
	TopMenu 		   tm;				// Верхнее меню
	TextView		   text1, text2,	// Тексты "About"
					   twDonate;
	ImageView		   ivAbout;			// Изображение "About"
	Boolean			   EnableStatus;
	AdapterKeyListener AKeyListener;
	TypeSet 		   ts;
	ControlActivity	   CActivity;
	ContentManager tc;
	ArrayList<NewsModel> NewsContentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wspa_info_screen);
		EnableStatus = true;
		CActivity = new ControlActivity(DataConsts.ACT_WSPA_INFO, this);
		context = getApplicationContext();
		ts = new TypeSet(context);
		ll_bot_menu = (LinearLayout) findViewById(R.id.ll_wspa_info_bot_menu);
		bm = new BottomMenu(this, ll_bot_menu, DataConsts.BOT_MENU_TYPE_WSPA_INFO, CActivity);
		ll_top_menu = (LinearLayout) findViewById(R.id.ll_wspa_info_top_menu);
		tm = new TopMenu(this, ll_top_menu, R.string.TITLE_WSPA_INFO,
				DataConsts.TOP_MENU_NO_BUTTON_INFO,
				DataConsts.TOP_MENU_BUTTON_SHARING, 
				DataConsts.TOP_MENU_CAN_BACK, bm, CActivity, null);
		AKeyListener = new AdapterKeyListener(bm, CActivity);
		text1 = (TextView) findViewById(R.id.twWspaInfoText1);
		text1 = ts.SetTF(text1, DataConsts.ARIAL_REGULAR);
		text2 = (TextView) findViewById(R.id.twWspaInfoText2);
		text2 = ts.SetTF(text2, DataConsts.ARIAL_REGULAR);
		ivAbout = (ImageView) findViewById(R.id.ivWspaInfoImage);
		btnAlpha  = (Button) findViewById(R.id.btn_alpha_wspa_info);
		btnAlpha.setOnClickListener(this);
		btnAlpha.setVisibility(Button.INVISIBLE);
		twDonate = (TextView) findViewById(R.id.detailDonateText);
		twDonate.setLinksClickable(true);
		twDonate.setMovementMethod(LinkMovementMethod.getInstance());
		twDonate.setText(Html.fromHtml(getString(R.string.news_details_donate_text)));
		twDonate.setOnClickListener(new OnClickListener() {
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
		ReadAbout();
	}
	
	private void ReadAbout(){
		AboutModel[] about = null;
		DBAdapter db = DBAdapterPublic.OpenDb(this);
		about = db.getAbout();
		DBAdapterPublic.CloseDb();
		text1.setText(Html.fromHtml(about[0].getContent1()));
		text2.setText(Html.fromHtml(about[0].getContent2()));
		new ImageGetter(this, ivAbout, ivAbout).execute(new String[]{about[0].getImage()});
		int t1 = ivAbout.getWidth();
		int t2 = ivAbout.getHeight();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		AKeyListener.KeyDown(keyCode);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.equals(btnAlpha)) bm.ChangeVisibility();
	}
	
	public void ChangeEnableStatus(){
		EnableStatus = !EnableStatus;
		if(EnableStatus) {
			btnAlpha.setVisibility(Button.INVISIBLE);
		}else{
			btnAlpha.setVisibility(Button.VISIBLE);
		}
	}
}