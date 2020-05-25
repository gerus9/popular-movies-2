package com.gerus.android.popularmovies1.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.gerus.android.popularmovies1.R;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderTrailer extends RecyclerView.ViewHolder {

	public final LinearLayout linearLayout;
	public final AppCompatTextView textView;

	public ViewHolderTrailer(View view) {
		super(view);
		linearLayout = view.findViewById(R.id.root);
		textView = view.findViewById(R.id.text);
	}
}
