package com.gerus.android.popularmovies1.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favorite")
public class FavoriteMovie {

	@PrimaryKey
	private int id;

	FavoriteMovie() {

	}

	public FavoriteMovie(int idMovie) {
		this.id = idMovie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
