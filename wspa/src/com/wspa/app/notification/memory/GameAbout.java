package com.wspa.app.notification.memory;

import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.TypeSet;
import com.wspa.app.notification.extras.ControlActivity;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.menu.BottomMenu;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class GameAbout extends View implements OnClickListener {

	RelativeLayout rl;
	View about;
	Button CloseBtn;
	TextView tw;
	TypeSet ts;
	BottomMenu bm;
	ControlActivity CActivity;
	public Boolean VisibilityStatus;		// Индикатор видимости view

	public GameAbout(Context context, RelativeLayout rel_layout,
			BottomMenu bot_menu, ControlActivity CA) {
		super(context);
		CActivity = CA;
		CActivity.ChangeEnableActivity();
		VisibilityStatus = true;
		bm = bot_menu;
		bm.IsEnable = false;
		rl = rel_layout;
		ts = new TypeSet(context);
		
		about = View.inflate(context, R.layout.activity_about_game, rl);		
		CloseBtn = (Button)about.findViewById(R.id.closeButton);
		CloseBtn.setOnClickListener(this);		
		
		TextView tw1 = (TextView)about.findViewById(R.id.textSpelGameAbout);
		tw1 = ts.SetTF(tw1, DataConsts.ARIAL_REGULAR);
		
		TextView tw2 = (TextView)about.findViewById(R.id.textScoreGameAbout);
		tw2 = ts.SetTF(tw2, DataConsts.ARIAL_REGULAR);
		
		TextView tw3 = (TextView)about.findViewById(R.id.textHintsGameAbout);
		tw3 = ts.SetTF(tw3, DataConsts.ARIAL_REGULAR);
		
		TextView tw4 = (TextView)about.findViewById(R.id.textInstelGameAbout);
		tw4 = ts.SetTF(tw4, DataConsts.ARIAL_REGULAR);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub	
		if(v.equals(CloseBtn)) {
			CloseView();
		}
	}
	
	public void CloseView(){
		//bm.ChangeEnableStatus();
		VisibilityStatus = false;
		CActivity.ChangeEnableActivity();
		bm.IsEnable = true;
		rl.removeAllViews();
	}
	
}