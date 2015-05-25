package io.elapse.stocks.models;

import io.elapse.stocks.application.StocksContentProvider;
import io.pivotal.arca.dispatcher.Query;

public class SymbolsQuery extends Query {

    public SymbolsQuery() {
        super(StocksContentProvider.Uris.SYMBOLS);
    }
}
