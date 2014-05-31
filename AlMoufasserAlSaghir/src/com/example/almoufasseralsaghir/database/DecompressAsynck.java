package com.example.almoufasseralsaghir.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import android.os.AsyncTask;
import android.util.Log;

public class DecompressAsynck extends AsyncTask<Void, Integer, Integer> {

	private String _zipFile;   
	private String _location;
	private int per = 0;
	private ZipManager zipManager = new ZipManager();
	DownloadNotifier _notifier;

	public DecompressAsynck(String zipFile, String location, DownloadNotifier notifier) {
		_zipFile = zipFile;     
		_location = location;      
		zipManager.dirChecker(location); 
		_notifier = notifier;
	}    

	protected Integer doInBackground(Void... params) {
		try  {       
			ZipFile zip = new ZipFile(_zipFile);
			_notifier.configureProgress(zip.size());
			FileInputStream fin = new FileInputStream(_zipFile);       
			ZipInputStream zin = new ZipInputStream(fin);
			ZipEntry ze = null;       
			while ((ze = zin.getNextEntry()) != null && !isCancelled()) {

				Log.v("Decompress", "Unzipping " + ze.getName());          
				if(ze.isDirectory()) {           
					zipManager.dirChecker(ze.getName());         
				} else {      
					// Here I am doing the update of my progress bar

					per++;
					publishProgress(per);

					FileOutputStream fout = new FileOutputStream(_location +ze.getName());           
					for (int c = zin.read(); c != -1; c = zin.read()) {  

						fout.write(c);           
					}            
					zin.closeEntry();          
					fout.close();         
				}                
			}       
			zin.close();    
		} catch(Exception e) {       
			Log.e("Decompress", "unzip", e);    
		}    
		
		return per;
	}    
	
	protected void onProgressUpdate(Integer... progress) {
		_notifier.onProgressDownload(per); //Since it's an inner class, Bar should be able to be called directly
	}

	protected void onPostExecute(Integer... result) {
		_notifier.onDownloadComplete();
		
		if(!isCancelled())
		{	
			File file = new File(_zipFile);
			file.delete();
		}
	}

}