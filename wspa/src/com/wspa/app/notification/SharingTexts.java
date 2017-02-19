package com.wspa.app.notification;

import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.news.NewsModel;

public class SharingTexts {

	public String GetTextGameScrore() {
		String Result = "";
		return Result;
	}

	public String GetTwitterWspaInfo() {
		return "Ben jij net als ik dierenliefhebber? Blijf dan op de hoogte via de WSPA World Animal News app! Download hier. http://welkombijwspa.nl/app";
	}

	public PostStruct GetText(int WhereSharing, int WhatSharing,
			NewsModel News, String GameScore) {
		PostStruct Result = new PostStruct();
		switch (WhereSharing) {
		case DataConsts.SHARE_FB: {
			switch (WhatSharing) {
			case DataConsts.SHARE_INFO: {
				Result.ShortText = "Wil jij ook alles weten over de wereldwijde strijd van WSPA tegen dierenleed? Download hier de WSPA World Animal News app (gratis voor donateurs)";
				Result.ImageUrl = "http://aapn.org/wp-content/themes/directorypress/thumbs/wspa-logo-300x300.jpg";
				Result.PostUrl = "http://welkombijwspa.nl/app";
				return Result;
			}
			case DataConsts.SHARE_NEWS_DETAILS: {
				Result.Caption = News.getShortText();
				Result.PostUrl = News.getUrl_share();
				Result.ImageUrl = News.getImage();
				Result.ShortText = News.getLongCaption();
				return Result;
			}
			case DataConsts.SHARE_GAME_SCORE: {
				Result.Caption = "Ik heb "
						+ GameScore
						+ " punten gehaald met Animal Memory via de WSPA app. Speel ook mee via de World Animal News app.";
				Result.PostUrl = "http://welkombijwspa.nl/app";
				Result.ImageUrl = "http://aapn.org/wp-content/themes/directorypress/thumbs/wspa-logo-300x300.jpg";
				return Result;
			}
			}// switch(WhatSharing) FACEBOOK
			break;
		}
		case DataConsts.SHARE_TW: {
			switch (WhatSharing) {
			case DataConsts.SHARE_INFO: {
				Result.Caption = "Ben jij net als ik dierenliefhebber? Blijf dan op de hoogte via de WSPA World Animal News app! Download hier. http://welkombijwspa.nl/app";
				return Result;
			}
			case DataConsts.SHARE_NEWS_DETAILS: {
				Result.Caption = News.getShortCaption() + " "
						+ News.getUrl_share();
				return Result;
			}
			case DataConsts.SHARE_GAME_SCORE: {
				Result.Caption = "Ik heb "
						+ GameScore
						+ " punten gehaald met Animal Memory via de WSPA app. Speel ook mee via de World Animal News app. http://welkombijwspa.nl/app";
				return Result;
			}
			}// switch(WhatSharing) TWITTER
			break;
		}
		}// switch(WhereSharing)
		return null;
	}// public PostStruct GetText

	public String GetUrlForGPlus(int WhatSharing, NewsModel News,
			String GameScore) {
		switch (WhatSharing) {
		case DataConsts.SHARE_INFO: {
			return "https://plus.google.com/share?client_id=755725993184.apps.googleusercontent.com&continue=com.wspa.app%3A%2F%2Fshare%2F&text=Wil%20jij%20ook%20alles%20weten%20over%20de%20wereldwijde%20strijd%20van%20WSPA%20tegen%20dierenleed%3F%20Download%20hier%20de%20WSPA%20World%20Animal%20News%20app%20%28gratis%20voor%20donateurs%29%20http%3A%2F%2Fwelkombijwspa.nl%2Fapp&bundle_id=com.wspa.app&gpsdk=1.0.0";
		}
		case DataConsts.SHARE_NEWS_DETAILS: {
			return "https://plus.google.com/share?client_id=755725993184.apps.googleusercontent.com&continue=com.wspa.app%3A%2F%2Fshare%2F&url="
					+ CodingUrl(News.getUrl_share()) + "&bundle_id=com.wspa.app&gpsdk=1.0.0";
		}
		case DataConsts.SHARE_GAME_SCORE: {
			return "https://plus.google.com/share?client_id=755725993184.apps.googleusercontent.com&continue=com.wspa.app%3A%2F%2Fshare%2F&text="
					+ "Ik%20heb%20" + GameScore.toString() + "%20punten%20gehaald%20met%20Animal%20Memory%20via%20de%20WSPA%20app.%20Speel%20ook%20mee%20via%20de%20World%20Animal%20News%20app."
					+ "%20http%3A%2F%2Fwelkombijwspa.nl%2Fapp&bundle_id=com.wspa.app&gpsdk=1.0.0";
		}
		default:
			return "";
		}// switch(WhatSharing)
	}
	
	private String CodingUrl(String url){
		String Result = "";
		for(int i = 0; i < url.length(); i++) {
			if(url.charAt(i) == ' ') Result += "%20";
			else if(url.charAt(i) == '/') Result += "%2F";
			else if(url.charAt(i) == ':') Result += "%3A";
			else Result+=url.charAt(i);
		}
		return Result;
	}
	
	
}
