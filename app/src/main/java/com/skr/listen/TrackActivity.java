package com.skr.listen;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class TrackActivity extends AppCompatActivity {
    public static final String EXTRA_TRACKNAME = "com.skr.listen.extra.TRACKNAME";
    private static final String TAG = "TrackActivity" ;

    private TextView name, description, views, counts, url;
    private ImageView image;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        final Database myDb = new Database(this);
        final TopTracks track = myDb.getTrack(getIntent().getStringExtra(EXTRA_TRACKNAME));

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        views = findViewById(R.id.views);
        counts = findViewById(R.id.counts);
        image = findViewById(R.id.image);
        url = findViewById(R.id.url);
        button = findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(button.getContext(), "Added to favourite", Toast.LENGTH_LONG);//This is where the error shows
                toast.show();
                myDb.addFavourite(track);
            }
        });



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
        Toast.makeText(button.getContext(), "Added to favourite", Toast.LENGTH_SHORT).show();

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
