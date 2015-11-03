package io.elapse.stocks.application;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

public class StocksNotificationService extends Service {


    public static void start(final Context context) {
        final Intent intent = new Intent(context, StocksNotificationService.class);
        context.startService(intent);
    }

    private ContentObserver mObserver = new ContentObserver(new Handler()) {

        @Override
        public void onChange(final boolean selfChange) {
            super.onChange(selfChange);

            Logger.v("Content Changed");

            StocksNotificationService.start(StocksNotificationService.this);
        }
    };

    private StocksNotificationManager mManager;

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.v("Created service %s", this.getClass());

        mManager = new StocksNotificationManager();

        final Uri uri = StocksContentProvider.Uris.STOCKS;
        getContentResolver().registerContentObserver(uri, false, mObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Logger.v("Destroyed service %s", this.getClass());

        getContentResolver().unregisterContentObserver(mObserver);
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        super.onStartCommand(intent, flags, startId);

        Logger.v("Started service %s", this.getClass());

        onHandleIntent(intent);

        return START_STICKY;
    }

    protected void onHandleIntent(final Intent intent) {

        mManager.handleNotificationEvent(this);
    }
}
