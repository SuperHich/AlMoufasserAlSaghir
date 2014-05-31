package com.example.almoufasseralsaghir.database;

import java.io.File;

import com.almoufasseralsaghir.external.TafseerManager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class AlMoufasserDownloadManager {
	
	final static String URL_FILE = "http://islam.ws/tafseer/basfar.zip";
//	final static String URL_FILE = "https://sites.google.com/site/compiletimeerrorcom/android-programming/CameraApp.rar";

	private static final String TAG = null;
	private static AlMoufasserDownloadManager mInstance;
	private Context context;
	private long downloadID;
	private boolean isDownloading = false;
	private boolean isUnzipping = false;
	private DownloadManager downloadManager;
	private DownloadNotifier notifier;
	
	private ProgressThread progressThread;
	private DecompressAsynck decompressAsync;
	private ZipManager zipManager = new ZipManager();
	
	String thePath;
	String zipFile;
	
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
	    	if (DownloadManager.STATUS_SUCCESSFUL != cursor.getInt(statusIndex)) {
	    	    Log.w(TAG, "Download Failed");
	    	    isDownloading = false;
	    	    return;
	    	} 

//	    	int uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
//	    	String downloadedPackageUriString = cursor.getString(uriIndex);
//	    	
//	    	Log.v("downloadedPackageUriString", downloadedPackageUriString);
//	    	
//	    	File downloadZipFile = new File(downloadedPackageUriString);
////	    	String thePath = downloadZipFile.getAbsolutePath() + File.separator;
//	    	
//	    	File d = context.getExternalFilesDir(null); 
//	    	String thePath;
//	    	if(d != null){
//	    		thePath = d.getAbsolutePath() + File.separator;
//	    		
//	    		zipManager.unzip(downloadZipFile.getAbsolutePath(), thePath);
//	    		
//	    		Decompress z = new Decompress (downloadZipFile.getAbsolutePath(), thePath );
//				z.unzip();
//
//				// no need of the zip file, then remove it
//				downloadZipFile.delete();
//		    	
//		    	TafseerManager.getInstance(context).getLoggedInUser().setDefaultReciter("2");
//	    	}
	    	
	    	decompressAsync = new DecompressAsynck(zipFile, thePath, notifier);
	    	decompressAsync.execute();
			
	    	isDownloading = false;
	    	setUnzipping(true);
	    	
	    }
	};



	public AlMoufasserDownloadManager(Context context) {

		this.context = context.getApplicationContext();
		downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		notifier = (DownloadNotifier) context;
		
	}
	
	public boolean initializeDownload(){
		
		File d = context.getExternalFilesDir(null);
		String basePath = d.getAbsolutePath() + File.separator;
		thePath = basePath + "basfar" + File.separator;
		zipFile = d.getAbsolutePath() + File.separator + "basfar.zip";
		
		TafseerManager.SecondReceiterPath = thePath;
		
		if(_isFileExist(thePath))
			return false;
		else if(_isFileExist(zipFile))
		{			
			decompressAsync = new DecompressAsynck(zipFile, thePath, notifier);
	    	decompressAsync.execute();
			setUnzipping(true);

    		return true;
		}
		
	
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL_FILE));

		// only download via WIFI
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		request.setTitle("basfar");
		request.setDescription("Downloading basfar MP3's songs");

		// we just want to download silently
		request.setVisibleInDownloadsUi(false);
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
		request.setDestinationInExternalFilesDir(context, null, "basfar.zip");
		
		// enqueue this request
		downloadID = downloadManager.enqueue(request);
		
		// when initialize
		context.registerReceiver(downloadCompleteReceiver, downloadCompleteIntentFilter);
		
		isDownloading = true;
		
		progressThread = new ProgressThread();
		progressThread.start();
		
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
		isDownloading = downloadManager.remove(downloadID) == 0;
		
		progressThread.interrupt();
		progressThread = null;
		
		unregisterReceiver();
	}
	
	public void cancelUnzip(){
		// remove this request		
		decompressAsync.cancel(true);
		setUnzipping(false);
	}
	
	public void unregisterReceiver(){
		// when exit
		context.unregisterReceiver(downloadCompleteReceiver);
	}

	private boolean _isFileExist(String path){
		File file = new File(path);
		if(file.exists())
			return true;

		return false;
	}
	
	public boolean isDownloading(){
		return isDownloading;
	}
	
	public boolean isUnzipping() {
		return isUnzipping;
	}

	public void setUnzipping(boolean isUnzipping) {
		this.isUnzipping = isUnzipping;
	}

	class ProgressThread extends Thread{
		 @Override
	        public void run() {

	            while (isDownloading) {

	                DownloadManager.Query q = new DownloadManager.Query();
	                q.setFilterById(downloadID);

	                Cursor cursor = downloadManager.query(q);
	                cursor.moveToFirst();
	                int bytes_downloaded = cursor.getInt(cursor
	                        .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
	                int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

	                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
	                    isDownloading = false;
	                }

	                final int dl_progress = (int) ((double)bytes_downloaded / (double)bytes_total * 100f);

	                notifier.onProgressDownload((int) dl_progress);
	                
	                cursor.close();
	                
	                try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }

	        }
	}

}
