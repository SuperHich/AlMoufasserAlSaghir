package com.almoufasseralsaghir.database;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class AlMoufasserDownloadManager {
	
	protected String URL_FILE;

	private static final String TAG = null;
	protected static AlMoufasserDownloadManager mInstance;
	protected Context context;
	private long downloadID = -1;
	private boolean isDownloading = false;
	private boolean isUnzipping = false;
	private DownloadManager downloadManager;
	private DownloadNotifier notifier;
	
	private ProgressThread progressThread;
	private DecompressAsynck decompressAsync;
	
	protected String basePath;
	protected String thePath;
	protected String zipFile;
	
	protected String folderName;
	protected String zipFileName;
	protected String description;
	
	protected int numberOfFiles = -1;
	
	protected boolean isDataReady = true;
	
	public synchronized static AlMoufasserDownloadManager getInstance(Context context) {
		if (mInstance == null)
			mInstance = new AlMoufasserDownloadManager(context);

		return mInstance;
	}
	
	private IntentFilter downloadCompleteIntentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
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
	    	    notifier.onErrorDownload();
	    	    isDownloading = false;
	    	    return;
	    	} 
	    	
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
	
	public void setDownloadNotifier(Context context){
		notifier = (DownloadNotifier) context;
	}
	
	public boolean initializeDownload(){
		
		if(_isFileExist(thePath) && (isNumberOfFilesComplete() && numberOfFiles != -1))
			return false;
		else if(_isFileExist(zipFile) )
		{	
			try {

				ZipFile zip = new ZipFile(zipFile);

				if(zip.size() == numberOfFiles){
					decompressAsync = new DecompressAsynck(zipFile, thePath, notifier);
					decompressAsync.execute();
					setUnzipping(true);

					return true;
				}else
				{
					File file = new File(zipFile);
					file.delete();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			File file = new File(zipFile);
			file.delete();
		}

		clearAllFiles();
		
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL_FILE));

		// only download via WIFI
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		request.setTitle(folderName);
		request.setDescription(description);

		// we just want to download silently
		request.setVisibleInDownloadsUi(false);
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
		request.setDestinationInExternalFilesDir(context, null, zipFileName);

		// enqueue this request
		downloadID = downloadManager.enqueue(request);

		// when initialize
		context.registerReceiver(downloadCompleteReceiver, downloadCompleteIntentFilter);

		isDownloading = downloadID != -1;

		if(isDownloading){
			progressThread = new ProgressThread();
			progressThread.start();
		}

		return isDownloading;
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
		
		if(progressThread != null){
			progressThread.interrupt();
			progressThread = null;
		}
		
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
	
	private void clearAllFiles(){
		try{
			File dir = new File(basePath);
			for(File fd : dir.listFiles()){
				if(fd.isFile())
					fd.delete();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
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

	public boolean isDataReady() {
		return isDataReady;
	}

	public void setDataReady(boolean isDataReady) {
		this.isDataReady = isDataReady;
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

//	                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
//	                    isDownloading = false;
//	                }

	                final int dl_progress = (int) ((double)bytes_downloaded / (double)bytes_total * 100f);

	                notifier.onProgressDownload((int) dl_progress);
	                
	                cursor.close();
	                
	                try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            }

	        }
	}
	
	protected boolean isNumberOfFilesComplete(){
		File dir = new File(thePath);
		File childfile[] = dir.listFiles();
		
		if(childfile != null)
			return childfile.length == numberOfFiles - 1;
		
		return false;
	}

}
