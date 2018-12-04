package com.skr.listen;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public static ArrayList<TopTracks> fetchTopTracks(String url) {
        ArrayList<TopTracks> topTracks = new ArrayList<>();
        try {
            URL new_url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) new_url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            String results = IOUtils.toString(inputStream);
            parseTopTracksJson(results, topTracks);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return topTracks;
    }

    private static void parseTopTracksJson(String data, ArrayList<TopTracks> list) {
        try {
            JSONObject mainObject = new JSONObject(data);
            JSONObject resArray = mainObject.getJSONObject("tracks");
            for (int i = 0; i < resArray.length(); i++) {
                JSONArray tracksArray = resArray.getJSONArray("track");
                for (int j = 0; j < tracksArray.length(); j++) {
                    JSONObject trackObject = tracksArray.getJSONObject(j);
                    TopTracks topTrack = new TopTracks();
                    topTrack.setTrackName(trackObject.getString("name"));
                    topTrack.setTrackPlayCount(Integer.valueOf(trackObject.getString("playcount")));
                    topTrack.setTrackListener(Integer.valueOf(trackObject.getString("listeners")));
                    topTrack.setTrackUrl(trackObject.getString("url"));
                    JSONObject artistObject = trackObject.getJSONObject("artist");
                    topTrack.setArtistName(artistObject.getString("name"));
                    JSONArray imagesArray = trackObject.getJSONArray("image");
                    topTrack.setArtistImage(imagesArray.getJSONObject(2).getString("#text"));
                    list.add(topTrack);
                    Log.d(TAG, "parseTopTracksJson: "+ topTrack.getTrackListener());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "parseTopTracksJson: HELLO");
        }
    }
}
