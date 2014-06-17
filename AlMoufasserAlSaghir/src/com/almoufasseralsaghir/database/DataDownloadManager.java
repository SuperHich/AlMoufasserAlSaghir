package com.almoufasseralsaghir.database;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;

import com.almoufasseralsaghir.external.TafseerManager;

public class DataDownloadManager extends AlMoufasserDownloadManager {

	public DataDownloadManager(Context context) {
		super(context);
	}
	
	@Override
	public boolean initializeDownload() {
		
		numberOfFiles = 2384;
		
		URL_FILE = "http://islam.ws/tafseer/externals.zip";
//		URL_FILE = "http://www.dropbox.com/s/2msafc1tz5f8kdw/externals.zip?dl=1";
		
		File d = context.getExternalFilesDir(null);
		if(d != null){
			String basePath = d.getAbsolutePath() + File.separator;

			folderName = "externals";
			zipFileName = "externals.zip";

			thePath = basePath + folderName + File.separator;
			zipFile = basePath + zipFileName;

			description = "Downloading "+ folderName +" data";

			TafseerManager.ExternalsPath = thePath;
			
			TafseerManager.MainReceiterPath = TafseerManager.ExternalsPath + File.separator + "MP3s" + File.separator;
			TafseerManager.AdvicesPath 		= TafseerManager.ExternalsPath + File.separator + "AdviceMP3s" + File.separator;
			TafseerManager.QuizPNGPath 		= TafseerManager.ExternalsPath + File.separator + "_QuizPNGS" + File.separator;
			TafseerManager.QuizPNGGrayPath 	= TafseerManager.ExternalsPath + File.separator + "_QuizPNGsGray" + File.separator;
			TafseerManager.FontsPath 		= TafseerManager.ExternalsPath + File.separator + "FONTS" + File.separator;
			
			return super.initializeDownload();
		}
	
		isDataReady = false;
		return false;
	}
	
	@Override
	protected boolean isNumberOfFilesComplete() {
		
		ArrayList<String> folders = new ArrayList<String>();
		folders.add(TafseerManager.AdvicesPath);
		folders.add(TafseerManager.MainReceiterPath);
		folders.add(TafseerManager.QuizPNGPath);
		folders.add(TafseerManager.QuizPNGGrayPath);
		folders.add(TafseerManager.FontsPath);
		
		int filesCounter = 5;
		
		for(String path : folders){

			File dir = new File(path);
			File childfile[] = dir.listFiles();

			if(childfile != null)
				filesCounter += childfile.length;
		}
	
		
		return filesCounter >= numberOfFiles;
	}

}
