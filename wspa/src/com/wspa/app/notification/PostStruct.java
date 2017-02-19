package com.wspa.app.notification;

public class PostStruct {
	public String Caption;
	public String PostUrl;
	public String ImageUrl;
	public String ShortText;
	
	public String PreviewText(){
		String Result ="";
		if(ShortText != null){
			Result += ShortText + " ";
		}
		if(Caption != null){
			Result += Caption + " ";
		}
		if(PostUrl != null){
			Result += PostUrl;
		}
		return Result;
	}
}
