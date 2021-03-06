package com.skr.listen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {

    private static final String TAG = "PopularActivity";
    Database myDb2;
    ArrayList<String> trackImage = new ArrayList<>();
    ArrayList<String> trackName = new ArrayList<>();
    ArrayList<String> artistName = new ArrayList<>();
    RecyclerView recyclerView;


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb2 = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        ArrayList<TopTracks> tracks = myDb2.getFavouriteTrack();

        for (TopTracks current : tracks) {
            trackImage.add(current.getArtistImage());
            trackName.add(current.getTrackName());
            artistName.add(String.valueOf(current.getArtistName()));
        }

        initRecyclerView();
    }


    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewAdapter adapter = new RecycleViewAdapter(this, trackName, artistName, trackImage, true);
        recyclerView.setAdapter(adapter);
    }
}
