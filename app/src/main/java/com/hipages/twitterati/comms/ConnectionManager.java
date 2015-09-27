package com.hipages.twitterati.comms;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.hipages.twitterati.views.MainActivity;


/**
 *
 */
public class ConnectionManager {
	private URL url = null;
	
	private Context context;
	
	
	public ConnectionManager(Context context, String requestURL){
		this.context = context;
		
		try {
			url = new URL(requestURL);
			
		} catch (MalformedURLException e) {
			Log.e(MainActivity.LOG_TAG, getClass().getSimpleName() + " - ConnectionManager - " + e.toString());
		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	public ByteArrayBuffer requestImage(){
		HttpURLConnection httpConnection;
		ByteArrayBuffer baf = new ByteArrayBuffer(1024);
		BufferedInputStream bis;

		if(!isNetworkAvailable()){
			return null;
		}
		
	    try {
	        httpConnection = (HttpURLConnection) url.openConnection();
	        
	        int responseCode = httpConnection.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	        	bis = new BufferedInputStream(httpConnection.getInputStream(), 1024);

				int current;
				while ((current = bis.read()) != -1) {
					baf.append((byte) current);
				}
	            
	        } 
	        
	    } catch (Exception ex) {
            Log.e(MainActivity.LOG_TAG, getClass().getSimpleName() + " - requestImage - " + ex.toString());
	    }
	    return baf;
	} 
	
}
