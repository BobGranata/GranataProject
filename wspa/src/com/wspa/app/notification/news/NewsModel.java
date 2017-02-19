package com.wspa.app.notification.news;

import java.util.Date;

import android.widget.SlidingDrawer;


public class NewsModel {
	private static String pubDate;
	private int id; 
	private String status;
	private String date;
	private int order;
	private int category;
	private String icon;
	private String image;
	private String shortCaption;
	private String shortText;
	private String longCaption;
	private String longText;
	private String url_share;
	private String url_video;
	
    public NewsModel() {}
	
    public NewsModel(int id, String status, String image, String shortCaption, String shortText, String longText) {
        this.id = id;
        this.status = status;
        this.image = image;
        this.shortCaption = shortCaption;
        this.shortText = shortText;
        this.longText = longText;
    }
    
    public void setPubDate(String pubDate){
    	this.pubDate = pubDate;
    }
    
    public String getPubDate(){
    	 return pubDate;
    }
    
    public void setId(int id){
    	this.id = id;
    }
    public void setStatus(String status){
    	this.status = status;
    }
    public void setDate(String date){
    	this.date = date;
    }
    public void setOrder(int order){
    	this.order = order;
    }
    public void setCategory(int category){
    	this.category = category;
    }
    public void setIcon(String icon){
    	this.icon = icon;
    }       
    public void setImage(String image){
    	this.image = image;
    }
    public void setShortCaption(String shortCaption){
    	this.shortCaption = shortCaption;
    }
    public void setShortText(String shortText){
    	//this.shortText = shortText;
    	this.shortText = shortText.substring(0, 100);
    }
    public void setLongCaption(String longCaption){
    	this.longCaption = longCaption;
    }
    public void setLongText(String longText){
    	this.longText = longText;
    }
    public void setUrl_share(String url_share){
    	this.url_share = url_share;
    }
    public void setUrl_video(String url_video){
    	this.url_video = url_video;
    }    
	
    public int getId(){
    	return id;
    }
    public String getStatus(){
    	return status;
    }
    public String getDate(){
    	return date;
    }
    public int getOrder(){
    	return order;
    }
    public int getCategory(){
    	return category;
    }
    public String getIcon(){
    	return icon;
    }
    public String getImage(){
    	return image;
    }
    public String getShortCaption(){
    	return shortCaption;
    }
    public String getShortText(){
    	return shortText;
    }   
    public String getLongCaption(){
    	return longCaption;
    }
    public String getLongText(){
    	return longText;
    }
    public String getUrl_share(){
    	return url_share;
    }
    public String getUrl_video(){
    	return url_video;
    }   
}
