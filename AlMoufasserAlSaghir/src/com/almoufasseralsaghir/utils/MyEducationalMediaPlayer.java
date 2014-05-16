package com.almoufasseralsaghir.utils;

import android.content.Context;
import android.media.MediaPlayer;


public class MyEducationalMediaPlayer  {
	
	Context context ;
	private MyEducationalMediaPlayer(Context context) {
		this.context = context;
	}
	
	public void StartSequence(Context context, int raw ){
		MediaPlayer mp = new MediaPlayer();
		mp = MediaPlayer.create(context, raw);
		mp.start();
	}
	public void StopSequence(MediaPlayer mp ){
		
		mp.release();
		mp = null ;
	}
	
	public static void StopAnySound(){
//		if(M8_Learn.mp != null) M8_Learn.mp.release();
//		if(M8_Learn.mp2 != null)M8_Learn.mp2.release();
//		if(M8_Practise_Ex1.mp != null)M8_Practise_Ex1.mp.release();
//		if(M8_Practise_Ex2.mp != null)M8_Practise_Ex2.mp.release();
//		if(M8_Practise_Ex3.mp != null)M8_Practise_Ex3.mp.release();
//		if(M8_Practise_Ex4.mp != null)M8_Practise_Ex4.mp.release();
//		if(M8_Practise_Ex5.mp != null)M8_Practise_Ex5.mp.release();
//		
//		////////////////////////////////////////////////////////////////////////////////////////////////
//		
//		if(M9_Learn.mp != null) M9_Learn.mp.release();
//		if(M9_Practise_Ex1.mp != null)M9_Practise_Ex1.mp.release();
//		if(M9_Practise_Ex2.mp != null)M9_Practise_Ex2.mp.release();
//		if(M9_Practise_Ex3.mp != null)M9_Practise_Ex3.mp.release();
//		
//		////////////////////////////////////////////////////////////////////////////////////////////////
//				
//		if(M10_Learn.mp != null) M10_Learn.mp.release();
//		if(M10_Practise_Ex1.mp != null)M10_Practise_Ex1.mp.release();
//		if(M10_Practise_Ex2.mp != null)M10_Practise_Ex2.mp.release();
//		if(M10_Practise_Ex3.mp != null)M10_Practise_Ex3.mp.release();
//		if(M10_Practise_Ex4_Part1.mp != null)M10_Practise_Ex4_Part1.mp.release();
//		
//		////////////////////////////////////////////////////////////////////////////////////////////////
//		
//		if(M11_Learn.mp != null) M11_Learn.mp.release();
//		if(M11_Practise_Ex1.mp != null)M11_Practise_Ex1.mp.release();
//		if(M11_Practise_Ex2.mp != null)M11_Practise_Ex2.mp.release();
//		if(M11_Practise_Ex3.mp != null)M11_Practise_Ex3.mp.release();
//		if(M11_Practise_Ex3.mp != null)M11_Practise_Ex3.mp.release();
//		
//		////////////////////////////////////////////////////////////////////////////////////////////////
//		
//		if(M12_Learn.mp != null) M12_Learn.mp.release();
//		if(M12_Practise_Ex1.mp != null)M12_Practise_Ex1.mp.release();
//		if(M12_Practise_Ex2.mp != null)M12_Practise_Ex2.mp.release();
//		if(M12_Practise_Ex3.mp != null)M12_Practise_Ex3.mp.release();
	
	}
	
	public static void PauseAnySound(){
//		if(M8_Learn.mp != null) M8_Learn.mp.pause();
//		if(M8_Learn.mp2 != null)M8_Learn.mp2.pause();
//		if(M8_Practise_Ex1.mp != null)M8_Practise_Ex1.mp.pause();
//		if(M8_Practise_Ex2.mp != null)M8_Practise_Ex2.mp.pause();
//		if(M8_Practise_Ex3.mp != null)M8_Practise_Ex3.mp.pause();
//		if(M8_Practise_Ex4.mp != null)M8_Practise_Ex4.mp.pause();
//		if(M8_Practise_Ex5.mp != null)M8_Practise_Ex5.mp.pause();
//		
//		if(M8_Regles_Part1.mp != null)M8_Regles_Part1.mp.pause();
//		if(M8_Regles_Part1.mp2 != null)M8_Regles_Part1.mp2.pause();
//		if(M8_Regles_Part2_Ex1.mp != null)M8_Regles_Part2_Ex1.mp.pause();
//		if(M8_Regles_Part2_Ex2_Part1.mp != null)M8_Regles_Part2_Ex2_Part1.mp.pause();
//		if(M8_Regles_Part2_Ex2_Part2.mp != null)M8_Regles_Part2_Ex2_Part2.mp.pause();
//		if(M8_Regles_Part2_Ex3_Part1.mp != null)M8_Regles_Part2_Ex3_Part1.mp.pause();
//		if(M8_Regles_Part2_Ex3_Part2.mp != null)M8_Regles_Part2_Ex3_Part2.mp.pause();
//		if(M8_Regles_Part2_Ex4.mp != null)M8_Regles_Part2_Ex4.mp.pause();
//		if(M8_Regles_Part2_Ex5.mp != null)M8_Regles_Part2_Ex5.mp.pause();
//		
//		if(M8_Ecriture_Part1.mp != null)M8_Ecriture_Part1.mp.pause();
//		if(M8_Ecriture_Part2.mp != null)M8_Ecriture_Part2.mp.pause();

	}
	
	public static void ResumeAnySound(){
//		if(M8_Learn.mp != null) M8_Learn.mp.start();
//		if(M8_Learn.mp2 != null)M8_Learn.mp2.start();
//		if(M8_Practise_Ex1.mp != null)M8_Practise_Ex1.mp.start();
//		if(M8_Practise_Ex2.mp != null)M8_Practise_Ex2.mp.start();
//		if(M8_Practise_Ex3.mp != null)M8_Practise_Ex3.mp.start();
//		if(M8_Practise_Ex4.mp != null)M8_Practise_Ex4.mp.start();
//		if(M8_Practise_Ex5.mp != null)M8_Practise_Ex5.mp.start();
//		
//		if(M8_Regles_Part1.mp != null)M8_Regles_Part1.mp.start();
//		if(M8_Regles_Part1.mp2 != null)M8_Regles_Part1.mp2.start();
//		if(M8_Regles_Part2_Ex1.mp != null)M8_Regles_Part2_Ex1.mp.start();
//		if(M8_Regles_Part2_Ex2_Part1.mp != null)M8_Regles_Part2_Ex2_Part1.mp.start();
//		if(M8_Regles_Part2_Ex2_Part2.mp != null)M8_Regles_Part2_Ex2_Part2.mp.start();
//		if(M8_Regles_Part2_Ex3_Part1.mp != null)M8_Regles_Part2_Ex3_Part1.mp.start();
//		if(M8_Regles_Part2_Ex3_Part2.mp != null)M8_Regles_Part2_Ex3_Part2.mp.start();
//		if(M8_Regles_Part2_Ex4.mp != null)M8_Regles_Part2_Ex4.mp.start();
//		if(M8_Regles_Part2_Ex5.mp != null)M8_Regles_Part2_Ex5.mp.start();
//		
//		if(M8_Ecriture_Part1.mp != null)M8_Ecriture_Part1.mp.start();
//		if(M8_Ecriture_Part2.mp != null)M8_Ecriture_Part2.mp.start();

	}
	
}
