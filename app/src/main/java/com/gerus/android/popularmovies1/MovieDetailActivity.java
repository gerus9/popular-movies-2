package com.gerus.android.popularmovies1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.gerus.android.popularmovies1.databinding.ActivityDetailBinding;
import com.gerus.android.popularmovies1.model.Movie;
import com.gerus.android.popularmovies1.utils.ImageUtil;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class MovieDetailActivity extends AppCompatActivity {

	private ActivityDetailBinding mBinding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

		if (validateMovieData(getIntent())) {
			Movie movie = getIntent().getParcelableExtra(Movie.ID);
			if (movie != null) {
				setTitle(movie.getTitle());
				setToolbar();
				setImage(movie);
				setRelease(movie);
				setAverage(movie);
				setDescription(movie);
			} else {
				closeOnError();
			}
		} else {
			closeOnError();
		}
	}

	private void setRelease(Movie movie) {
		mBinding.txtRelease.setText(movie.getReleaseDate());
	}

	private void setDescription(Movie movie) {
		mBinding.txtDescription.setText(movie.getOverview());
	}

	private void setAverage(Movie movie) {
		mBinding.circleDisplay.setTextSize(getResources().getDimension(R.dimen.size_text_radio));
		mBinding.circleDisplay.setAnimDuration(1500);
		mBinding.circleDisplay.setValueWidthPercent(35f);
		mBinding.circleDisplay.setDrawText(true);
		mBinding.circleDisplay.setFormatDigits(1);
		mBinding.circleDisplay.setStepSize(0.5f);
		mBinding.circleDisplay.showValue((float) (movie.getVoteAverage() * 10), 100f, true);
	}

	private void setImage(Movie movie) {
		Picasso.get().load(getImage(movie)).placeholder(R.drawable.vc_placeholder).error(R.drawable.vc_error).into(mBinding.image);
	}

	private void closeOnError() {
		Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
		finish();
	}

	private boolean validateMovieData(Intent intent) {
		return intent != null && intent.hasExtra(Movie.ID);
	}

	private void setToolbar() {
		setSupportActionBar(findViewById(R.id.toolbar));
		ActionBar voActionBar = getSupportActionBar();
		if (voActionBar != null) {
			voActionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	private String getImage(Movie movie) {
		int orientation = getResources().getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_LANDSCAPE || TextUtils.isEmpty(movie.getBackdropPath())) {
			return ImageUtil.buildURLPoster(this, movie.getPosterPath());
		} else {
			return ImageUtil.buildURLBackdrop(this, movie.getBackdropPath());
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
