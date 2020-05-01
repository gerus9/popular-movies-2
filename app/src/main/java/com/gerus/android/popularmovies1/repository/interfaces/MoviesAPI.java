package com.gerus.android.popularmovies1.repository.interfaces;

import com.gerus.android.popularmovies1.repository.model.MovieRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesAPI {
	@GET("movie/popular/")
	Call<MovieRequest> getPopular(@Query("api_key") String apiKey);

	@GET("movie/top_rated/")
	Call<MovieRequest> getTopRated(@Query("api_key") String apiKey);
}
