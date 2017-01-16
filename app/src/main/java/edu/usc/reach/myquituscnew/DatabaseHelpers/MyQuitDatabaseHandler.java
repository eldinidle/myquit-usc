package edu.usc.reach.myquituscnew.DatabaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eldin on 3/17/15.
 */
public class MyQuitDatabaseHandler extends SQLiteOpenHelper {

    private static MyQuitDatabaseHandler sInstance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyQuitDatabase";
    private static final String TABLE_MICRO = "microrandomization";

    private static final String KEY_COUNT = "count";
    private static final String KEY_DATETIME = "datetime";
    private static final String KEY_SITUATION = "situation";
    private static final String KEY_ACTIVATED = "activated";

    private MyQuitDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MyQuitDatabaseHandler getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new MyQuitDatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MICRO_TABLE = "CREATE TABLE " + TABLE_MICRO + "("
                + KEY_COUNT + " INTEGER PRIMARY KEY," + KEY_DATETIME +
                " TEXT ," + KEY_SITUATION + " TEXT,"
                + KEY_ACTIVATED + " BOOLEAN" + ")";
        db.execSQL(CREATE_MICRO_TABLE);
    }

    public void addPlannedSituation(PlannedSituation situation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATETIME, situation.getDateTime());
        values.put(KEY_SITUATION, situation.getSituation());
        values.put(KEY_ACTIVATED, situation.getActivated());

        db.insert(TABLE_MICRO, null, values);
        db.close();
    }

    public List<PlannedSituation> getAllPlannedSituations() {
        List<PlannedSituation> situationList = new ArrayList<PlannedSituation>();

        String selectQuery = "SELECT  * FROM " + TABLE_MICRO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                PlannedSituation situation = new PlannedSituation();
                situation.setCount(Integer.parseInt(cursor.getString(0)));
                situation.setDateTime(cursor.getString(1));
                situation.setSituation(cursor.getString(2));
                situation.setActivated(Boolean.valueOf(cursor.getString(3)));

                situationList.add(situation);
            } while (cursor.moveToNext());
        }

        
        return situationList;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MICRO);
        onCreate(db);
    }
}
