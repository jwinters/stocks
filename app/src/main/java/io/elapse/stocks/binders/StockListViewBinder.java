package io.elapse.stocks.binders;

import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import io.elapse.stocks.R;
import io.pivotal.arca.adapters.Binding;
import io.pivotal.arca.adapters.ViewBinder;

public class StockListViewBinder implements ViewBinder {

	public static final int RED = Color.parseColor("#D00100");
	public static final int GREEN = Color.parseColor("#008E00");

	@Override
	public boolean setViewValue(final View view, final Cursor cursor, final Binding binding) {
		switch (view.getId()) {
			case R.id.stock_c:
				return  setStockDifferenceValue((TextView) view, cursor, binding);
			case R.id.stock_cp:
				return  setStockPercentageValue((TextView) view, cursor, binding);
			default:
				return false;
		}
	}

	private boolean setStockDifferenceValue(final TextView view, final Cursor cursor, final Binding binding) {
		final String string = cursor.getString(binding.getColumnIndex());
		setTextColor(view, string);
		view.setText(formatText(string));
		return true;
	}

	private boolean setStockPercentageValue(final TextView view, final Cursor cursor, final Binding binding) {
		final String string = cursor.getString(binding.getColumnIndex());
		setTextColor(view, string);
		view.setText("(" + (formatText(string)) + "%)");
		return true;
	}

	private void setTextColor(final TextView view, final String value) {
		try {
			final boolean negative = Float.parseFloat(value) < 0;
			view.setTextColor(negative ? RED : GREEN);
		} catch (final NumberFormatException e) {
			view.setTextColor(Color.BLACK);
		}
	}

	private String formatText(final String string) {
		return string.isEmpty() ? "0.00" : string;
	}
}