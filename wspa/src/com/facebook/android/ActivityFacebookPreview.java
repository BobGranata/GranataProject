package com.facebook.android;

import com.wspa.app.notification.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ActivityFacebookPreview extends Activity implements OnClickListener{

	Button Post, Cancel;
	EditText et;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.facebook_dialog);
		Post = (Button) findViewById(R.id.btn_fb_post);
		Post.setOnClickListener(this);
		Cancel = (Button) findViewById(R.id.btn_fb_cancel);
		Cancel.setOnClickListener(this);
		et.setText(getIntent().getStringExtra("FB_POST_MESSAGE"));
		et.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
	            if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
	                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	                in.hideSoftInputFromWindow(et.getWindowToken(), 0);
	               return true;
	            }
				return false;
			}
	    });
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.equals(Post)){
			
		}else if(v.equals(Cancel)){
			
		}
	}
	
}
