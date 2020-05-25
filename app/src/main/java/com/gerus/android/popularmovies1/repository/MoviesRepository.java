package com.gerus.android.popularmovies1.repository;

import com.gerus.android.popularmovies1.AppExecutors;
import com.gerus.android.popularmovies1.R;
import com.gerus.android.popularmovies1.database.FavoriteMovie;
import com.gerus.android.popularmovies1.database.MoviesDatabase;
import com.gerus.android.popularmovies1.model.APIResponse;
import com.gerus.android.popularmovies1.model.ErrorMessage;
import com.gerus.android.popularmovies1.model.Movie;
import com.gerus.android.popularmovies1.model.ReviewsInfo;
import com.gerus.android.popularmovies1.model.VideoInfo;
import com.gerus.android.popularmovies1.repository.interfaces.MoviesAPI;
import com.gerus.android.popularmovies1.repository.model.ErrorRequest;
import com.gerus.android.popularmovies1.repository.model.MovieRequest;
import com.gerus.android.popularmovies1.repository.model.ReviewsRequest;
import com.gerus.android.popularmovies1.repository.model.VideosRequest;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MoviesRepository implements MovieReposityCallback {

	private static final Object LOCK = new Object();

	private static MoviesRepository sInstance;
	private final MoviesAPI moviesAPI;
	private MoviesDatabase moviesDB;
	private final String key;
	private final AppExecutors executors;

	private MoviesRepository(MoviesAPI moviesAPI, MoviesDatabase moviesDB, AppExecutors executors, String key) {
		this.moviesAPI = moviesAPI;
		this.moviesDB = moviesDB;
		this.key = key;
		this.executors = executors;
	}

	public static MovieReposityCallback getInstance(MoviesAPI moviesAPI, MoviesDatabase moviesDB, AppExecutors executors, String keyAPI) {
		if (sInstance == null) {
			synchronized (LOCK) {
				sInstance = new MoviesRepository(moviesAPI, moviesDB, executors, keyAPI);
			}
		}
		return sInstance;
	}

	@EverythingIsNonNull
	public MutableLiveData<APIResponse<List<Movie>>> getPopular() {
		MutableLiveData<APIResponse<List<Movie>>> listMutableLiveData = new MutableLiveData<>();
		moviesAPI.getPopular(key).enqueue(new Callback<MovieRequest>() {

			@Override
			public void onResponse(Call<MovieRequest> call, Response<MovieRequest> response) {
				processResponseLiveDataList(listMutableLiveData, response);
			}

			@Override
			public void onFailure(Call<MovieRequest> call, Throwable t) {
				listMutableLiveData.postValue(new APIResponse<>(getErrorNetwork()));
			}
		});
		return listMutableLiveData;
	}

	@EverythingIsNonNull
	public MutableLiveData<APIResponse<List<Movie>>> getTopRated() {
		MutableLiveData<APIResponse<List<Movie>>> listMutableLiveData = new MutableLiveData<>();
		moviesAPI.getTopRated(key).enqueue(new Callback<MovieRequest>() {

			@Override
			public void onResponse(Call<MovieRequest> call, Response<MovieRequest> response) {
				processResponseLiveDataList(listMutableLiveData, response);
			}

			@Override
			public void onFailure(Call<MovieRequest> call, Throwable t) {
				listMutableLiveData.postValue(new APIResponse<>(getErrorNetwork()));
			}
		});
		return listMutableLiveData;
	}

	@Override
	public MutableLiveData<List<VideoInfo>> getVideoInfo(int idMovie) {
		MutableLiveData<List<VideoInfo>> listMutableLiveData = new MutableLiveData<>();
		moviesAPI.getVideos(idMovie, key).enqueue(new Callback<VideosRequest>() {

			@Override
			public void onResponse(@NonNull Call<VideosRequest> call, @NonNull Response<VideosRequest> response) {
				if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
					listMutableLiveData.postValue((response.body().getResults()));
				}
			}

			@Override
			public void onFailure(@NonNull Call<VideosRequest> call, @NonNull Throwable t) {

			}
		});
		return listMutableLiveData;
	}

	@Override
	public MutableLiveData<List<ReviewsInfo>> getReviewsInfo(int idMovie) {
		MutableLiveData<List<ReviewsInfo>> listMutableLiveData = new MutableLiveData<>();
		moviesAPI.getReviews(idMovie, key).enqueue(new Callback<ReviewsRequest>() {

			@Override
			public void onResponse(@NonNull Call<ReviewsRequest> call, @NonNull Response<ReviewsRequest> response) {
				if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
					listMutableLiveData.postValue((response.body().getResults()));
				}
			}

			@Override
			public void onFailure(@NonNull Call<ReviewsRequest> call, @NonNull Throwable t) {

			}
		});
		return listMutableLiveData;
	}

	@Override
	public MutableLiveData<Boolean> isFavorite(int idMovie) {
		MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();
		executors.diskIO().execute(() -> {
			FavoriteMovie favoriteMovie = moviesDB.favoriteDAO().isFavorite(idMovie);
			isFavorite.postValue(favoriteMovie != null);
		});
		return isFavorite;
	}

	@Override
	public void setFavoriteMovie(int idMovie) {
		executors.diskIO().execute(() -> moviesDB.favoriteDAO().insertFavorite(new FavoriteMovie(idMovie)));
	}

	@Override
	public void deleteFavoriteMovie(int idMovie) {
		executors.diskIO().execute(() -> moviesDB.favoriteDAO().deleteFavorite(new FavoriteMovie(idMovie)));
	}

	private void processResponseLiveDataList(MutableLiveData<APIResponse<List<Movie>>> listMutableLiveData, Response<MovieRequest> response) {
		if (response.isSuccessful() && response.body() != null && response.body().getResults() != null && !response.body().getResults().isEmpty()) {
			addNewMovies(response.body().getResults());
			listMutableLiveData.postValue(new APIResponse<>(response.body().getResults()));
		} else {
			listMutableLiveData.postValue(new APIResponse<>(processError(response)));
		}
	}

	public LiveData<List<Movie>> getFavoriteMovies() {
		return moviesDB.favoriteDAO().getListFavorites();
	}

	private void addNewMovies(List<Movie> results) {
		executors.diskIO().execute(() -> moviesDB.moviesDAO().insertMovies(results));
	}

	private ErrorMessage getErrorNetwork() {
		return new ErrorMessage(ErrorMessage.ErrorType.REQUEST, R.string.error_network);
	}

	private ErrorMessage processError(Response<MovieRequest> errorBody) {
		try {
			String errorMsg = (errorBody.errorBody() != null) ? errorBody.errorBody().string() : "";
			ErrorRequest errorRequest = new Gson().fromJson(errorMsg, ErrorRequest.class);
			if (errorRequest.getStatusCode() == ErrorRequest.ERROR_CODE_KEY) {
				return new ErrorMessage(ErrorMessage.ErrorType.CRITICAL, errorRequest.getStatusMessage());
			} else {
				return new ErrorMessage(ErrorMessage.ErrorType.REQUEST, errorRequest.getStatusMessage());
			}
		} catch (Exception e) {
			return new ErrorMessage(ErrorMessage.ErrorType.REQUEST, R.string.error_server);
		}
	}
}
