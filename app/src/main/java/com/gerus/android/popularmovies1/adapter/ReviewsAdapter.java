package com.gerus.android.popularmovies1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gerus.android.popularmovies1.R;
import com.gerus.android.popularmovies1.holder.ViewHolderReview;
import com.gerus.android.popularmovies1.model.ReviewsInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsAdapter extends RecyclerView.Adapter<ViewHolderReview> {

	private List<ReviewsInfo> reviewsInfoList;

	public ReviewsAdapter(@NonNull List<ReviewsInfo> reviewsInfoList) {
		this.reviewsInfoList = reviewsInfoList;
	}

	@NonNull
	@Override
	public ViewHolderReview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
		return new ViewHolderReview(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolderReview holder, int position) {
		ReviewsInfo reviewsInfo = reviewsInfoList.get(position);
		holder.title.setText(reviewsInfo.getAuthor());
		holder.subtitle.setText(reviewsInfo.getContent());
	}

	@Override
	public int getItemCount() {
		return reviewsInfoList.size();
	}

	public void addData(List<ReviewsInfo> reviewsInfoList) {
		this.reviewsInfoList.clear();
		this.reviewsInfoList.addAll(reviewsInfoList);
		notifyDataSetChanged();
	}
}
