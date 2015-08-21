package io.elapse.stocks.application;

import android.util.Log;

public class Logger {

    private static final boolean sDebug = true;

	public static void v(final String message, final Object... objects) {
        if (sDebug) Log.v("Stocks", String.format(message, objects));
    }
}