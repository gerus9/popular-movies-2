package com.gerus.android.popularmovies1;

import android.app.Application;

import com.gerus.android.popularmovies1.model.APIResponse;
import com.gerus.android.popularmovies1.model.Movie;
import com.gerus.android.popularmovies1.repository.MoviesRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MoviesViewModel extends AndroidViewModel {

	private MoviesRepository repository;
	private MutableLiveData<APIResponse<List<Movie>>> listMoviesPopular;
	private MutableLiveData<APIResponse<List<Movie>>> listMoviesTopRated;

	public MoviesViewModel(@NonNull Application application) {
		super(application);
		repository = MoviesRepository.getInstance(application);
		listMoviesPopular = repository.getPopular();
		listMoviesTopRated = repository.getTopRated();
	}

	MutableLiveData<APIResponse<List<Movie>>> getListMoviesPopular() {
		return listMoviesPopular;
	}

	MutableLiveData<APIResponse<List<Movie>>> getListMoviesTopRated() {
		return listMoviesTopRated;
	}
}
