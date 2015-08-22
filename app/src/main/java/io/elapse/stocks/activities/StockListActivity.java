package io.elapse.stocks.activities;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import io.elapse.stocks.R;
import io.elapse.stocks.application.StocksAuthenticator;
import io.elapse.stocks.application.StocksContentProvider;
import io.elapse.stocks.application.StocksPreferences;

public class StockListActivity extends ActionBarActivity {

	public static void newInstance(final Context context) {
		final Intent intent = new Intent(context, StockListActivity.class);
		context.startActivity(intent);
	}

	private ContentObserver mObserver = new ContentObserver(new Handler()) {

		@Override
		public void onChange(final boolean selfChange) {
			super.onChange(selfChange);

			updateLastUpdatedDate();
		}
	};

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_list);
		setTitle(R.string.title_stock_list);

		requestSync();
	}

	@Override
	protected void onStart() {
		super.onStart();

		final Uri uri = StocksContentProvider.Uris.STOCKS;
		getContentResolver().registerContentObserver(uri, false, mObserver);
	}

	@Override
	protected void onPause() {
		super.onPause();

		getContentResolver().unregisterContentObserver(mObserver);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.stock_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_reload:
				requestSync();
				return true;

			case R.id.menu_add:
				StockAddActivity.newInstance(this);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void updateLastUpdatedDate() {
		final String lastUpdated = StocksPreferences.getLastUpdated(this);
		final TextView textView = (TextView) findViewById(R.id.stocks_last_updated);

		textView.setText(lastUpdated);
	}

	private void requestSync() {
		final String authority = StocksContentProvider.AUTHORITY;
		final String accountName = StocksAuthenticator.ACCOUNT_NAME;
		final String accountType = StocksAuthenticator.ACCOUNT_TYPE;
		final Account account = new Account(accountName, accountType);

		ContentResolver.requestSync(account, authority, Bundle.EMPTY);
	}
}