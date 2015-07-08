package io.elapse.stocks.application;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import io.elapse.stocks.models.SymbolsQuery;
import io.elapse.stocks.operations.GetStockListOperation;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.dispatcher.RequestExecutor;
import io.pivotal.arca.service.OperationService;

public class StocksSyncAdapter extends AbstractThreadedSyncAdapter {

	public StocksSyncAdapter(final Context context, final boolean autoInitialize) {
		super(context, autoInitialize);
	}

	@Override
	public void onPerformSync(final Account account, final Bundle extras, final String authority, final ContentProviderClient provider, final SyncResult syncResult) {

		Log.v("debug", "Syncing - Start");

		final ContentResolver contentResolver = getContext().getContentResolver();
		final RequestExecutor executor = new RequestExecutor.DefaultRequestExecutor(contentResolver);

		final QueryResult result = executor.execute(new SymbolsQuery());
		final Cursor cursor = result.getResult();

		Log.v("debug", "Syncing - Getting symbols");

		if (cursor.moveToFirst()) {
			final String columnName = StocksContentProvider.SymbolView.Columns.SYMBOLS;
			final String symbols = cursor.getString(cursor.getColumnIndex(columnName));

			if (symbols != null) {
				Log.v("debug", "Syncing - Found symbols : " + symbols);

				OperationService.start(getContext(), new GetStockListOperation(symbols));
			}
		}

		Log.v("debug", "Syncing - Finished");

		result.close();
	}
}