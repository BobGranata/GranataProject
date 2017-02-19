package com.wspa.app.notification.extras;

import java.util.Calendar;

public class ParseData {
	static public String InDotStringLine(float score){		
		StringBuffer dotScore  = new StringBuffer();
		dotScore.append(String.valueOf(score));
		dotScore.delete(dotScore.length()-2, dotScore.length());
		
		int countDot = 0;
		if (dotScore.length() > 0)
			countDot = (dotScore.length()-1)/3;		
		for (int i = 1; i <= countDot; i++)
			if (i == 1)
				dotScore.insert(dotScore.length()-3*i, ".");
			else
				dotScore.insert(dotScore.length()-(3*i+1), ".");
			
		return dotScore.toString();
	}
	
	static public String getDateNews(String Date){
		String coolDate = null;
		String[] namesOfDays = new String[7];//хранит названия дней
		
		namesOfDays[0] = "MA"; //maandag
		namesOfDays[1] = "DI"; //dinsdag
		namesOfDays[2] = "WO"; //woensdag
		namesOfDays[3] = "DO"; //donderdag
		namesOfDays[4] = "VR"; //vrijdag
		namesOfDays[5] = "ZA"; //zaterdag
		namesOfDays[6] = "ZO"; //zondag
		
		String[] namesOfMonth = {"JANUARI", "FEBRUARI", "MAART", "APRIL", "MEI", "JUNI",
				"JULI", "AUGUSTUS", "SEPTEMBER", "OKTOBER", "NOVEMBER", "DECEMBER"} ;
	    
		int year = Integer.parseInt(Date.substring(0, 4));
		int month = Integer.parseInt(Date.substring(4, 6));
		int day = Integer.parseInt(Date.substring(6, 8));
		
//		Januari Februari Maart April Mei Juni Juli Augustus September Oktober November December
		
	    Calendar c = Calendar.getInstance();
	    c.set(year, month, day);	    
	    int dow = c.get(Calendar.DAY_OF_WEEK);
	    
	    String coolDayOfWeek = null;
	    if (dow == 2)
	    	coolDayOfWeek = namesOfDays[0];
	    if (dow == 3)
	    	coolDayOfWeek = namesOfDays[1];
	    if (dow == 4)
	    	coolDayOfWeek = namesOfDays[2];
	    if (dow == 5)
	    	coolDayOfWeek = namesOfDays[3];
	    if (dow == 6)
	    	coolDayOfWeek = namesOfDays[4];
	    if (dow == 7)
	    	coolDayOfWeek = namesOfDays[5];
	    if (dow == 1)
	    	coolDayOfWeek = namesOfDays[6];
	    
	    String coolMonth = null;
	    for (int i = 0; i <= 11; i++)
	    	if(i+1 == month){
	    		coolMonth = namesOfMonth[i];
	    		break;
	    		}	 
		
	    coolDate = coolDayOfWeek + " " + day + " " + coolMonth;
		return coolDate;
	}
}
