package com.skr.listen;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.View;
import android.widget.ProgressBar;


import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<TopTracks> topTracks = new ArrayList<>();
    ArrayList<String> trackName = new ArrayList<>();
    ArrayList<String> trackImage = new ArrayList<>();
    ArrayList<String> trackDescription = new ArrayList<>();
    //ArrayList<TopTracks> databaseTest = new ArrayList<>();
    dataBase myDB;
    RecyclerView recyclerView;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new dataBase(this);
        progressBar = findViewById(R.id.progressBar);
        new FetchTopTracks().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "Item1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item2 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item3 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class FetchTopTracks extends AsyncTask<Void, Void, Void> {
        final private String API_KEY = "b832400592df462ed1c55376edbff179";
        private String url;

        @Override
        protected Void doInBackground(Void... voids) {
            url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=" + API_KEY + "&format=json";
            topTracks = Utils.fetchTopTracks(url);

            for (TopTracks current : topTracks) {
                String track_name = current.getTrackName();
                trackName.add(track_name);
                int track_play_count = current.getTrackPlayCount();
                int track_listener = current.getTrackListener();
                String track_url = current.getTrackUrl();
                String artist_name = current.getArtistName();
                trackDescription.add(artist_name);
                String artist_image = current.getArtistImage();
                trackImage.add(artist_image);
//                myDB.insertData(track_name, track_play_count, track_listener, track_url, artist_name, artist_image);
            }

//            databaseTest = myDB.getAllData();
//            for (TopTracks current_a : databaseTest){
//                Log.d(TAG, "doInBackground: " + current_a.getTrackName());
//                Log.d(TAG, "doInBackground: " + current_a.getTrackPlayCount());
//                Log.d(TAG, "doInBackground: " + current_a.getTrackListener());
//                Log.d(TAG, "doInBackground: " + current_a.getTrackUrl());
//                Log.d(TAG, "doInBackground: " + current_a.getArtistName());
//                Log.d(TAG, "doInBackground: " + current_a.getArtistImage());
//            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.INVISIBLE);
            initRecyclerView();
        }
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView:  init recyclerView  //" + topTracks.toString());
        recyclerView = findViewById(R.id.recyclerView);
        RecycleViewAdapter adapter = new RecycleViewAdapter(this, trackName, trackDescription, trackImage);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}