package com.twitter.android;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.twitter.android.TwitterApp.TwDialogListener;
import com.wspa.app.notification.PostStruct;
import com.wspa.app.notification.R;

public class Twitt {

	private TwitterApp mTwitter;
	private Activity activity;
	private String twitt_msg;

	public Twitt(Activity act, String consumer_key, String consumer_secret) {
		this.activity = act;
		mTwitter = new TwitterApp(activity, consumer_key, consumer_secret);
	}

	public void shareToTwitter(PostStruct msg) {
		this.twitt_msg = msg.Caption;
		mTwitter.setListener(mTwLoginDialogListener);

		if (mTwitter.hasAccessToken()) {
			showTwittDialog();
		} else {
			mTwitter.authorize();
		}
	}

	private void showTwittDialog() {

		final Dialog dialog = new Dialog(activity);
		dialog.setContentView(R.layout.twitt_dialog);
		dialog.setTitle("Twitter");

		Button btnPost = (Button) dialog.findViewById(R.id.btnpost);
		final EditText et = (EditText) dialog.findViewById(R.id.twittext);
		et.setText(twitt_msg);
		et.setSelection(et.getText().length());
		btnPost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				twitt_msg = et.getText().toString().trim();

				if (twitt_msg.length() == 0) {
					showToast("Twitt is leeg!!!");
					return;
				} else if (twitt_msg.length() > 140) {
					showToast("Twitt is meer dan 140 tekens niet toegestaan!!!");
					return;
				}
				dialog.dismiss();
				new PostTwittTask().execute(twitt_msg);

			}

		});

		dialog.show();

	}

	private TwDialogListener mTwLoginDialogListener = new TwDialogListener() {

		public void onError(String value) {
			showToast("inloggen mislukt");
			mTwitter.resetAccessToken();
		}

		public void onComplete(String value) {
			showTwittDialog();
		}
	};

	void showToast(final String msg) {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

			}
		});

	}

	class PostTwittTask extends AsyncTask<String, Void, String> {
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(activity);
			pDialog.setMessage("Posting Twitt...");
			pDialog.setCancelable(false);
			pDialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... twitt) {
			try {
				mTwitter.updateStatus(twitt[0]);
				return "success";

			} catch (Exception e) {
				if (e.getMessage().toString().contains("duplicate")) {
					return "Plaatsing mislukt als gevolg van Duplicate bericht...";
				}
				e.printStackTrace();
				return "Posting Mislukt!!!";
			}

		}

		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();

			if (null != result && result.equals("success")) {
				showToast("Succesvol Geplaatst");

			} else {
				showToast(result);
			}

			super.onPostExecute(result);
		}
	}
}
