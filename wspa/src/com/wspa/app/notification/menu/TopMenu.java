package com.wspa.app.notification.menu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.FacebookConnector;
import com.facebook.android.FacebookError;
import com.google.analytics.tracking.android.EasyTracker;
import com.twitter.android.Twitt;
import com.wspa.app.notification.PostStruct;
import com.wspa.app.notification.R;
import com.wspa.app.notification.SharingTexts;
import com.wspa.app.notification.interfaces.FbConnectorListener;
import com.wspa.app.notification.memory.GameAbout;
import com.wspa.app.notification.menu.BottomMenu;
import com.wspa.app.notification.news.NewsModel;
import com.wspa.app.notification.adapters.TypeSet;
import com.wspa.app.notification.extras.ControlActivity;
import com.wspa.app.notification.extras.DataConsts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ViewConstructor")
public class TopMenu extends View implements OnClickListener,
		FbConnectorListener, RequestListener {

	Context context;
	View menu;
	TextView tw; // Заголовок (название открытого раздела приложения)
	TextView twFb, twTw, twGp;
	ImageView btnInfo, // Кнопка "Info"
			btnMenu, // Кнопка "Menu"
			btnSharing, // Кнопка "Sharing"
			btnBack; // Эмблема "WSPA", выполянющая функции кнопки "назад"
	BottomMenu bm; // Нижнее меню приложения
	TypeSet ts;
	ControlActivity CActivity;
	Dialog dialog, fbPreviewDialog, gplusDialog;
	GameAbout AboutGame;
	// Instance of Facebook Class
	private FacebookConnector fc;
	String FILENAME = "AndroidSSO_data";
	// Twitter
	public String consumer_key;
	public String secret_key;
	public SharingTexts share;
	public NewsModel NewsDetails;
	PostStruct PostS;
	WebView webView;
	private ProgressDialog mSpinner;

	public TopMenu(Context con, LinearLayout ll, int title,
			Boolean needBtnInfo, Boolean needBtnSharing,
			Boolean canCloseActivity, BottomMenu bot_men, ControlActivity CA,
			NewsModel News) {
		super(con);
		context = con;
		CActivity = CA;
		menu = View.inflate(context, R.layout.top_menu, ll);
		// Инициализация компонентов
		NewsDetails = News;
		ts = new TypeSet(context);
		tw = (TextView) menu.findViewById(R.id.twTopMenuTitle);
		tw = ts.SetTF(tw, DataConsts.ARIAL_BOLD);
		tw.setText(title);
		tw.setOnClickListener(this);
		btnMenu = (ImageView) menu.findViewById(R.id.btn_menu);
		btnMenu.setOnClickListener(this);
		btnInfo = (ImageView) menu.findViewById(R.id.btn_info);
		btnSharing = (ImageView) menu.findViewById(R.id.btn_sharing);
		btnBack = (ImageView) menu.findViewById(R.id.ivTopMenuWspaTitle);
		if (needBtnInfo) {
			btnInfo.setOnClickListener(this);
		} else {
			btnInfo.setVisibility(ImageView.INVISIBLE);
		}
		if (needBtnSharing) {
			// Initial Facebook
			btnSharing.setOnClickListener(this);
			dialog = new Dialog(context);
			dialog.setContentView(R.layout.dialogsharing);
			dialog.setTitle(CActivity.GetActivity().getApplication()
					.getText(R.string.DIALOG_SHAR_CAPTION));
			ImageView btnFacebook = (ImageView) dialog
					.findViewById(R.id.ivSharingFacebook);
			ImageView btnTwitter = (ImageView) dialog
					.findViewById(R.id.ivSharingTwitter);
			ImageView btnGPlus = (ImageView) dialog
					.findViewById(R.id.ivSharingGPlus);
			twFb = (TextView) dialog.findViewById(R.id.tvSharingFacebook);
			twTw = (TextView) dialog.findViewById(R.id.tvSharingTwitter);
			twGp = (TextView) dialog.findViewById(R.id.tvSharingGPlus);
			// G+
			btnGPlus.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					CreateGPlus();
				}
			});
			twGp.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					CreateGPlus();
				}
			});
			Button btnClose = (Button) dialog
					.findViewById(R.id.btnSharingClose);
			// FB
			btnFacebook.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					CreateFacebook();
				}
			});
			twFb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					CreateFacebook();
				}
			});
			// TW
			btnTwitter.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					CreateTwitter();
				}
			});
			twTw.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					CreateTwitter();
				}
			});
			btnClose.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
		} else {
			btnSharing.setVisibility(ImageView.INVISIBLE);
		}
		if (canCloseActivity) {
			btnBack.setOnClickListener(this);
		}
		bm = bot_men;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(btnMenu)) {
			bm.ChangeVisibility();
			// Кнопка "Info"
		} else if (v.equals(btnInfo)) {
			CActivity.ShowAboutGame();
			// Кнопка "Sharing"
		} else if (v.equals(btnSharing)) {
			StartSharingDialog();
			// Нажата эмблема "WSPA" (выполнить "назад")
		} else if (v.equals(btnBack) || v.equals(tw)) {
			CActivity.GoBack();
		}
	}

	public void StartSharingDialog(){
		if (CActivity.GetShareType() == DataConsts.SHARE_GAME_SCORE) {
			CreateFacebook();
		} else {
			dialog.show();
		}
	}
	
	public void CreateFacebook() {
		fc = new FacebookConnector(CActivity.GetActivity(), this);
		share = new SharingTexts();
		fbPreviewDialog = new Dialog(context);
		fbPreviewDialog.setContentView(R.layout.facebook_dialog);
		fbPreviewDialog.setTitle("Post preview");
		EditText etFbText = (EditText) fbPreviewDialog
				.findViewById(R.id.et_fb_postedtext);
		PostS = share.GetText(DataConsts.SHARE_FB, CActivity.GetShareType(),
				NewsDetails, CActivity.GetGameResult());
		etFbText.setText(PostS.PreviewText());
		Button btnCancel = (Button) fbPreviewDialog
				.findViewById(R.id.btn_fb_cancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fbPreviewDialog.dismiss();
			}
		});
		Button btnPost = (Button) fbPreviewDialog
				.findViewById(R.id.btn_fb_post);
		btnPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fbPreviewDialog.dismiss();
				fc.authorize();
				FbPosting();
			}
		});
		fbPreviewDialog.show();
	}// public void CreateFacebook()

	public void FbPosting() {
		fc.feedPost(PostS, this);
	}

	public void CreateTwitter() {
		consumer_key = CActivity.GetActivity().getString(
				R.string.TWITTER_CONSUMER_KEY);
		secret_key = CActivity.GetActivity().getString(
				R.string.TWITTER_CONSUMER_SEKRET);
		Twitt twitt = new Twitt((Activity) CActivity.GetActivity(),
				consumer_key, secret_key);
		share = new SharingTexts();
		twitt.shareToTwitter(share.GetText(DataConsts.SHARE_TW,
				CActivity.GetShareType(), NewsDetails,
				CActivity.GetGameResult()));

	} // public void CreateTwitter()

	@SuppressLint("SetJavaScriptEnabled")
	public void CreateGPlus() {
		mSpinner = new ProgressDialog(CActivity.GetActivity());
		mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mSpinner.setMessage("Laden...");
		mSpinner.show();
		gplusDialog = new Dialog(context);
		gplusDialog.setContentView(R.layout.gplus_dialog);
		gplusDialog.setTitle("Post preview");
		gplusDialog.setCancelable(true);
		webView = (WebView) gplusDialog.findViewById(R.id.gplusWebView);
		webView.setVerticalScrollBarEnabled(true);
		webView.setHorizontalScrollBarEnabled(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new MyWebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				mSpinner.dismiss();
				gplusDialog.show();
			}
		});
		share = new SharingTexts();
		String url = share.GetUrlForGPlus(CActivity.GetShareType(),
				NewsDetails, CActivity.GetGameResult());
		webView.loadUrl(url);
		// CActivity.GetActivity().startActivity(new
		// Intent(CActivity.GetActivity(), ActivityGPlus.class));
	}

	@Override
	public void onLogIn(boolean error) {
		// TODO Auto-generated method stub
		fc.feedPost(share.GetText(DataConsts.SHARE_FB,
				CActivity.GetShareType(), NewsDetails,
				CActivity.GetGameResult()), this);
	}

	@Override
	public void onLogOut(boolean error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onComplete(String response, Object state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onIOException(IOException e, Object state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFileNotFoundException(FileNotFoundException e, Object state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMalformedURLException(MalformedURLException e, Object state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFacebookError(FacebookError e, Object state) {
		// TODO Auto-generated method stub

	}

	public class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.equals("com.wspa.app://share/?result=success")
					|| url.equals("com.wspa.app://share/?result=canceled")) {
				webView = null;
				gplusDialog.dismiss();
				return true;
			}
			view.loadUrl(url);
			return true;
		}

		public void onPageStarted(WebView view, String url) {
			// TODO Auto-generated method stub
			mSpinner.show();

		}
	}// public class MyWebViewClient
}
