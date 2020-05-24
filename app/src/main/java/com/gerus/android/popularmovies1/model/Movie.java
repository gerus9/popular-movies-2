package com.gerus.android.popularmovies1.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Movies")
public class Movie implements Parcelable {

	public static final String ID = Movie.class.getSimpleName();

	@PrimaryKey
	private int id;
	private double popularity;
	@SerializedName("vote_count")
	private int voteCount;
	private boolean video;
	@SerializedName("poster_path")
	private String posterPath;
	private boolean adult;
	@SerializedName("backdrop_path")
	private String backdropPath;
	@SerializedName("original_language")
	private String originalLanguage;
	@SerializedName("original_title")
	private String originalTitle;
	private String title;
	@SerializedName("vote_average")
	private double voteAverage;
	private String overview;
	@SerializedName("release_date")
	private String releaseDate;

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(this.popularity);
		dest.writeInt(this.voteCount);
		dest.writeByte(this.video ? (byte) 1 : (byte) 0);
		dest.writeString(this.posterPath);
		dest.writeInt(this.id);
		dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
		dest.writeString(this.backdropPath);
		dest.writeString(this.originalLanguage);
		dest.writeString(this.originalTitle);
		dest.writeString(this.title);
		dest.writeDouble(this.voteAverage);
		dest.writeString(this.overview);
		dest.writeString(this.releaseDate);
	}

	public Movie() {
	}

	private Movie(Parcel in) {
		this.popularity = in.readDouble();
		this.voteCount = in.readInt();
		this.video = in.readByte() != 0;
		this.posterPath = in.readString();
		this.id = in.readInt();
		this.adult = in.readByte() != 0;
		this.backdropPath = in.readString();
		this.originalLanguage = in.readString();
		this.originalTitle = in.readString();
		this.title = in.readString();
		this.voteAverage = in.readDouble();
		this.overview = in.readString();
		this.releaseDate = in.readString();
	}

	public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

		@Override
		public Movie createFromParcel(Parcel source) {
			return new Movie(source);
		}

		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};
}
