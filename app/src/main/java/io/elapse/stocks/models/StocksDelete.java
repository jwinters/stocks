package io.elapse.stocks.models;

import io.elapse.stocks.application.StocksContentProvider;
import io.elapse.stocks.application.StocksContentProvider.StockTable;
import io.pivotal.arca.dispatcher.Delete;

public class StocksDelete extends Delete {

    public StocksDelete(final String id) {
        super(StocksContentProvider.Uris.STOCKS);

        final String whereClause = StockTable.Columns.ID + "=?";
        final String[] whereArgs = new String[] { id };

        setWhere(whereClause, whereArgs);
    }
}
