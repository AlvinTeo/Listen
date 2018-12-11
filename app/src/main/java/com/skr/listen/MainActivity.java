package com.skr.listen;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
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
    RecyclerView recyclerView;
    FloatingActionButton searchButton;
    Database myDb ;
    private ProgressBar progressBar;
    private NotificationManager mNotifyManager;
    private static final String PRIMARY_CHANNEL_ID = "notification_switch";
    private static final int NOTIFICATION_ID = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(intent);
            }
        });
        new FetchTopTracks().execute();
    createNotificationChannel();
    getNotificationBuilder();

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
                Intent intent2 = new Intent(MainActivity.this, AboutUsActivity.class);
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
                myDb.insertData(track_name, track_play_count, track_listener, track_url, artist_name, artist_image);
            }

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
        RecycleViewAdapter adapter = new RecycleViewAdapter(this, trackName, trackDescription, trackImage, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotifyManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            getString(R.string.notification_channel_name),
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    (getString(R.string.notification_channel_description));

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }


    private void getNotificationBuilder() {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity
                (this, NOTIFICATION_ID, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification with all of the parameters.
        NotificationCompat.Builder notifyBuilder = new NotificationCompat
                .Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Welcome to Listen")
                .setContentText("Get the latest top tracks in Listen")
                .setSmallIcon(R.drawable.ic_icon)
                .setAutoCancel(true).setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

    }


}