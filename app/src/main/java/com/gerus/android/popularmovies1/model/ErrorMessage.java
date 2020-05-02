package com.gerus.android.popularmovies1.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

public class ErrorMessage {

	@Retention(RetentionPolicy.SOURCE)
	@IntDef({ErrorType.CRITICAL, ErrorType.REQUEST})
	public @interface ErrorType {
		int CRITICAL = 0;
		int REQUEST = 1;
	}

	private final int type;
	private String errorMsg;

	public ErrorMessage(int type, String errorMsg) {
		this.type = type;
		this.errorMsg = errorMsg;
	}

	public int getType() {
		return type;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
