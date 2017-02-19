package com.wspa.app.notification.news;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.google.analytics.tracking.android.EasyTracker;
import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.AdapterKeyListener;
import com.wspa.app.notification.adapters.DropDownSpinnerAdapter;
import com.wspa.app.notification.adapters.SharedPref;
import com.wspa.app.notification.models.AuthentModel;
import com.wspa.app.notification.content.ContentManager;
import com.wspa.app.notification.content.DBAdapter;
import com.wspa.app.notification.content.DBAdapterPublic;
import com.wspa.app.notification.adapters.ListNewsAdapter;
import com.wspa.app.notification.content.NetworkManager;
import com.wspa.app.notification.content.NetworkQueue.NetworkQueueListener;
import com.wspa.app.notification.adapters.TypeSet;
import com.wspa.app.notification.extras.ControlActivity;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.extras.SettingsConsts;
import com.wspa.app.notification.interfaces.FbConnectorListener;
import com.wspa.app.notification.menu.BottomMenu;
import com.wspa.app.notification.menu.TopMenu;
import com.wspa.app.notification.news.ActivityNewsDetails;
import com.wspa.app.notification.splash.ActivityRegCode;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ToggleButton;
import android.widget.Spinner;

public class ActivityNews extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	ArrayList<NewsModel> NewsContentList; // массив с данными новостей
	ListView newsListView; // список новостей
	ToggleButton sortTogButton; // кнопка выбора сортировки
	Spinner categSpin; // спинер выбора категории новостей
	ContentManager tc;
	BottomMenu bm; // нижнее меню
	TopMenu tm; // верхнее меню
	LinearLayout ll_bot_menu, ll_top_menu; // Layouts для верхнего и нижнего
											// меню
	TypeSet ts; // для установки шрифта
	public static boolean ContentUpdated = false;

	Button btnAlpha;
	Boolean EnableStatus;
	AdapterKeyListener AKeyListener;
	ControlActivity CActivity;
	AuthentModel auth;
	SharedPref sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		EasyTracker.getInstance().setContext(getApplicationContext());
		EasyTracker.getTracker().trackEvent(getString(R.string.A_A_LADEN),
				getString(R.string.A_L_NEWS),
				getString(R.string.A_L_NEWS),
				null);
		EnableStatus = true;
		CActivity = new ControlActivity(DataConsts.ACT_NEWS, this);
		ll_bot_menu = (LinearLayout) findViewById(R.id.ll_news_bot_menu);
		ll_top_menu = (LinearLayout) findViewById(R.id.ll_news_top_menu);
		bm = new BottomMenu(this, ll_bot_menu, DataConsts.BOT_MENU_TYPE_NEWS,
				CActivity);
		tm = new TopMenu(this, ll_top_menu, R.string.TITLE_NEWS,
				DataConsts.TOP_MENU_NO_BUTTON_INFO,
				DataConsts.TOP_MENU_BUTTON_SHARING,
				DataConsts.TOP_MENU_NO_BACK, bm, CActivity, null);
		btnAlpha = (Button) findViewById(R.id.btn_alpha_news);
		btnAlpha.setOnClickListener(this);
		btnAlpha.setVisibility(Button.INVISIBLE);
		AKeyListener = new AdapterKeyListener(bm, CActivity);
		// https://app.welkombijwspa.nl/api/359028031080110/84R3M2/
		// получаем экземпляр элемента ListView
		newsListView = (ListView) findViewById(R.id.newsList);
		categSpin = (Spinner) findViewById(R.id.newsCategoriSpinner);
		TextView sortText = (TextView) findViewById(R.id.sorteerTextView);
		sortTogButton = (ToggleButton) findViewById(R.id.toggleButton1);
		sortTogButton.setOnCheckedChangeListener(this);

		ts = new TypeSet(getApplicationContext());
		TypeSet ts = new TypeSet(this);
		sortText = ts.SetTF(sortText, DataConsts.ARIAL_REGULAR);
		
		sp = new SharedPref(getApplicationContext(), getString(R.string.APP_SET));		
//		true = datum
//		false = views		
		sortTogButton.setChecked(sp.ReadBoolValue(SettingsConsts.NEWS_SORT_TYPE));

		// Настраиваем адаптер для спиннера выбора категории новостей.
