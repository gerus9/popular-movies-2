package com.gerus.android.popularmovies1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gerus.android.popularmovies1.model.Movie;
import com.gerus.android.popularmovies1.utils.ImageUtil;
import com.gerus.android.popularmovies1.view.CircleDisplay;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

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
		TextView description = findViewById(R.id.txt_release);
		description.setText(movie.getReleaseDate());
	}

	private void setDescription(Movie movie) {
		TextView description = findViewById(R.id.txt_description);
		description.setText(movie.getOverview());
	}

	private void setAverage(Movie movie) {
		CircleDisplay mCircleDisplay = findViewById(R.id.circleDisplay);
		mCircleDisplay.setTextSize(getResources().getDimension(R.dimen.size_text_radio));
		mCircleDisplay.setAnimDuration(1500);
		mCircleDisplay.setValueWidthPercent(35f);
		mCircleDisplay.setDrawText(true);
		mCircleDisplay.setFormatDigits(1);
		mCircleDisplay.setStepSize(0.5f);
		mCircleDisplay.showValue((float) (movie.getVoteAverage() * 10), 100f, true);
	}

	private void setImage(Movie movie) {
		ImageView imageView = findViewById(R.id.image);
		Picasso.get().load(getImage(movie)).placeholder(R.drawable.vc_placeholder).error(R.drawable.vc_error).into(imageView);
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
