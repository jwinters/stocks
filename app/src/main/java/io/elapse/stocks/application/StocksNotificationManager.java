package io.elapse.stocks.application;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;

import io.elapse.stocks.R;
import io.elapse.stocks.activities.StockListActivity;
import io.elapse.stocks.application.StocksContentProvider.StockTable;
import io.elapse.stocks.models.StocksQuery;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.monitor.ArcaExecutor;

public class StocksNotificationManager {

    private static final int NOTIFICATION_ID = 0;

    private static final int START_TIME_24_HRS = 9;
    private static final int FINISH_TIME_24_HRS = 17;


    public void handleNotificationEvent(final Context context) {
        if (shouldShowNotification()) {
            showNotification(context);
        } else {
            hideNotification(context);
        }
    }

    private boolean shouldShowNotification() {
        final DateTimeZone zone = DateTimeZone.getDefault();
        final DateTime now = DateTime.now(zone);
        final int hour = now.getHourOfDay();
        final int day = now.getDayOfWeek();
        final boolean isWeekday = day !=  DateTimeConstants.SATURDAY && day != DateTimeConstants.SUNDAY;
        final boolean isWorkingHours = hour >= START_TIME_24_HRS && hour <= FINISH_TIME_24_HRS;
        return isWeekday && isWorkingHours;
    }

    private void showNotification(final Context context) {
        final ContentResolver resolver = context.getContentResolver();
        final ArcaExecutor executor = new ArcaExecutor.DefaultArcaExecutor(resolver, context);
        final QueryResult result = executor.execute(new StocksQuery());

        showNotification(context, result.getData());

        result.close();
    }

    private void showNotification(final Context context, final Cursor cursor) {
        final NotificationManager service = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final Notification notification = createNotification(context, cursor);

        service.notify(NOTIFICATION_ID, notification);
    }

    private void hideNotification(final Context context) {
        final NotificationManager service = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        service.cancel(NOTIFICATION_ID);
    }

    public Notification createNotification(final Context context, final Cursor cursor) {
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
