package com.wspa.app.notification.memory;

import com.google.analytics.tracking.android.EasyTracker;
import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.AdapterKeyListener;
import com.wspa.app.notification.content.ContentManager;
import com.wspa.app.notification.content.DBAdapter;
import com.wspa.app.notification.content.DBAdapterPublic;
import com.wspa.app.notification.models.MemoryModel;
import com.wspa.app.notification.content.NetworkManager;
import com.wspa.app.notification.adapters.TypeSet;
import com.wspa.app.notification.content.NetworkQueue.NetworkQueueListener;
import com.wspa.app.notification.extras.ControlActivity;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.menu.BottomMenu;
import com.wspa.app.notification.menu.TopMenu;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;

public class ActivityGameMainMenu extends Activity implements OnClickListener {

	Button 		   btnGameAbout,			//  нопка "Speluitleg"
				   btnGameResults,			//  нопка "Highscore"
				   btnGameStart,			//  нопка "START SPEL"
				   btn_alpha,
				   InfoButton;
	Boolean 	   EnableStatus;			// »ндикатор доступности элементов интерфейса
	ImageView 	   spelIV;
	GameAbout 	   AboutGame;
	RelativeLayout aboutLay, mainLay;
	GameHighScore  HighScoreView;
	Spinner		   mySpinner;
	LinearLayout   ll_bot_menu,					// layout дл€ нижнего меню
	 			   ll_top_menu;					// layout дл€ верхнего меню
	BottomMenu 	   bm;							// нижнее меню
	TopMenu 	   tm;							// верхнее меню
	AdapterKeyListener AKeyListener;
	ControlActivity CActivity;
	private ContentManager tc;
	private MemoryImageGetter loader;
	TypeSet ts;
	private static boolean MemoryUpdated = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_main_menu);
		EasyTracker.getInstance().setContext(getApplicationContext());
		EasyTracker.getTracker().trackEvent(getString(R.string.A_A_LADEN),
				getString(R.string.A_L_MEMORY),
				getString(R.string.A_L_MEMORY),
				null);
		
		CActivity = new ControlActivity(DataConsts.ACT_GAME_MAIN_MENU, this);
		ll_bot_menu = (LinearLayout) findViewById(R.id.ll_game_main_menu_bot_menu);
		ll_top_menu = (LinearLayout) findViewById(R.id.ll_game_main_menu_top_menu);
		bm = new BottomMenu(this, ll_bot_menu, DataConsts.BOT_MENU_TYPE_MEMORY, CActivity);
		tm = new TopMenu(this, ll_top_menu, R.string.TITLE_MEMORY,
				DataConsts.TOP_MENU_BUTTON_INFO,
				DataConsts.TOP_MENU_NO_BUTTON_SHARING, 
				DataConsts.TOP_MENU_CAN_BACK, bm, CActivity, null);
		mainLay = (RelativeLayout)findViewById(R.id.mainLay);
		aboutLay = (RelativeLayout)findViewById(R.id.secLay);
		mySpinner = (Spinner)findViewById(R.id.difficultSpinner);
		
		TextView instelTV = (TextView)findViewById(R.id.instelGameSettingTV);
		//TextView moelilTV = (TextView)findViewById(R.id.moelilGameSettingTV);
		ts = new TypeSet(getApplicationContext());
		instelTV = ts.SetTF(instelTV, DataConsts.ARIAL_BOLD);
		
		//moelilTV = ts.SetTF(moelilTV, DataConsts.ARIAL_BOLD);
		
		btnGameAbout = (Button)findViewById(R.id.infoButton);
		btnGameAbout = ts.SetTF(btnGameAbout, DataConsts.ARIAL_BOLD);
		btnGameAbout.setOnClickListener(this);
		
		btnGameStart = (Button)findViewById(R.id.memorButton);
		btnGameStart = ts.SetTF(btnGameStart, DataConsts.ARIAL_BOLD);
		btnGameStart.setOnClickListener(this);
		
		
		btnGameResults = (Button)findViewById(R.id.highScoreButton);
		btnGameResults = ts.SetTF(btnGameResults, DataConsts.ARIAL_BOLD);
		btnGameResults.setOnClickListener(this);
		
		if (!MemoryUpdated){
			readMemory();
			MemoryUpdated = true;
		}
		btn_alpha = (Button) findViewById(R.id.btn_alpha_game_main_menu);
		btn_alpha.setOnClickListener(this);
		btn_alpha.setVisibility(Button.INVISIBLE);
		EnableStatus = true;
		AKeyListener = new AdapterKeyListener(bm, CActivity);
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		try {
			if (HighScoreView != null && HighScoreView.VisibilityStatus &&
					keyCode == KeyEvent.KEYCODE_BACK) {
				HighScoreView.CloseView();
			} else if (AboutGame != null && AboutGame.VisibilityStatus &&
					keyCode == KeyEvent.KEYCODE_BACK){
				AboutGame.CloseView();
			} else {
				AKeyListener.KeyDown(keyCode);
			}
		} catch (Exception exception) {
			AKeyListener.KeyDown(keyCode);
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	/*
		if (bm.present_visibility) {
			bm.ChangeVisibility();
			ChangeEnableStatus();
			return;
		}else{*/
			if (v.equals(btnGameAbout)) {
				ShowAboutGame();
			} else if (v.equals(btnGameResults)) {
				HighScoreView = new GameHighScore(getApplicationContext(), aboutLay, bm, CActivity);
			} else if (v.equals(btnGameStart)) {
				Intent intent = new Intent(ActivityGameMainMenu.this, ActivityGameProcess.class);
		        String strChooseDiff = mySpinner.getSelectedItem().toString();  
		        intent.putExtra("DIFFICULTY", strChooseDiff);
		        startActivity(intent);
			} else if (v.equals(btn_alpha)){
				bm.ChangeVisibility();
			}
		//}
	}// public void onClick(View v)
	
	public void ShowAboutGame(){
		AboutGame = new GameAbout(getApplicationContext(), aboutLay, bm, CActivity);
	}
	
	public void ChangeEnableStatus(){
		EnableStatus = !EnableStatus;
		if(EnableStatus) {
			btn_alpha.setVisibility(Button.INVISIBLE);
		}else{
			btn_alpha.setVisibility(Button.VISIBLE);
		}
	}
	
	protected void readMemory() {
		MemoryModel[] memor = null;
		
		DBAdapter db = DBAdapterPublic.OpenDb(this);
		memor = db.getMemory();	
		DBAdapterPublic.CloseDb();
		//—сылки на изображени€ дл€ загрузки
		String[] imagesUrls;		
		if (memor != null) {
			imagesUrls = new String[memor.length];
			for (int i = 0; i < memor.length; i++)
				imagesUrls[i] = memor[i].getIcon();
			loader = new MemoryImageGetter(this);		
			loader.execute(imagesUrls);
		}				
	}
}
