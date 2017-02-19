package com.wspa.app.notification.content;

import java.util.ArrayList;
import java.util.List;

/**
 * Queue of NetworkMangers. Allows to listen to the end of the queue.
 * Every manager that is added here should be 'pending'
 * @author kanawa
 *
 */
public class NetworkQueue{
	
	NetworkQueueListener mListener = null;
	List<NetworkManager> mList = new ArrayList<NetworkManager>();
	
	public NetworkQueue(){}
	public NetworkQueue(NetworkQueueListener listener){
		mListener = listener;
	}
	
	/**
	 * Adds new NetworkMAnager to the queue.
	 * Will not work if manager is not pending
	 * @param manager NetworkMAnager to be added
	 */
	public void add(NetworkManager manager){
		if(!manager.isPending()) return;
		
		mList.add(manager);
	}
	
	/**
	 * proceeds to the next request and execute it
	 */
	public void next(){
		if(mList.isEmpty()){
			if(mListener != null) mListener.onFinish();
			return;
		}
		
		NetworkManager manager = mList.get(0);
		mList.remove(0);
		manager.performPendingRequest();
		if(mListener != null) mListener.onNext(manager);
	}
	
	public interface NetworkQueueListener{
		void onNext(NetworkManager manager);
		void onFinish();
	}
}

