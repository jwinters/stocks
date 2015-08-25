package io.elapse.stocks.application;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import io.elapse.stocks.R;
import io.elapse.stocks.activities.StockListActivity;
import io.elapse.stocks.application.StocksContentProvider.StockTable;

public class StocksNotificationManager {

    public void showNotification(final Context context, final Cursor cursor) {
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, createNotification(context, cursor));
    }

    public void hideNotification(final Context context) {
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.cancelAll();
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
            // notification.bigContentView = remoteViews;
        }

        return notification;
    }

    private RemoteViews createRemoteViews(final Context context, final Cursor cursor) {
        final String packageName = context.getPackageName();

        final RemoteViews remoteViews = new RemoteViews(packageName, R.layout.notification_stocks);
        remoteViews.removeAllViews(R.id.stocks_container);

        while (cursor.moveToNext()) {
            final RemoteViews stockItem = new RemoteViews(packageName, R.layout.grid_item_stock);

            stockItem.setTextViewText(R.id.stock_t, getString(cursor, StockTable.Columns.T));
            stockItem.setTextViewText(R.id.stock_l, getString(cursor, StockTable.Columns.L));
            stockItem.setTextViewText(R.id.stock_c, getString(cursor, StockTable.Columns.C));
            stockItem.setTextViewText(R.id.stock_cp, getString(cursor, StockTable.Columns.CP));

            remoteViews.addView(R.id.stocks_container, stockItem);
        }

        return remoteViews;
    }

    private String getString(final Cursor cursor, final String columnName) {
        final int columnIndex = cursor.getColumnIndex(columnName);
        final String string = cursor.getString(columnIndex);
        return string.isEmpty() ? "0.00" : string;
    }
}
