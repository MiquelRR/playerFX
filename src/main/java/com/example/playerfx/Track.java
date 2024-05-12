package com.example.playerfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.Observable;
import javafx.collections.ObservableMap;

public class Track {

    private static List<String> playedList = new ArrayList<>();
    private static Map<String, Track> discoteca = new HashMap<>();

    String fileURI;

    // raw metadata Map<String,ByteBuffer> The raw metadata according to the
    // appropriate media specification. The key "ID3" maps to MP3 ID3v2 metadata.
    private String album_artist; // The artist for the overall album, possibly "Various Artists" for
                                 // compilations.
    private String album; // The name of the album.
    private String artist; // The artist of the track.
    // private String comment_N; // A comment where N is a 0-relative index. Comment
    // format:
    // ContentDescription[lng]=Comment
    private String composer; // The composer of the track.
    private Integer year; // The year the track was recorded.
    private String disc_count; // The number of discs in the album.
    private String disc_number; // The 1-relative index of the disc on which this track appears.
    private String duration; // The duration of the track.
    private String genre; // The genre of the track, for example, "Classical," "Darkwave," or "Jazz."
    // image javafx.scene.image.Image The album cover.
    private String title; // The name of the track.
    private Integer track_count; // The number of tracks on the album.
    private Integer track_number; // The 1-relatve index of this track on the disc.

    Track(String fileURI, ObservableMap<String, Object> metadata) {
        this.fileURI = fileURI;
        for (String key : metadata.keySet()) {
            if (key.equals("album artist"))
                this.album_artist = metadata.get("album artist").toString();
            if (key.equals("album"))
                this.album = metadata.get("album").toString();
            if (key.equals("artist"))
                this.artist = metadata.get("artist").toString();

            if (key.equals("composer"))
                this.composer = metadata.get("composer").toString();
            if (key.equals("year"))
                this.year = (Integer) metadata.get("year");
            if (key.equals("disc count"))
                this.disc_count = metadata.get("disc count").toString();
            if (key.equals("disc number"))
                this.disc_number = metadata.get("disc number").toString();
            if (key.equals("duration"))
                this.duration = metadata.get("duration").toString();
            if (key.equals("genre"))
                this.genre = metadata.get("genre").toString();

            if (key.equals("title"))
                this.title = metadata.get("title").toString();
            if (key.equals("track count"))
                this.track_count = (Integer) metadata.get("track count");
            if (key.equals("track number"))
                this.track_number = (Integer) metadata.get("track number");
        }

        discoteca.put(fileURI,this);
        if(!playedList.get(playedList.size()-1).equals(fileURI)){
            playedList.add(fileURI);
        } 
    }

}
