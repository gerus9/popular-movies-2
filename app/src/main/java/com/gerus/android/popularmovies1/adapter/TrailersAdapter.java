package com.gerus.android.popularmovies1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gerus.android.popularmovies1.R;
import com.gerus.android.popularmovies1.holder.ViewHolderTrailer;
import com.gerus.android.popularmovies1.model.VideoInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrailersAdapter extends RecyclerView.Adapter<ViewHolderTrailer> {

	private TrailersAdapterCallback callback;
	private List<VideoInfo> videoInfoList;

	public TrailersAdapter(@NonNull List<VideoInfo> videoInfoList, @NonNull TrailersAdapterCallback callback) {
		this.callback = callback;
		this.videoInfoList = videoInfoList;
	}

	@NonNull
	@Override
	public ViewHolderTrailer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View mView;
		mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
		return new ViewHolderTrailer(mView);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolderTrailer holder, int position) {
		VideoInfo videoInfo = videoInfoList.get(position);
		holder.textView.setText(videoInfo.getName());
		holder.linearLayout.setOnClickListener(view -> {
			if (callback != null) {
				callback.onTrailerSelected(videoInfo);
			}
		});
	}

	@Override
	public int getItemCount() {
		return videoInfoList.size();
	}

	public void addData(List<VideoInfo> videoInfoList) {
		this.videoInfoList.clear();
		this.videoInfoList.addAll(videoInfoList);
		notifyDataSetChanged();
	}
}
