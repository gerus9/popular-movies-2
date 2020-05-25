package com.gerus.android.popularmovies1;

import android.app.Application;

import com.gerus.android.popularmovies1.model.APIResponse;
import com.gerus.android.popularmovies1.model.Movie;
import com.gerus.android.popularmovies1.repository.MovieReposityCallback;
import com.gerus.android.popularmovies1.utils.MoviesRepositoryUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MoviesViewModel extends AndroidViewModel {

	private MovieReposityCallback repository;

	public MoviesViewModel(@NonNull Application application) {
		super(application);
		repository = new MoviesRepositoryUtils(application).getRepository();
	}

	MutableLiveData<APIResponse<List<Movie>>> getListMoviesPopular() {
		return repository.getPopular();
	}

	MutableLiveData<APIResponse<List<Movie>>> getListMoviesTopRated() {
		return repository.getTopRated();
	}

	LiveData<List<Movie>> getFavoriteMovies() {
		return repository.getFavoriteMovies();
	}
}
