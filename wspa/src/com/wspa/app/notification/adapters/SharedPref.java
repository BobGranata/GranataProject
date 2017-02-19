package com.wspa.app.notification.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.wspa.app.notification.R;
import com.wspa.app.notification.extras.SettingsConsts;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPref implements Comparator<Float> {

	public SharedPreferences sp;

	@SuppressWarnings("static-access")
	public SharedPref(Context context, String FileName) {
		// TODO Auto-generated constructor stub
		sp = context.getSharedPreferences(FileName, context.MODE_PRIVATE);
	}

	public Boolean IsRegistered() {
		if (sp.contains(SettingsConsts.IS_REGISTERED)) {
			return sp.getBoolean(SettingsConsts.IS_REGISTERED, false);
		} else {
			Editor editor = sp.edit();
			editor.putBoolean(SettingsConsts.IS_REGISTERED, false);
			editor.commit();
			return false;
		}
	}// private Boolean IsRegistered()

	public float[] ReadGameResults() {
		float[] Results = new float[10];
		for (int i = 0; i < 10; i++) {
			if (sp.contains(SettingsConsts.GAME_RESULT + String.valueOf(i + 1))) {
				Results[i] = sp.getFloat(
						(SettingsConsts.GAME_RESULT + String.valueOf(i + 1)),
						-1);
			} else {
				Results[i] = -1;
			}
		}
		return Results;
	}

	public ArrayList<Float> GetGameResults() {
		ArrayList<Float> Results = new ArrayList<Float>();
		float[] TempResults = ReadGameResults();
		for (int i = 0; i < 10; i++)
			if (!(TempResults[i] < 0))
				Results.add(TempResults[i]);
		return Results;
	}

	public void SaveResult(float NewResult) {
		Editor editor = sp.edit();
		ArrayList<Float> Results = GetGameResults();
		Results.add(NewResult);
		Collections.sort(Results, this);
		for (int i = 0; i < Results.size(); i++) {
			if (i == 10)
				break;
			editor.putFloat(
					(SettingsConsts.GAME_RESULT + String.valueOf(i + 1)),
					Results.get(i));
		}
		editor.commit();
	}// public void SaveResult(float NewResult

	@Override
	public int compare(Float lhs, Float rhs) {
		// TODO Auto-generated method stub
		return (rhs.compareTo(lhs));
	}
	
	public Boolean ReadBoolValue(String Name){
		if (sp.contains(Name)) {
			Boolean result = false;
			try{
				result = sp.getBoolean(Name, true);
			}catch(Exception e){ }
			return result;
		} else {
			Editor editor = sp.edit();
			editor.putBoolean(Name, true);
			editor.commit();
		}
		return true;
	}
		
	public void SetBoolValue(String Name, Boolean Value){
		Editor editor = sp.edit();
		editor.putBoolean(Name, Value);
		editor.commit();
	}
	
	public int ReadIntValue(String Name){
		if (sp.contains(Name)) {
			return sp.getInt(Name, 1);
		} else {
			Editor editor = sp.edit();
			editor.putInt(Name, 1);
			editor.commit();
		}
		return 1;
	}
	
	public void SetIntValue(String Name, int Value){
		Editor editor = sp.edit();
		editor.putInt(Name, Value);
		editor.commit();
	}
	
	public String ReadStringValue(String Name){
		if (sp.contains(Name)) {
			return sp.getString(Name, "");
		} else {
			Editor editor = sp.edit();
			editor.putString(Name, "");
			editor.commit();
		}
		return "";
	}
	
	public void SetStringValue(String Name, String Value){
		Editor editor = sp.edit();
		editor.putString(Name, Value);
		editor.commit();
	}
	
	public Long ReadLongValue(String Name){
		if (sp.contains(Name)) {
			return sp.getLong(Name, 0);
		} else {
			Editor editor = sp.edit();
			editor.putLong(Name, 0);
			editor.commit();
		}
		return 0l;
	}
	
	public void SetLongValue(String Name, Long Value){
		Editor editor = sp.edit();
		editor.putLong(Name, Value);
		editor.commit();
	}
}