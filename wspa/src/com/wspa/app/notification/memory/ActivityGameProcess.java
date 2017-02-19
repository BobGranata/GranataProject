package com.wspa.app.notification.memory;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.AdapterKeyListener;
import com.wspa.app.notification.content.DBAdapter;
import com.wspa.app.notification.content.DBAdapterPublic;
import com.wspa.app.notification.adapters.Extras;
import com.wspa.app.notification.models.MemoryModel;
import com.wspa.app.notification.extras.ControlActivity;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.extras.ParseData;
import com.wspa.app.notification.menu.BottomMenu;
import com.wspa.app.notification.menu.TopMenu;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

public class ActivityGameProcess extends Activity implements OnClickListener{
	
TimerTask mTimerTask;
	final Handler handler = new Handler();
	Timer t = new Timer();	
	
	private final int incrPoint = 100000;
	private final int decrPoint = 10000;
	private final int hintDecrPoint = 7000;
	
	int gameFildSize;
	int amountAllCardInFild;
	MemoryCard[][] fildMemoryCard;
	boolean pushHint = false; //true-если кнопка "hint" была нажата
	int countHintPress = 0;
	int CountOpenCard = 0;    //0-ни одной карты не открыто, 1-одна карта открыта, 2-две карты открыто
	float gameScore = 0;        //в переменной gameScore хранятся очки набранные игроком
	
	int pointsIncreased = 1;  //переменная множитель для увеличения очков за серию правильных открытий.
	int pointsDecreases = 0;  //переменная множитель для уменьшения очков за серию неправильных открытий.
	
	String strChooseDiff;
	
	private MemoryCard FirstOpenCard, SecondOpenCard, HintOpenCard;	
	
	private int tickCardLive = 0, tickHintLive = 0, tickAnimLive = 0;
	private int displayWidth = 0;		
	
	ArrayList<Bitmap> cardImage;
	
	TableLayout layout;
	TextView gameScoreView;
	Context context;
	
	LinearLayout ll_bot_menu, ll_top_menu;
	BottomMenu bm;
	TopMenu tm;
	Button hintButton,
		   btnAlpha;
	AdapterKeyListener AKeyListener;
	ControlActivity CActivity;
	public Boolean EnableStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memory);
		EnableStatus = true;
		CActivity = new ControlActivity(DataConsts.ACT_GAME_PROCESS, this);
		ll_bot_menu = (LinearLayout) findViewById(R.id.ll_game_start_bot_menu);
		bm = new BottomMenu(this, ll_bot_menu, DataConsts.BOT_MENU_TYPE_OTHER, CActivity);
		ll_top_menu = (LinearLayout) findViewById(R.id.ll_game_start_top_menu);
		tm = new TopMenu(this, ll_top_menu, R.string.TITLE_MEMORY_START,
				DataConsts.TOP_MENU_NO_BUTTON_INFO,
				DataConsts.TOP_MENU_BUTTON_SHARING,
				DataConsts.TOP_MENU_CAN_BACK, bm, CActivity, null);
