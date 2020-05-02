package com.gerus.android.popularmovies1;

import android.os.Bundle;

import com.gerus.android.popularmovies1.adapter.MoviesAdapter;
import com.gerus.android.popularmovies1.adapter.MoviesAdapterCallback;
import com.gerus.android.popularmovies1.model.ErrorMessage;
import com.gerus.android.popularmovies1.model.Movie;
import com.gerus.android.popularmovies1.repository.MoviesRepository;
import com.gerus.android.popularmovies1.repository.interfaces.MoviesCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesActivity extends AppCompatActivity implements MoviesAdapterCallback {
	private MoviesAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();

	}

	private void initViews() {
		final GridLayoutManager mLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.numberColumns));
		mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(int position) {
				return mAdapter.getNumberColumns(position);
			}
		});
		mAdapter = new MoviesAdapter(this, new ArrayList<>(), this);
		RecyclerView mRecyclerView = findViewById(R.id.rcl_movies);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setAdapter(mAdapter);
	}

	private void fetchData() {
		MoviesRepository moviesRepository = new MoviesRepository(getApplicationContext());
		moviesRepository.getTopMovies(new MoviesCallback() {

			@Override
			public void onSuccess(List<Movie> movieList) {
				mAdapter.addData(movieList);
			}

			@Override
			public void onError(ErrorMessage message) {
				showErrorDialog(message);
			}
		});
	}

	private void showErrorDialog(ErrorMessage errorMessage){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage(errorMessage.getErrorMsg());
		switch (errorMessage.getType()){
			case ErrorMessage.ErrorType.CRITICAL:
				alertDialogBuilder.setNeutralButton(getString(android.R.string.ok), (dialogInterface, i) -> finish());
			break;
			case ErrorMessage.ErrorType.REQUEST:
				alertDialogBuilder.setPositiveButton(getString(R.string.retry), (dialogInterface, i) -> fetchData());
				alertDialogBuilder.setNegativeButton(getString(android.R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());
				break;
		}
		alertDialogBuilder.show();
	}

	@Override
	public void onClickEmptyLayout() {
		fetchData();
	}

	@Override
	public void onItemSelected(Movie movie) {

	}
}
