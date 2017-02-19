package com.wspa.app.notification.content;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.wspa.app.notification.R;
import com.wspa.app.notification.adapters.NetworkListener;
import com.wspa.app.notification.adapters.ProgressDialogPublic;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;


/**
 * Class for performing network requests in the background
 * @author kanawa
 *
 */
public class NetworkManager {
	
	Context mContext;
	NetworkListener mCallback;
	boolean mShowProgress;
	
	/**
	 * next two fields are used for planning pending request 
	 */
	String mAddressPending = null;
	Boolean mIsPostPending = null;
 
	/**
	 * Check internet conection
	 * @param context
	 * @return true if internet is reachable, false otherwise
	 */
	public static boolean isOnline(Context context) {
	    ConnectivityManager cm =
	        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}

	
	/**
	 * Ususal constructor
	 * @param context - Context of the Android app. Used for showing progress dialog
	 * @param listener - Callback. Should be instance of NetworkListener interface 
	 */
	public NetworkManager(Context context, NetworkListener listener) {
		Construct(context, listener, true);
	}
	
	public NetworkManager(Context context, boolean showProgress, NetworkListener listener) {
		Construct(context, listener, showProgress);
	}
	
	private void Construct(Context context, NetworkListener listener, boolean showProgress){
		mContext = context;
		mCallback = listener;
		mShowProgress = showProgress;
	}
	
	
	/**
	 * Makes a custom request in the background
	 * @param requestUri - URI to request
	 * @param isPost - defines whether POST or GET should be performed
	 */
	public void performRawRequest(String requestUri, boolean isPost){
		try {
			URI uri = new URI(mContext.getResources().getString(R.string.url_base) + 
					requestUri);
			new InternalTask(isPost).execute(new URI[]{uri});
		} catch (URISyntaxException e) {
			e.printStackTrace();
			mCallback.onSuccess(null, mShowProgress, null, true);
		}
	}
	
	public void performRawGet(String requestUri){
		performRawRequest(requestUri, false);
	}
	
	public void performRawPost(String requestUri){
		performRawRequest(requestUri, true);
	}
	
	/**
	 * Mark this NetworkManager as 'pending' and set url and request type
	 * @param addressPending adress to pefrom request to
	 * @param isPostPending defines is this request get or post
	 * @return the NetworkManager itself
	 */
	public NetworkManager setPending(String addressPending, boolean isPostPending) {
		this.mAddressPending = addressPending;
		this.mIsPostPending = isPostPending;
		return this;
	}
	
	public void performPendingRequest(){
		performRawRequest(mAddressPending, mIsPostPending);
	}
	
	public boolean isPending(){
		return mAddressPending != null && mIsPostPending != null;
	}

	private class InternalTask extends AsyncTask<URI, Integer, String>{
		
		boolean mIsPost = false;
		boolean mErrorHappened = false;
		URI mUrl;
		
		{
//			mProgressDialog.setOnCancelListener(new OnCancelListener() {
//				@Override
//				public void onCancel(DialogInterface dialog) {
//					mErrorHappened = true;
//					InternalTask.this.cancel(true);
//					onPostExecute(null);
//				}
//			});
		}
		
		public InternalTask() {}
		public InternalTask(boolean isPost) {
			mIsPost = isPost;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(mShowProgress) ProgressDialogPublic.ShowProgressDialog(mContext);
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			mCallback.onSuccess(result, mShowProgress, mUrl, mErrorHappened);
			if(mShowProgress) ProgressDialogPublic.RemoveProgressDialog();	
		}
		
		@Override
		protected String doInBackground(URI... params) {
			String result = null;
			mUrl = params[0];
			try{
				HttpClient client = new DefaultHttpClient();
				HttpUriRequest req = mIsPost ? new HttpPost(mUrl) : new HttpGet(mUrl);
				ResponseHandler<String> handler = new BasicResponseHandler();
				result = handler.handleResponse(client.execute(req));
			}catch(Exception e){
				e.printStackTrace();
				mErrorHappened = true;
			}
			
			return result;
		}
	}
	
	
}

