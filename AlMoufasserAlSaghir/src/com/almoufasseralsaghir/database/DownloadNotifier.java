package com.almoufasseralsaghir.database;

/**
 * AlMoufasserAlSaghir
 * @author HICHEM LAROUSSI - RAMI TRABELSI
 * Copyright (c) 2014 Zad Group. All rights reserved.
 */

public interface DownloadNotifier {

	void onProgressDownload(int progress);
	void onDownloadComplete();
	void configureProgress(int maxSize);
	void onErrorDownload();
	
}
