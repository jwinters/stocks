package io.elapse.stocks.application;

import android.app.Application;

public class StocksApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        StocksNotificationService.start(this);
    }
}
