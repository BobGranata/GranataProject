package com.wspa.app.notification.models;

public class NotifyModel {
		private String pushId; 
		private String optin;
		private String categories;		
		private String sound;
		
	    public NotifyModel() {}
		
	    public void setPushId(String pushId){
	    	this.pushId = pushId;
	    }
	    public void setOptin(String optin){
	    	this.optin = optin;
	    }
	    public void setCategories(String categories){
	    	this.categories = categories;
	    }
	    public void setSound(String sound){
	    	this.sound = sound;
	    }
	 
		
	    public String getId(){
	    	return pushId;
	    }
	    public String getOptin(){
	    	return optin;
	    }
	    public String getCategories(){
	    	return categories;
	    }
	    public String getSound(){
	    	return sound;
	    }
}