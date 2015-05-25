package io.elapse.stocks.models;

import io.elapse.stocks.application.StocksContentProvider;
import io.pivotal.arca.dispatcher.Query;

public class SearchQuery extends Query {

    public SearchQuery(final String query) {
        super(StocksContentProvider.Uris.SEARCH, 10000);

        final String whereClause = "query = ?";
        final String[] whereArgs = new String[] { query };

        setWhere(whereClause, whereArgs);
    }

}
