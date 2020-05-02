package com.gerus.android.popularmovies1.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.gerus.android.popularmovies1.R;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderEmpty extends RecyclerView.ViewHolder {

	public final LinearLayout emptyLayout;

	public ViewHolderEmpty(View view) {
		super(view);
		emptyLayout = view.findViewById(R.id.empty_layout);
	}
}
