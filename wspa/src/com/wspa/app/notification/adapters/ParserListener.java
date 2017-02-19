package com.wspa.app.notification.adapters;

import java.net.URI;
import java.util.List;

public interface ParserListener<T> {
	abstract void onFinish(List<T> result, URI url);
}
