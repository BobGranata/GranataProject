package com.wspa.app.notification.content;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.wspa.app.notification.adapters.Extras;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

public class ImageGetter extends AsyncTask<String, Void, Bitmap>{
	
	ImageView mView, mBgView;
	ImageView mYouTubeIcon_view = null;
	Context mContext;
	String mFilename = null;
	
	public ImageGetter(Context context, ImageView view, ImageView bg_view){
		mBgView = bg_view;
		mView = view;
		mContext = context;
	}
	
	public ImageGetter(Context context, ImageView view, ImageView bg_view, ImageView youTubeIcon_view){
		mBgView = bg_view;
		mView = view;
		mYouTubeIcon_view = youTubeIcon_view;
		mContext = context;
	}
	
	
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if(result != null){
			mView.setImageBitmap(result);
			mBgView.setVisibility(View.VISIBLE);
			if (mYouTubeIcon_view != null)
				mYouTubeIcon_view.setVisibility(View.VISIBLE);
			}
	}
	
		
	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap result = null;
		
		ContextWrapper cw = new ContextWrapper(mContext);
		mFilename = Extras.GetMD5(params[0]);
		if(mFilename == null) return null;
		if(mFilename.equals(mView.getTag())) return null;
		
		FileInputStream fis = null;
		FileDescriptor fd = null;
		
		try {
			fis = cw.openFileInput(mFilename);
			fd = fis.getFD();
		} catch (Exception fileException) {
			try{
				URLConnection connection = new URL(params[0]).openConnection();
				FileOutputStream fos = cw.openFileOutput(mFilename, Context.MODE_PRIVATE);
				InputStream is = null;
				is = connection.getInputStream();
				
				byte[] buffer = new byte[1024];
				int bytesRead = 0;
				while((bytesRead = is.read(buffer, 0, buffer.length)) != -1){
					fos.write(buffer, 0, bytesRead);
				}
				fos.close();
				is.close();
				fis = cw.openFileInput(mFilename);
				fd = fis.getFD();
			} catch (Exception e){
				e.printStackTrace();
			} 
			
		}
				
		if(fd != null){
			try{
				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFileDescriptor(fd, null, opts);
				System.out.println("w=" + opts.outWidth + "; h=" + opts.outHeight);
				if(opts.outWidth > 800 || opts.outHeight > 800) throw new Exception();
				result = BitmapFactory.decodeFileDescriptor(fd);
				mView.setTag(mFilename);
				fis.close();
			} catch(Exception e){} //Image can be very large
		}
		
		return result;
	}
	
}