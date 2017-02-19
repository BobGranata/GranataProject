package com.wspa.app.notification.content;

import com.wspa.app.notification.models.MemoryModel;
import com.wspa.app.notification.news.NewsModel;
import com.wspa.app.notification.adapters.Extras;
import com.wspa.app.notification.info.AboutModel;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
	
	private static final String DATABASE_NAME = "wspa";
    private static final int DATABASE_VERSION = 1;
    
    private static final String KEY_ID = "_id";
    private static final String KEY_STATUS = "_status";
    private static final String KEY_DATE = "_date";
    private static final String KEY_ORDER = "_order";
    private static final String KEY_CATEGORY = "_category";
    private static final String KEY_ICON = "_icon";
    private static final String KEY_IMAGE = "_image";
    private static final String KEY_SHORTCAPTION = "_shortCaption";
    private static final String KEY_SHORTTEXT = "_shortText";
    private static final String KEY_LONGCAPTION = "_longCaption";
    private static final String KEY_LONGTEXT = "_longText";
    private static final String KEY_URLSHARE = "_url_share";
    private static final String KEY_URLVIDEO = "_url_video";        
    
    private static final String KEY_CONTENT1 = "_content1";
    private static final String KEY_CONTENT2 = "_content2";
    
    private static final String KEY_NEWSPUBDATE = "_news";
    private static final String KEY_ABOUTPUBDATE = "_about";
    private static final String KEY_MEMORYPUBDATE = "_memory";
    
    private static final String TABLE_NEWS = "news_table";
    private static final String TABLE_MEMORY = "memory_table";
    private static final String TABLE_CONTENT = "content_table";
    private static final String TABLE_PUBDATE = "pubdate_table";
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private Context context;
    
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
        	//TODO remove autoincrements
        	db.execSQL("create table " + TABLE_NEWS + 
        			" (" + KEY_ID + " integer primary key, " + 
        			KEY_STATUS + " text not null," +
        			KEY_DATE + " text not null," +
        			KEY_ORDER + " integer," +
        			KEY_CATEGORY + " integer," +
        			KEY_ICON + " text not null," +
        			KEY_IMAGE + " text not null," +
        			KEY_SHORTCAPTION + " text not null," +
        			KEY_SHORTTEXT + " text not null," +
        			KEY_LONGCAPTION + " text not null," +
        			KEY_LONGTEXT + " text not null," +
        			KEY_URLSHARE + " text not null," +
        			KEY_URLVIDEO + " text not null)");
        	
        	db.execSQL("create table " + TABLE_MEMORY + 
        			" (" + KEY_ID + " integer primary key, " + 
        			KEY_STATUS + " text not null," +
        			KEY_DATE + " text not null," +
        			KEY_ICON + " text not null)");
        	
        	db.execSQL("create table " + TABLE_CONTENT + 
        			" (" + KEY_IMAGE + " text primary key, " + 
        			KEY_CONTENT1 + " text not null," +
        			KEY_CONTENT2 + " text not null," +
        			KEY_URLSHARE + " text not null)");
        	
        	db.execSQL("create table " + TABLE_PUBDATE +
        			" (" + KEY_ID + " integer primary key, " + 
        			KEY_NEWSPUBDATE + " text," +
        			KEY_MEMORYPUBDATE + " text," +
        			KEY_ABOUTPUBDATE + " text)");        	
        	        	        	     	
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
        int newVersion) 
        {            
        	db.execSQL("drop table if exist " + TABLE_NEWS);
        	db.execSQL("drop table if exist " + TABLE_MEMORY);
        	db.execSQL("drop table if exist " + TABLE_CONTENT);
            onCreate(db);
        }
    }    
    
    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() 
    {
        DBHelper.close();
    }
    
    public boolean isOpened(){
    	return db.isOpen();
    }       
    
	public void pubdate_add(String pubDate, int mode) {
		ContentValues values = new ContentValues();
		values.put(KEY_ID, 1);
		switch (mode) {
		case 1: {
			values.put(KEY_NEWSPUBDATE, pubDate); // PDATE_NEWS
		}
			break;
		case 2: {
			values.put(KEY_MEMORYPUBDATE, pubDate); // PDATE_MEMORY
		}
			break;
		case 3: {
			values.put(KEY_ABOUTPUBDATE, pubDate); // PDATE_ABOUT
		}
			break;
		}

		Cursor cur = db.query(TABLE_PUBDATE, new String[] { KEY_ID,
				KEY_NEWSPUBDATE, KEY_MEMORYPUBDATE, KEY_ABOUTPUBDATE }, null, null, null,
				null, null);    			
    	int num = cur.getCount();
    	
		db.beginTransaction();
		try {
			if (num >= 1)
				db.updateWithOnConflict(TABLE_PUBDATE, values, null,
						null, SQLiteDatabase.CONFLICT_IGNORE);
			else {
				db.insertWithOnConflict(TABLE_PUBDATE, null, values,
						SQLiteDatabase.CONFLICT_REPLACE);
				}							
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
    	
    }
    
    public String[] getPubdate(){
		Cursor cur = db.query(TABLE_PUBDATE, new String[] { KEY_ID,
				KEY_NEWSPUBDATE, KEY_MEMORYPUBDATE, KEY_ABOUTPUBDATE }, null, null, null,
				null, null);    			
    	int num = cur.getCount();
    	String[] result = new String[3];
		if (num > 0) {			
			for (int i = 0; i < num; i++) {
				cur.moveToPosition(i);				
				result[0] = cur.getString(1);
				result[1] = cur.getString(2);
				result[2] = cur.getString(3);				
			}
		}
		cur.close();
		return result;
	}
    
    public void about_add(AboutModel about){
    	
		ContentValues values = new ContentValues();
		values.put(KEY_IMAGE, about.getImage());
		values.put(KEY_CONTENT1, about.getContent1());
		values.put(KEY_CONTENT2, about.getContent2());
		values.put(KEY_URLSHARE, about.getUrl_share());		
    	
    	db.beginTransaction();
    	try{    		   			
			db.insertWithOnConflict(TABLE_CONTENT, null, values,
					SQLiteDatabase.CONFLICT_REPLACE);	    			
			
    		db.setTransactionSuccessful();
    	} catch(Exception e){
    		e.printStackTrace();
    	} finally{
    		db.endTransaction();
    	}
    }    
    
    public AboutModel[] getAbout(){
		Cursor cur = db.query(TABLE_CONTENT, new String[] { KEY_IMAGE,
				KEY_CONTENT1, KEY_CONTENT2, KEY_URLSHARE }, null, null, null,
				null, null);    	
		AboutModel[] result = null;
    	int num = cur.getCount();
		if (num > 0) {
			result = new AboutModel[num];
			for (int i = 0; i < num; i++) {
				cur.moveToPosition(i);
				result[i] = new AboutModel();
				result[i].setImage(cur.getString(0));
				result[i].setContent1(cur.getString(1));
				result[i].setContent2(cur.getString(2));
				result[i].setUrl_share(cur.getString(3));
			}
		}
		cur.close();
		return result;
	}
    
    public void news_add(List<NewsModel> list){
    	    	
    	db.beginTransaction();    	
    	try{
    		for(NewsModel news: list){   		
    			if (news.getStatus().equals("DELETE")){
    				NewsModel delNews = getNewsById(news.getId());
    				// что бы не усложнять, удалим ненужные картинки прямо здесь 
					String fileNameIcon = Extras.GetMD5(delNews.getIcon());
					String fileNameImage = Extras.GetMD5(delNews.getImage());					
					context.deleteFile(fileNameIcon);
					context.deleteFile(fileNameImage);
					
    				String whereClause = KEY_ID + "=" + news.getId();
    				db.delete(TABLE_NEWS, whereClause, null);
    				}
				else
					db.insertWithOnConflict(TABLE_NEWS, null,
							get_news_content(news),
							SQLiteDatabase.CONFLICT_REPLACE);
			}
    		db.setTransactionSuccessful();
    	} catch(Exception e){
    		e.printStackTrace();
    	} finally{
    		db.endTransaction();
    	}
    }    
    
	private ContentValues get_news_content(NewsModel news) {		
		ContentValues values = new ContentValues();
		values.put(KEY_ID, news.getId());
		values.put(KEY_STATUS, news.getStatus());
		values.put(KEY_DATE, news.getDate());
		values.put(KEY_ORDER, news.getOrder());
		values.put(KEY_CATEGORY, news.getCategory());
		values.put(KEY_ICON, news.getIcon());
		values.put(KEY_IMAGE, news.getImage());
		values.put(KEY_SHORTCAPTION, news.getShortCaption());
		values.put(KEY_SHORTTEXT, news.getShortText());
		values.put(KEY_LONGCAPTION, news.getLongCaption());
		values.put(KEY_LONGTEXT, news.getLongText());
		values.put(KEY_URLSHARE, news.getUrl_share());
		values.put(KEY_URLVIDEO, news.getUrl_video());
		return values;
	}
	
	public NewsModel[] getNewsList(boolean modeSortList, int modeCategory) {
		String Order = null;
		if (modeSortList)
			Order = KEY_DATE + " DESC";
		else 
			Order = KEY_ORDER + " DESC";				
//		Huis/zwerfdieren
//		Rampen
//		Veehouderij
//		In het Wild
//		Algemeen
		String whereClause;
//		modeCategory++;
		if (modeCategory == 0)
			whereClause = null;
		else
			whereClause = KEY_CATEGORY + "=" + modeCategory;

		Cursor cur = db.query(TABLE_NEWS, new String[] { KEY_ID, KEY_STATUS,
				KEY_DATE, KEY_ORDER, KEY_CATEGORY, KEY_ICON, KEY_IMAGE,
				KEY_SHORTCAPTION, KEY_SHORTTEXT, KEY_LONGCAPTION, KEY_LONGTEXT,
				KEY_URLSHARE, KEY_URLVIDEO }, whereClause, null, null, null, Order);// KEY_TITLE

		NewsModel[] result = null;
		int num = cur.getCount();
		if (num > 0) {
			result = new NewsModel[num];
			for (int i = 0; i < num; i++) {
				cur.moveToPosition(i);
				result[i] = new NewsModel();
				result[i].setId(cur.getInt(0));
				result[i].setStatus(cur.getString(1));
				result[i].setDate(cur.getString(2));
				result[i].setOrder(cur.getInt(3));
				result[i].setCategory(cur.getInt(4));
				result[i].setIcon(cur.getString(5));
				result[i].setImage(cur.getString(6));
				result[i].setShortCaption(cur.getString(7));
				result[i].setShortText(cur.getString(8));
				result[i].setLongCaption(cur.getString(9));
				result[i].setLongText(cur.getString(10));
				result[i].setUrl_share(cur.getString(11));
				result[i].setUrl_video(cur.getString(12));
			}
		}
		cur.close();
		return result;
	}

	public NewsModel getNewsById(int id) {
		Cursor cur = db.query(TABLE_NEWS, new String[] { KEY_ID, KEY_STATUS,
				KEY_DATE, KEY_ORDER, KEY_CATEGORY, KEY_ICON, KEY_IMAGE,
				KEY_SHORTCAPTION, KEY_SHORTTEXT, KEY_LONGCAPTION, KEY_LONGTEXT,
				KEY_URLSHARE, KEY_URLVIDEO },
				KEY_ID + " = ?",
				new String[] { String.valueOf(id) }, null, null, null);
		if (!cur.moveToFirst()) {
			cur.close();
			return null;
		}
		NewsModel result = new NewsModel();
		result.setId(cur.getInt(0));
		result.setStatus(cur.getString(1));
		result.setDate(cur.getString(2));
		result.setOrder(cur.getInt(3));
		result.setCategory(cur.getInt(4));
		result.setIcon(cur.getString(5));
		result.setImage(cur.getString(6));
		result.setShortCaption(cur.getString(7));
		result.setShortText(cur.getString(8));
		result.setLongCaption(cur.getString(9));
		result.setLongText(cur.getString(10));
		result.setUrl_share(cur.getString(11));
		result.setUrl_video(cur.getString(12));
		cur.close();
		return result;
	}

//    public void news_add(List<NewsModel> list){
//    	
//    	db.beginTransaction();
//    	try{
//    		for(NewsModel news: list){   			
//    			db.insertWithOnConflict(TABLE_NEWS, null, get_news_content(news),
//    					SQLiteDatabase.CONFLICT_REPLACE);    			
//    		}
//    		db.setTransactionSuccessful();
//    	} catch(Exception e){
//    		e.printStackTrace();
//    	} finally{
//    		db.endTransaction();
//    	}
//    }  
	public void saveMemory(List<MemoryModel> list) {

		db.beginTransaction();
		try {
			for (MemoryModel memor : list)
				if (memor.getStatus().equals("DELETE")) {
					MemoryModel delMemor = getMemorById(memor.getId());
					// что бы не усложнять, удалим ненужные картинки прямо здесь
					String fileNameIcon = Extras.GetMD5(delMemor.getIcon());
					context.deleteFile(fileNameIcon);

					String whereClause = KEY_ID + "=" + memor.getId();
					db.delete(TABLE_MEMORY, whereClause, null);
				} else
					db.insertWithOnConflict(TABLE_MEMORY, null,
							get_memory_content(memor),
							SQLiteDatabase.CONFLICT_REPLACE);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}   
	
	public MemoryModel getMemorById(int id) {		
		
		Cursor cur = db.query(TABLE_MEMORY, new String[] { KEY_ID, KEY_STATUS,
				KEY_DATE, KEY_ICON }, KEY_ID + " = ?",
				new String[] { String.valueOf(id) }, null, null, null);
		if (!cur.moveToFirst()) {
			cur.close();
			return null;
		}
		MemoryModel result = new MemoryModel();
		result.setId(cur.getInt(0));
		result.setStatus(cur.getString(1));
		result.setDate(cur.getString(2));
		result.setIcon(cur.getString(3));
		cur.close();
		return result;
	}
		
	private ContentValues get_memory_content(MemoryModel memor) {		
		ContentValues values = new ContentValues();
		values.put(KEY_ID, memor.getId());
		values.put(KEY_STATUS, memor.getStatus());
		values.put(KEY_DATE, memor.getDate());
		values.put(KEY_ICON, memor.getIcon());
		return values;
	}	
	
	public MemoryModel[] getMemory() {
		Cursor cur = db.query(TABLE_MEMORY, new String[] { KEY_ID, KEY_STATUS,
				KEY_DATE, KEY_ICON }, null, null, null, null, null);
		MemoryModel[] result = null;
		int num = cur.getCount();
		if (num > 0) {
			result = new MemoryModel[num];
			for (int i = 0; i < num; i++) {
				cur.moveToPosition(i);
				result[i] = new MemoryModel();
				result[i].setId(cur.getInt(0));
				result[i].setStatus(cur.getString(1));
				result[i].setDate(cur.getString(2));
				result[i].setIcon(cur.getString(3));
			}
		}
		cur.close();
		return result;
	}	
	
//    public void saveAuth(AuthentModel auth){	
//    	
//		ContentValues values = new ContentValues();
//		values.put(KEY_AUTHOUT, auth.getAuth_outcome());
//		values.put(KEY_TRIES, auth.getTries());
//		values.put(KEY_EMAIL, auth.getEmail());
//		values.put(KEY_OPTIN, auth.getOptin());
//		values.put(KEY_ERROR, auth.getError());
//		values.put(KEY_REASON, auth.getReason());
//		values.put(KEY_CAPTION, auth.getCaption());
//		values.put(KEY_MESSAGE, auth.getMessage());
//    	
//    	db.beginTransaction();
//    	try{
//			db.insertWithOnConflict(TABLE_AUTH, null, values,
//					SQLiteDatabase.CONFLICT_REPLACE);	    		
//    		db.setTransactionSuccessful();
//    	} catch(Exception e){
//    		e.printStackTrace();
//    	} finally{
//    		db.endTransaction();
//    	}
//    }   
    
//    public AuthentModel[] getAuth(){
//		Cursor cur = db.query(TABLE_AUTH, new String[] { KEY_AUTHOUT,
//				KEY_TRIES, KEY_EMAIL, KEY_OPTIN, KEY_ERROR, KEY_REASON,
//				KEY_REASON, KEY_CAPTION, KEY_MESSAGE }, null, null, null, null,
//				null);
//		AuthentModel[] result = null;
//    	int num = cur.getCount();
//		if (num > 0) {
//			result = new AuthentModel[num];
//			for (int i = 0; i < num; i++) {
//				cur.moveToPosition(i);
//				result[i] = new AuthentModel();
//				result[i].setAuth_outcome(cur.getString(0));
//				result[i].setTries(cur.getInt(1));
//				result[i].setEmail(cur.getString(2));
//				//result[i].setOptin(cur.getString(3));
//				result[i].setError(cur.getString(4));
//				result[i].setReason(cur.getString(5));
//				result[i].setCaption(cur.getString(6));
//				result[i].setMessage(cur.getString(7));
//				 
//			}
//		}
//		cur.close();
//		return result;
//	}
    
    
    /**
     * returns the number of hotels in the DB
     * @return hotels number (0+)
     */
//    public int getHotelsCount(){
//    	return getItemsCount(TABLE_HOTELS);
//    }
    
    /**
     * count the number of entries in a specific table
     * @param table table name
     * @return number of entries
     */
    private int getItemsCount(String table){
    	int result = 0;
    	Cursor cur = db.rawQuery("select count(*) from " + table, null);
    	if(cur.moveToFirst()){
    		result = cur.getInt(0);
    	}    	
    	return result;
    }
   
 }

