package com.wspa.app.notification.adapters;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Extras {
	
	public static final String DATE_FORMAT_SAVE = "dd, MMMMMMM yyyy hh:mm aa";
	public static final String DATE_FORMAT_SHOW = "dd, MMMMMMM yyyy";
	
	public static final String GetMD5(String in){
		MessageDigest md5 ;        
        StringBuffer  result = new StringBuffer();
        
        try {
                                    
            md5 = MessageDigest.getInstance("md5");
            
            md5.reset();
            md5.update(in.getBytes()); 
                        
                        
            byte messageDigest[] = md5.digest();
                        
            for (int i = 0; i < messageDigest.length; i++) {
            	result.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
                                                                                        
        } 
        catch (NoSuchAlgorithmException e) {                        
            return e.toString();
        }
        
        return result.toString();
	}
	
}
