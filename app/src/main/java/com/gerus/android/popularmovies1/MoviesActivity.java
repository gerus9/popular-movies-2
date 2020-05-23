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
import com.gerus.android.popularmovies1.model.APIResponse;
import com.gerus.android.popularmovies1.model.ErrorMessage;
import com.gerus.android.popularmovies1.model.Movie;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesActivity extends AppCompatActivity implements MoviesAdapterCallback, PopupMenu.OnMenuItemClickListener {

	private MoviesAdapter mAdapter;
	private @FilterType
	int filterSelected = FilterType.POPULAR;


	@Retention(RetentionPolicy.SOURCE)
	public @interface FilterType {

		int POPULAR = 0;

		int TOP_RATED = 1;
	}


	private MoviesViewModel viewModel;

	private static String BUNDLE_MOVIES = "Movies";
	private static String BUNDLE_FILTER = "Filter";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		if (savedInstanceState != null) {
			filterSelected = savedInstanceState.getInt(BUNDLE_FILTER);
		}
		viewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
		fetchDataByFilterSelected();
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
		LifecycleOwner owner = this;
		if (filterSelected == FilterType.POPULAR) {
			viewModel.getListMoviesPopular().observe(owner, listAPIResponse -> {
				viewModel.getListMoviesPopular().removeObservers(owner);
				parserAPIResponse(listAPIResponse);
			});
		} else {
			viewModel.getListMoviesTopRated().observe(this, listAPIResponse -> {
				viewModel.getListMoviesTopRated().removeObservers(owner);
				parserAPIResponse(listAPIResponse);
			});
		}
	}

	private void parserAPIResponse(APIResponse<List<Movie>> listAPIResponse) {
		if (listAPIResponse.hasError()) {
			showErrorDialog(listAPIResponse.getError());
		} else {
			setAdapterList(listAPIResponse.getData());
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
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		ArrayList<Movie> arrayList = (ArrayList<Movie>) mAdapter.getListItems();
		outState.putParcelableArrayList(BUNDLE_MOVIES, arrayList);
		outState.putInt(BUNDLE_FILTER, filterSelected);
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.filter_menu) {
			PopupMenu popup = new PopupMenu(this, findViewById(R.id.filter_menu));
			popup.setOnMenuItemClickListener(this);
			popup.inflate(R.menu.filter);
			popup.getMenu().findItem(getFilterId()).setChecked(true);
			popup.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private int getFilterId() {
		return filterSelected == FilterType.TOP_RATED ? R.id.filter_top_rated : R.id.filter_popular;
	}

	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case R.id.filter_popular:
				filterSelected = FilterType.POPULAR;
				fetchDataByFilterSelected();
				break;
			case R.id.filter_top_rated:
				filterSelected = FilterType.TOP_RATED;
				fetchDataByFilterSelected();
				break;
		}
		return false;
	}

	private void setAdapterList(List<Movie> movieList) {
		if (mAdapter != null) {
			mAdapter.addData(movieList);
		}
	}
}
