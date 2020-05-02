package com.gerus.android.popularmovies1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gerus.android.popularmovies1.R;
import com.gerus.android.popularmovies1.holder.ViewHolderData;
import com.gerus.android.popularmovies1.holder.ViewHolderEmpty;
import com.gerus.android.popularmovies1.model.Movie;
import com.gerus.android.popularmovies1.utils.ImageUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapter extends RecyclerView.Adapter {

	private final int TYPE_EMPTY = 0;
	private final int TYPE_DATA = 1;

	private List<Movie> movieList;
	private Context context;
	private MoviesAdapterCallback callback;

	public MoviesAdapter(@NonNull Context context, @NonNull List<Movie> movieList, @NonNull MoviesAdapterCallback callback) {
		this.context = context;
		this.movieList = movieList;
		this.callback = callback;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View mView;
		if (viewType == TYPE_DATA) {
			mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_view, parent, false);
			return new ViewHolderData(mView);
		} else {
			mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_layout, parent, false);
			return new ViewHolderEmpty(mView);
		}
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof ViewHolderData) {
			ViewHolderData viewHolderData = (ViewHolderData) holder;
			Movie movie = movieList.get(position);
			Picasso.get()
				   .load(ImageUtil.buildURLPoster(context, movie.getPosterPath()))
				   .placeholder(R.drawable.vc_placeholder)
				   .error(R.drawable.vc_error)
				   .into(viewHolderData.imageView);
			viewHolderData.imageView.setOnClickListener(view -> callback.onItemSelected(movie));
		} else if (holder instanceof ViewHolderEmpty) {
			((ViewHolderEmpty) holder).emptyLayout.setOnClickListener(view -> callback.onClickEmptyLayout());
		}
	}

	@Override
	public int getItemViewType(int position) {
		return movieList.isEmpty() ? TYPE_EMPTY : TYPE_DATA;
	}

	@Override
	public int getItemCount() {
		return movieList.isEmpty() ? 1 : movieList.size();
	}

	public void addData(List<Movie> movieList) {
		this.movieList.clear();
		this.movieList.addAll(movieList);
		notifyDataSetChanged();
	}

	public int getNumberColumns(int position) {
		if (position == movieList.size()) {
			return context.getResources().getInteger(R.integer.numberColumns);
		} else {
			return 1;
		}
	}
}
