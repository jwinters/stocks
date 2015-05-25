package io.elapse.stocks.application;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StocksAuthenticatorService extends Service {

	private StocksAuthenticator mAuthenticator;

	@Override
	public void onCreate() {
		super.onCreate();
		
		if (mAuthenticator == null) { 
			mAuthenticator = new StocksAuthenticator(this);
		}
	}
	
	@Override
	public IBinder onBind(final Intent intent) {
		return mAuthenticator.getIBinder();
	}
}