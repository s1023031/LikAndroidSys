package com.lik.android.om;

import java.util.Map;

import com.lik.android.main.LikDBAdapter;

public interface ProcessDownloadInterface {
	
	public boolean processDownload(LikDBAdapter adapter, Map<String,String> detail, boolean isOnlyInsert);

}
