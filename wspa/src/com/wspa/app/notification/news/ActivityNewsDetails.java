package com.wspa.app.notification.news;

import com.google.analytics.tracking.android.EasyTracker;
import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.AdapterKeyListener;
import com.wspa.app.notification.content.DBAdapter;
import com.wspa.app.notification.content.DBAdapterPublic;
import com.wspa.app.notification.content.ImageGetter;
import com.wspa.app.notification.adapters.TypeSet;
import com.wspa.app.notification.extras.ControlActivity;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.extras.ParseData;
import com.wspa.app.notification.menu.BottomMenu;
import com.wspa.app.notification.menu.TopMenu;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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

public class ActivityNewsDetails extends Activity implements OnClickListener {

	 LinearLayout ll_bot_menu,					// layout для нижнего меню
	 			  ll_top_menu;					// layout для верхнего меню
	 BottomMenu   bm;							// нижнее меню
	 TopMenu 	  tm;							// верхнее меню
	 AdapterKeyListener AKeyListener;
	 ControlActivity CActivity;
	 Boolean	  EnableStatus;
	 Button 	  btnAlpha;
	 TypeSet	  ts;
	 NewsModel    news;
	 
	 ImageView    mImageYouTubeIcon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_testnews);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_details);
		EnableStatus = true;
		CActivity = new ControlActivity(DataConsts.ACT_NEWS_DETAILS, this);
		ts = new TypeSet(getApplicationContext());
		ll_bot_menu = (LinearLayout) findViewById(R.id.ll_news_details_bot_menu);
		ll_top_menu = (LinearLayout) findViewById(R.id.ll_news_details_top_menu);
		btnAlpha = (Button) findViewById(R.id.btn_alpha_news_details);
		btnAlpha.setVisibility(Button.INVISIBLE);
		btnAlpha.setOnClickListener(this);

		TextView titleDetails = (TextView)findViewById(R.id.detailTitleTV);
		titleDetails = ts.SetTF(titleDetails, DataConsts.ARIAL_BOLD);
		TextView dateDetails = (TextView)findViewById(R.id.detailsDateTV);
		ImageView imageDetails = (ImageView)findViewById(R.id.detailsImageView);
		ImageView imageDetailsBg = (ImageView)findViewById(R.id.newsDetailsBgIV);		
		TextView mainTextDetails = (TextView)findViewById(R.id.detailTextTV);
		TextView donateDetails = (TextView)findViewById(R.id.detailDonateTV);
		donateDetails.setLinksClickable(true);
		donateDetails.setClickable(true);
		donateDetails.setMovementMethod(LinkMovementMethod.getInstance());
		donateDetails.setText(Html.fromHtml(getString(R.string.news_details_donate_text)));
		donateDetails.setOnClickListener(new OnClickListener() {
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
		
	    Intent intent = getIntent();
	    int id = intent.getIntExtra("NEWSID",0);
	    
		DBAdapter db = DBAdapterPublic.OpenDb(this);
		news = db.getNewsById(id);		
		DBAdapterPublic.CloseDb();
		
		bm = new BottomMenu(this, ll_bot_menu, DataConsts.BOT_MENU_TYPE_OTHER, CActivity);
		tm = new TopMenu(this, ll_top_menu, R.string.TITLE_NEWS,
				DataConsts.TOP_MENU_NO_BUTTON_INFO,
				DataConsts.TOP_MENU_BUTTON_SHARING, 
				DataConsts.TOP_MENU_CAN_BACK, bm, CActivity, news);
	    
	    titleDetails.setText(news.getLongCaption());
	    
	    news.getDate();
	    dateDetails.setText(ParseData.getDateNews(news.getDate()));	    
	    mainTextDetails.setText(Html.fromHtml(news.getLongText()));
	    
	    TypeSet ts = new TypeSet(this);	
	    mainTextDetails = ts.SetTF(mainTextDetails, DataConsts.ARIAL_REGULAR);
	    dateDetails = ts.SetTF(dateDetails, DataConsts.ARIAL_REGULAR);
	    donateDetails = ts.SetTF(donateDetails, DataConsts.ARIAL_REGULAR);
	    
	    mImageYouTubeIcon = null;
	    if (news.getUrl_video().length() > 0)
	    	mImageYouTubeIcon = (ImageView)findViewById(R.id.youTubeIV);
	    
	    new ImageGetter(this, imageDetails, imageDetailsBg, mImageYouTubeIcon).execute(new String[]{news.getImage()});
	    AKeyListener = new AdapterKeyListener(bm, CActivity);	   
	    
	    imageDetails.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (news.getUrl_video().length() > 0) {					
					Uri address = Uri.parse(news.getUrl_video());
					Intent openlink = new Intent(Intent.ACTION_VIEW, address);
					startActivity(openlink);
				}
			}
		});
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
