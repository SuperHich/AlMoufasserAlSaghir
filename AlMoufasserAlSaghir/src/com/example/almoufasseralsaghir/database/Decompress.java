package com.example.almoufasseralsaghir.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import android.util.Log;

/**
 * @author Hichem.Laroussi - admin
 *
 */
public class Decompress {

	private final String TAG = "FSDS: " + getClass().getSimpleName();

	private String _zipFile; 
	private String _location; 

	FileInputStream fin ; 
	ZipInputStream zin; 
	ZipEntry ze = null ;
	byte [] bufReader = new byte [5000];


	public Decompress(String zipFile, String location) { 
		_zipFile = zipFile; 
		_location = location; 

		_dirChecker(""); 
	} 


	private ZipEntry nextEntry () {

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


	private boolean extractFile (String fileName) {

		try {

			FileOutputStream fout = new FileOutputStream (fileName); 
			int c;

			while ((c = zin.read (bufReader)) != -1) {
				fout.write(bufReader, 0, c);
			}
			fout.close(); 
			fout.flush();
		}

		catch (Exception e)
		{
			Log.e(TAG, "Extract File Failed: "  + e.getMessage()); 
			return false;		  
		}

		return true;
	}


	public boolean unzip() { 

		try  { 
			fin = new FileInputStream(_zipFile); 
			zin = new ZipInputStream(fin); 
			ze = null; 


			while ((ze = nextEntry () ) != null) { 
				Log.v(TAG, "Unzipping: " + ze.getName()); 

				if(ze.isDirectory()) {
					_dirChecker(ze.getName()); 
				} 
				else
				{ 
					extractFile (_location + ze.getName());
					zin.closeEntry(); 
				} 
			} 
			zin.close(); 
		} 
		catch(Exception e) { 
			Log.e(TAG, "Unzip failed: "  + e.getMessage()); 

			return false;
		} 

		return true;
	} 



	private void _dirChecker(String dir) { 
		File f = new File(_location + dir); 

		if(!f.isDirectory()) { 
			f.mkdirs(); 
		} 
	} 
} 