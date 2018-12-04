package com.skr.listen;

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
            JSONArray resArray = mainObject.getJSONArray("tracks");
            for (int i = 0; i < resArray.length(); i++) {
                JSONObject jsonObject = resArray.getJSONObject(i);
                TopTracks topTrack = new TopTracks();
                topTrack.setTrackName(jsonObject.getString(""));
                topTrack.setTrackPlayCount(99);
                topTrack.setTrackUrl("");
                topTrack.setArtistName("");
                topTrack.setArtistImage("");
                list.add(topTrack);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
