package com.gerus.android.popularmovies1.repository;

import com.gerus.android.popularmovies1.model.APIResponse;
import com.gerus.android.popularmovies1.model.Movie;
import com.gerus.android.popularmovies1.model.ReviewsInfo;
import com.gerus.android.popularmovies1.model.VideoInfo;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public interface MovieReposityCallback {

	MutableLiveData<APIResponse<List<Movie>>> getPopular();

	MutableLiveData<APIResponse<List<Movie>>> getTopRated();

	MutableLiveData<APIResponse<List<Movie>>> getFavoriteMovies();

	MutableLiveData<List<VideoInfo>> getVideoInfo(int idMovie);

	MutableLiveData<List<ReviewsInfo>> getReviewsInfo(int idMovie);

	MutableLiveData<Boolean> isFavorite(int idMovie);

	void setFavoriteMovie(int idMovie);

	void deleteFavoriteMovie(int idMovie);
}
