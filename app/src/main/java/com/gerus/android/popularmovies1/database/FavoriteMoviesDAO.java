package com.gerus.android.popularmovies1.database;

import com.gerus.android.popularmovies1.model.Movie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FavoriteMoviesDAO {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertFavorite(FavoriteMovie id);

	@Delete
	void deleteFavorite(FavoriteMovie id);

	@Query("SELECT * FROM Favorite WHERE id = :id")
	FavoriteMovie isFavorite(int id);

	@Query("SELECT * FROM Movies AS mov INNER JOIN Favorite as fav WHERE mov.id = fav.id")
	List<Movie> getListFavorites();
}
