package com.gerus.android.popularmovies1;

import android.app.Application;

import com.gerus.android.popularmovies1.repository.MoviesRepository;
import com.gerus.android.popularmovies1.utils.MoviesRepositoryUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MovieDetailViewModel extends AndroidViewModel {

	private MoviesRepository repository;

	public MovieDetailViewModel(@NonNull Application application) {
		super(application);
		repository = new MoviesRepositoryUtils(application).getRepository();
	}

	LiveData<Boolean> isFavorite(int idMovie) {
		return repository.isFavorite(idMovie);
	}

	void setFavoriteMovie(int idMovie){
		repository.setFavoriteMovie(idMovie);
	}

	void unFavoriteMovie(int idMovie){
		repository.deleteFavoriteMovie(idMovie);
	}


}
