package io.elapse.stocks.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import io.elapse.stocks.R;
import io.elapse.stocks.models.StocksDelete;
import io.elapse.stocks.monitors.StockDeleteMonitor;
import io.pivotal.arca.fragments.ArcaDispatcherFactory;
import io.pivotal.arca.monitor.ArcaDispatcher;

public class StockDeleteFragment extends ListFragment {

    private String mIdentifier;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stock_delete, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String[] items = new String[] { "OK", "CANCEL" };
        final int resource = android.R.layout.simple_list_item_1;

        setListAdapter(new ArrayAdapter<String>(getActivity(), resource, items));
    }

    @Override
    public void onListItemClick(final ListView listView, final View view, final int position, final long id) {
        if (position == 0) {
            final ArcaDispatcher dispatcher = ArcaDispatcherFactory.generateDispatcher(this);
            dispatcher.setRequestMonitor(new StockDeleteMonitor());
            dispatcher.execute(new StocksDelete(mIdentifier));
        }

        getActivity().finish();
    }

    public void setId(final String id) {
        mIdentifier = id;
    }
}