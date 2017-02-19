package com.wspa.app.notification.adapters;

import java.net.URI;

public interface NetworkListener {
	abstract void onSuccess(String result, boolean showProgress, URI url, boolean isError);
}
