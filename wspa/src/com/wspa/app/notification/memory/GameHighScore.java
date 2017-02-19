package com.wspa.app.notification.memory;

import java.util.ArrayList;

import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.SharedPref;
import com.wspa.app.notification.adapters.TypeSet;
import com.wspa.app.notification.extras.ControlActivity;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.extras.ParseData;
import com.wspa.app.notification.menu.BottomMenu;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class GameHighScore extends View implements OnClickListener {

	public Boolean VisibilityStatus;		// Индикатор видимости view
	int ParrentID;
	RelativeLayout rl;
	View about;
	Button CloseBtn;	
	TextView TopListTV, titleTop10TV, textTop10TV, puntenTop10TV;

	SharedPref spResult;
	String allResult;
	TypeSet ts;
	ControlActivity CActivity;
	BottomMenu bm;

	public GameHighScore(Context context, RelativeLayout rel_layout, 
			BottomMenu bot_menu, ControlActivity CA) {
		super(context);
		VisibilityStatus = true;
		CActivity = CA;
		CActivity.ChangeEnableActivity();
		rl = rel_layout;
		bm = bot_menu;
		bm.IsEnable = false;
		rl = rel_layout;
		ts = new TypeSet(context);
		spResult = new SharedPref(context, context.getString(R.string.APP_SET));		
		about = View.inflate(context, R.layout.high_score_list, rl);				
		
		CloseBtn = (Button)about.findViewById(R.id.closeHighTopButton);
		CloseBtn.setOnClickListener(this);
		
		TopListTV = (TextView)about.findViewById(R.id.highScorListTextView);
		TopListTV = ts.SetTF(TopListTV, DataConsts.ARIAL_REGULAR);
		
		titleTop10TV = (TextView)about.findViewById(R.id.top10TextView);
		titleTop10TV = ts.SetTF(titleTop10TV, DataConsts.ARIAL_REGULAR);
		
		textTop10TV = (TextView)about.findViewById(R.id.readyMadeTextView);
		textTop10TV = ts.SetTF(textTop10TV, DataConsts.ARIAL_REGULAR);
		
		puntenTop10TV = (TextView)about.findViewById(R.id.puntenTextView);
		titleTop10TV = ts.SetTF(titleTop10TV, DataConsts.ARIAL_REGULAR);
		
		ArrayList<Float> arrayResult = spResult.GetGameResults();
		allResult = "";
		boolean alternation = true;		
		
		int countResult = arrayResult.size();		
		
		for(int i = 0; i < 10; i++){
			if (alternation){
				allResult += "<b><FONT color=\'#e37222\'>";
				alternation = false;
				}
			else {
				allResult += "<b><FONT color=\'#747678\'>";
				alternation = true;
			}
			String zero = "0";
			if (i == 9)
				zero = "";
			allResult += zero + (i+1) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			if (i < countResult)
				allResult += ParseData.InDotStringLine(arrayResult.get(i)) + "</b><br></FONT>"; 
			else
				allResult += "00.000.000" + "</b><br></FONT>";
			}
		TopListTV.setText(Html.fromHtml(allResult));
		TopListTV.setLineSpacing(6, 1);
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
		bm.IsEnable = true;
		CActivity.ChangeEnableActivity();
		rl.removeAllViews();
	}
	
}

