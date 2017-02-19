package com.wspa.app.notification.memory;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.wspa.app.notification.adapters.Extras;
import com.wspa.app.notification.adapters.ProgressDialogPublic;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Pair;

//AsyncTask параметризуется:
//входной параметр - строки, представляют url
//параметр прогресса - пара вида: позиция виджета+изоборажение для него
//параметр результат - Void, он нам не нужен
class MemoryImageGetter extends AsyncTask<String, Pair<Integer, Bitmap>, Void> {
	Context mContext;

	public MemoryImageGetter(Context context) {
		mContext = context;
		ProgressDialogPublic.ShowProgressDialog(mContext);
	}

	@Override
	protected Void doInBackground(String... urls) {
		for (int i = 0; i < urls.length; i++) {
			ContextWrapper cw = new ContextWrapper(mContext);
			String mFilename = Extras.GetMD5(urls[i]);
			if (mFilename == null)
				return null;
			// if(mFilename.equals(mView.getTag())) return null;

			FileInputStream fis = null;
			FileDescriptor fd = null;

			try {
				fis = cw.openFileInput(mFilename);
				fd = fis.getFD();
			} catch (Exception fileException) {
				try {
					URLConnection connection = new URL(urls[i])
							.openConnection();
					FileOutputStream fos = cw.openFileOutput(mFilename,
							Context.MODE_PRIVATE);
					InputStream is = null;
					is = connection.getInputStream();

					byte[] buffer = new byte[1024];
					int bytesRead = 0;
					while ((bytesRead = is.read(buffer, 0, buffer.length)) != -1) {
						fos.write(buffer, 0, bytesRead);
					}
					fos.close();
					is.close();
					fis = cw.openFileInput(mFilename);
					fd = fis.getFD();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Pair... progress) {
		// int position = progress[0].first;
		// int currentProgress = position+1;
		// Bitmap image = progress[0].second;
		// progressBar.setProgress(currentProgress);
		// imageViews.get(position).setImageBitmap(image);
	}

	@Override
	protected void onPostExecute(Void result) {
		ProgressDialogPublic.RemoveProgressDialog();	
	}

//	private Bitmap getImageByUrl(String url) throws IOException,
//			MalformedURLException {
//		// Вот так можно получить изображение по url
//		// Bitmap image = BitmapFactory.depreStream((InputStream)new
//		// URL(url).getContent());
//		Bitmap image = null;
//		return image;
//	}
}
