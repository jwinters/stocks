package io.elapse.stocks.monitors;

import android.content.ContentResolver;
import android.content.Context;

import io.elapse.stocks.application.StocksContentProvider;
import io.pivotal.arca.dispatcher.Delete;
import io.pivotal.arca.dispatcher.DeleteResult;
import io.pivotal.arca.monitor.RequestMonitor;

public class StockDeleteMonitor extends RequestMonitor.AbstractRequestMonitor {

    @Override
    public int onPostExecute(final Context context, final Delete request, final DeleteResult result) {
        final ContentResolver resolver = context.getContentResolver();
        resolver.notifyChange(StocksContentProvider.Uris.STOCKS, null);
        return Flags.DATA_VALID;
    }
}
