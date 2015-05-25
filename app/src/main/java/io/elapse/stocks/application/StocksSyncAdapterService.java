package io.elapse.stocks.application;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class StocksSyncAdapterService extends Service {

	private static StocksSyncAdapter sSyncAdapter;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		synchronized (this) {
			if (sSyncAdapter == null) {
				final Context context = getApplicationContext();
				sSyncAdapter = new StocksSyncAdapter(context, true);
			}
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return sSyncAdapter.getSyncAdapterBinder();
	}

}