package com.example.almoufasseralsaghir;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.Utils;
import com.example.almoufasseralsaghir.database.DataDownloadManager;
import com.example.almoufasseralsaghir.database.DownloadNotifier;
import com.example.almoufasseralsaghir.entity.User;

@SuppressLint("HandlerLeak")
public class SplashHome extends MySuperScaler implements DownloadNotifier {
	private static final String TAG = "SplashHome";
	private static final int STOPSPLASH = 0;
	private static final long SPLASHTIME = 2000;
	
	private DataDownloadManager ddm;

	private SeekBar mPB;

	private FontFitTextView mStatusText;
	private FontFitTextView mProgressPercent;

	private View mDownloaderLayout;
	
	private Handler splashHandler = new Handler() {
		private	Intent intent  ;

		public void handleMessage(Message msg) {

			User user = myDB.whoIsLoggedIn();
			mTafseerManager.setLoggedInUser(user);

			if (user.isLoggedIn())
			{	
				intent = new Intent(SplashHome.this, SouraActivity.class);
			} else 	
			{
				MainActivity.first_entry = true ;
				MainActivity.play_welcomer = true ;
				intent = new Intent(SplashHome.this, MainActivity.class);
			}
			SplashHome.this.startActivity(intent);
			Utils.animateSlide(SplashHome.this);
			SplashHome.this.finish();

			super.handleMessage(msg);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashhome);
		
		ddm = new DataDownloadManager(this);
		
		mPB = (SeekBar) findViewById(R.id.progressBar);
		mStatusText = (FontFitTextView) findViewById(R.id.statusText);
		mProgressPercent = (FontFitTextView) findViewById(R.id.progressAsPercentage);
		mDownloaderLayout = findViewById(R.id.downloaderLayout);

		ShapeDrawable thumb = new ShapeDrawable(new RectShape());
	    thumb.getPaint().setColor(Color.rgb(0, 0, 0));
	    thumb.setIntrinsicHeight(-80);
	    thumb.setIntrinsicWidth(30);
	    mPB.setThumb(thumb);
	    mPB.setMax(100);
		
		if(ddm.initializeDownload()) {
			
			mDownloaderLayout.setVisibility(View.VISIBLE);
			mStatusText.setVisibility(View.VISIBLE);	
			mStatusText.setText(R.string.downloading);
		}
		else if(ddm.isDataReady())
		{
			Message msg = new Message();
			msg.what = STOPSPLASH;

			splashHandler.sendMessageDelayed(msg, SPLASHTIME);
		}else
		{
			Toast.makeText(this, R.string.error_starting, Toast.LENGTH_LONG).show();
			finish();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			splashHandler.removeMessages(STOPSPLASH);
			if(ddm.isDownloading())
				ddm.cancelDownload();
			else if(ddm.isUnzipping())
				ddm.cancelUnzip();
		}
		return super.onKeyDown(keyCode, event);


	}	
	
	@Override
	public void onProgressDownload(final int progress) {
		SplashHome.this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mPB.setProgress(progress);
				mProgressPercent.setText(progress + " %");
			}
		});
		
//		Log.e(TAG, "progress " + mPB.getProgress());
	}

	@Override
	public void onDownloadComplete() {
		
		Log.e(TAG, "download and unzip completed ");
		
		if(ddm.isUnzipping())
		{
			ddm.setUnzipping(false);
		}
		
		// All data ready... We can launch app
		SplashHome.this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mDownloaderLayout.setVisibility(View.GONE);
				
				Message msg = new Message();
				msg.what = STOPSPLASH;

				splashHandler.sendMessageDelayed(msg, SPLASHTIME);
			}
		});
		
	}
	
	@Override
	public void configureProgress(final int maxSize) {
		
		SplashHome.this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mPB.setProgress(0);				
				mStatusText.setText(R.string.unzipping);
			}
		});
		
	}
	
	@Override
	public void onErrorDownload() {
		ddm.cancelDownload();
		
		showPopUp(this, R.string.error_download);
	};
	
	public void showPopUp(Context context, int message) {

		// Alert dialogue
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		// set dialog message
		alertDialogBuilder
				.setMessage(context.getResources().getString(message))
				.setCancelable(false)
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						finish();
					}
				});
		// show it
		alertDialogBuilder.show();
	}

}