package io.elapse.stocks.monitors;

import android.content.Context;

import io.pivotal.arca.dispatcher.Query;
import io.pivotal.arca.dispatcher.QueryResult;
import io.pivotal.arca.monitor.RequestMonitor.AbstractRequestMonitor;

public class StockListMonitor extends AbstractRequestMonitor {

	@Override
	public int onPostExecute(final Context context, final Query request, final QueryResult result) {
		return Flags.DATA_VALID;
	}
}