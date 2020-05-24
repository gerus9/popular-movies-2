package com.gerus.android.popularmovies1.utils;

import android.app.Application;

import com.gerus.android.popularmovies1.AppExecutors;
import com.gerus.android.popularmovies1.database.MoviesDatabase;
import com.gerus.android.popularmovies1.repository.MoviesRepository;
import com.gerus.android.popularmovies1.repository.interfaces.MoviesAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepositoryUtils {

	private Application application;
	private final String BASE_URL = "https://api.themoviedb.org/3/";
	private final String PROPERTIES = "config.properties";
	private final String PROPERTIES_API_KEY = "apiKey";

	public MoviesRepositoryUtils(Application application) {
		this.application = application;
	}

	public MoviesRepository getRepository() {
		AppExecutors executors = AppExecutors.getInstance();
		Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
		MoviesAPI moviesAPI = retrofit.create(MoviesAPI.class);
		MoviesDatabase moviesDatabase = MoviesDatabase.getInstance(application);
		String key = ConfigUtil.getProperties(PROPERTIES, application).getProperty(PROPERTIES_API_KEY);
		return MoviesRepository.getInstance(moviesAPI, moviesDatabase, executors, key);
	}
}
