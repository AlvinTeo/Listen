package com.skr.listen;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.util.Log;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    dataBase myDB ;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDB = new dataBase(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchTopTracks().execute();

    }

    public class FetchTopTracks extends AsyncTask<Void, Void, Void> {
        final private String API_KEY = "b832400592df462ed1c55376edbff179";
        private String url;

        ArrayList<TopTracks> topTracks;

        @Override
        protected Void doInBackground(Void... voids) {
            url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=" + API_KEY + "&format=json";
            topTracks = Utils.fetchTopTracks(url);

            for(TopTracks current : topTracks){
                String track_name = current.getTrackName();
                int track_play_count =current.getTrackPlayCount();
                int track_listener = current.getTrackListener();
                String track_url = current.getTrackUrl();
                String artist_name = current.getArtistName();
                String artist_image = current.getArtistImage();
                myDB.insertData(track_name, track_play_count, track_listener, track_url, artist_name, artist_image);
            }

            ArrayList<TopTracks> res = myDB.getAllData();
            for (TopTracks current_a : res){
                Log.d(TAG, "doInBackground: " + current_a.getTrackName());
                Log.d(TAG, "doInBackground: " + current_a.getTrackPlayCount());
                Log.d(TAG, "doInBackground: " + current_a.getTrackListener());
                Log.d(TAG, "doInBackground: " + current_a.getTrackUrl());
                Log.d(TAG, "doInBackground: " + current_a.getArtistName());
                Log.d(TAG, "doInBackground: " + current_a.getArtistImage());


            }

            return null;
        }
    }


}