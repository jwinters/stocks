package io.elapse.stocks.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class StocksPreferences {

    public interface Property {
        String LAST_UPDATED = "last_updated";
    }

    private static final String FORMAT = "h:mm aa MMM dd, yyyy";
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(FORMAT, Locale.getDefault());

    public static SharedPreferences getSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getLastUpdated(final Context context) {
        final SharedPreferences preferences = getSharedPreferences(context);
        final long lastUpdated = preferences.getLong(Property.LAST_UPDATED, 0);
        return FORMATTER.format(lastUpdated);
    }

    @SuppressLint("CommitPrefEdits")
    public static void setLastUpdated(final Context context, final long updated) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(Property.LAST_UPDATED, updated);
        editor.commit();
    }
}
