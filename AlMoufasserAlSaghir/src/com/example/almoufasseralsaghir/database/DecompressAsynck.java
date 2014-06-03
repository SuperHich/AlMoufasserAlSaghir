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

	private static final String TAG = "DecompressAsynck";
	private String _zipFile;   
	private String _location;
	private int per = 0;
	DownloadNotifier _notifier;
	
	FileInputStream fin ; 
	ZipInputStream zin; 
	ZipEntry ze = null ;
	byte [] bufReader = new byte [5000];

	public DecompressAsynck(String zipFile, String location, DownloadNotifier notifier) {
		_zipFile = zipFile;     
		_location = location;      
		dirChecker(location); 
		_notifier = notifier;
	}    

	@Override
	protected Integer doInBackground(Void... params) {
		try  {       
			ZipFile zip = new ZipFile(_zipFile);
			_notifier.configureProgress(zip.size());
			
			fin = new FileInputStream(_zipFile); 
			  zin = new ZipInputStream(fin); 
			  ze = null; 
	      
	      
			  while ((ze = nextEntry (zin) ) != null && !isCancelled()) { 
				  Log.v(TAG, "Unzipping: " + ze.getName()); 
	 
				  if(ze.isDirectory()) {
					  dirChecker(_location + ze.getName()); 
				  } 
				  else
				  { 
					  per++;
					  int unzip_progress =  (int) ((double)per / (double)zip.size() * 100f);
					  publishProgress((int)unzip_progress);
					  
					  FileOutputStream fout = new FileOutputStream (_location + ze.getName()); 
					  int c;
					  
					  while ((c = zin.read (bufReader)) != -1) {
						  fout.write(bufReader, 0, c);
					  }
					  fout.close(); 
					  fout.flush();

					  zin.closeEntry(); 
				  } 
			  } 
			  zin.close();
		} catch(Exception e) {       
			Log.e("Decompress", "unzip", e);    
		}    
		
		return per;
	}    
	
	@Override
	protected void onProgressUpdate(Integer... progress) {	
		_notifier.onProgressDownload(progress[0]); //Since it's an inner class, Bar should be able to be called directly
	}
	
	@Override
	protected void onPostExecute(Integer result) {		
		_notifier.onDownloadComplete();
		
		if(!isCancelled())
		{	
			File file = new File(_zipFile);
			file.delete();
		}
	}
	
	  private ZipEntry nextEntry (ZipInputStream zin) {
		  
		  boolean terminated = false;
		  ZipEntry ze = null;

		  while (! terminated) {
			  try {
				  ze = zin.getNextEntry();
				  terminated = true;
			  }
			  catch (Exception e) {
					Log.e(TAG, "Extract File Invalid File Name : "  + e.getMessage() );
			  }
		  }

		  return ze;
	  }
	  
	public static void dirChecker(String dir) {
		File f = new File(dir);
		if (!f.isDirectory()) {
			f.mkdirs();
		}
	}

}