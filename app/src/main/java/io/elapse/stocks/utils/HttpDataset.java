package io.elapse.stocks.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import io.pivotal.arca.provider.DataUtils;
import io.pivotal.arca.provider.Dataset;
import io.pivotal.arca.utils.Logger;

public abstract class HttpDataset<T> implements Dataset {

    protected List<T> query(final String query) throws Exception {
        return null;
    }

    protected T update(final T object) throws Exception {
        return null;
    }

    protected long insert(final T object) throws Exception {
        return 0;
    }

    protected int insert(final List<T> list) throws Exception {
        return 0;
    }

    protected T delete(final long id) throws Exception {
        return null;
    }

    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        try {
            final List<T> list = query(selectionArgs[0]);
            return ContentUtils.getCursor(list);
        } catch (final Exception e) {
            Logger.ex(e);
            return new MatrixCursor(new String[] { "_id" });
        }
    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
        try {
            final T original = DataUtils.getObject(values, getClassType());
            return update(original) != null ? 1 : 0;
        } catch (final Exception e) {
            Logger.ex(e);
            return 0;
        }
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        try {
            final T original = DataUtils.getObject(values, getClassType());
            return ContentUris.withAppendedId(uri, insert(original));
        } catch (final Exception e) {
            Logger.ex(e);
            return Uri.EMPTY;
        }
    }

    @Override
    public int bulkInsert(final Uri uri, final ContentValues[] values) {
        try {
            final List<T> list = DataUtils.getList(values, getClassType());
            return insert(list);
        } catch (final Exception e) {
            Logger.ex(e);
            return 0;
        }
    }

    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        try {
            final T object = delete(ContentUris.parseId(uri));
            return object != null ? 1 : 0;
        } catch (final Exception e) {
            Logger.ex(e);
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    private Class<T> getClassType() {
        final ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }
}
