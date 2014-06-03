package com.example.almoufasseralsaghir;

import android.annotation.SuppressLint;
import android.app.Dialog;
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

import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.Utils;
import com.example.almoufasseralsaghir.database.DownloadNotifier;
import com.example.almoufasseralsaghir.database.MainReceiterDownloadManager;
import com.example.almoufasseralsaghir.entity.User;



@SuppressLint("HandlerLeak")
public class SplashHome extends MySuperScaler implements DownloadNotifier {
	private static final String TAG = "SplashHome";
	private static final int STOPSPLASH = 0;
	private static final long SPLASHTIME = 2000;
	
	private MainReceiterDownloadManager downloadManager;

	private SeekBar mPB;

	private FontFitTextView mStatusText;
	private FontFitTextView mProgressPercent;

	private View mDownloaderLayout;

	private Dialog mCellDialog;
	
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
		
		downloadManager = new MainReceiterDownloadManager(this);
		
		mPB = (SeekBar) findViewById(R.id.progressBar);
		mStatusText = (FontFitTextView) findViewById(R.id.statusText);
		mProgressPercent = (FontFitTextView) findViewById(R.id.progressAsPercentage);
		mDownloaderLayout = findViewById(R.id.downloaderLayout);

		mCellDialog = new Dialog(this);
		
		ShapeDrawable thumb = new ShapeDrawable(new RectShape());
	    thumb.getPaint().setColor(Color.rgb(0, 0, 0));
	    thumb.setIntrinsicHeight(-80);
	    thumb.setIntrinsicWidth(30);
	    mPB.setThumb(thumb);
	    mPB.setMax(100);
		
		if(downloadManager.initializeDownload()) {
			
			mDownloaderLayout.setVisibility(View.VISIBLE);
			mStatusText.setVisibility(View.VISIBLE);	
			mStatusText.setText("Downloading");
		}
		else
		{
			Message msg = new Message();
			msg.what = STOPSPLASH;

			splashHandler.sendMessageDelayed(msg, SPLASHTIME);
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			splashHandler.removeMessages(STOPSPLASH);
			if(downloadManager.isDownloading())
				downloadManager.cancelDownload();
			else if(downloadManager.isUnzipping())
				downloadManager.cancelUnzip();
		}
		return super.onKeyDown(keyCode, event);


	}	
	
//	private void showCellPopup(Context context) {
//		
//		// Alert dialogue
//		mCellDialog = new Dialog(context);
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//				context);
//		// set dialog message
//		alertDialogBuilder
//				.setTitle("Warning !")
//				.setMessage(getString(R.string.text_paused_cellular) + "\n" + getString(R.string.text_paused_cellular_2))
//				.setCancelable(false)
//				.setPositiveButton(R.string.text_button_resume_cellular, new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//						
//						mRemoteService.setDownloadFlags(IDownloaderService.FLAGS_DOWNLOAD_OVER_CELLULAR);
//						mRemoteService.requestContinueDownload();
//						dismissCellPopup();
//						
//					}
//				})
//				.setNegativeButton(R.string.text_button_wifi_settings, new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//						
//						startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//						
//					}
//				});
//						
//		// show it
//		mCellDialog = alertDialogBuilder.show();
//	}
//	
//	private void dismissCellPopup(){
//		if(mCellDialog != null){
//			mCellDialog.dismiss();
//		}
//	}
	
	@Override
	public void onProgressDownload(final int progress) {
		SplashHome.this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mPB.setProgress(progress);
				mProgressPercent.setText(progress + " %");
			}
		});
		
		Log.e(TAG, "progress " + mPB.getProgress());
	}

	@Override
	public void onDownloadComplete() {
		
		Log.e(TAG, "download and unzip completed ");
		
		downloadManager.setUnzipping(false);
		
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
				
				mStatusText.setVisibility(View.VISIBLE);
				mStatusText.setText("Unzipping");
			}
		});
		
	}

}