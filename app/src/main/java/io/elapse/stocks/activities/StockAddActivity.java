package io.elapse.stocks.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup;

import io.elapse.stocks.R;

public class StockAddActivity extends FragmentActivity {

	public static void newInstance(final Context context) {
		final Intent intent = new Intent(context, StockAddActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		final int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, metrics);
		getWindow().setLayout(metrics.widthPixels - pixels, ViewGroup.LayoutParams.MATCH_PARENT);

		setContentView(R.layout.activity_stock_add);
		setTitle("Add Stock");
	}
}