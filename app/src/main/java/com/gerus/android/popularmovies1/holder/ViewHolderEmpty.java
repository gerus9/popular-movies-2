package com.gerus.android.popularmovies1.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gerus.android.popularmovies1.R;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderEmpty extends RecyclerView.ViewHolder {

	public final LinearLayout emptyLayout;
	public final TextView textView;
	public final ImageView imageView;

	public ViewHolderEmpty(View view) {
		super(view);
		emptyLayout = view.findViewById(R.id.empty_layout);
		textView = view.findViewById(R.id.empty_text);
		imageView = view.findViewById(R.id.empty_image);
	}
}
