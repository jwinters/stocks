package io.elapse.stocks.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import java.util.Arrays;
import java.util.Collection;

import io.elapse.stocks.R;
import io.elapse.stocks.application.StocksContentProvider.StockTable;
import io.elapse.stocks.models.SearchQuery;
import io.elapse.stocks.models.StocksInsert;
import io.elapse.stocks.monitors.StockAddMonitor;
import io.elapse.stocks.utils.ContentUtils;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.fragments.ArcaFragment;
import io.pivotal.arca.fragments.ArcaFragmentBindings;
import io.pivotal.arca.fragments.ArcaSimpleAdapterFragment;

@ArcaFragment(
        fragmentLayout = R.layout.fragment_stock_add,
        adapterItemLayout = R.layout.list_item_stock,
        monitor = StockAddMonitor.class
)
public class StockAddFragment extends ArcaSimpleAdapterFragment implements TextWatcher {

    @ArcaFragmentBindings
    private static final Collection<Binding> BINDINGS = Arrays.asList(
            new Binding(R.id.stock_t, StockTable.Columns.T),
            new Binding(R.id.stock_l, StockTable.Columns.L),
            new Binding(R.id.stock_c, StockTable.Columns.C),
            new Binding(R.id.stock_cp, StockTable.Columns.CP)
    );

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
        final Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
        final ContentValues values = ContentUtils.getContentValues(cursor, StockTable.class, 0);
        values.remove(StockTable.Columns._ID);

        getRequestDispatcher().execute(new StocksInsert(values));
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editText = (EditText) view.findViewById(R.id.stock_search);
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(final CharSequence sequence, final int start, final int count, final int after) {}

    @Override
    public void onTextChanged(final CharSequence sequence, final int start, final int before, final int count) {
        final String query = String.valueOf(sequence);
        execute(new SearchQuery(query));
    }

    @Override
    public void afterTextChanged(final Editable sequence) {}
}