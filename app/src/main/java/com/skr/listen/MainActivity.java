package com.skr.listen;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.util.Log;


import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    dataBase myDB;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

            int index = 0;
            for(TopTracks current : topTracks){
                    String track_name = current.getTrackName();
                    String track_play_count = Integer.toString(current.getTrackPlayCount());
                    String track_listener = Integer.toString(current.getTrackListener());
                    String track_url = current.getTrackUrl();
                    String artist_name = current.getArtistName();
                    String artist_image = current.getArtistImage();
                    myDB.insertData(track_name, track_play_count, track_listener, track_url, artist_name, artist_image);
                index++;
            }
//            StringBuffer buffer = new StringBuffer();
//            Cursor res = myDB.getAllData();
//            while (res.moveToNext()){
//                buffer.append("Id :"+ res.getString(0)+"\n");
//                buffer.append("Track Name :"+ res.getString(1)+"\n");
//                buffer.append("Track Play Count :"+ res.getString(2)+"\n");
//                buffer.append("Track Listener :"+ res.getString(3)+"\n\n");
//                buffer.append("Track Url :"+ res.getString(4)+"\n\n");
//                buffer.append("Artist Name :"+ res.getString(5)+"\n\n");
//                buffer.append("Artist Image :"+ res.getString(6)+"\n\n");
//            }
//
//            Log.d(TAG, "doInBackground: " + buffer.toString());

            return null;
        }
    }


}