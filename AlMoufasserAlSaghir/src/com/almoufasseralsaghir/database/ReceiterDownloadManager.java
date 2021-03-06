package com.almoufasseralsaghir.database;

import java.io.File;

import com.almoufasseralsaghir.external.TafseerManager;

import android.content.Context;

/**
 * AlMoufasserAlSaghir
 * @author HICHEM LAROUSSI - RAMI TRABELSI
 * Copyright (c) 2014 Zad Group. All rights reserved.
 */

public class ReceiterDownloadManager extends AlMoufasserDownloadManager {

	public ReceiterDownloadManager(Context context) {
		super(context);
	}
	
	@Override
	public boolean initializeDownload() {
		
		numberOfFiles = 1664;
		
		URL_FILE = "http://zadgroup.net/tafseer/basfar.zip";
//		URL_FILE = "http://islam.ws/tafseer/basfar.zip";

		File d = context.getExternalFilesDir(null);
		String basePath = d.getAbsolutePath() + File.separator;
		
		folderName = "basfar";
		zipFileName = "basfar.zip";
		
		thePath = TafseerManager.ExternalsPath + folderName + File.separator;
		zipFile = basePath + zipFileName;
		
		description = "Downloading "+ folderName +" MP3's songs";
		
		TafseerManager.SecondReceiterPath = thePath;
		
		return super.initializeDownload();
	}
	
	@Override
	protected boolean isNumberOfFilesComplete() {
		File dir = new File(thePath);
		File childfile[] = dir.listFiles();

		if(childfile != null)
			return childfile.length == numberOfFiles;

		return false;

	}

}
