package com.gerus.android.popularmovies1.repository;

import android.content.Context;

import com.gerus.android.popularmovies1.R;
import com.gerus.android.popularmovies1.model.ErrorMessage;
import com.gerus.android.popularmovies1.repository.interfaces.MoviesAPI;
import com.gerus.android.popularmovies1.repository.interfaces.MoviesCallback;
import com.gerus.android.popularmovies1.repository.model.ErrorRequest;
import com.gerus.android.popularmovies1.repository.model.MovieRequest;
import com.gerus.android.popularmovies1.utils.ConfigUtil;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class MoviesRepository {

	private final String BASE_URL = "https://api.themoviedb.org/3/";
	private final String PROPERTIES = "config.properties";
	private final String PROPERTIES_API_KEY = "apiKey";

	private Retrofit retrofit;
	private MoviesAPI moviesAPI;
	private String key;
	private Context context;

	public MoviesRepository(Context context) {
		this.context = context;
		retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
		moviesAPI = retrofit.create(MoviesAPI.class);
		key = ConfigUtil.getProperties(PROPERTIES, context).getProperty(PROPERTIES_API_KEY);
	}

	@EverythingIsNonNull
	public void getTopMovies(@NonNull final MoviesCallback callback) {
		moviesAPI.getTopRated(key).enqueue(new Callback<MovieRequest>() {

			@Override
			public void onResponse(Call<MovieRequest> call, Response<MovieRequest> response) {
				if (response.isSuccessful() && response.body() != null && response.body().getResults() != null && !response.body().getResults().isEmpty()) {
					callback.onSuccess(response.body().getResults());
				} else {
					callback.onError(processError(response));
				}
			}

			@Override
			public void onFailure(Call<MovieRequest> call, Throwable t) {
				callback.onError(new ErrorMessage(ErrorMessage.ErrorType.REQUEST, context.getString(R.string.error_network)));
			}
		});
	}

	private ErrorMessage processError(Response errorBody) {
		try {
			String errorMsg = (errorBody.errorBody() != null) ? errorBody.errorBody().string() : "";
			ErrorRequest errorRequest = new Gson().fromJson(errorMsg, ErrorRequest.class);
			if (errorRequest.getStatusCode() == ErrorRequest.ERROR_CODE_KEY) {
				return new ErrorMessage(ErrorMessage.ErrorType.CRITICAL, errorRequest.getStatusMessage());
			} else {
				return new ErrorMessage(ErrorMessage.ErrorType.REQUEST, errorRequest.getStatusMessage());
			}
		} catch (Exception e) {
			return new ErrorMessage(ErrorMessage.ErrorType.REQUEST, context.getString(R.string.error_server));
		}
	}
}
