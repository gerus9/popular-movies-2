package com.gerus.android.popularmovies1.repository.model;

import com.google.gson.annotations.SerializedName;

public class ErrorRequest {

	public final static int ERROR_CODE_KEY = 7;

	@SerializedName("status_message")
	private String statusMessage;
	@SerializedName("status_code")
	private int statusCode;

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
