package com.gerus.android.popularmovies1.repository.interfaces;

import com.gerus.android.popularmovies1.model.ErrorMessage;
import com.gerus.android.popularmovies1.model.Movie;

import java.util.List;

public interface MoviesCallback {

	void onSuccess(List<Movie> movieList);

	void onError(ErrorMessage message);

}
