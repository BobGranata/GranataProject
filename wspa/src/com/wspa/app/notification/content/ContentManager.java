package com.wspa.app.notification.content;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.ContentListener;
import com.wspa.app.notification.adapters.NetworkListener;
import com.wspa.app.notification.adapters.ParserListener;
import com.wspa.app.notification.adapters.SharedPref;
import com.wspa.app.notification.content.NetworkQueue.NetworkQueueListener;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.extras.SettingsConsts;
import com.wspa.app.notification.models.AboModel;
import com.wspa.app.notification.models.AuthentModel;
import com.wspa.app.notification.models.MemoryModel;
import com.wspa.app.notification.models.NotifyModel;
import com.wspa.app.notification.news.ActivityNews;
import com.wspa.app.notification.news.ActivityNewsDetails;
import com.wspa.app.notification.news.NewsModel;
import com.wspa.app.notification.info.AboutModel;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class ContentManager {	
	/**
	 * Shows whether initialUpdate for Db was called
	 */
	private static boolean InitialDataUpdated = false;

	private static boolean InfoUpdated = false;
	
	private static boolean NewsUpdated = false;
	
	private static boolean WspaContentUpdated = false;
	
	private static boolean MemoryUpdated = false;
	
	private static boolean StatusUpdated = false;
	
	Context mContext;
	ContentListener mListener = null;
	NetworkQueue mQueue = null;
	private AuthentModel mAuthent = null;
	private AboModel mAbon = null;
	private NotifyModel mNotify = null;
	private String mStatus[] = null;
	
	public ContentManager(Context context) {
		mContext = context;
	}
	
	public ContentManager(Context context, ContentListener listener) {
		mContext = context;
		mListener = listener;
	}
	
	/**
	 * does NOT support ContentListener
	 */
	// Возвращает EMEI-код телефона
	public String GetIMEI(){
		TelephonyManager telephonyManager = (TelephonyManager)mContext.getSystemService("phone");
		return telephonyManager.getDeviceId();
	}
	
	// Читает из файла код приложения
	public String GetCode(){
		SharedPref sp = new SharedPref(mContext, mContext.getString(R.string.APP_SET));
		return sp.ReadStringValue(SettingsConsts.PASSWORD);
	}
	
	// Читает из файла настроек группы уведомлений
	public String GetNotificationsGroup(){
		SharedPref sp = new SharedPref(mContext, mContext.getString(R.string.APP_SET));
		String Result = "";
		Boolean Indicator = false;
		if(sp.ReadBoolValue(SettingsConsts.PUSH_BEER)) {
			Result += "1";
			Indicator = true;
		}
		if(sp.ReadBoolValue(SettingsConsts.PUSH_HUISDIEREN)) {
			if(Indicator) Result += "-2"; else { Result += "2"; Indicator = true; }
		}
		if(sp.ReadBoolValue(SettingsConsts.PUSH_KIPPEN)) {
			if(Indicator) Result += "-3"; else { Result += "3"; Indicator = true; }
		}
		if(sp.ReadBoolValue(SettingsConsts.PUSH_OLIFANT)) {
			if(Indicator) Result += "-4"; else { Result += "4"; Indicator = true; }
		}
		if(sp.ReadBoolValue(SettingsConsts.PUSH_VEE)) {
			if(Indicator) Result += "-5"; else Result += "5";
		}
		return Result;
	}
	
	// Читает из файла настроек тип звуковых уведомлений
	public String GetSoundType(){
		SharedPref sp = new SharedPref(mContext, mContext.getString(R.string.APP_SET));
		String Result = String.valueOf(sp.ReadIntValue(SettingsConsts.PUSH_SOUND_TYPE) - 1);
		if(Result.equals("0")) return "default"; 
		else return Result;
	}
	
	public String GetPushID(){
		SharedPref sp = new SharedPref(mContext, mContext.getString(R.string.APP_SET));
		return sp.ReadStringValue("PUSH_ACCESS_TOKEN");
	}
	
	public String GetNotifyState(){
		SharedPref sp = new SharedPref(mContext, mContext.getString(R.string.APP_SET));
		if(sp.ReadBoolValue(SettingsConsts.PUSH_STATUS)) return "TRUE"; else return "FALSE";
	}
	
	public AuthentModel getAuth(){ 
		return mAuthent; 
		}
	
	public AboModel getAbonnement(){ 
		return mAbon; 
		}
	
	public String[] getStatus(){ 
		return mStatus; 
		}
	
	public NotifyModel getNotify(){
		return mNotify;
	}
	
	public void updateWspaContent(String[] statusPubDate, NetworkQueueListener listener){
		/*
		if(WspaContentUpdated){
			listener.onFinish();
			return;
		}*/
		String urlsNews =  GetIMEI() + "/" + GetCode() + "/GET/NEWS/";// + datePub[0];
		String urlsMemory = GetIMEI() + "/" + GetCode() + "/GET/MEMORY/";// + datePub[1];
		String urlsAbout = GetIMEI() + "/" + GetCode() + "/GET/CONTENT/";// + datePub[2];
		
		DBAdapter db = DBAdapterPublic.OpenDb(mContext);
		String[] existPubDate = db.getPubdate();
		DBAdapterPublic.CloseDb();   	
		
		mQueue = new NetworkQueue(listener);
		
		if (!statusPubDate[0].equals(existPubDate[0])){
			if (existPubDate[0] != null)
				urlsNews += existPubDate[0];
			mQueue.add(prepareManagerForNews(urlsNews));
			}
		if (!statusPubDate[1].equals(existPubDate[1])){
			if (existPubDate[1] != null)
				urlsMemory += existPubDate[1];		
			mQueue.add(prepareManagerForMemory(urlsMemory));
			}
		if (!statusPubDate[2].equals(existPubDate[2])){
			if (existPubDate[2] != null)
				urlsAbout += existPubDate[2];
			mQueue.add(prepareManagerForAbout(urlsAbout));			
			}
		mQueue.next();
		//WspaContentUpdated = true;
	}
	
	private NetworkManager prepareManagerForNews(String urls){	
	return new NetworkManager(mContext, new NetworkListener() {
		@Override
		public void onSuccess(String result, boolean showProgress, URI url, boolean  isError) {
			if(!isError){
				DBAdapter db = DBAdapterPublic.OpenDb(mContext);
				List<NewsModel> listNews = new ParserJSON(url, showProgress, mContext).parseNews(result);
				db.news_add(listNews);
				db.pubdate_add(listNews.get(0).getPubDate(), DataConsts.PDATE_NEWS);
				DBAdapterPublic.CloseDb();
			}
			mQueue.next();
		}
	}).setPending(urls, false);
}
	private NetworkManager prepareManagerForAbout(String urls){	
	return new NetworkManager(mContext, new NetworkListener() {
		@Override
		public void onSuccess(String result, boolean showProgress, URI url, boolean  isError) {
			if(!isError){
				DBAdapter db = DBAdapterPublic.OpenDb(mContext);
				AboutModel aboutData = new ParserJSON(url, showProgress, mContext).parseAbout(result).get(0);
				db.about_add(aboutData);
				db.pubdate_add(aboutData.getPubDate(), DataConsts.PDATE_ABOUT);
				DBAdapterPublic.CloseDb();
			}
			mQueue.next();
		}
	}).setPending(urls, false);
}
//================================================================================================================	
	/*
	public void initialUpdate(boolean showProgress, NetworkQueueListener listener){
		if(InitialDataUpdated){
			listener.onFinish();
			return;
		}
		String urls= GetEMEI() + "/" + GetCode() + "/GET/NEWS/20130111120000";
		mQueue = new NetworkQueue(listener);
		updateNews(urls, showProgress);
//		mQueue = new NetworkQueue(listener);
//		urls= "359028031080110/84R3M2/GET/CONTENT/20130111120000";
//		updateAbout(urls, showProgress);
		//updateCategories(showProgress);
		//updateShopdines(listener, showProgress);
		InitialDataUpdated = true;
	}*/

/*	public void updateNews(String urls, boolean showProgress){
			NetworkManager manager = new NetworkManager(mContext, showProgress, new NetworkListener() {
				@Override
				public void onSuccess(String result, boolean showProgress, URI url, boolean  isError) {
					if(isError){
						mQueue.next();
						return;
					}
					new ParserJSON(url, showProgress, mContext).parseNewsAsync(result, new ParserListener<NewsModel>() {
											
						//@Override
						public void onFinish(List<NewsModel> list, URI url) {
							DBAdapter db = DBAdapterPublic.OpenDb(mContext);
							db.news_add(list);
							db.pubdate_add(list.get(0).getPubDate(), DataConsts.PDATE_NEWS);
							DBAdapterPublic.CloseDb();
							mQueue.next();
						}
					});
				}
			});
			manager.setPending(urls, false);
			mQueue.add(manager);
//			manager.performRawGet(urls[i]);
		mQueue.next();
	}*/
	/*
	public void updateAbout(NetworkQueueListener listener){
		if(NewsUpdated){
			if(listener != null) listener.onFinish();
			return;
		}
		mQueue = new NetworkQueue(listener);
		mQueue.add(prepareAboutManager(GetEMEI() + GetCode() + "/GET/CONTENT/20130111120000"));
		mQueue.next();
		NewsUpdated = true;
	}*/
	/*
	private NetworkManager prepareAboutManager(String url){
		return	new NetworkManager(mContext, new NetworkListener() {
				@Override
				public void onSuccess(String result, boolean showProgress, URI url, boolean  isError) {
					if(isError){
						if(mListener != null) mListener.onContentReady();
						if(mQueue != null) mQueue.next();
						return;
					}
					new ParserJSON(url, showProgress, mContext).parseAboutAsync(result, new ParserListener<AboutModel>() {
	
						@Override
						public void onFinish(List<AboutModel> result, URI url) {
							if(url != null){
								DBAdapter db = DBAdapterPublic.OpenDb(mContext);
								db.about_add(result.get(0));
								DBAdapterPublic.CloseDb();
							}
							if(mQueue != null)mQueue.next();
							if(mListener != null) mListener.onContentReady();
						}
					});
				}
			})		
			.setPending(url,false);
	}*/
	/*
	public void updateMemory(NetworkQueueListener listener) {
		if (MemoryUpdated) {
			listener.onFinish();
			return;
		}
		mQueue = new NetworkQueue(listener);
		mQueue.add(prepareManagerForMemory(GetEMEI() + "/" + GetCode() + "/GET/MEMORY/20130111120000"));
		mQueue.next();
		MemoryUpdated = true;
	}*/
	
	private NetworkManager prepareManagerForMemory(String urls){	
		return new NetworkManager(mContext, new NetworkListener() {
			@Override
			public void onSuccess(String result, boolean showProgress, URI url, boolean  isError) {
				if(!isError){
					DBAdapter db = DBAdapterPublic.OpenDb(mContext);
					List<MemoryModel> memoryList = new ParserJSON(url, showProgress, mContext).parseMemory(result);
					db.saveMemory(memoryList);
					db.pubdate_add(memoryList.get(0).getPubDate(), DataConsts.PDATE_MEMORY);
					DBAdapterPublic.CloseDb();
				}
				mQueue.next();
			}
		}).setPending(urls, false);
	}

	public void updateStatus(NetworkQueueListener listener){
/*		if(StatusUpdated){
			listener.onFinish();
			return;
		}*/
		String urls =  GetIMEI() + "/" + GetCode() + "/STATUS/"; 
		mQueue = new NetworkQueue(listener);
		mQueue.add(prepareManagerForStatus(urls));
		mQueue.next();
		//StatusUpdated = true;
	}
	
	private NetworkManager prepareManagerForStatus(String urls){	
		return new NetworkManager(mContext, new NetworkListener() {
			@Override
			public void onSuccess(String result, boolean showProgress, URI url, boolean  isError) {
				if(!isError){					
					mStatus = new ParserJSON(url, showProgress, mContext).parseStatus(result);					
				}
				mQueue.next();
			}
		}).setPending(urls, false);
	}	
	
	public void updateAuthenticatie(String code, NetworkQueueListener listener){
		String urls = GetIMEI() + "/" + code + "/";
		mQueue = new NetworkQueue(listener);
		mQueue.add(prepareManagerForAuthenticatie(urls));
		mQueue.next();
		InfoUpdated = true;
	}	

	private NetworkManager prepareManagerForAuthenticatie(String urls){	
		return new NetworkManager(mContext, new NetworkListener() {
			@Override
			public void onSuccess(String result, boolean showProgress, URI url, boolean  isError) {
				if(!isError){					
					mAuthent = new ParserJSON(url, showProgress, mContext).parseAuthent(result).get(0);					
				}
				mQueue.next();
			}
		}).setPending(urls, false);
	}	
	
	public void updateNotify(NetworkQueueListener listener){
		String urls = null, 
			   NotState = GetNotifyState();
		if (NotState.equals("TRUE")) {
			String sound = GetSoundType();
			urls = GetIMEI() + "/" + GetCode() + "/UPDATE/NOTIFY/" + GetPushID() + "/"
					+ NotState + "/" + GetNotificationsGroup() + "/" + sound;
		} else {
			urls = GetIMEI() + "/" + GetCode() + "/UPDATE/NOTIFY/" + GetPushID() + "/"
					+ NotState;
		}
		mQueue = new NetworkQueue(listener);
		mQueue.add(prepareManagerForNotify(urls));
		mQueue.next();		
	}	

	private NetworkManager prepareManagerForNotify(String urls){	
		return new NetworkManager(mContext, new NetworkListener() {
			@Override
			public void onSuccess(String result, boolean showProgress, URI url, boolean  isError) {
				if(!isError){
					try {
						mNotify = new ParserJSON(url, showProgress, mContext).parseNotify(result).get(0);
					} catch (Exception e) { }
				}
				mQueue.next();
			}
		}).setPending(urls, false);
	}	
	
	public void updateAbonnement(String email, NetworkQueueListener listener){
		String urls = GetIMEI() + "/" + GetCode() + "/UPDATE/ABO/" + email + "/TRUE";
		mQueue = new NetworkQueue(listener);
		mQueue.add(prepareManagerForAbonnement(urls));
		mQueue.next();
	}	

	private NetworkManager prepareManagerForAbonnement(String urls){	
		return new NetworkManager(mContext, new NetworkListener() {
			@Override
			public void onSuccess(String result, boolean showProgress, URI url, boolean  isError) {
				if(!isError){					
					mAbon = new ParserJSON(url, showProgress, mContext).parseAbo(result).get(0);					
				}
				mQueue.next();
			}
		}).setPending(urls, false);
	}		
	
}
