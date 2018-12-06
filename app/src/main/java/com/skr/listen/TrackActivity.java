package com.skr.listen;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class TrackActivity extends AppCompatActivity {
    public static final String EXTRA_TRACKNAME = "com.skr.listen.extra.TRACKNAME";
    public static final String EXTRA_TRACKIMAGE = "com.skr.listen.extra.TRACKIMAGE";
    public static final String EXTRA_TRACKDESCRIPTION = "com.skr.listen.extra.TRACKDESCRIPTION";

    private TextView name;
    private TextView description;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        image = findViewById(R.id.image);

        Glide.with(getApplicationContext())
                .asBitmap()
                .load(getIntent().getStringExtra(EXTRA_TRACKIMAGE))
                .into(image);

        name.setText(getIntent().getStringExtra(EXTRA_TRACKNAME));
        description.setText(getIntent().getStringExtra(EXTRA_TRACKDESCRIPTION));
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
