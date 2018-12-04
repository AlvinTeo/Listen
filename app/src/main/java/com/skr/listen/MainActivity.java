package com.skr.listen;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
            return null;
        }
    }
}