//		tm = new TopMenu(this, ll_top_menu, R.string.TITLE_MEMORY_START,
//				DataConsts.TOP_MENU_NO_BUTTON_INFO,
//				DataConsts.TOP_MENU_BUTTON_SHARING,
//				DataConsts.TOP_MENU_CAN_BACK, bm, CActivity, null);
		AKeyListener = new AdapterKeyListener(bm, CActivity);
		layout = (TableLayout)findViewById(R.id.memTableLay);
		gameScoreView = (TextView)findViewById(R.id.scoreTextView);
		hintButton = (Button)findViewById(R.id.newButton);		
		btnAlpha = (Button) findViewById(R.id.btn_alpha_game_process);
		btnAlpha.setVisibility(Button.INVISIBLE);
		btnAlpha.setOnClickListener(this);
		context = getApplicationContext();
		
		hintButton.setEnabled(false);	
		
		// узнаем размеры экрана из класса Display
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics metricsB = new DisplayMetrics();
		display.getMetrics(metricsB);
		displayWidth = metricsB.widthPixels;		                       
		
		//обработка нажатиня на кнопку "hint", которая открывает все карточки на несколько миллисекунд
		//и отнимает очки при повторном нажатии
		hintButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (CountOpenCard == 1) {
					findHintPairCard();
					hintButton.setEnabled(false);
					pushHint = true;
					if (countHintPress == 0) {
						hintButton.setText("HINT(-7.000)");
						countHintPress++;
					} else if (countHintPress < 5) {
						countHintPress++;
						if ((gameScore - hintDecrPoint) < 0)
							gameScore = 0;
						else
							gameScore -= hintDecrPoint;
						gameScoreView.setText(ParseData.InDotStringLine(gameScore));
					}// if
				}// if
			}// onClick
		});// hintButton.setOnClickListener
		
	    Intent intent = getIntent();
	    strChooseDiff = intent.getStringExtra("DIFFICULTY");
	    	    
	    if (strChooseDiff.equals("4x4"))
	    	gameFildSize = 4;
	    else if (strChooseDiff.equals("6x6"))
	    	gameFildSize = 6;
	    
	    amountAllCardInFild = gameFildSize*gameFildSize;
		
	    fildMemoryCard = new MemoryCard[gameFildSize][gameFildSize];
    	    
	    cardImage = getImageFromFile();	    
	    Collections.shuffle(cardImage);
		buildGameField();
		doTimerTask();
		}	   	
		
	    public class Listener implements View.OnClickListener {
        private int x = 0;
        private int y = 0;

        public Listener(int x, int y, int indexResCard) {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {
        	
        	if (pushHint == false){
//				if (CountOpenCard != 2) {
//					MemoryCard singleMemoryCard = (MemoryCard) view;
//				}
				if (CountOpenCard == 0 && fildMemoryCard[x][y].cardOnTable) {
					
					FirstOpenCard = fildMemoryCard[x][y];
					FirstOpenCard.setOpenCard(true);
					
					CountOpenCard = 1;
					if (countHintPress < 5)
						hintButton.setEnabled(true);
				} else if (CountOpenCard == 1
						&& (FirstOpenCard.xPosCard != x || FirstOpenCard.yPosCard != y)
						&& fildMemoryCard[x][y].cardOnTable) {
					
					SecondOpenCard = fildMemoryCard[x][y];
					SecondOpenCard.setOpenCard(true);
					
					CountOpenCard = 2;
				}
			}
		}
    }

	private void buildGameField() {
		int imageIndex = 0;
		//создадим игровое поле циклом
		for (int i = 0, iRes = 0, lenI = gameFildSize; i < lenI; i++) {
			TableRow row = new TableRow(this); // создание строки таблицы
			for (int j = 0, lenJ = gameFildSize; j < lenJ; j++) {
				MemoryCard singleMemoryCard = new MemoryCard(this);
				fildMemoryCard[i][j] = singleMemoryCard;
				//установка слушателя, реагирующего на клик по кнопке
				singleMemoryCard.setOnClickListener(new Listener(i, j, imageIndex++));
				row.addView(singleMemoryCard, new TableRow.LayoutParams(
						TableRow.LayoutParams.WRAP_CONTENT,
						// добавление карточки в строку таблицы
						TableRow.LayoutParams.WRAP_CONTENT));
				singleMemoryCard.indexResurse = cardImage.get(iRes).hashCode();
				singleMemoryCard.openSideImage = cardImage.get(iRes++);
				singleMemoryCard.xPosCard = i;
				singleMemoryCard.yPosCard = j;
				
				singleMemoryCard.setImageResource(R.drawable.blank_side_card);
				singleMemoryCard.setPadding(3, 3, 3, 3);		
				
//				displayWidth; 
//				displayHeight;
				int heighCard = 0;
				
				if (gameFildSize == 4) {
					if (displayWidth == 320)
						heighCard = 76;
					else
						heighCard = displayWidth / 4 - 6;
				}

				if (gameFildSize == 6) {
					if (displayWidth == 320)
						heighCard = 50;
					else
						heighCard = displayWidth / 6 - 4;
				}
				singleMemoryCard.getLayoutParams().height = heighCard;
				singleMemoryCard.getLayoutParams().width = heighCard;
				singleMemoryCard.setScaleType(ScaleType.FIT_XY);				
			}
			// добавление строки в таблицу
			layout.addView(row, new TableLayout.LayoutParams( 
					TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
		}
	}

//1) until user guess first pair his points is 0
//2) first "help" costs 0 points
//3) if user guesses pairs of cards without one by one each time points increased by percent like 110%, 120%, 130%, 140%
//4) same if user doesn't guess all the time his points decreases like 100%, 105%, 110%, 115%
//5) in case user completes earlier then X seconds we increase his point with x2
//6) in case uses completes earlier then Y seconds (Y is greater then X) we increase his points on x1.1 .. x1.4)
//7) if more then Y we give no bonus points
	
	
    public void doTimerTask(){   	 
		mTimerTask = new TimerTask() {
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						//если открыто две карты, значит запускается счётчик до закрытия карт
						
						if (pushHint == true) {
//							tickHintLive++;
							//FirstOpenCard.setOpenCard(true);
							//HintOpenCard.setOpenCard(true);
							
							FirstOpenCard.cardOnTable = false;																
							HintOpenCard.cardOnTable = false;
							if (checkGameOver())
								createGameResultAct();
							
							pushHint = false;
							CountOpenCard = 0;
							//tickHintLive = 0;
						}					
//						if (tickHintLive > 7) { //было 5
//							FirstOpenCard.setOpenCard(false);
//							HintOpenCard.setOpenCard(false);
//							
//							FirstOpenCard.cardOnTable = false;																
//							HintOpenCard.cardOnTable = false;
//							if (checkGameOver())
//								createGameResultAct();
//							
//							pushHint = false;
//							CountOpenCard = 0;
//							tickHintLive = 0;
//						}
						
//						if (CountOpenCard > 0)
//							tickAnimLive++;
						
						if (CountOpenCard == 2)
							tickCardLive++;						
						//если прошло 30 миллисекунд 
						if (tickCardLive > 7) { //было 3							
							CountOpenCard = 0;
							tickCardLive = 0;
							
							int res1 = FirstOpenCard.indexResurse;
							int res2 = SecondOpenCard.indexResurse;							
							
							int x1 = FirstOpenCard.xPosCard;
							int y1 = FirstOpenCard.yPosCard;
							
							int x2 = SecondOpenCard.xPosCard;
							int y2 = SecondOpenCard.yPosCard;
							
							// проверка на совпадение карточек друг с другом
							// если карточки совпадают то делаем их невидимыми и начисляем очки
							if (res1 == res2 && (x1 != x2 || y1 != y2)) {															
								IncreasedGameScore();
								FirstOpenCard.cardOnTable = false;																
								SecondOpenCard.cardOnTable = false;
								if (checkGameOver())
									createGameResultAct();
							} else {			
								DecreasesGameScore();
								FirstOpenCard.setOpenCard(false);
								SecondOpenCard.setOpenCard(false);
							}
						}
						Log.d("TIMER", "TimerTask run");
					}
				});
			}
		};
		// public void schedule (TimerTask task, long delay, long period)
		t.schedule(mTimerTask, 500, 100); //
	}		
    
  //====================================createGameResultAct============================================
  //функция createGameResultAct вызывается при завершении игры, создаёт новое активити
  	private void createGameResultAct() {
  		finish();
  		Intent intent = new Intent(this, ActivityGameResult.class);        
        intent.putExtra("DIFFICULTY", strChooseDiff);
  		//float gameScore = Float.parseFloat(gameScoreView.getText().toString());
  		intent.putExtra("SCORE", gameScore);
  		startActivity(intent);
  	}
  	
    //100,000 points. If images wrong he gets -10,000
    private void IncreasedGameScore(){  
    	pointsDecreases = 0;    	
		float factorIncreas = pointsIncreased;
		if (pointsIncreased > 0) {
			// если один раз карточки выбраны без ошибок то множитель
			// factorIncreas = 110% или 1.1,
			// если два раза то 120% или 1.2 и т.д.
			factorIncreas = (factorIncreas * 10 + 100) / 100;
		} else factorIncreas = 1;
		pointsIncreased++;
		gameScore += (incrPoint * factorIncreas);
		//this.gameScore = 
		gameScoreView.setText(ParseData.InDotStringLine(gameScore));
	}
    
	private void DecreasesGameScore() {
		pointsIncreased = 0;
		pointsDecreases++;
		float factorIncreas = pointsDecreases;
		if (pointsDecreases > 1) {
			// если один раз карточки выбраны не верное то множитель
			// factorDecreases = 100% или 1,
			// если два раза то 105% или 1.05 и т.д.
			factorIncreas = (factorIncreas * 5 + 100) / 100;
		}
		else factorIncreas = 1;
		if ((gameScore - decrPoint * factorIncreas) < 0)
			gameScore = 0;
		else
			gameScore -= (decrPoint * factorIncreas);
		gameScoreView.setText(ParseData.InDotStringLine(gameScore));//String.valueOf(gameScore));
	}	
	
	private boolean checkGameOver(){
		for (int i = 0, lenI = gameFildSize; i < lenI; i++) 
			for (int j = 0, lenJ = gameFildSize; j < lenJ; j++)
				//если хотя бы одна карта лежит на столе возвращаем false
				if (fildMemoryCard[i][j].cardOnTable)
					return false;				
		//если мы не вернули false, значит ни одной карты на столе нет, игра окончена
		return true;
	}
	
	private void findHintPairCard() {
		for (int i = 0, lenI = gameFildSize; i < lenI; i++) {
			for (int j = 0, lenJ = gameFildSize; j < lenJ; j++) {
				if (fildMemoryCard[i][j].indexResurse == FirstOpenCard.indexResurse
						&& (i != FirstOpenCard.xPosCard || j != FirstOpenCard.yPosCard)) {
					fildMemoryCard[i][j].setOpenCard(true);
					HintOpenCard = fildMemoryCard[i][j];
				}
			}
		}
	}
	
	private ArrayList<Bitmap> getImageFromFile(){
		MemoryModel[] memor = null;		
		
		DBAdapter db = DBAdapterPublic.OpenDb(this);
		memor = db.getMemory();
		DBAdapterPublic.CloseDb();				
		int numImage = memor.length;		
		numImage = amountAllCardInFild/2;
		ArrayList<Bitmap> cardImageResult = new ArrayList<Bitmap>(amountAllCardInFild);
		
		for (int i = 0; i <= numImage - 1; i++) {
			FileInputStream fis = null;
			FileDescriptor fd = null;
			String Filename = Extras.GetMD5(memor[i].getIcon());
			try {
				fis = this.openFileInput(Filename);				
				fd = fis.getFD();				
			} catch (Exception fileException) {

			}
			if (fd != null) {
				try {
					BitmapFactory.Options opts = new BitmapFactory.Options();
					opts.inJustDecodeBounds = true;					
					BitmapFactory.decodeFileDescriptor(fd, null, opts);
					Bitmap image = BitmapFactory.decodeFileDescriptor(fd);
					cardImageResult.add(image);
					fis.close();
				} catch (Exception e) {
				} // Image can be very large
			}
		}
		for (int i = 0; i <= numImage-1; i++)
			cardImageResult.add(cardImageResult.get(i));			
		return cardImageResult;
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		AKeyListener.KeyDown(keyCode);
		return true;
	}
	
}
