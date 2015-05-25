package io.elapse.stocks.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

import io.elapse.stocks.R;
import io.elapse.stocks.application.StocksAuthenticator;
import io.elapse.stocks.application.StocksContentProvider;

public class LoginActivity extends Activity {

	private static final int SYNCABLE = 1;
	private static final int ONE_HOUR = 60 * 60;

	public static void newInstance(final Context context) {
		final Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		if (getAccounts().length == 0) {
			setupAccount();
		}

		finish();
	}
	
	private Account[] getAccounts() {
		final String type = StocksAuthenticator.ACCOUNT_TYPE;
		final AccountManager manager = AccountManager.get(this);
		return manager.getAccountsByType(type);
	}
	
	private void setupAccount() {
		final Account account = createAccount();

		setupSync(account);

		setupAccount(account);
	}

	private Account createAccount() {
		final String name = StocksAuthenticator.ACCOUNT_NAME;
		final String type = StocksAuthenticator.ACCOUNT_TYPE;

		return new Account(name, type);
	}

	private void setupSync(final Account account) {
		final String authority = StocksContentProvider.AUTHORITY;

		ContentResolver.setIsSyncable(account, authority, SYNCABLE);
		ContentResolver.setSyncAutomatically(account, authority, true);
		ContentResolver.addPeriodicSync(account, authority, Bundle.EMPTY, ONE_HOUR);
	}

	private void setupAccount(final Account account) {
		final String tokenType = StocksAuthenticator.AUTH_TOKEN_TYPE;
		final String authToken = UUID.randomUUID().toString();

		final AccountManager manager = AccountManager.get(this);
		manager.addAccountExplicitly(account, null, null);
		manager.setAuthToken(account, tokenType, authToken);
	}
}