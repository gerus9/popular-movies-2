package com.gerus.android.popularmovies1.model;

public class APIResponse<T> {

	private T data;
	private ErrorMessage error;

	public APIResponse(T data) {
		this.data = data;
	}

	public APIResponse(ErrorMessage error) {
		this.error = error;
	}

	public T getData() {
		return data;
	}

	public ErrorMessage getError() {
		return error;
	}

	public boolean hasError() {
		return error != null;
	}
}