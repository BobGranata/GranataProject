package com.wspa.app.notification.menu;

import com.wspa.app.notification.notifications.ActivityNotifications;
import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.TypeSet;
import com.wspa.app.notification.extras.ControlActivity;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.info.ActivityWspaInfo;
import com.wspa.app.notification.memory.ActivityGameMainMenu;
import com.wspa.app.notification.news.ActivityNews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

@SuppressLint("ViewConstructor")
public class BottomMenu extends View implements OnClickListener {

	ImageView btnNews, 				// Кнопка "News"
			  btnMemory, 			// Кнопка "Memory"
			  btnWspInfo, 			// Кнопка "WSPA Info"
			  btnNotification, 		// Кнопка "Notification"
			  background;			// фон меню
	int 	  present_active_type;  // Индикатор - текущая активная кнопка
	public boolean   
			  present_visibility;	// Индикатор состояния видимости меню
	public boolean IsEnable;
	Context   context;
	View 	  menu;
	TypeSet   ts;
	ControlActivity CActivity;


	public BottomMenu(Context con, LinearLayout ll, int bot_menu_type, ControlActivity CA) {
		super(con);
		CActivity = CA;
		context = con;
		present_active_type = bot_menu_type;
		IsEnable = true;
		menu = View.inflate(context, R.layout.bottom_menu, ll);
		btnNews = (ImageView) menu.findViewById(R.id.btn_news);
		btnMemory = (ImageView) menu.findViewById(R.id.btn_memory);
		btnWspInfo = (ImageView) menu.findViewById(R.id.btn_wspa_info);
		btnNotification = (ImageView) menu.findViewById(R.id.btn_notification);
		background = (ImageView) menu.findViewById(R.id.bot_menu_back);
		btnNews.setOnClickListener(this);
		btnMemory.setOnClickListener(this);
		btnWspInfo.setOnClickListener(this);
		btnNotification.setOnClickListener(this);
		Invisible();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ChangeVisibility();
		Intent intent = null;
		if (v.equals(btnNews) && present_active_type != DataConsts.BOT_MENU_TYPE_NEWS) {
			intent = new Intent(context.getApplicationContext(), ActivityNews.class);
		}
		if (v.equals(btnMemory) && present_active_type != DataConsts.BOT_MENU_TYPE_MEMORY) {
			intent = new Intent(context.getApplicationContext(), ActivityGameMainMenu.class);
		}
		if (v.equals(btnWspInfo) && present_active_type != DataConsts.BOT_MENU_TYPE_WSPA_INFO) {
			intent = new Intent(context.getApplicationContext(), ActivityWspaInfo.class);
		}
		if (v.equals(btnNotification) && present_active_type != DataConsts.BOT_MENU_TYPE_NOTIFICATION) {
			intent = new Intent(context.getApplicationContext(), ActivityNotifications.class);
		}
		if (intent != null) {
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent);
			CActivity.GetActivity().finish();
		}
	}// public void onClick(View v)

	public void ChangeVisibility() {
			if(!IsEnable) return;
			if (present_visibility) {
				Invisible();
			} else {
				Visible();
			}
			ChangeEnableParrentActivity();
	}// public void ChangeVisibility()
	
	public void Invisible() {
		background.setVisibility(INVISIBLE);
		btnNews.setVisibility(INVISIBLE);
		btnMemory.setVisibility(INVISIBLE);
		btnWspInfo.setVisibility(INVISIBLE);
		btnNotification.setVisibility(INVISIBLE);
		btnNews.setEnabled(false);
		btnMemory.setEnabled(false);
		btnWspInfo.setEnabled(false);
		btnNotification.setEnabled(false);
		present_visibility = false;
	}
	
	public void Visible() {
		background.setVisibility(VISIBLE);
		btnNews.setVisibility(VISIBLE);
		btnMemory.setVisibility(VISIBLE);
		btnWspInfo.setVisibility(VISIBLE);
		btnNotification.setVisibility(VISIBLE);
		btnNews.setEnabled(true);
		btnMemory.setEnabled(true);
		btnWspInfo.setEnabled(true);
		btnNotification.setEnabled(true);
		present_visibility = true;
	}
	
	public void ChangeEnableParrentActivity(){
		CActivity.ChangeEnableActivity();
	}
	
}// public class BottomMenu extends View implements OnClickListener
