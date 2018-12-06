package com.skr.listen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Tracks.db";
    public static final String TABLE_NAME = "tracks_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "trackName";
    public static final String COL_3 = "trackPlayCount";
    public static final String COL_4 = "trackListener";
    public static final String COL_5 = "trackUrl";
    public static final String COL_6 = "artistName";
    public static final String COL_7 = "artistImage";
    private static final String TAG = "Database";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,trackName TEXT,trackPlayCount INTEGER,trackListener INTEGER, trackUrl TEXT, artistName TEXT, artistImage TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String trackName,int trackPlayCount,int trackListener,String trackUrl,String artistName, String artistImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,trackName);
        contentValues.put(COL_3,trackPlayCount);
        contentValues.put(COL_4,trackListener);
        contentValues.put(COL_5,trackUrl);
        contentValues.put(COL_6,artistName);
        contentValues.put(COL_7,artistImage);
        Log.d(TAG, "insertData: " + trackName);
        Log.d(TAG, "insertData: " + trackPlayCount);
        Log.d(TAG, "insertData: " + trackListener);
        Log.d(TAG, "insertData: " + trackUrl);
        Log.d(TAG, "insertData: " + artistImage);
        Log.d(TAG, "insertData: " + artistName);


        db.insert(Database.TABLE_NAME,null ,contentValues);
        db.close();

    }

    public ArrayList<TopTracks> getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+Database.TABLE_NAME ,null);


        ArrayList<TopTracks> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String track_name = cursor.getString(1);
            String track_play_count = cursor.getString(2);
            String track_listener = cursor.getString(3);
            String track_url = cursor.getString(4);
            String artist_name = cursor.getString(5);
            String artist_image = cursor.getString(6);

            TopTracks current = new TopTracks(track_name,Integer.parseInt(track_play_count),Integer.parseInt(track_listener),track_url,artist_name,artist_image);
            result.add(current);
        }

        cursor.close();
        db.close();
        return result;
    }


    public TopTracks getTrack(String trackName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+Database.TABLE_NAME+" WHERE trackName = ?" , new String[]{trackName});
        TopTracks current = null;
        Log.d(TAG, "getTrack: " + trackName);

        if (cursor != null && cursor.moveToFirst()) {
            String track_name = cursor.getString(1);
            String track_play_count = cursor.getString(2);
            String track_listener = cursor.getString(3);
            String track_url = cursor.getString(4);
            String artist_name = cursor.getString(5);
            String artist_image = cursor.getString(6);

            current = new TopTracks(track_name,Integer.parseInt(track_play_count),Integer.parseInt(track_listener),track_url,artist_name,artist_image);
            Log.d(TAG, "getTrack: " + track_name);

        }

        cursor.close();
        db.close();

        return current ;
    }

}
