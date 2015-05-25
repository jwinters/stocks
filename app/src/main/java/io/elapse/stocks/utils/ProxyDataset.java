package io.elapse.stocks.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import io.pivotal.arca.provider.Dataset;

public abstract class ProxyDataset implements Dataset {

    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        return new MatrixCursor(new String[] { "_id" });
    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
        return 0;
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        return Uri.EMPTY;
    }

    @Override
    public int bulkInsert(final Uri uri, final ContentValues[] values) {
        return 0;
    }

    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        return 0;
    }
}
