package com.wspa.app.notification.interfaces;

public interface FbConnectorListener {
	
	abstract void onLogIn(boolean error);
	abstract void onLogOut(boolean error);
	
}
