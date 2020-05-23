package com.gerus.android.popularmovies1.repository;

import android.annotation.SuppressLint;
import android.content.Context;

import com.gerus.android.popularmovies1.R;
import com.gerus.android.popularmovies1.model.APIResponse;
import com.gerus.android.popularmovies1.model.ErrorMessage;
import com.gerus.android.popularmovies1.model.Movie;
import com.gerus.android.popularmovies1.repository.interfaces.MoviesAPI;
import com.gerus.android.popularmovies1.repository.model.ErrorRequest;
import com.gerus.android.popularmovies1.repository.model.MovieRequest;
import com.gerus.android.popularmovies1.utils.ConfigUtil;
import com.google.gson.Gson;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class MoviesRepository {

	@SuppressLint("StaticFieldLeak")
	private static MoviesRepository repository;
	private final MoviesAPI moviesAPI;
	private final String key;
	private final Context context;

	private MoviesRepository(Context context) {
		final String BASE_URL = "https://api.themoviedb.org/3/";
		final String PROPERTIES = "config.properties";
		final String PROPERTIES_API_KEY = "apiKey";

		this.context = context;
		Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
		moviesAPI = retrofit.create(MoviesAPI.class);
		key = ConfigUtil.getProperties(PROPERTIES, context).getProperty(PROPERTIES_API_KEY);
	}

	public static MoviesRepository getInstance(Context context) {
		if (repository == null) {
			repository = new MoviesRepository(context);
		}
		return repository;
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

	private void processResponseLiveDataList(MutableLiveData<APIResponse<List<Movie>>> listMutableLiveData, Response<MovieRequest> response) {
		if (response.isSuccessful() && response.body() != null && response.body().getResults() != null && !response.body().getResults().isEmpty()) {
			listMutableLiveData.postValue(new APIResponse<>(response.body().getResults()));
		} else {
			listMutableLiveData.postValue(new APIResponse<>(processError(response)));
		}
	}

	private ErrorMessage getErrorNetwork() {
		return new ErrorMessage(ErrorMessage.ErrorType.REQUEST, context.getString(R.string.error_network));
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
			return new ErrorMessage(ErrorMessage.ErrorType.REQUEST, context.getString(R.string.error_server));
		}
	}
}
