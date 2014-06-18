package com.almoufasseralsaghir.utils;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.almoufasseralsaghir.R;


public class DensityHelper {

	static DensityHelper myInstance;
	public static int density, height, width;
	
	public final static int MAIN_BUTTONS_PADDING = 180;
	public final static int MAIN_BUTTONS_PADDING_TABLET = 360;
	private static boolean tabletSize = false;
	
	
	public static DensityHelper getInstance(Context context) {
		if (myInstance == null)
			myInstance = new DensityHelper(context);
		return myInstance;
	}

	public DensityHelper(Context context) {
		initFonts(context);
		InitDensityHelper(context);
	}

	public static void InitDensityHelper (Context context) {

		DisplayMetrics metrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		density = metrics.densityDpi;
		
		tabletSize = context.getResources().getBoolean(R.bool.isTablet);
		if (tabletSize) {
			height = metrics.widthPixels;
			width = metrics.heightPixels;
		} else {
			height = metrics.heightPixels;
			width = metrics.widthPixels;
		}

	}
	
	
	public static float getScaledValue(float initial){
		if(tabletSize)
			return (density * initial) / 160f;
		else
			return (density * initial) / 320f;
	}
	
	public static float convertDpToPixel(int valueDp){
		return valueDp * (density / 160f);
	}

	public static float convertPixelToDp(int valuePx){
		return valuePx / (density / 160f);
	}
	
	
	private void initFonts(Context context){
		
	}

}
