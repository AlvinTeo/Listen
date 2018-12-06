package com.skr.listen;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class TrackActivity extends AppCompatActivity {
    public static final String EXTRA_TRACKNAME = "com.skr.listen.extra.TRACKNAME";
    private static final String TAG = "TrackActivity" ;

    private TextView name, description, views, counts, url;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        Database myDb = new Database(this);
        TopTracks track = myDb.getTrack(getIntent().getStringExtra(EXTRA_TRACKNAME));

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        views = findViewById(R.id.views);
        counts = findViewById(R.id.counts);
        image = findViewById(R.id.image);
        url = findViewById(R.id.url);


        //url.setMovementMethod(LinkMovementMethod.getInstance());


        Glide.with(getApplicationContext())
                .asBitmap()
                .load(track.getArtistImage())
                .into(image);

        name.setText(track.getTrackName());
        description.setText(track.getArtistName());
        views.setText(String.valueOf(track.getTrackListener()));
        counts.setText(String.valueOf(track.getTrackPlayCount()));
        url.setText(track.getTrackUrl());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
