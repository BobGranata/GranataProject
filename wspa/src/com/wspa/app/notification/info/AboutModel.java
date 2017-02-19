package com.wspa.app.notification.info;

public class AboutModel {
	private static String pubDate;
	private String image;
	private String content1;
	private String content2;
	private String url_share;	
	
	public AboutModel() {}	
	
	public void setPubDate(String pubDate){
    	this.pubDate = pubDate;
    }
    
    public String getPubDate(){
    	 return pubDate;
    }
    
    public void setImage(String image){
    	this.image = image;
    }
    public void setContent1(String content1){
    	this.content1 = content1;
    }
    public void setContent2(String content2){
    	this.content2 = content2;
    }
    public void setUrl_share(String url_share){
    	this.url_share = url_share;
    }
	
    public String getImage(){
    	return image;
    }
    public String getContent1(){
    	return content1;
    }
    public String getContent2(){
    	return content2;
    }   
    public String getUrl_share(){
    	return url_share;
    }
}
