package com.almoufasseralsaghir.external;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.almoufasseralsaghir.utils.IMediaPlayerNotifier;

/**
 * AlMoufasserAlSaghir
 * @author HICHEM LAROUSSI - RAMI TRABELSI
 * Copyright (c) 2014 Zad Group. All rights reserved.
 */

public class TafseerMediaPlayer {

	public static MediaPlayer m ;
	private Context context;
	private MediaPlayer player;
	private IMediaPlayerNotifier notifier;
	
	public TafseerMediaPlayer(Context context) {
		this.context = context;
		notifier = (IMediaPlayerNotifier) context;
		
	}
	
//	public static void playAya(Context context, String ayaAudio){
//		
//		try {
//	        if (m.isPlaying()) {
//	            m.stop();
//	            m.release();
//	        //    m = new MediaPlayer();
//	        }
//
//	        AssetFileDescriptor descriptor = context.getAssets().openFd("PLAYER/"+ayaAudio+".mp3");
//	        m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
//	        descriptor.close();
//
//	        m.prepare();
//	    //    m.setVolume(1f, 1f);
//	    //    m.setLooping(true);
//	        m.start();
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//		
//	}
	
	public boolean isPlaying(){
		
		if (player != null) return player.isPlaying();
		return false ;
	}
	
	public void stop(){
		if (player != null){
			player.stop();
			player.release();
			player = null ;
		}
		
	}
	
	public void playWithCompletion(String audio){
		try{
			stop();

			AssetFileDescriptor afd = context.getAssets().openFd(audio);

			player = new MediaPlayer();
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

			player.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {

					notifier.onCompletion();

				}
			});

			player.prepare();
			player.start();

		}catch(IOException ex) {
			ex.printStackTrace();
		}
		catch(IllegalStateException ex) {
			ex.printStackTrace();
		}
	}
	
	public void playFromSdcardWithCompletion(String audio){
		try{
			stop();

			player = new MediaPlayer();
			player.setDataSource(audio);

			player.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {

					notifier.onCompletion();

				}
			});

			player.prepare();
			player.start();

		}catch(IOException ex) {
			ex.printStackTrace();
		}
		catch(IllegalStateException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void play(String audio) {
		try{
			stop();

			AssetFileDescriptor afd = context.getAssets().openFd(audio);

			player = new MediaPlayer();
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			player.prepare();
			player.start();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		catch(IllegalStateException ex) {
			ex.printStackTrace();
		}
	}
	
	public void playFromSdcard(String audio) {
		try{
			stop();

			player = new MediaPlayer();
			player.setDataSource(audio);
			player.prepare();
			player.start();

		}catch(IOException ex) {
			ex.printStackTrace();
		}
		catch(IllegalStateException ex) {
			ex.printStackTrace();
		}
	}
	
	public String formatAssetSong(String track){
		return "PLAYER/"+track+".mp3";
	}
	
	public String formatReceiterSDCardSong(String track, String defaultReceiter){
	
		if(defaultReceiter.equals("2")){
			
			return TafseerManager.SecondReceiterPath + track + ".mp3";
		}
		
		return TafseerManager.MainReceiterPath + track + ".mp3";
	}
	
	public String shuffleAdviceSong(String track){
		return TafseerManager.AdvicesPath + track + ".mp3";
//		int track = 1;
//		do{
//			Random random = new Random();
//			track = random.nextInt(TafseerManager.MAX_ADVICES_MP3);
//		}while(TafseerManager.EXCLUDED_ADVICES_MP3.contains(track));
//	
//		return TafseerManager.AdvicesPath + track + ".mp3";
	}
}
