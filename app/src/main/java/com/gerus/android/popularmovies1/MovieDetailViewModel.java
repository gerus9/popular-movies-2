package com.gerus.android.popularmovies1;

import android.app.Application;

import com.gerus.android.popularmovies1.model.ReviewsInfo;
import com.gerus.android.popularmovies1.model.VideoInfo;
import com.gerus.android.popularmovies1.repository.MovieReposityCallback;
import com.gerus.android.popularmovies1.utils.MoviesRepositoryUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MovieDetailViewModel extends AndroidViewModel {

	private MovieReposityCallback repository;

	public MovieDetailViewModel(@NonNull Application application) {
		super(application);
		repository = new MoviesRepositoryUtils(application).getRepository();
	}

	LiveData<Boolean> isFavorite(int idMovie) {
		return repository.isFavorite(idMovie);
	}

	void setFavoriteMovie(int idMovie) {
		repository.setFavoriteMovie(idMovie);
	}

	void unFavoriteMovie(int idMovie) {
		repository.deleteFavoriteMovie(idMovie);
	}

	LiveData<List<VideoInfo>> getVideos(int idMovie) {
		return repository.getVideoInfo(idMovie);
	}

	LiveData<List<ReviewsInfo>> getReviews(int idMovie) {
		return repository.getReviewsInfo(idMovie);
	}
}
