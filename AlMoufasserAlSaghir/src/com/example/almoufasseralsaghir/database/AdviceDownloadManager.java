package com.example.almoufasseralsaghir.database;

import java.io.File;

import android.content.Context;

import com.almoufasseralsaghir.external.TafseerManager;

public class AdviceDownloadManager extends AlMoufasserDownloadManager {

	public AdviceDownloadManager(Context context) {
		super(context);
	}
	
	@Override
	public boolean initializeDownload() {
		
		numberOfFiles = 350;
		
		URL_FILE = "https://islam.ws/tafseer/AdviceMP3s.zip";
		
		File d = context.getExternalFilesDir(null);
		String basePath = d.getAbsolutePath() + File.separator;
		
		folderName = "AdviceMP3s";
		zipFileName = "AdviceMP3s.zip";
		
		thePath = basePath;
		zipFile = basePath + zipFileName;
		
		description = "Downloading "+ folderName +" MP3's songs";
		
		TafseerManager.AdvicesPath = thePath + folderName + File.separator;
		
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
