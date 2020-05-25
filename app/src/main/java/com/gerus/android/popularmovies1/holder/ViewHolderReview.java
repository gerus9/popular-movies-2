package com.gerus.android.popularmovies1.holder;

import android.view.View;

import com.gerus.android.popularmovies1.R;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderReview extends RecyclerView.ViewHolder {

	public final AppCompatTextView title;
	public final AppCompatTextView subtitle;

	public ViewHolderReview(View view) {
		super(view);
		title = view.findViewById(R.id.title);
		subtitle = view.findViewById(R.id.subtitle);
	}
}
