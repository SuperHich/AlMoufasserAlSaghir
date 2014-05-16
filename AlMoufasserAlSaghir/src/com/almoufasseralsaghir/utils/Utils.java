package com.almoufasseralsaghir.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;

public class Utils {

	
	public static void animateFad(Activity pActivity){
		pActivity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
	
	@SuppressLint("NewApi")
	public static void animateFrags(android.support.v4.app.FragmentTransaction ft2){
		ft2.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
	}
	
	public static void stopMediaPlayer(MediaPlayer mp){
			if (mp != null) {
				mp.stop();
				mp.release();
			}
	}
	public void startMediaPlayer(MediaPlayer mp){
		if (mp != null) {
			mp.stop();
			mp.release();
		}
	}
}
