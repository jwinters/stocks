package io.elapse.stocks.application;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import io.elapse.stocks.models.Stock;

public class StocksApi {

	private static final String BASE_URL = "http://finance.google.com/finance/info?q=";

	public static Stock.List getStockList(final String query) throws IOException {
		final URL url = new URL(BASE_URL + query);
		Logger.v("Request : " + url);
		final String result = loadContent(url).substring(3);
		final ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(result, Stock.List.class);
	}

	private static String loadContent(final URL url) throws IOException {
		final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		final InputStream inputStream = connection.getInputStream();
		final Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
		final String result = scanner.hasNext() ? scanner.next() : "";
		connection.disconnect();
		return result;
	}
}