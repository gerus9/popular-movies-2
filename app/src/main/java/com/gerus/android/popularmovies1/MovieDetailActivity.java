package com.gerus.android.popularmovies1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.gerus.android.popularmovies1.adapter.ReviewsAdapter;
import com.gerus.android.popularmovies1.adapter.TrailersAdapter;
import com.gerus.android.popularmovies1.adapter.TrailersAdapterCallback;
import com.gerus.android.popularmovies1.databinding.ActivityDetailBinding;
import com.gerus.android.popularmovies1.model.Movie;
import com.gerus.android.popularmovies1.model.VideoInfo;
import com.gerus.android.popularmovies1.utils.ImageUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieDetailActivity extends AppCompatActivity implements TrailersAdapterCallback {

	private RecyclerView recyclerViewTrailers;
	private RecyclerView recyclerViewReviews;
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
				setVideos();
				setReviews();
			} else {
				closeOnError();
			}
		} else {
			closeOnError();
		}
	}

	private void setVideos() {
		RecyclerView recyclerviewVideo = Objects.requireNonNull(mBinding.cardTrailers).getRecyclerView();
		TrailersAdapter trailersAdapter = new TrailersAdapter(new ArrayList<>(), this);
		recyclerviewVideo.setLayoutManager(new LinearLayoutManager(this));
		recyclerviewVideo.setAdapter(trailersAdapter);
		model.getVideos(movie.getId()).observe(this, trailersAdapter::addData);
	}

	private void setReviews() {
		RecyclerView recyclerviewVideo = Objects.requireNonNull(mBinding.cardReviews).getRecyclerView();
		ReviewsAdapter reviewsAdapter = new ReviewsAdapter(new ArrayList<>());
		recyclerviewVideo.setLayoutManager(new LinearLayoutManager(this));
		recyclerviewVideo.setAdapter(reviewsAdapter);
		model.getReviews(movie.getId()).observe(this, reviewsAdapter::addData);
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

	@Override
	public void onTrailerSelected(VideoInfo videoInfo) {
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=" + videoInfo.getName()));
		webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(webIntent);
	}
}
