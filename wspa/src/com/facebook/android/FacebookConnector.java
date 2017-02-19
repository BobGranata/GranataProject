package com.facebook.android;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.wspa.app.notification.PostStruct;
import com.wspa.app.notification.R;
import com.wspa.app.notification.interfaces.FbConnectorListener;

public class FacebookConnector implements DialogListener {
	
	private String app_id;
	Facebook facebook;
	AsyncFacebookRunner asyncFacebook;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs = null;
    Activity con;
    FbConnectorListener listener;
    
    Runnable logouter = null;
    
    public FacebookConnector(Activity context, FbConnectorListener fbListener){
    	con = context;
    	app_id = con.getString(R.string.FB_API_KEY);
    	facebook = new Facebook(app_id);
    	asyncFacebook = new AsyncFacebookRunner(facebook);
    	listener = fbListener;
    }
    
    public void authorizeCallback(int requestCode, int resultCode, Intent data){
    	facebook.authorizeCallback(requestCode, resultCode, data);
    }
    
    private SharedPreferences getPrefs(){
		if(mPrefs == null) mPrefs = con.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		
		return mPrefs;
	}
    
    public void feedPost(PostStruct Post, RequestListener listener){
    	Bundle parameters = new Bundle();
    	if (Post.Caption != null )parameters.putString("caption", Post.Caption);
    	if (Post.ShortText != null) parameters.putString("message", Post.ShortText);
    	if (Post.ImageUrl != null) parameters.putString("picture", Post.ImageUrl);
    	if (Post.PostUrl != null) parameters.putString("link", Post.PostUrl);
    	asyncFacebook.request("me/feed", parameters, "POST", listener, "");
    }
    
    public void authorize(){
        if(this.isLogged()){
        	//this.showMessage("You are already logged in");
        } else {
        	facebook.authorize(con, new String[]{"publish_stream"}, Facebook.FORCE_DIALOG_AUTH, this);
        }
    }
    
    public void logout(){
    	SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString("access_token", null);
        editor.putLong("access_expires", 0);
        editor.commit();
        
        logouter = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String result = facebook.logout(con);
					if("true".equals(result)){
						if(listener != null) listener.onLogOut(false);
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					if(listener != null) listener.onLogOut(true);
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					if(listener != null) listener.onLogOut(true);
					e.printStackTrace();
				}
			}
		};
		
		Handler hand = new Handler(con.getMainLooper());
		hand.post(logouter);
    }
    
    public boolean isLogged(){
    	String access_token = getPrefs().getString("access_token", null);
        long expires = getPrefs().getLong("access_expires", 0);
        if(access_token != null) {
            facebook.setAccessToken(access_token);
        }
        if(expires != 0) {
            facebook.setAccessExpires(expires);
        }
        
        return facebook.isSessionValid();
    }

	@Override
	public void onComplete(Bundle values) {
		SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString("access_token", facebook.getAccessToken());
        editor.putLong("access_expires", facebook.getAccessExpires());
        editor.commit();
        
        if(listener != null) listener.onLogIn(false);
	}

	@Override
	public void onFacebookError(FacebookError e) {
		if(listener != null) listener.onLogIn(true);
	}

	@Override
	public void onError(DialogError e) {
		if(listener != null) listener.onLogIn(true);
	}

	@Override
	public void onCancel() {
		if(listener != null) listener.onLogIn(true);
	}
	
	private void showMessage(String text){
		Toast.makeText(con, text, Toast.LENGTH_SHORT).show();
	}

}
