package edu.usc.reach.myquituscnew.DatabaseHelpers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MyQuitContentProvider extends ContentProvider {

    private static final String AUTHORITY = "edu.usc.reach.myquitusc.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private MyQuitDatabaseHandler MQdbHelper;


    public MyQuitContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
       // // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        MQdbHelper.addPlannedSituation(new PlannedSituation(values.getAsString("datetime"),values.getAsString("situation"),values.getAsBoolean("activated")));
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_URI + "/" + true);
    }

    @Override
    public boolean onCreate() {
        MQdbHelper = MyQuitDatabaseHandler.getInstance(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
