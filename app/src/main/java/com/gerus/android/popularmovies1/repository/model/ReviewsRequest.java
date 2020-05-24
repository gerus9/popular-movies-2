package com.gerus.android.popularmovies1.repository.model;

import com.gerus.android.popularmovies1.model.ReviewsInfo;

import java.util.List;

public class ReviewsRequest {

	private int id;
	private List<ReviewsInfo> results;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ReviewsInfo> getResults() {
		return results;
	}

	public void setResults(List<ReviewsInfo> results) {
		this.results = results;
	}
}
