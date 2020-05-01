package com.gerus.android.popularmovies1.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

	private static final String TAG = ConfigUtil.class.getSimpleName();

	public static Properties getProperties(String nameFile, Context context) {
		Properties properties = new Properties();
		try{
			AssetManager assetManager = context.getAssets();
			InputStream inputStream = assetManager.open(nameFile);
			properties.load(inputStream);
		} catch (Exception e){
			Log.e(TAG, "Properties files not exists "+nameFile, e);
		}
		return properties;
	}
}
