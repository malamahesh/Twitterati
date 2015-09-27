package com.hipages.twitterati.adapters;

import com.hipages.twitterati.R;
import com.hipages.twitterati.models.Search;
import com.hipages.twitterati.models.Searches;
import com.hipages.twitterati.tasks.DownloadImageTask;
import com.hipages.twitterati.views.MainActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	private Searches arrListSearches;
	private MainActivity mActivity;

	public ListAdapter(Searches arrListSearches, MainActivity activity) {
		this.arrListSearches = arrListSearches;
		mActivity = activity;
	}

	@Override
	public int getCount() {
		return arrListSearches.size();
	}

	@Override
	public Object getItem(int position) {
		return arrListSearches.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		try {
			if (convertView == null) {
				viewHolder = new ViewHolder();
				LayoutInflater vi = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(mActivity.getResources().getLayout(R.layout.list_row), null);
				viewHolder.tvText = (TextView) convertView.findViewById(R.id.tvText);
				viewHolder.imgDisplay = (ImageView) convertView.findViewById(R.id.imgDisplay);
				viewHolder.prgBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			if (viewHolder != null) {
				Search search = arrListSearches.get(position);

				viewHolder.tvText.setText(search.getText());

				if (search.getUser().getProfileImage() != null) {
					viewHolder.imgDisplay.setImageBitmap(search.getUser().getProfileImage());
					viewHolder.prgBar.setVisibility(View.GONE);
				} else {
					viewHolder.imgDisplay.setImageResource(R.mipmap.ic_launcher);
					if(search.getUser().getProfileImageUrl() != null && search.getUser().getProfileImageUrl().length() != 0) {
						viewHolder.prgBar.setVisibility(View.VISIBLE);
						getImage(search,position,viewHolder);
					}
				}
			}
		} catch (Exception e) {
			Log.e(MainActivity.LOG_TAG, getClass().getSimpleName() + " - getView - " + e.toString());
		}
		return convertView;
	}

	private void getImage(Search search, int position, ViewHolder viewHolder) {
		try {
			if (search.getUser().getProfileImage() == null) {
				DownloadImageTask fetchTask = new DownloadImageTask(search,position,viewHolder,this,mActivity);
				fetchTask.execute();
			}
		} catch (Exception e) {
			Log.e(MainActivity.LOG_TAG, getClass().getSimpleName() + " - getImage - " + e.toString());
		}
	}
	
	public void updateImageInfo(Search search, int position, ViewHolder viewHolder) {
		try {
			arrListSearches.set(position, search);
			viewHolder.imgDisplay.setImageBitmap(search.getUser().getProfileImage());
			viewHolder.prgBar.setVisibility(View.GONE);
		} catch (Exception e) {
			Log.e(MainActivity.LOG_TAG, getClass().getSimpleName() + " - updateImageInfo - " + e.toString());
		}
	}

	public class ViewHolder {
		TextView tvText;
		ImageView imgDisplay;
		ProgressBar prgBar;
	}
}
