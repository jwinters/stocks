package io.elapse.stocks.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import io.elapse.stocks.R;
import io.elapse.stocks.fragments.StockDeleteFragment;

public class StockDeleteActivity extends FragmentActivity {

	private interface Extras {
		String STOCK_ID = "stock_id";
	}

	public static void newInstance(final Context context, final String id) {
		final Intent intent = new Intent(context, StockDeleteActivity.class);
		intent.putExtra(Extras.STOCK_ID, id);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_delete);
		setTitle("Delete Stock?");

		final String stockId = getIntent().getStringExtra(Extras.STOCK_ID);

		if (stockId == null) {
			Toast.makeText(this, "Stock Id cannot be null", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		getFragment().setId(stockId);
	}

	private StockDeleteFragment getFragment() {
		final FragmentManager manager = getSupportFragmentManager();
		return (StockDeleteFragment) manager.findFragmentById(R.id.fragment_stock_delete);
	}
}