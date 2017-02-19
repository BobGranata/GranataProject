package com.wspa.app.notification.models;

public class AuthentModel {
	String auth_outcome;
	int tries;
	String email;
	boolean optin;
	String error;
	String reason;
	String caption;
	String message;
	
    public AuthentModel() {}
	
    public AuthentModel(String auth_outcome, String email, boolean optin) {
        this.auth_outcome = auth_outcome;
        this.email = email;
        this.optin = optin;
    }
    public void setAuth_outcome(String auth_outcome){
    	this.auth_outcome = auth_outcome;
    }
    public void setTries(int tries){
    	this.tries = tries;
    }
    public void setEmail(String email){
    	this.email = email;
    }
    public void setOptin(boolean optin){
    	this.optin = optin;
    }
    public void setError(String error){
    	this.error = error;
    }
    public void setReason(String reason){
    	this.reason = reason;
    }       
    public void setCaption(String caption){
    	this.caption = caption;
    }
    public void setMessage(String message){
    	this.message = message;
    }   
	
    public String getAuth_outcome(){
    	return auth_outcome;
    }
    public int getTries(){
    	return tries;
    }
    public String getEmail(){
    	return email;
    }
    public boolean getOptin(){
    	return optin;
    }
    public String getError(){
    	return error;
    }
    public String getReason(){
    	return reason;
    }
    public String getCaption(){
    	return caption;
    }
    public String getMessage(){
    	return message;
	}
}