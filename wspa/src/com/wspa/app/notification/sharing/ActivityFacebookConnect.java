package com.wspa.app.notification.sharing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.wspa.app.notification.R;

public class ActivityFacebookConnect extends Activity {
	// Instance of Facebook Class
	private Facebook facebook;
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME;
	private SharedPreferences mPrefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharing_screen);
		facebook = new Facebook(getString(R.string.FB_API_KEY));
		FILENAME = "AndroidSSO_data";
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		this.loginToFacebook();
		/*btnFbLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("Image Button", "button Clicked");
				loginToFacebook();
			}
		});
		btnFbGetProfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getProfileInformation();
			}
		});
		btnPostToWall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				postToWall();
			}
		});
		btnShowAccessTokens.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showAccessTokens();
			}
		});*/
	}
	/**
	 * Function to login into facebook
	 * */
	public void loginToFacebook() {
		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);
		if (access_token != null) {
			facebook.setAccessToken(access_token);
			/*btnFbLogin.setVisibility(View.INVISIBLE);
			// Making get profile button visible
			btnFbGetProfile.setVisibility(View.VISIBLE);
			// Making post to wall visible
			btnPostToWall.setVisibility(View.VISIBLE);
			// Making show access tokens button visible
			btnShowAccessTokens.setVisibility(View.VISIBLE);*/
			postToWall();
			Log.d("FB Sessions", "" + facebook.isSessionValid());
		}
		if (expires != 0) facebook.setAccessExpires(expires);
		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					new String[] { "email", "publish_stream" },
					new DialogListener() {
						@Override
						public void onCancel() {
							// Function to handle cancel event
							finish();
						}
						@Override
						public void onComplete(Bundle values) {
							// Function to handle complete event
							// Edit Preferences and update facebook acess_token
							SharedPreferences.Editor editor = mPrefs.edit();
							editor.putString("access_token", facebook.getAccessToken());
							editor.putLong("access_expires", facebook.getAccessExpires());
							editor.commit();
							/*// Making Login button invisible
							btnFbLogin.setVisibility(View.INVISIBLE);
							// Making logout Button visible
							btnFbGetProfile.setVisibility(View.VISIBLE);
							// Making post to wall visible
							btnPostToWall.setVisibility(View.VISIBLE);
							// Making show access tokens button visible
							btnShowAccessTokens.setVisibility(View.VISIBLE);*/
							postToWall();
						}
						@Override
						public void onError(DialogError error) {
							// Function to handle error
							finish();
						}
						@Override
						public void onFacebookError(FacebookError fberror) {
							// Function to handle Facebook errors
							finish();
						}
					});
		}// if (!facebook.isSessionValid())
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}
	/**
	 * Get Profile information by making request to Facebook Graph API
	 * */
	public void getProfileInformation() {
		/*mAsyncRunner.request("me", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Profile", response);
				String json = response;
				try {
					// Facebook Profile JSON data
					JSONObject profile = new JSONObject(json);
					// getting name of the user
					final String name = profile.getString("name");
					// getting email of the user
					final String email = profile.getString("email");
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onIOException(IOException e, Object state) {
			}
			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}
			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}
			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});*/
	}
	/**
	 * Function to post to facebook wall
	 * */
	public void postToWall() {
		// post on user's wall.
		facebook.dialog(this, "feed", new DialogListener() {
			@Override
			public void onFacebookError(FacebookError e) {
				finish();
			}
			@Override
			public void onError(DialogError e) {
				finish();
			}
			@Override
			public void onComplete(Bundle values) {
				finish();
			}
			@Override
			public void onCancel() {
				finish();
			}
		});

	}
	/**
	 * Function to show Access Tokens
	 * */
	public void showAccessTokens() {
		String access_token = facebook.getAccessToken();
		Toast.makeText(getApplicationContext(),
				"Access Token: " + access_token, Toast.LENGTH_LONG).show();
	}
	/**
	 * Function to Logout user from Facebook
	 * */
	public void logoutFromFacebook() {
		mAsyncRunner.logout(this, new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Logout from Facebook", response);
				if (Boolean.parseBoolean(response) == true) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							/*// make Login button visible
							btnFbLogin.setVisibility(View.VISIBLE);
							// making all remaining buttons invisible
							btnFbGetProfile.setVisibility(View.INVISIBLE);
							btnPostToWall.setVisibility(View.INVISIBLE);
							btnShowAccessTokens.setVisibility(View.INVISIBLE);*/
						}

					});

				}
			}
			@Override
			public void onIOException(IOException e, Object state) {
			}
			@Override
			public void onFileNotFoundException(FileNotFoundException e, Object state) {
			}
			@Override
			public void onMalformedURLException(MalformedURLException e, Object state) {
			}
			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}// public void logoutFromFacebook()
}