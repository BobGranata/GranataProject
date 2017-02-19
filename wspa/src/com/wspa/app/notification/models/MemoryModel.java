package com.wspa.app.notification.models;

public class MemoryModel {
	private static String pubDate;
	private int id;
	private String status;
	private String date;
	private String icon;

	public MemoryModel() {
	}

	public MemoryModel(int id, String status, String date, String icon) {
		this.id = id;
		this.status = status;
		this.date = date;
		this.icon = icon;
	}

	public void setPubDate(String pubDate){
    	this.pubDate = pubDate;
    }
    
    public String getPubDate(){
    	 return pubDate;
    }
	
	public void setId(int id) {
		this.id = id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public String getDate() {
		return date;
	}

	public String getIcon() {
		return icon;
	}
}
