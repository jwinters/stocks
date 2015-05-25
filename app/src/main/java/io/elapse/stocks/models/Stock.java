package io.elapse.stocks.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

import io.elapse.stocks.application.StocksContentProvider.StockTable.Columns;
import io.pivotal.arca.provider.ColumnName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {

	public static class List extends ArrayList<Stock> {
		private static final long serialVersionUID = 1L;
	}

	@ColumnName(Columns.ID) public String id;
	@ColumnName(Columns.T) public String t;
	@ColumnName(Columns.E) public String e;
	@ColumnName(Columns.L) public String l;
	@ColumnName(Columns.L_FIX) public String l_fix;
	@ColumnName(Columns.L_CUR) public String l_cur;
	@ColumnName(Columns.S) public String s;
	@ColumnName(Columns.LT) public String lt;
	@ColumnName(Columns.LT_DTS) public String lt_dts;
	@ColumnName(Columns.C) public String c;
	@ColumnName(Columns.CP) public String cp;
	@ColumnName(Columns.CCOL) public String ccol;
	@ColumnName(Columns.PCLS_FIX) public String pcls_fix;
}