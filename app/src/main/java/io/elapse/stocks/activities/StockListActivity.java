package io.elapse.stocks.activities;

import android.accounts.Account;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RemoteViews;

import io.elapse.stocks.R;
import io.elapse.stocks.application.StocksAuthenticator;
import io.elapse.stocks.application.StocksContentProvider;
import io.elapse.stocks.application.StocksContentProvider.StockTable;
import io.pivotal.arca.dispatcher.Query;
import io.pivotal.arca.dispatcher.QueryListener;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.fragments.ArcaDispatcherFactory;
import io.pivotal.arca.monitor.ArcaDispatcher;

public class StockListActivity extends ActionBarActivity implements QueryListener {

	public static void newInstance(final Context context) {
		final Intent intent = new Intent(context, StockListActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_list);
		setTitle(R.string.title_stock_list);

		final ArcaDispatcher dispatcher = ArcaDispatcherFactory.generateDispatcher(this);
		dispatcher.execute(new Query(StocksContentProvider.Uris.STOCKS), this);

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

	@Override
	public void onRequestComplete(final QueryResult result) {
		showNotification(this, result.getResult());
	}

	@Override
	public void onRequestReset() {}


	private void showNotification(final Context context, final Cursor cursor) {
		final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		notificationManager.notify(0, createNotification(context, cursor));
	}

    private Notification createNotification(final Context context, final Cursor cursor) {
        final RemoteViews remoteViews = createRemoteViews(context, cursor);

        final Intent intent = new Intent(context, StockListActivity.class);
        final PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_action_refresh)
                .setContentIntent(contentIntent)
                .setContent(remoteViews)
                .setOngoing(true);

        final Notification notification = builder.build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification.bigContentView = remoteViews;
        }
        
        return notification;
    }

    private RemoteViews createRemoteViews(final Context context, final Cursor cursor) {
        final String packageName = context.getPackageName();

        final RemoteViews remoteViews = new RemoteViews(packageName, R.layout.notification_stocks);
        remoteViews.removeAllViews(R.id.stocks_container);

        while (cursor.moveToNext()) {
            final RemoteViews stockItem = new RemoteViews(packageName, R.layout.grid_item_stock);

            stockItem.setTextViewText(R.id.stock_t, cursor.getString(cursor.getColumnIndex(StockTable.Columns.T)));
            stockItem.setTextViewText(R.id.stock_l, cursor.getString(cursor.getColumnIndex(StockTable.Columns.L)));
            stockItem.setTextViewText(R.id.stock_c, cursor.getString(cursor.getColumnIndex(StockTable.Columns.C)));
            stockItem.setTextViewText(R.id.stock_cp, cursor.getString(cursor.getColumnIndex(StockTable.Columns.CP)));

            remoteViews.addView(R.id.stocks_container, stockItem);
        }

        return remoteViews;
    }
}