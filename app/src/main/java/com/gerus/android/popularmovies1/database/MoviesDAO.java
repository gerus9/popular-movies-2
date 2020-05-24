package com.gerus.android.popularmovies1.database;

import com.gerus.android.popularmovies1.model.Movie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MoviesDAO {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertMovies(List<Movie> movieList);

	@Query("DELETE FROM movies")
	void deleteAllMovies();
}
