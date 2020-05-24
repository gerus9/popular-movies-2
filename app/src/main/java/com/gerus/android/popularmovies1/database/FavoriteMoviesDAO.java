package com.gerus.android.popularmovies1.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavoriteMoviesDAO {

	@Insert
	void insertFavorite(FavoriteMovie id);

	@Delete
	void deleteFavorite(FavoriteMovie id);

	@Query("SELECT * FROM Favorite WHERE id = :id")
	FavoriteMovie isFavorite(int id);

}
