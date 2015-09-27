package com.hipages.twitterati.tasks;

import java.io.ByteArrayInputStream;

import com.hipages.twitterati.adapters.ListAdapter;
import com.hipages.twitterati.comms.ConnectionManager;
import com.hipages.twitterati.models.Search;
import com.hipages.twitterati.views.MainActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

/**
 *
 */
public class DownloadImageTask extends AsyncTask<Void, Void, Void> {
	private ListAdapter activity;
	private Search search;
	private int position;
	private ListAdapter.ViewHolder viewHolder;
	private Context context;

	public DownloadImageTask(Search info, int position, ListAdapter.ViewHolder viewHolder, ListAdapter activity,
			Context context) {
		this.activity = activity;
		this.search = info;
		this.position = position;
		this.viewHolder = viewHolder;
		this.context = context;
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			ConnectionManager connectionManager = new ConnectionManager(context, search.getUser().getProfileImageUrl());
			byte[] imageByteArray = connectionManager.requestImage().buffer();
			ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
			Bitmap image = BitmapFactory.decodeStream(imageStream);
			search.getUser().setProfileImage(image);
			if(image == null)
				search.getUser().setProfileImageUrl("");
		} catch (Exception e) {
			Log.e(MainActivity.LOG_TAG, getClass().getSimpleName() + " - doInBackground - " + e.toString());
		}
		return null;
	}

	protected void onPostExecute(Void result) {
		activity.updateImageInfo(search, position, viewHolder);
	}

}
