package com.wspa.app.notification.memory;

import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.AdapterKeyListener;
import com.wspa.app.notification.adapters.SharedPref;
import com.wspa.app.notification.extras.ControlActivity;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.extras.ParseData;
import com.wspa.app.notification.menu.BottomMenu;
import com.wspa.app.notification.menu.TopMenu;
import com.wspa.app.notification.memory.GameHighScore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityGameResult extends Activity implements OnClickListener{

	Button btnAlpha;

	TextView gameScoreView;	
	SharedPref sp;
	Button reGameBtn, highScoreTopBtn;	
	RelativeLayout GameResRLay, highScoreTopLay;
	LinearLayout   ll_bot_menu,					// layout для нижнего меню
	   			   ll_top_menu;					// layout для верхнего меню
	BottomMenu 	   bm;							// нижнее меню
	TopMenu 	   tm;							// верхнее меню
	float gameResult;
	String strChooseDiff;
	
	GameHighScore HighScoreView;
	AdapterKeyListener AKeyListener;
	ControlActivity CActivity;
	Boolean EnableStatus;						// Индикатор доступности элементов интерфейса
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_result);
		
		CActivity = new ControlActivity(DataConsts.ACT_GAME_RESULT, this);
		EnableStatus = true;
		ll_bot_menu = (LinearLayout) findViewById(R.id.ll_game_result_bot_menu);
		ll_top_menu = (LinearLayout) findViewById(R.id.ll_game_result_top_menu);
		bm = new BottomMenu(this, ll_bot_menu, DataConsts.BOT_MENU_TYPE_OTHER,CActivity);
		tm = new TopMenu(this, ll_top_menu, R.string.TITLE_MEMORY,
				DataConsts.TOP_MENU_NO_BUTTON_INFO,
				DataConsts.TOP_MENU_BUTTON_SHARING,
				DataConsts.TOP_MENU_CAN_BACK, bm, CActivity, null);
		sp = new SharedPref(getApplicationContext(), getString(R.string.APP_SET));
		
		gameScoreView = (TextView)findViewById(R.id.gameResTextView);
		reGameBtn = (Button)findViewById(R.id.btnReGame);
		highScoreTopBtn = (Button)findViewById(R.id.deelJeScoeBtn);
		GameResRLay = (RelativeLayout) findViewById(R.id.gameResultLay);
		highScoreTopLay = (RelativeLayout) findViewById(R.id.highScoreTopLay);
		
		highScoreTopBtn.setOnClickListener(this);
		reGameBtn.setOnClickListener(this);
		
	    Intent intent = getIntent();
	    gameResult = intent.getFloatExtra("SCORE", 0);
	    strChooseDiff = intent.getStringExtra("DIFFICULTY");
	    // Read high scores
	    //ArrayList<Float> mas = sp.GetGameResults();
	    // Save gameresult	    
	    sp.SaveResult(gameResult);	    	    
	    gameScoreView.setText(ParseData.InDotStringLine(gameResult));//String.valueOf(gameResult));
		btnAlpha = (Button) findViewById(R.id.btn_alpha_game_result);
		btnAlpha.setOnClickListener(this);
		btnAlpha.setVisibility(Button.INVISIBLE);
	    AKeyListener = new AdapterKeyListener(bm, CActivity);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		try{
			if(HighScoreView.VisibilityStatus && HighScoreView != null &&
					keyCode == KeyEvent.KEYCODE_BACK){
				HighScoreView.CloseView();
			}else{
				AKeyListener.KeyDown(keyCode);
			}
		}catch(Exception exception)
		{
			AKeyListener.KeyDown(keyCode);
		}
		return true;
	}
	
	public void ChangeEnableStatus(){
		EnableStatus = !EnableStatus;
		if(EnableStatus) {
			btnAlpha.setVisibility(Button.INVISIBLE);
		}else{
			btnAlpha.setVisibility(Button.VISIBLE);
		}
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
			// Посмотреть лучшие результаты игры
		if(v.equals(highScoreTopBtn)) {
			tm.StartSharingDialog();
			// Играть заново
		} else if(v.equals(reGameBtn)) {
			finish();
			Intent intent = new Intent(this, ActivityGameProcess.class);		  
			intent.putExtra("DIFFICULTY", strChooseDiff);
			startActivity(intent);	
		} else if(v.equals(btnAlpha)){
			bm.ChangeVisibility();
		}
	}// public void onClick(View v)
	
	public String GetGameResult(){
		return ParseData.InDotStringLine(gameResult);
	}

}	




