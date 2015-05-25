package io.elapse.stocks.utils;

import android.database.Cursor;
import android.database.MatrixCursor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ContentUtils {

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
