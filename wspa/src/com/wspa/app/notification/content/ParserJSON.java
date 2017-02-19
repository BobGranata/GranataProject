package com.wspa.app.notification.content;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.wspa.app.notification.models.AboModel;
import com.wspa.app.notification.models.AuthentModel;
import com.wspa.app.notification.models.MemoryModel;
import com.wspa.app.notification.models.MessageModel;
import com.wspa.app.notification.models.NotifyModel;
import com.wspa.app.notification.news.NewsModel;
import com.wspa.app.notification.adapters.ParserListener;
import com.wspa.app.notification.adapters.ProgressDialogPublic;
import com.wspa.app.notification.info.AboutModel;
//import ae.sevdotcom.interfaces.ParserListener;
//import ae.sevdotcom.items.Facility;
//import ae.sevdotcom.items.MoePlace;
//import ae.sevdotcom.items.MoePromoNews;
//import ae.sevdotcom.maf.maadi.R;
//import ae.sevdotcom.useful.Extras;
//import ae.sevdotcom.useful.ModeGlobal;
//import ae.sevdotcom.useful.PosOnMap;
//import ae.sevdotcom.useful.ProgressDialogPublic;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ParserJSON {

	URI mUrl = null;
	boolean mShowProgress = true;
	Context mContext;

	// public ParserJSON(){}

	public ParserJSON(URI url, boolean showProgress, Context context) {
		mUrl = url;
		mShowProgress = showProgress;
		mContext = context;
	}

	public void parseNewsAsync(String data, ParserListener<NewsModel> listener) {

		ParsingRunnable<NewsModel> runnable = new ParsingRunnable<NewsModel>() {
			@Override
			public List<NewsModel> parse(String data) {
				List<NewsModel> result = new ArrayList<NewsModel>();

				try {
					JSONObject root = new JSONObject(data);
					String authOutcome = root.getString("auth_outcome");
					JSONObject actionValues = root
							.getJSONObject("action_values");

					String pubDate = actionValues.getString("pubdate");
					int itemsCount = actionValues.getInt("count");

					JSONObject items = actionValues.getJSONObject("items");
					for (int i = 0; i < itemsCount; i++) {
						try {
							JSONObject item = items.getJSONObject(String
									.valueOf(i));
							NewsModel news = new NewsModel();
							news.setPubDate(pubDate);
							news.setId(item.getInt("id"));
							news.setStatus(item.getString("status"));
							news.setDate(item.getString("date"));
							news.setOrder(item.getInt("order"));
							news.setCategory(item.getInt("category"));

							news.setIcon(item.getString("icon"));
							news.setImage(item.getString("image"));
							news.setShortCaption(item
									.getString("short_caption"));
							news.setShortText(item.getString("short_content"));
							news.setLongCaption(item.getString("long_caption"));
							news.setLongText(item.getString("long_content"));

							news.setUrl_share(item.getString("url_share"));
							news.setUrl_video(item.getString("url_video"));
							result.add(news);
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return result;
			}
		};

		new AsyncParsing<NewsModel>(runnable, listener)
				.execute(new String[] { data });

	}

	public void parseAboutAsync(String data, ParserListener<AboutModel> listener) {

		ParsingRunnable<AboutModel> runnable = new ParsingRunnable<AboutModel>() {
			@Override
			public List<AboutModel> parse(String data) {
				List<AboutModel> result = new ArrayList<AboutModel>();

				try {
					JSONObject root = new JSONObject(data);
					String authOutcome = root.getString("auth_outcome");
					JSONObject actionValues = root
							.getJSONObject("action_values");

					int itemsCount = actionValues.getInt("count");

					JSONObject items = actionValues.getJSONObject("items");
					for (int i = 1; i <= itemsCount; i++) {

						try {
							JSONObject item = items.getJSONObject(String
									.valueOf(i));
							AboutModel about = new AboutModel();
							about.setImage(item.getString("image"));
							about.setContent1(item.getString("content_1"));
							about.setContent2(item.getString("content_2"));
							about.setUrl_share(item.getString("url_share"));

							result.add(about);
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return result;
			}
		};

		new AsyncParsing<AboutModel>(runnable, listener)
				.execute(new String[] { data });
	}

	public MessageModel parseMessage(String data) {
		MessageModel mess = new MessageModel();
		try {
			JSONObject root = new JSONObject(data);
			String alert = root.getString("alert");
			String sound = root.getString("sound");
			mess.setAlert(alert);
			mess.setSound(sound);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return mess;
	}

	public List<AuthentModel> parseAuthent(String data) {
		List<AuthentModel> result = new ArrayList<AuthentModel>();

		if (data == null)
			return result;
		if (data.length() == 0)
			return result;
		try {
			JSONObject root = new JSONObject(data);
			String authOutcome = root.getString("auth_outcome");
			JSONObject authValues = root.getJSONObject("auth_values");

			try {
				// JSONObject authValue =
				// authValues.getJSONObject(String.valueOf(1));
				AuthentModel auth;
				if (authOutcome.length() == 7) {
					auth = new AuthentModel(authOutcome,
							authValues.getString("email"),
							authValues.getBoolean("optin"));
					result.add(auth);
					return result;
				} else {
					auth = new AuthentModel();
					auth.setAuth_outcome(authOutcome);
					auth.setTries(authValues.getInt("tries"));
					auth.setError(authValues.getString("error"));
					auth.setReason(authValues.getString("reason"));
					auth.setCaption(authValues.getString("caption"));
					auth.setMessage(authValues.getString("message"));
				}
				result.add(auth);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<NotifyModel> parseNotify(String data) {
		List<NotifyModel> result = new ArrayList<NotifyModel>();

		if (data == null)
			return null;
		if (data.length() == 0)
			return null;
		try {
			JSONObject root = new JSONObject(data);
			
			if (root.getString("auth_outcome").equals("FAIL")) {
				return null;
			}

			JSONObject actionValues = root.getJSONObject("action_values");
			try {
				NotifyModel notif = new NotifyModel();
				notif.setPushId(actionValues.getString("push_id"));
				notif.setOptin(actionValues.getString("optin"));
				notif.setCategories(actionValues.getString("categories"));
				notif.setSound(actionValues.getString("sound"));
				result.add(notif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<AboModel> parseAbo(String data) {
		List<AboModel> result = new ArrayList<AboModel>();
		if (data == null)
			return result;
		if (data.length() == 0)
			return result;
		try {
			JSONObject root = new JSONObject(data);

			AboModel abon = new AboModel();
			String authOutcome = root.getString("auth_outcome");

			abon.setActionOutcome(root.getString("action_outcome"));
			JSONObject authValues = root.getJSONObject("auth_values");
			JSONObject actionValues = root.getJSONObject("action_values");
			if (root.getString("action_outcome").equals("FAIL"))
				try {
					abon.setError(actionValues.getString("error"));
					abon.setReason(actionValues.getString("reason"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			try {
				abon.setEmail(authValues.getString("email"));
				abon.setOptin(authValues.getString("optin"));
				result.add(abon);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<NewsModel> parseNews(String data) {
		List<NewsModel> result = new ArrayList<NewsModel>();

		if (data == null)
			return result;
		if (data.length() == 0)
			return result;
		try {
			JSONObject root = new JSONObject(data);

			JSONObject actionValues = root.getJSONObject("action_values");
			String pubDate = actionValues.getString("pubdate");
			int itemsCount = actionValues.getInt("count");

			JSONObject items = actionValues.getJSONObject("items");
			for (int i = 0; i <= itemsCount; i++) {
				try {
					JSONObject item = items.getJSONObject(String.valueOf(i));
					NewsModel news = new NewsModel();
					news.setPubDate(pubDate);
					news.setId(item.getInt("id"));
					news.setStatus(item.getString("status"));

					if (!news.getStatus().equals("DELETE")) {
						news.setDate(item.getString("date"));
						news.setOrder(item.getInt("order"));
						news.setCategory(item.getInt("category"));

						news.setIcon(item.getString("icon"));
						news.setImage(item.getString("image"));

						news.setShortCaption(item.getString("short_caption"));
						news.setShortText(item.getString("short_content"));
						news.setLongCaption(item.getString("long_caption"));
						news.setLongText(item.getString("long_content"));

						news.setUrl_share(item.getString("url_share"));
						news.setUrl_video(item.getString("url_video"));
					}
					result.add(news);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<AboutModel> parseAbout(String data) {
		List<AboutModel> result = new ArrayList<AboutModel>();

		if (data == null)
			return result;
		if (data.length() == 0)
			return result;
		try {
			JSONObject root = new JSONObject(data);

			JSONObject actionValues = root.getJSONObject("action_values");
			String pubDate = actionValues.getString("pubdate");
			int itemsCount = actionValues.getInt("count");

			JSONObject items = actionValues.getJSONObject("items");
			for (int i = 0; i <= itemsCount; i++) {
				try {
					JSONObject item = items.getJSONObject(String.valueOf(i));
					AboutModel about = new AboutModel();
					about.setPubDate(pubDate);
					about.setImage(item.getString("image"));
					about.setContent1(item.getString("content_1"));
					about.setContent2(item.getString("content_2"));
					about.setUrl_share(item.getString("url_share"));
					result.add(about);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String[] parseStatus(String data) {
		String[] result = new String[3];

		if (data == null)
			return null;
		if (data.length() == 0)
			return null;
		try {
			JSONObject root = new JSONObject(data);

			if (root.getString("auth_outcome").equals("SUCCESS")) {

				JSONObject actionValues = root.getJSONObject("action_values");

				result[0] = actionValues.getString("news");
				result[1] = actionValues.getString("memory");
				result[2] = actionValues.getString("content");
			}

			else if (root.getString("auth_outcome").equals("FAIL")) {
				JSONObject authValues = root.getJSONObject("auth_values");
				result[0] = root.getString("auth_outcome");

				result[1] = authValues.getString("error");

				result[2] = authValues.getString("reason");

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<MemoryModel> parseMemory(String data) {
		List<MemoryModel> result = new ArrayList<MemoryModel>();

		if (data == null)
			return result;
		if (data.length() == 0)
			return result;
		try {
			JSONObject root = new JSONObject(data);

			JSONObject actionValues = root.getJSONObject("action_values");
			String pubDate = actionValues.getString("pubdate");
			int itemsCount = actionValues.getInt("count");

			JSONObject items = actionValues.getJSONObject("items");
			for (int i = 0; i <= itemsCount; i++) {
				try {
					JSONObject item = items.getJSONObject(String.valueOf(i));
					MemoryModel memor = new MemoryModel();
					memor.setPubDate(pubDate);
					memor.setId(item.getInt("id"));
					memor.setStatus(item.getString("status"));
					if (!memor.getStatus().equals("DELETE")) {
						memor.setDate(item.getString("date"));
						memor.setIcon(item.getString("icon"));
					}
					result.add(memor);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	private interface ParsingRunnable<T> {
		abstract List<T> parse(String data);
	}

	private class AsyncParsing<Type> extends
			AsyncTask<String, Void, List<Type>> {

		ParsingRunnable<Type> mRunnable;
		ParserListener<Type> mCallback;

		public AsyncParsing(ParsingRunnable<Type> runnable,
				ParserListener<Type> listener) {
			// TODO Auto-generated constructor stub

			mRunnable = runnable;
			mCallback = listener;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mShowProgress)
				ProgressDialogPublic.ShowProgressDialog(mContext);
		}

		@Override
		protected void onPostExecute(List<Type> result) {
			super.onPostExecute(result);

			mCallback.onFinish(result, mUrl);
			if (mShowProgress)
				ProgressDialogPublic.RemoveProgressDialog();
		}

		@Override
		protected List<Type> doInBackground(String... params) {

			return mRunnable.parse(params[0]);
		}

	}

}
