package com.hufeng.droidtools;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.CancellationSignal;

import java.util.ArrayList;

/**
 * Created by feng on 2014-04-15.
 */
public class DemoContentProvider extends ContentProvider {

    static ArrayList<String> mBooks = new ArrayList<String>();

    private static final String AUTHORITY = "com.hufeng.droidtools.provider";

    public static Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/book");

    private static final UriMatcher URI_MATCHER;

    private static final int BOOKS = 1;
    private static final int BOOK_ID = 2;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, "book", BOOKS);
        URI_MATCHER.addURI(AUTHORITY, "book" + "/#", BOOK_ID);
    }

    @Override
    public boolean onCreate() {
        mBooks.add("book 1");
        mBooks.add("book 2");
        mBooks.add("book 3");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (URI_MATCHER.match(uri)) {
            case BOOKS:
                String title = values.getAsString("title");
                mBooks.add(title);
                break;
        }
        Uri uri_with_id = ContentUris.withAppendedId(uri, mBooks.size());
        getContext().getContentResolver().notifyChange(uri_with_id, null);
        return uri_with_id;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            case BOOKS:
                count = mBooks.size();
                mBooks.clear();
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        MatrixCursor cursor = null;
        switch (URI_MATCHER.match(uri)) {
            case BOOKS:
                cursor = new MatrixCursor(new String[]{"_id","title"}, mBooks.size());
                if (mBooks.size() > 0) {
                    for(int i=0; i < mBooks.size(); i++) {
                        cursor.newRow().add(i+1).add(mBooks.get(i));
                    }
                }
                break;
            case BOOK_ID:
                int id = Integer.parseInt(uri.getPathSegments().get(1));
                boolean flag_hit = id>0 && id <=mBooks.size();
                cursor = new MatrixCursor(new String[]{"_id", "title"}, flag_hit?1:0);
                if (flag_hit) {
                    cursor.newRow().add(id).add(mBooks.get(id-1));
                }
                break;
        }
        // Tell the cursor what uri to watch, so it knows when its source data
        // changes
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }
}
