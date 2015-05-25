package io.elapse.stocks.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import io.elapse.stocks.R;
import io.elapse.stocks.fragments.StockItemFragment;

public class StockItemActivity extends ActionBarActivity {

	private interface Extras {
		String STOCK_ID = "stock_id";
	}

	public static void newInstance(final Context context, final String id) {
		final Intent intent = new Intent(context, StockItemActivity.class);
		intent.putExtra(Extras.STOCK_ID, id);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_item);
		setTitle(R.string.title_stock_list);

		final String stockId = getIntent().getStringExtra(Extras.STOCK_ID);

		if (stockId == null) {
			Toast.makeText(this, "Stock Id cannot be null", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		getFragment().setId(stockId);
	}

	private StockItemFragment getFragment() {
		final FragmentManager manager = getFragmentManager();
		return (StockItemFragment) manager.findFragmentById(R.id.fragment_stock_item);
	}
}