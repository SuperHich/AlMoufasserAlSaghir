package com.almoufasseralsaghir.external;

import java.io.IOException;

import com.almoufasseralsaghir.utils.IMediaPlayerNotifier;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

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
		
		}catch(IOException ex){ex.printStackTrace();}
		catch(IllegalStateException ex){ex.printStackTrace();}
	}
	
	public void play(String audio) throws IOException{
		
		stop();
		
	    AssetFileDescriptor afd = context.getAssets().openFd(audio);
	    
	    player = new MediaPlayer();
	    player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
	    player.prepare();
	    player.start();
	}
	
	public String formatAssetSong(String track){
		return "PLAYER/"+track+".mp3";
	}
}
