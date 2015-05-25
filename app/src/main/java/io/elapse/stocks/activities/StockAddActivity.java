package io.elapse.stocks.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import io.elapse.stocks.R;

public class StockAddActivity extends ActionBarActivity {

	public static void newInstance(final Context context) {
		final Intent intent = new Intent(context, StockAddActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		final int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, metrics);
		getWindow().setLayout(metrics.widthPixels - pixels, metrics.heightPixels - pixels);

		setContentView(R.layout.activity_stock_add);
	}
}