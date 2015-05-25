package io.elapse.stocks.monitors;

import android.content.ContentResolver;
import android.content.Context;

import io.elapse.stocks.application.StocksContentProvider;
import io.pivotal.arca.dispatcher.Insert;
import io.pivotal.arca.dispatcher.InsertResult;
import io.pivotal.arca.monitor.RequestMonitor;

public class StockAddMonitor extends RequestMonitor.AbstractRequestMonitor {

    @Override
    public int onPostExecute(final Context context, final Insert request, final InsertResult result) {
        final ContentResolver resolver = context.getContentResolver();
        resolver.notifyChange(StocksContentProvider.Uris.STOCKS, null);
        return Flags.DATA_VALID;
    }
}
