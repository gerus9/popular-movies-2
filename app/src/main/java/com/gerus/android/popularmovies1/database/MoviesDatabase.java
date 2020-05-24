package com.gerus.android.popularmovies1.database;

import android.content.Context;

import com.gerus.android.popularmovies1.model.Movie;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Movie.class, FavoriteMovie.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

	private static final String DATABASE_NAME = "movies_db";

	// For Singleton instantiation
	private static final Object LOCK = new Object();
	private static MoviesDatabase sInstance;

	public static MoviesDatabase getInstance(Context context) {
		if (sInstance == null) {
			synchronized (LOCK) {
				sInstance = Room.databaseBuilder(context.getApplicationContext(),
												 MoviesDatabase.class, MoviesDatabase.DATABASE_NAME).build();
			}
		}
		return sInstance;
	}

	@NonNull
	@Override
	protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
		return getOpenHelper();
	}

	@NonNull
	@Override
	protected InvalidationTracker createInvalidationTracker() {
		return getInvalidationTracker();
	}

	@Override
	public void clearAllTables() {

	}

	// The associated DAOs for the database
	public abstract MoviesDAO moviesDAO();

	public abstract FavoriteMoviesDAO favoriteDAO();
}
