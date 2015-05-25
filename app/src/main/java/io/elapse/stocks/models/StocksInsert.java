package io.elapse.stocks.models;

import android.content.ContentValues;

import io.elapse.stocks.application.StocksContentProvider;
import io.pivotal.arca.dispatcher.Insert;

public class StocksInsert extends Insert {

    public StocksInsert(final ContentValues values) {
        super(StocksContentProvider.Uris.STOCKS, values);
    }
}
