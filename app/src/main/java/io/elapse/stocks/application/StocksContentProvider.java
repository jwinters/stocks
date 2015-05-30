package io.elapse.stocks.application;

import android.net.Uri;

import io.elapse.stocks.models.Stock;
import io.elapse.stocks.utils.HttpDataset;
import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.DatabaseProvider;
import io.pivotal.arca.provider.SQLiteTable;
import io.pivotal.arca.provider.SQLiteView;
import io.pivotal.arca.provider.Select;
import io.pivotal.arca.provider.SelectFrom;
import io.pivotal.arca.provider.Unique;
import io.pivotal.arca.provider.Unique.OnConflict;

public class StocksContentProvider extends DatabaseProvider {

	public static final String AUTHORITY = StocksContentProvider.class.getName();

	private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

	public static final class Uris {
		public static final Uri STOCKS = Uri.withAppendedPath(BASE_URI, Paths.STOCKS);
		public static final Uri SYMBOLS = Uri.withAppendedPath(BASE_URI, Paths.SYMBOLS);
		public static final Uri SEARCH = Uri.withAppendedPath(BASE_URI, Paths.SEARCH);
	}

	private static final class Paths {
		public static final String STOCKS = "stocks";
		public static final String SYMBOLS = "symbols";
		public static final String SEARCH = "search";
	}

	@Override
	public boolean onCreate() {
		registerDataset(AUTHORITY, Paths.STOCKS, StockTable.class);
		registerDataset(AUTHORITY, Paths.SYMBOLS, SymbolView.class);
		registerDataset(AUTHORITY, Paths.SEARCH, StockSearch.class);
		return true;
	}

	public static class StockTable extends SQLiteTable {
		public interface Columns extends SQLiteTable.Columns {
			@Unique(OnConflict.REPLACE)
			@Column(Column.Type.TEXT) String ID = "id";
			@Column(Column.Type.TEXT) String T = "t";
			@Column(Column.Type.TEXT) String E = "e";
			@Column(Column.Type.TEXT) String L = "l";
			@Column(Column.Type.TEXT) String L_FIX = "l_fix";
			@Column(Column.Type.TEXT) String L_CUR = "l_cur";
			@Column(Column.Type.TEXT) String S = "s";
			@Column(Column.Type.TEXT) String LT = "lt";
			@Column(Column.Type.TEXT) String LT_DTS = "lt_dts";
			@Column(Column.Type.TEXT) String C = "c";
			@Column(Column.Type.TEXT) String CP = "cp";
			@Column(Column.Type.TEXT) String CCOL = "ccol";
			@Column(Column.Type.TEXT) String PCLS_FIX = "pcls_fix";
		}
	}

	public static class SymbolView extends SQLiteView {

		@SelectFrom("StockTable as s")
		public interface Columns {
			@Select("GROUP_CONCAT(s.e || ':' || s.t)") String SYMBOLS = "symbols";
		}
	}

	public static class StockSearch extends HttpDataset<Stock> {

		@Override
		protected Stock.List query(final String query) throws Exception {
			return StocksApi.getStockList(query);
		}
	}
}