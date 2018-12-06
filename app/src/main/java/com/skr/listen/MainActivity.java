package com.skr.listen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<TopTracks> topTracks = new ArrayList<>();
    ArrayList<String> trackName = new ArrayList<>();
    ArrayList<String> trackImage = new ArrayList<>();
    ArrayList<String> trackDescription = new ArrayList<>();
    //ArrayList<TopTracks> databaseTest = new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton searchButton;
    Database myDb ;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        new FetchTopTracks().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.item1:
////                Toast.makeText(this, "Item1 selected", Toast.LENGTH_SHORT).show();
////                return true;
            case R.id.item2:
                Intent intent = new Intent(MainActivity.this, PopularActivity.class);
                startActivity(intent);
                return true;
            case R.id.item3:
                Intent intent2 = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(intent2);
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
                //myDb.insertData(track_name, track_play_count, track_listener, track_url, artist_name, artist_image);
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
        recyclerView = findViewById(R.id.recyclerView);
        RecycleViewAdapter adapter = new RecycleViewAdapter(this, trackName, trackDescription, trackImage);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}