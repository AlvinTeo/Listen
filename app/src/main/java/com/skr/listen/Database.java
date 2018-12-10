package com.skr.listen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Tracks.db";
    public static final String TABLE_NAME = "tracks_table";
    public static final String TABLE_NAME_TWO = "favourite_table";
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
        db.execSQL("create table " + TABLE_NAME_TWO +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,trackName TEXT,trackPlayCount INTEGER,trackListener INTEGER, trackUrl TEXT, artistName TEXT, artistImage TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_TWO);
        onCreate(db);
    }





    public void insertData(String trackName, int trackPlayCount, int trackListener, String trackUrl, String artistName, String artistImage) {
        ArrayList<TopTracks> data = getFamousTrack();
        boolean repeat = false;

            for(TopTracks current : data){
                if(current.getTrackName().equals(trackName)){
                    repeat = true;
                }
            }

        if(!repeat) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, trackName);
            contentValues.put(COL_3, trackPlayCount);
            contentValues.put(COL_4, trackListener);
            contentValues.put(COL_5, trackUrl);
            contentValues.put(COL_6, artistName);
            contentValues.put(COL_7, artistImage);
            db.insert(Database.TABLE_NAME,null ,contentValues);
            db.close();
        }
    }

    public ArrayList<TopTracks> getFamousTrack() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+Database.TABLE_NAME +" ORDER BY trackPlayCount DESC" ,null);

        ArrayList<TopTracks> result = new ArrayList<>();

        if (cursor.moveToFirst()) {

            //Loop through the table rows
            do {
                String track_name = cursor.getString(1);
                String track_play_count = cursor.getString(2);
                String track_listener = cursor.getString(3);
                String track_url = cursor.getString(4);
                String artist_name = cursor.getString(5);
                String artist_image = cursor.getString(6);
                TopTracks current = new TopTracks(track_name,Integer.parseInt(track_play_count),Integer.parseInt(track_listener),track_url,artist_name,artist_image);
                result.add(current);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }


    public TopTracks getTrack(String trackName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+Database.TABLE_NAME+" WHERE trackName = ?" , new String[]{trackName});
        TopTracks current = null;

        if (cursor != null && cursor.moveToFirst()) {
            String track_name = cursor.getString(1);
            String track_play_count = cursor.getString(2);
            String track_listener = cursor.getString(3);
            String track_url = cursor.getString(4);
            String artist_name = cursor.getString(5);
            String artist_image = cursor.getString(6);

            current = new TopTracks(track_name,Integer.parseInt(track_play_count),Integer.parseInt(track_listener),track_url,artist_name,artist_image);

        }

        cursor.close();
        db.close();

        return current ;
    }

    public void addFavourite(TopTracks track){
        String trackName = track.getTrackName();
        String trackPlayCount = String.valueOf(track.getTrackPlayCount());
        String trackListener = String.valueOf(track.getTrackListener());
        String trackUrl = track.getTrackUrl();
        String artistName = track.getArtistName();
        String artistImage = track.getArtistImage();

        ArrayList<TopTracks> data = getFavouriteTrack();
        boolean repeat = false;

        for(TopTracks current : data){
            if(current.getTrackName().equals(trackName)){
                repeat = true;
            }
        }

        if(!repeat) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, trackName);
            contentValues.put(COL_3, trackPlayCount);
            contentValues.put(COL_4, trackListener);
            contentValues.put(COL_5, trackUrl);
            contentValues.put(COL_6, artistName);
            contentValues.put(COL_7, artistImage);
            db.insert(Database.TABLE_NAME_TWO,null ,contentValues);
            db.close();
        }
    }

    public ArrayList<TopTracks> getFavouriteTrack() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+Database.TABLE_NAME_TWO,null);

        ArrayList<TopTracks> result = new ArrayList<>();

        if (cursor.moveToFirst()) {

            //Loop through the table rows
            do {
                String track_name = cursor.getString(1);
                String track_play_count = cursor.getString(2);
                String track_listener = cursor.getString(3);
                String track_url = cursor.getString(4);
                String artist_name = cursor.getString(5);
                String artist_image = cursor.getString(6);
                TopTracks current = new TopTracks(track_name,Integer.parseInt(track_play_count),Integer.parseInt(track_listener),track_url,artist_name,artist_image);
                result.add(current);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }


    public void deleteItem(String trackName) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME_TWO, COL_2 + "= ?" , new String[]{trackName});
            db.close();
    }

}
