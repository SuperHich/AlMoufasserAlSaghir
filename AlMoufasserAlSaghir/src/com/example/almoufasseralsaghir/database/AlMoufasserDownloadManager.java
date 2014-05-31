package com.example.almoufasseralsaghir.database;

import java.io.File;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.almoufasseralsaghir.external.TafseerManager;

public class AlMoufasserDownloadManager {
	
	final static String URL_FILE = "http://islam.ws/tafseer/basfar.zip";
//	final static String URL_FILE = "https://sites.google.com/site/compiletimeerrorcom/android-programming/CameraApp.rar";

	private static final String TAG = null;
	private static AlMoufasserDownloadManager mInstance;
	private Context context;
	private long downloadID;
	private boolean isDownloading = false;
	
	public synchronized static AlMoufasserDownloadManager getInstance(Context context) {
		if (mInstance == null)
			mInstance = new AlMoufasserDownloadManager(context);

		return mInstance;
	}
	
	private String downloadCompleteIntentName = DownloadManager.ACTION_DOWNLOAD_COMPLETE;
	private IntentFilter downloadCompleteIntentFilter = new IntentFilter(downloadCompleteIntentName);
	private BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	
	    	long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L);
	    	if (id != downloadID) {
	    	    Log.v(TAG, "Ingnoring unrelated download " + id);
	    	    return;
	    	}
	    	
	    	DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
			DownloadManager.Query query = new DownloadManager.Query();
			query.setFilterById(id);
			Cursor cursor = downloadManager.query(query);

			// it shouldn't be empty, but just in case
			if (!cursor.moveToFirst()) {
			    Log.e(TAG, "Empty row");
			    return;
			}
			
	    	int statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
	    	int downloadedBytesIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
	    	int totalBytesIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
	    	if(DownloadManager.STATUS_RUNNING == cursor.getInt(statusIndex)) {
	    		int bytes_downloaded = cursor.getInt(downloadedBytesIndex);
	    		int bytes_total = cursor.getInt(totalBytesIndex);
	    		
	    		final int dl_progress = (int) ((double)bytes_downloaded / (double)bytes_total * 100f);
	    		Log.w(TAG, dl_progress + "%");
	    	}
	    	else if (DownloadManager.STATUS_SUCCESSFUL != cursor.getInt(statusIndex)) {
	    	    Log.w(TAG, "Download Failed");
	    	    isDownloading = false;
	    	    return;
	    	} 

	    	int uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
	    	String downloadedPackageUriString = cursor.getString(uriIndex);
	    	
	    	Log.v("downloadedPackageUriString", downloadedPackageUriString);
	    	
	    	File downloadZipFile = new File(downloadedPackageUriString);
//	    	String thePath = downloadZipFile.getAbsolutePath() + File.separator;
	    	
	    	File d = context.getExternalFilesDir(null); 
	    	String thePath;
	    	if(d != null){
	    		thePath = d.getAbsolutePath() + File.separator;
	    		
	    		Decompress z = new Decompress (downloadZipFile.getAbsolutePath(), thePath );
				z.unzip();

				// no need of the zip file, then remove it
				downloadZipFile.delete();
		    	
		    	TafseerManager.getInstance(context).getLoggedInUser().setDefaultReciter("2");
	    	}
	    	
	    	
	    	
	    }
	};



	public AlMoufasserDownloadManager(Context context) {

		this.context = context.getApplicationContext();
		
	}
	
	public boolean initializeDownload(){
		
		File d = context.getExternalFilesDir(null);
		String thePath = d.getAbsolutePath() + File.separator + "basfar";
		if(_isFolderExist(thePath))
			return false;
	
		
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL_FILE));

		// only download via WIFI
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		request.setTitle("basfar");
		request.setDescription("Downloading basfar MP3's songs");

		// we just want to download silently
		request.setVisibleInDownloadsUi(false);
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
		request.setDestinationInExternalFilesDir(context, null, "basfar.zip");
//		request.setDestinationInExternalFilesDir(context, null, "CameraApp.rar");
		
		// enqueue this request
		DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		downloadID = downloadManager.enqueue(request);
		
		// when initialize
		context.registerReceiver(downloadCompleteReceiver, downloadCompleteIntentFilter);
		
		isDownloading = true;
		
		return true;
	}
	
	public void queryDownloadState(long id){
		DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		DownloadManager.Query query = new DownloadManager.Query();
		query.setFilterById(id);
		Cursor cursor = downloadManager.query(query);

		// it shouldn't be empty, but just in case
		if (!cursor.moveToFirst()) {
		    Log.e(TAG, "Empty row");
		    return;
		}
	}
	
	public void cancelDownload(){
		// remove this request
		DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		isDownloading = downloadManager.remove(downloadID) > 0;
		
		unregisterReceiver();
	}
	
	public void unregisterReceiver(){
		// when exit
		context.unregisterReceiver(downloadCompleteReceiver);
	}

	private boolean _isFolderExist(String path){
			File folder = new File(path);
			if(folder.exists())
				return true;
			
			return false;
		}
	
	public boolean isDownloading(){
		return isDownloading;
	}
}
