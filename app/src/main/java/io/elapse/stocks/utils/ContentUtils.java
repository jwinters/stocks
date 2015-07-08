package io.elapse.stocks.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.pivotal.arca.provider.Column;
import io.pivotal.arca.utils.Logger;

public class ContentUtils {

    public static ContentValues getContentValues(final Cursor cursor, final Class<?> klass, final int position) {
        final ContentValues values = new ContentValues();
        try {
            if (cursor.moveToPosition(position)) {
                getColumns(cursor, klass, values);
            }
        } catch (final Exception e) {
            Logger.ex(e);
        }
        return values;
    }

    private static void getColumns(final Cursor cursor, final Class<?> klass, final ContentValues values) throws Exception {
        final Field[] fields = klass.getFields();
        for (final Field field : fields) {
            getField(cursor, field, values);
        }

        final Class<?>[] klasses = klass.getDeclaredClasses();
        for (int i = 0; i < klasses.length; i++) {
            getColumns(cursor, klasses[i], values);
        }
    }

    private static void getField(final Cursor cursor, final Field field, final ContentValues values) throws Exception {
        final Column columnType = field.getAnnotation(Column.class);
        if (columnType != null) {

            final String columnName = (String) field.get(null);
            Log.v("test", "columnName: " + columnName);
            final int columnIndex = cursor.getColumnIndex(columnName);
            Log.v("test", "columnIndex: " + columnIndex);

            if (columnType.value() == Column.Type.TEXT) {
                values.put(columnName, cursor.getString(columnIndex));

            } else if (columnType.value() == Column.Type.BLOB) {
                values.put(columnName, cursor.getBlob(columnIndex));

            } else if (columnType.value() == Column.Type.INTEGER) {
                values.put(columnName, cursor.getInt(columnIndex));

            } else if (columnType.value() == Column.Type.REAL) {
                values.put(columnName, cursor.getFloat(columnIndex));
            }
        }
    }



    public static <T> Cursor getCursor(final List<T> list) throws IllegalAccessException {
        if (list == null || list.isEmpty())
            return new MatrixCursor(new String[] { "_id" });

        MatrixCursor cursor = null;

        int position = 0;
        for (final T object : list) {

            final List<String> titles = new ArrayList<String>();
            titles.add("_id");

            final List<String> objects = new ArrayList<String>();
            objects.add(String.valueOf(position++));

            final Field[] fields = object.getClass().getFields();
            for (final Field field : fields) {
                field.setAccessible(true);
                final Object value = field.get(object);

                if (cursor == null) {
                    titles.add(field.getName());
                }

                objects.add(String.valueOf(value));
            }

            if (cursor == null) {
                cursor = new MatrixCursor(titles.toArray(new String[list.size()]));
            }

            cursor.addRow(objects);
        }

        return cursor;
    }
}
