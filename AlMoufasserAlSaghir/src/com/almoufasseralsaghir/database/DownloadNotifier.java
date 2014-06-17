package com.almoufasseralsaghir.database;

public interface DownloadNotifier {

	void onProgressDownload(int progress);
	void onDownloadComplete();
	void configureProgress(int maxSize);
	void onErrorDownload();
	
}
