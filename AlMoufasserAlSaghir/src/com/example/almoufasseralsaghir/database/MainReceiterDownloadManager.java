package com.example.almoufasseralsaghir.database;

import java.io.File;

import android.content.Context;

import com.almoufasseralsaghir.external.TafseerManager;

public class MainReceiterDownloadManager extends AlMoufasserDownloadManager {

	public MainReceiterDownloadManager(Context context) {
		super(context);
	}
	
	@Override
	public boolean initializeDownload() {
		
		numberOfFiles = 1607;
		
		URL_FILE = "http://islam.ws/tafseer/MP3s.zip";
		
		File d = context.getExternalFilesDir(null);
		String basePath = d.getAbsolutePath() + File.separator;
		
		folderName = "MP3s";
		zipFileName = "MP3s.zip";
		
		thePath = basePath;
		zipFile = basePath + zipFileName;
		
		description = "Downloading "+ folderName +" MP3's songs";
		
		TafseerManager.MainReceiterPath = thePath + folderName + File.separator;
		
		return super.initializeDownload();
	}
	
	@Override
	protected boolean isNumberOfFilesComplete() {
		File dir = new File(thePath + folderName + File.separator);
		File childfile[] = dir.listFiles();
		
		if(childfile != null)
			return childfile.length == numberOfFiles - 1;
		
		return false;
	}

}
