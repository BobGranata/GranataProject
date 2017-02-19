package com.wspa.app.notification.models;

public class MessageModel {
	private String sound;
	private String alert;

	public MessageModel() {
	}

	public String getSound() {
		return sound;
	}

	public int GetSound() {
		try {
			return (Integer.parseInt(String.valueOf(sound.charAt(0))));
		} catch (Exception e) {
			return 0;
		}
	}

	public String getAlert() {
		return alert;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}
}
