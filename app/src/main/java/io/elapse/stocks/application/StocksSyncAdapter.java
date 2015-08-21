package io.elapse.stocks.application;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;

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

		Logger.v("Syncing - Start");

		final ContentResolver contentResolver = getContext().getContentResolver();
		final RequestExecutor executor = new RequestExecutor.DefaultRequestExecutor(contentResolver);

		final QueryResult result = executor.execute(new SymbolsQuery());
		final Cursor cursor = result.getResult();

		Logger.v("Syncing - Getting symbols");

		if (cursor.moveToFirst()) {
			final String columnName = StocksContentProvider.SymbolView.Columns.SYMBOLS;
			final String symbols = cursor.getString(cursor.getColumnIndex(columnName));

			if (symbols != null) {
				Logger.v("Syncing - Found symbols : %s", symbols);

				OperationService.start(getContext(), new GetStockListOperation(symbols));
			}
		}

		Logger.v("Syncing - Finished");

		result.close();
	}
}