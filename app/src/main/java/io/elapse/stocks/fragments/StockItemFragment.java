package io.elapse.stocks.fragments;

import java.util.Arrays;
import java.util.Collection;

import io.elapse.stocks.R;
import io.elapse.stocks.application.StocksContentProvider.StockTable;
import io.elapse.stocks.binders.StockListViewBinder;
import io.elapse.stocks.models.StocksQuery;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;
import io.pivotal.arca.fragments.ArcaSimpleItemFragment;

@ArcaFragment(
        fragmentLayout = R.layout.fragment_stock_item,
        binder = StockListViewBinder.class
)
public class StockItemFragment extends ArcaSimpleItemFragment {

    @ArcaFragmentBindings
    private static final Collection<Binding> BINDINGS = Arrays.asList(
            new Binding(R.id.stock_t, StockTable.Columns.T),
            new Binding(R.id.stock_l, StockTable.Columns.L),
            new Binding(R.id.stock_c, StockTable.Columns.C),
            new Binding(R.id.stock_cp, StockTable.Columns.CP)
    );

    public void setId(final String id) {
        execute(new StocksQuery(id));
    }
}