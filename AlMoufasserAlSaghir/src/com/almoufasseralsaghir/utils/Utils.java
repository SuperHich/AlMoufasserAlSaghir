package com.almoufasseralsaghir.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

	
	public static void animateFad(Activity pActivity){
		pActivity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
	
	public static void animateSlide(Activity pActivity){
		pActivity.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
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
	public static boolean isOnline(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	
}
