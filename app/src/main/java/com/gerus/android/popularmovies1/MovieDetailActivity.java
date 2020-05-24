package com.gerus.android.popularmovies1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.gerus.android.popularmovies1.databinding.ActivityDetailBinding;
import com.gerus.android.popularmovies1.model.Movie;
import com.gerus.android.popularmovies1.utils.ImageUtil;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

public class MovieDetailActivity extends AppCompatActivity {

	private ActivityDetailBinding mBinding;
	private MovieDetailViewModel model;
	private MenuItem favoriteMenu;
	private Movie movie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		if (validateMovieData(getIntent())) {
			model = new ViewModelProvider(this).get(MovieDetailViewModel.class);
			mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
			movie = getIntent().getParcelableExtra(Movie.ID);
			if (movie != null) {
				setTitle(movie.getTitle());
				setImage();
				setRelease(movie);
				setAverage();
				setDescription();
			} else {
				closeOnError();
			}
		} else {
			closeOnError();
		}
	}

	private void setFavoriteMovie(Movie movie) {
		model.isFavorite(movie.getId()).observe(this, this::updateFavoriteUI);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_favorite, menu);
		favoriteMenu = menu.findItem(R.id.favorite_menu);
		setFavoriteMovie(movie);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.favorite_menu) {
			if (item.isChecked()) {
				model.setFavoriteMovie(movie.getId());
			} else {
				model.unFavoriteMovie(movie.getId());
			}
			updateFavoriteUI(item.isChecked());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateFavoriteUI(boolean checked) {
		favoriteMenu.setIcon(checked ? R.drawable.vc_star_full : R.drawable.vc_star_empty);
		favoriteMenu.setChecked(!checked);
	}

	private void setRelease(Movie movie) {
		mBinding.txtRelease.setText(movie.getReleaseDate());
	}

	private void setDescription() {
		mBinding.txtDescription.setText(movie.getOverview());
	}

	private void setAverage() {
		mBinding.circleDisplay.showValue((float) (movie.getVoteAverage() * 10), 100f, true);
	}

	private void setImage() {
		Picasso.get().load(getImage()).placeholder(R.drawable.vc_placeholder).error(R.drawable.vc_error).into(mBinding.image);
	}

	private void closeOnError() {
		Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
		finish();
	}

	private boolean validateMovieData(Intent intent) {
		return intent != null && intent.hasExtra(Movie.ID);
	}

	private String getImage() {
		return ImageUtil.buildURLPoster(this, this.movie.getPosterPath());
	}
}
