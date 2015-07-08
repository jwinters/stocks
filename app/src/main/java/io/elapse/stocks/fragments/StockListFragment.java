package io.elapse.stocks.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.Arrays;
import java.util.Collection;

import io.elapse.stocks.R;
import io.elapse.stocks.activities.StockDeleteActivity;
import io.elapse.stocks.activities.StockItemActivity;
import io.elapse.stocks.application.StocksContentProvider.StockTable;
import io.elapse.stocks.binders.StockListViewBinder;
import io.elapse.stocks.models.StocksQuery;
import io.elapse.stocks.monitors.StockListMonitor;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;
import io.pivotal.arca.fragments.ArcaSimpleAdapterFragment;

@ArcaFragment(
        fragmentLayout = R.layout.fragment_stock_list,
        adapterItemLayout = R.layout.list_item_stock,
        binder = StockListViewBinder.class,
        monitor = StockListMonitor.class
)
public class StockListFragment extends ArcaSimpleAdapterFragment {

    @ArcaFragmentBindings
    private static final Collection<Binding> BINDINGS = Arrays.asList(
            new Binding(R.id.stock_t, StockTable.Columns.T),
            new Binding(R.id.stock_l, StockTable.Columns.L),
            new Binding(R.id.stock_c, StockTable.Columns.C),
            new Binding(R.id.stock_cp, StockTable.Columns.CP)
    );

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        execute(new StocksQuery());
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
        final Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
        final String itemId = cursor.getString(cursor.getColumnIndex(StockTable.Columns.ID));

        StockItemActivity.newInstance(getActivity(), itemId);
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
        final Cursor cursor = (Cursor) getAdapterView().getItemAtPosition(position);
        final String itemId = cursor.getString(cursor.getColumnIndex(StockTable.Columns.ID));

        StockDeleteActivity.newInstance(getActivity(), itemId);
        return true;
    }
}