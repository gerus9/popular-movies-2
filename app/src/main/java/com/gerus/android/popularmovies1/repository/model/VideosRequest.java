package com.gerus.android.popularmovies1.repository.model;

import com.gerus.android.popularmovies1.model.VideoInfo;

import java.util.List;

public class VideosRequest {

	private int id;
	private List<VideoInfo> results;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<VideoInfo> getResults() {
		return results;
	}

	public void setResults(List<VideoInfo> results) {
		this.results = results;
	}
}
