package com.skr.listen;

public class TopTracks {
    private String trackName;
    private int trackPlayCount;
    private int trackListener;
    private String trackUrl;
    private String artistName;
    private String artistImage;

    public TopTracks(String trackName, int trackPlayCount,int trackListener, String trackUrl, String artistName, String artistImage) {
        this.trackName = trackName;
        this.trackPlayCount = trackPlayCount;
        this.trackListener = trackListener;
        this.trackUrl = trackUrl;
        this.artistName = artistName;
        this.artistImage = artistImage;
    }

    public TopTracks() {
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public int getTrackPlayCount() {
        return trackPlayCount;
    }

    public void setTrackPlayCount(int trackPlayCount) { this.trackPlayCount = trackPlayCount; }

    public int getTrackListener() {
        return trackListener;
    }

    public void setTrackListener(int trackListener) { this.trackListener = trackListener; }

    public String getTrackUrl() {
        return trackUrl;
    }

    public void setTrackUrl(String trackUrl) {
        this.trackUrl = trackUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistImage() {
        return artistImage;
    }

    public void setArtistImage(String artistImage) {
        this.artistImage = artistImage;
    }
}
