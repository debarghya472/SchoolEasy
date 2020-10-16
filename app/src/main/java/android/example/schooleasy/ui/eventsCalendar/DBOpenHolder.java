package android.example.schooleasy.ui.eventsCalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

public class DBOpenHolder extends SQLiteOpenHelper {
    private static final String CREATE_EVENTS_TABLE = "create table "+ DBStructure.Event_Table_Name+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            +DBStructure.Event+" TEXT, "+DBStructure.time+" TEXT, "+DBStructure.date+" TEXT, "+ DBStructure.month+" TEXT, "
            +DBStructure.year +" TEXT)";
    private static final String DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS " +DBStructure.Event_Table_Name;

    public DBOpenHolder(@Nullable Context context) {
        super(context, DBStructure.DB_NAME , null, DBStructure.DB_Version );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_EVENTS_TABLE);
        onCreate(db);
    }

    public void saveEvent(String event, String time, String date, String month, String year, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBStructure.Event,event);
        contentValues.put(DBStructure.time,time);
        contentValues.put(DBStructure.date,date);
        contentValues.put(DBStructure.month,month);
        contentValues.put(DBStructure.year,year);
        sqLiteDatabase.insert(DBStructure.Event_Table_Name,null,contentValues);

    }
    public Cursor ReadEvents(String date,SQLiteDatabase database){
        String [] projections = {DBStructure.Event, DBStructure.time, DBStructure.date,DBStructure.month,DBStructure.year};
        String selection = DBStructure.date + "=?";
        String [] selectionArgs = {date};

        return database.query(DBStructure.Event_Table_Name,projections,selection,selectionArgs,null,null,null);
    }
    public Cursor ReadEventsPerMonth(String month, String year,SQLiteDatabase database){
        String [] projections = {DBStructure.Event, DBStructure.time, DBStructure.date,DBStructure.month,DBStructure.year};
        String selection = DBStructure.month + "=? and "+DBStructure.year+"=?";
        String [] selectionArgs = {month,year};

        return database.query(DBStructure.Event_Table_Name,projections,selection,selectionArgs,null,null,null);
    }
}
