package com.almoufasseralsaghir.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * AlMoufasserAlSaghir
 * @author HICHEM LAROUSSI - RAMI TRABELSI
 * Copyright (c) 2014 Zad Group. All rights reserved.
 */

public class AlMoufasserFonts {
	
	private static Typeface ge_ss_med;
	
	public static void InitAlMoufasserFonts(Context context){
		ge_ss_med  				= Typeface.createFromAsset(context.getAssets(), "fonts/GE_SS_Two_Bold.otf");
	}

	public static Typeface getFont_ge_ss_med() {
		return ge_ss_med;
	}
}
