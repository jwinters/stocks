package io.elapse.stocks.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.elapse.stocks.R;
import io.elapse.stocks.models.StocksDelete;
import io.elapse.stocks.monitors.StockDeleteMonitor;
import io.pivotal.arca.fragments.ArcaDispatcherFactory;
import io.pivotal.arca.monitor.ArcaDispatcher;

public class StockDeleteFragment extends Fragment implements View.OnClickListener {

    private String mIdentifier;
    private Button mOkButton, mCancelButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stock_delete, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mOkButton = (Button) view.findViewById(R.id.button_ok);
        mOkButton.setOnClickListener(this);

        mCancelButton = (Button) view.findViewById(R.id.button_cancel);
        mCancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {

        if (view == mOkButton) {
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