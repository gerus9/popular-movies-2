package com.gerus.android.popularmovies1.utils;

import android.content.Context;

import com.gerus.android.popularmovies1.R;

public class ImageUtil {

	public static String buildURLPoster(Context context, String pathImage) {
		return context.getString(R.string.image_path, context.getString(R.string.image_size_poster), pathImage);
	}

	public static String buildURLBackdrop(Context context,String pathImage) {
		return context.getString(R.string.image_path, context.getString(R.string.image_size_backdrop), pathImage);
	}
}
