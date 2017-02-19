package com.wspa.app.notification.adapters;

import android.view.KeyEvent;

import com.wspa.app.notification.extras.ControlActivity;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.menu.BottomMenu;
import com.wspa.app.notification.news.ActivityNews;

import android.app.Activity;
import android.content.Intent;

public class AdapterKeyListener {

	BottomMenu bm;				// Нижнее меню приложения
	ControlActivity CActivity;
	
	public AdapterKeyListener(BottomMenu bot_menu, ControlActivity ca){
		bm = bot_menu;
		CActivity = ca;
	}
	
	public void KeyDown(int keyCode){
		switch (keyCode) {
			// Кнопка "Меню"
		case KeyEvent.KEYCODE_MENU: {
				// Показать-скрыть нижнее меню
			bm.ChangeVisibility();
			break;
		}
			// Кнопка "назад" 
		case KeyEvent.KEYCODE_BACK:{
			if (bm.present_visibility) {
					// Скрыть нижнее меню, если отображается
				bm.ChangeVisibility();
			} else {
				/*
				switch(CActivity.GetShareType()){
				case DataConsts.ACT_NEWS: {
					CActivity.GetActivity().finish();
					break;
				}
				case DataConsts.ACT_NEWS_DETAILS: {
					CActivity.GetActivity().finish();
					break;
				}
				case DataConsts.ACT_GAME_MAIN_MENU: {
					Intent intent = new Intent(CActivity.GetActivity(), ActivityNews.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					CActivity.GetActivity().startActivity(intent);
					CActivity.GetActivity().finish();
					break;
				}
				case DataConsts.ACT_GAME_PROCESS: {
					CActivity.GetActivity().finish();
					break;
				}
				case DataConsts.ACT_GAME_RESULT: {
					CActivity.GetActivity().finish();
					break;
				}
				case DataConsts.ACT_WSPA_INFO: {
					Intent intent = new Intent(CActivity.GetActivity(), ActivityNews.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					CActivity.GetActivity().startActivity(intent);
					CActivity.GetActivity().finish();
					break;
				}
				case DataConsts.ACT_NOTOFICATION: {
					Intent intent = new Intent(CActivity.GetActivity(), ActivityNews.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					CActivity.GetActivity().startActivity(intent);
					CActivity.GetActivity().finish();
					break;
				}
				}*/
				CActivity.GoBack();
			}
		}
		}// switch (keyCode)
	}// public void KeyDown(int keyCode)
	
}
