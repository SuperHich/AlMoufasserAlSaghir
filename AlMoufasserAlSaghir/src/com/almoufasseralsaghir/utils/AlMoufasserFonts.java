package com.almoufasseralsaghir.utils;

import android.content.Context;
import android.graphics.Typeface;

public class AlMoufasserFonts {
	
	private static Typeface ge_ss_med;
	
	public static void InitAlMoufasserFonts(Context context){
		ge_ss_med  				= Typeface.createFromAsset(context.getAssets(), "fonts/ge-ss-med.ttf");
	}

	public static Typeface getFont_ge_ss_med() {
		return ge_ss_med;
	}
}
