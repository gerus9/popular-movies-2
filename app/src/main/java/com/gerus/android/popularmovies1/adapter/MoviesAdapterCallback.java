package com.gerus.android.popularmovies1.adapter;

import com.gerus.android.popularmovies1.model.Movie;

public interface MoviesAdapterCallback {

	void onClickEmptyLayout();

	void onItemSelected(Movie movie);
}
