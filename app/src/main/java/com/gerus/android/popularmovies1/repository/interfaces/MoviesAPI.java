package com.gerus.android.popularmovies1.repository.interfaces;

import com.gerus.android.popularmovies1.repository.model.MovieRequest;
import com.gerus.android.popularmovies1.repository.model.ReviewsRequest;
import com.gerus.android.popularmovies1.repository.model.VideosRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesAPI {

	@GET("movie/popular/")
	Call<MovieRequest> getPopular(@Query("api_key") String apiKey);

	@GET("movie/top_rated/")
	Call<MovieRequest> getTopRated(@Query("api_key") String apiKey);

	@GET("movie/{movieId}/videos")
	Call<VideosRequest> getVideos(@Path("movieId") int movie, @Query("api_key") String apiKey);

	@GET("movie/{movieId}/reviews")
	Call<ReviewsRequest> getReviews(@Path("movieId") int movie, @Query("api_key") String apiKey);
}
