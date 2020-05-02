package com.gerus.android.popularmovies1.holder;

import android.view.View;
import android.widget.ImageView;

import com.gerus.android.popularmovies1.R;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderData extends RecyclerView.ViewHolder {

	public final ImageView imageView;

	public ViewHolderData(View view) {
		super(view);
		imageView = view.findViewById(R.id.image_movie);
	}
}
