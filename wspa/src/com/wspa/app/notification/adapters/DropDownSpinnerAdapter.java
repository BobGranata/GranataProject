package com.wspa.app.notification.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DropDownSpinnerAdapter extends ArrayAdapter {

	private Context context;
	private int textViewResourceId;
	private String[] objects;
	public static boolean flag = false;

	public DropDownSpinnerAdapter(Context context, int textViewResourceId,
			String[] objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = View.inflate(context, textViewResourceId, null);
		if (flag != false) {
			TextView tv = (TextView) convertView;
			
//		    <string-array name="news_cetegory_list">
//	        <item>Alle categorie¸n</item>
//	        <item>Algemeen</item>
//	        <item>Huis- &amp; zwerfdieren</item>
//	        <item>Dieren in het wild</item>
//	        <item>Dieren in rampen</item>
//	        <item>Dieren in de landbouw</item>
//	    </string-array>
	//    
//	    <string-array name="news_cetegory_list_cut">
//	        <item>Alle categorie¸n</item>
//	        <item>Algemeen</item>
//	        <item>Huis- &amp; zwerf..</item>
//	        <item>Dieren..het wild</item>
//	        <item>Dieren..rampen</item>
//	        <item>Dieren..dbouw</item>
//	    </string-array>
			if (objects[position].equals("Huis- & zwerfdieren"))
				tv.setText("Huis- & zwerf...");
			else if (objects[position].equals("Dieren in het wild"))
				tv.setText("Dieren...het wild");
			else if (objects[position].equals("Dieren in de landbouw"))
				tv.setText("Dieren...dbouw");
			else if (objects[position].equals("Dieren in rampen"))
				tv.setText("Dieren..rampen");
			else
				tv.setText(objects[position]);
		} else {
			TextView tv = (TextView) convertView;
			tv.setText("Alle categorie¸n");
		}
		return convertView;
	}
}
