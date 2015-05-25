package io.elapse.stocks.activities;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import io.elapse.stocks.R;
import io.elapse.stocks.application.StocksAuthenticator;
import io.elapse.stocks.application.StocksContentProvider;

public class StockListActivity extends ActionBarActivity {

	public static void newInstance(final Context context) {
		final Intent intent = new Intent(context, StockListActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_list);
		setTitle(R.string.title_stock_list);

		reload();
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
				reload();
				return true;

			case R.id.menu_add:
				StockAddActivity.newInstance(this);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void reload() {
		final String authority = StocksContentProvider.AUTHORITY;
		final String accountName = StocksAuthenticator.ACCOUNT_NAME;
		final String accountType = StocksAuthenticator.ACCOUNT_TYPE;
		final Account account = new Account(accountName, accountType);

		ContentResolver.requestSync(account, authority, Bundle.EMPTY);
	}
}