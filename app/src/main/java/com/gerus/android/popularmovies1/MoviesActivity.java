package com.gerus.android.popularmovies1;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.gerus.android.popularmovies1.adapter.MoviesAdapter;
import com.gerus.android.popularmovies1.adapter.MoviesAdapterCallback;
import com.gerus.android.popularmovies1.model.ErrorMessage;
import com.gerus.android.popularmovies1.model.Movie;
import com.gerus.android.popularmovies1.repository.MoviesRepository;
import com.gerus.android.popularmovies1.repository.interfaces.MoviesCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesActivity extends AppCompatActivity implements MoviesAdapterCallback, PopupMenu.OnMenuItemClickListener, MoviesCallback {

	private MoviesAdapter mAdapter;
	private int filterSelected = R.id.filter_popular;
	private MoviesRepository moviesRepository;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		moviesRepository = new MoviesRepository(getApplicationContext());
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

	private void fetchDataByFilterSelected() {
		if (filterSelected == R.id.filter_popular) {
			moviesRepository.getPopular(this);
		} else {
			moviesRepository.getTopRated(this);
		}
	}

	private void showErrorDialog(ErrorMessage errorMessage) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage(errorMessage.getErrorMsg());
		switch (errorMessage.getType()) {
			case ErrorMessage.ErrorType.CRITICAL:
				alertDialogBuilder.setNeutralButton(getString(android.R.string.ok), (dialogInterface, i) -> finish());
				break;
			case ErrorMessage.ErrorType.REQUEST:
				alertDialogBuilder.setPositiveButton(getString(R.string.retry), (dialogInterface, i) -> fetchDataByFilterSelected());
				alertDialogBuilder.setNegativeButton(getString(android.R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());
				break;
		}
		alertDialogBuilder.show();
	}

	@Override
	public void onClickEmptyLayout() {
		fetchDataByFilterSelected();
	}

	@Override
	public void onItemSelected(Movie movie) {
		Intent intent = new Intent(this, MovieDetailActivity.class);
		intent.putExtra(Movie.ID, movie);
		startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.filter_menu) {
			PopupMenu popup = new PopupMenu(this, findViewById(R.id.filter_menu));
			popup.setOnMenuItemClickListener(this);
			popup.inflate(R.menu.filter);
			popup.getMenu().findItem(filterSelected).setChecked(true);
			popup.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case R.id.filter_popular:
				filterSelected = R.id.filter_popular;
				fetchDataByFilterSelected();
				break;
			case R.id.filter_top_rated:
				filterSelected = R.id.filter_top_rated;
				fetchDataByFilterSelected();
				break;
		}
		return false;
	}

	@Override
	public void onSuccess(List<Movie> movieList) {
		mAdapter.addData(movieList);
	}

	@Override
	public void onError(ErrorMessage message) {
		showErrorDialog(message);
	}
}