//		ArrayAdapter<?> spinAdapter = ArrayAdapter.createFromResource(this,
//				R.array.news_cetegory_list_cut, R.layout.news_categ_spinner); //news_cetegory_list
//		
//		spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		categSpin.setAdapter(spinAdapter);
		
		
		String[] cats = { "Alle categorieёn", "Algemeen", "Huis- & zwerfdieren", "Dieren in het wild", "Dieren in rampen", "Dieren in de landbouw"};
		DropDownSpinnerAdapter adapter = new DropDownSpinnerAdapter(this,
				R.layout.news_categ_spinner, cats);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    categSpin.setAdapter(adapter);

		categSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View itemSelected, int selectedItemPosition,
							long selectedId) {
						// При выборе какого либо пункта категории, вызывается
						// функция перерисовки списка новостей
						setListViewData();
						DropDownSpinnerAdapter.flag = true;  
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
//		// Если новости открыты впервые то запускается функция запроса статуса
//		// (даты публикации) news, about и memory
//		if (!ContentUpdated) {
//			tc = new ContentManager(this);
//			tc.updateStatus(new NetworkQueueListener() {
//
//				@Override
//				public void onNext(NetworkManager manager) {
//					// System.out.println("QUEUE - next");
//				}
//
//				@Override
//				public void onFinish() {
//					// При завершении запроса запускается функция обновления
//					// всех данных таблиц news, about и memory
//					updateAllData();
//					ContentUpdated = true;
//				}
//			});
//		} else
//			setListViewData();
		newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent,
							View itemClicked, int position, long id) {
						// При клике на эелементе списка запускаем активность
						// описания деталей новостей.
						Intent intent = new Intent(ActivityNews.this,
								ActivityNewsDetails.class);
						int putId = NewsContentList.get(position).getId();
						intent.putExtra("NEWSID", putId);
						startActivity(intent);
					}
				});
		
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		// Если новости открыты впервые то запускается функция запроса статуса
		// (даты публикации) news, about и memory
			tc = new ContentManager(this);
			tc.updateStatus(new NetworkQueueListener() {

				@Override
				public void onNext(NetworkManager manager) {
					// System.out.println("QUEUE - next");
				}

				@Override
				public void onFinish() {
					// При завершении запроса запускается функция обновления
					// всех данных таблиц news, about и memory
					updateAllData();					
				}
			});		
    }

	@Override
    protected void onStop(){
    	super.onStop();
    }

	private void updateAllData(){			
		String pubDate[] = null;
		pubDate = tc.getStatus();
		if (!pubDate[0].equals("FAIL")) {
			tc.updateWspaContent(pubDate, new NetworkQueueListener() {
				@Override
				public void onNext(NetworkManager manager) {
				}

				@Override
				public void onFinish() {
					// При завершении запроса к серверу, вызывается функция
					// перерисовки списка новостей
					setListViewData();
				}
			});
		}
		else {
			//Выполняется при ошибке
			Toast.makeText(this, getString(R.string.ERROR_CODE_BLOCKED), Toast.LENGTH_SHORT).show();
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					sp.SetBoolValue(SettingsConsts.IS_REGISTERED, false);
					finish();
					Intent intent = new Intent(ActivityNews.this, ActivityRegCode.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			}, 4000);
		}
	}
	
	// В функции "setListViewData" устонавливается адаптер списка в соответствии
	// с установками категории и сортировки.
	private void setListViewData(){
		NewsContentList = new ArrayList<NewsModel>();		
		
		NewsModel[] news = null;
		
		DBAdapter db = DBAdapterPublic.OpenDb(this);
		// Возвращаем список новостей из таблицы базы данных, в параметрах
		// функции getNewsList передаются
		// пользовательские установки сортировки и категорий новостей.
		
		news = db.getNewsList(sortTogButton.isChecked(), categSpin.getSelectedItemPosition());		
		
		DBAdapterPublic.CloseDb();

		if (news != null)
			for (int i = 0; i <= news.length - 1; i++) {
				NewsContentList.add(news[i]);
				}

		// используем кастомный адаптер данных
		ListNewsAdapter adapter = new ListNewsAdapter(this, NewsContentList);

		newsListView.setAdapter(adapter);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		AKeyListener.KeyDown(keyCode);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.equals(btnAlpha)) {
			bm.ChangeVisibility();
		}
	}

	public void ChangeEnableStatus() {
		EnableStatus = !EnableStatus;
		if (EnableStatus) {
			btnAlpha.setVisibility(Button.INVISIBLE);
		} else {
			btnAlpha.setVisibility(Button.VISIBLE);
		}
	}

	// Обработка щелчка по кнопке sortTogButton (Sorteer) выбора сортировки.
	// Которая может принимать два состояния.
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// При нажатии на кнопку вызываем функцию сортировки списка в
		// соответствуии с выбранным параметром		
		sp.SetBoolValue(SettingsConsts.NEWS_SORT_TYPE, isChecked);
		sortTogButton.setChecked(isChecked);
		setListViewData();
	}
}
