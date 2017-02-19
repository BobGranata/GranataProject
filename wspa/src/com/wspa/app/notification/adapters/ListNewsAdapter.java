package com.wspa.app.notification.adapters;

import com.wspa.app.notification.content.ImageGetter;
import com.wspa.app.notification.extras.DataConsts;
import com.wspa.app.notification.extras.ParseData;
import com.wspa.app.notification.news.NewsModel;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import com.wspa.app.notification.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListNewsAdapter extends ArrayAdapter<NewsModel> {
	private final Context context;
	private final ArrayList<NewsModel> values;
	private String urlVideo;

	public ListNewsAdapter(Context context, ArrayList<NewsModel> values) {
		super(context, R.layout.news_list_item, values);
		this.context = context;
		this.values = values;
	}
	
    // Класс для сохранения во внешний класс и для ограничения доступа
    // из потомков класса
    static class ViewHolder {
        public ImageView imageViewBg;
        public ImageView imageView;
        public TextView titleNews;
        public TextView textNews;
        public TextView dateNews;     
        
		public RelativeLayout itemLay;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHolder буферизирует оценку различных полей шаблона элемента

        ViewHolder holder;
        // Очищает сущетсвующий шаблон, если параметр задан
        // Работает только если базовый шаблон для всех классов один и тот же
        View rowView = convertView;
        if (rowView == null) {
        	LayoutInflater inflater = (LayoutInflater) context
    				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	rowView = inflater.inflate(R.layout.news_list_item, null, true);
            holder = new ViewHolder();
            holder.titleNews = (TextView) rowView.findViewById(R.id.titleNewsItemTV);
            holder.textNews = (TextView) rowView.findViewById(R.id.shortTextItemNewsTV);
            holder.dateNews = (TextView) rowView.findViewById(R.id.dateTV);
            holder.imageViewBg = (ImageView) rowView.findViewById(R.id.newsItemBgIV);
            holder.imageView = (ImageView) rowView.findViewById(R.id.newsItemIV);
            holder.itemLay = (RelativeLayout) rowView.findViewById(R.id.newsItemRLay);    
            
            Typeface regular = Typeface.createFromAsset(context.getAssets(), "fonts/arial.ttf");
    		holder.textNews.setTypeface(regular);
    		holder.dateNews.setTypeface(regular);
    		holder.titleNews.setTypeface(regular);
            
            rowView.setTag(holder);
        } else {
        	holder = (ViewHolder) rowView.getTag();
        }
        
        holder.titleNews.setText(values.get(position).getShortCaption());
        holder.textNews.setText(values.get(position).getShortText());       
        
		urlVideo = values.get(position).getUrl_video();

		String coolDate = ParseData.getDateNews(values.get(position).getDate());
		holder.dateNews.setText(coolDate);				
		
		FileInputStream fis = null;
		FileDescriptor fd = null;
		String Filename = Extras.GetMD5(values.get(position).getIcon());
		try {
			fis = context.openFileInput(Filename);
			fd = fis.getFD();
		} catch (Exception fileException) {
			new ImageGetter(context, holder.imageView, holder.imageViewBg).execute(new String[] { values
					.get(position).getIcon() });
		}
		if (fd != null) {
			try {
				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFileDescriptor(fd, null, opts);
				holder.imageView.setImageBitmap(BitmapFactory.decodeFileDescriptor(fd));
				holder.imageView.setTag(Filename);
				holder.imageViewBg.setVisibility(View.VISIBLE);
				fis.close();
			} catch (Exception e) {
			} // Image can be very large
		}

		if (position % 2 == 1) {
			holder.itemLay.setBackgroundResource(R.drawable.news_item_bg);
		} else {
			holder.itemLay.setBackgroundResource(R.drawable.news_item2_bg);
		}
		
		holder.imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (urlVideo.length() > 0) {
					Uri address = Uri.parse(urlVideo);
					Intent openlink = new Intent(Intent.ACTION_VIEW, address);
					context.startActivity(openlink);
				}
			}
		});

//=============================================================================================        
//		LayoutInflater inflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View rowView = inflater.inflate(R.layout.news_list_item, parent, false);
//		TextView titleNews = (TextView) rowView
//				.findViewById(R.id.titleNewsItemTV);
//		TextView textNews = (TextView) rowView
//				.findViewById(R.id.shortTextItemNewsTV);
//		TextView dateNews = (TextView) rowView.findViewById(R.id.dateTV);
//
//		TypeSet ts = new TypeSet(context);
//		textNews = ts.SetTF(textNews, DataConsts.ARIAL_REGULAR);
//		dateNews = ts.SetTF(dateNews, DataConsts.ARIAL_REGULAR);
//		titleNews = ts.SetTF(titleNews, DataConsts.ARIAL_REGULAR);
//
//		ImageView imageViewBg = (ImageView) rowView.findViewById(R.id.newsItemBgIV);
//		ImageView imageView = (ImageView) rowView.findViewById(R.id.newsItemIV);		
//		RelativeLayout itemLay = (RelativeLayout) rowView
//				.findViewById(R.id.newsItemRLay);
//
//		titleNews.setText(values.get(position).getShortCaption());
//		textNews.setText(values.get(position).getShortText());

//		urlVideo = values.get(position).getUrl_video();
//
//		String coolDate = ParseData.getDateNews(values.get(position).getDate());
//		dateNews.setText(coolDate);		
//
//		FileInputStream fis = null;
//		FileDescriptor fd = null;
//		String Filename = Extras.GetMD5(values.get(position).getIcon());
//		try {
//			fis = context.openFileInput(Filename);
//			fd = fis.getFD();
//		} catch (Exception fileException) {
//			new ImageGetter(context, imageView, imageViewBg).execute(new String[] { values
//					.get(position).getIcon() });
//		}
//		if (fd != null) {
//			try {
//				BitmapFactory.Options opts = new BitmapFactory.Options();
//				opts.inJustDecodeBounds = true;
//				BitmapFactory.decodeFileDescriptor(fd, null, opts);
//				// System.out.println("w=" + opts.outWidth + "; h=" +
//				// opts.outHeight);
//				// if(opts.outWidth > 800 || opts.outHeight > 800) throw new
//				// Exception();
//				imageView.setImageBitmap(BitmapFactory.decodeFileDescriptor(fd));
//				imageView.setTag(Filename);
//				imageViewBg.setVisibility(View.VISIBLE);
//				fis.close();
//			} catch (Exception e) {
//			} // Image can be very large
//		}
//
//		if (position % 2 == 1) {
//			itemLay.setBackgroundResource(R.drawable.news_item_bg);
//		} else {
//			itemLay.setBackgroundResource(R.drawable.news_item2_bg);
//		}
//
//		imageView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (urlVideo.length() > 0) {
//					ImageView tw = (ImageView) v
//							.findViewById(R.id.imageViewNewsDetails);
//					Uri address = Uri
//							.parse(urlVideo);
//					Intent openlink = new Intent(Intent.ACTION_VIEW, address);
//					context.startActivity(openlink);
//				}
//			}
//		});
		return rowView;
	}
}
