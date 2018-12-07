package com.skr.listen;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PopularActivity extends AppCompatActivity {
    private static final String TAG = "PopularActivity" ;
    Database myDb2 ;
    ArrayList<String> trackImage = new ArrayList<>();
    ArrayList<String> trackName = new ArrayList<>();
    ArrayList<String> playCount = new ArrayList<>();
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
        setContentView(R.layout.activity_search);
        ArrayList<TopTracks> tracks = myDb2.getFamousTrack();

        for(TopTracks current : tracks){
            trackImage.add(current.getArtistImage());
            trackName.add(current.getTrackName());
            playCount.add(String.valueOf(current.getTrackPlayCount()));
        }

        //initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView2);
        RecycleViewAdapter adapter = new RecycleViewAdapter(this, trackName, playCount, trackImage);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
