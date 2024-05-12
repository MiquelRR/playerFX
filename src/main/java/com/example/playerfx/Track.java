package com.example.playerfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.util.Duration;

public class Track {

    private static List<String> playedList = new ArrayList<>();
    private static Map<String, Track> discoteca = new HashMap<>();
    private static Map<String, String> viewText = new HashMap<>() {
    };
    static {

    }
    @FXML
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
    private Double duration; // The duration of the track.
    private String genre; // The genre of the track, for example, "Classical," "Darkwave," or "Jazz."
    // image javafx.scene.image.Image The album cover.
    private String title; // The name of the track.
    private Integer track_count; // The number of tracks on the album.
    private Integer track_number; // The 1-relatve index of this track on the disc.

    static String toMmSs(Double secs) {
        if (secs != null) {
            Long min = Math.round(secs / 60);
            Long sec = Math.round(secs % 60);
            return String.format("%02d:%02d", min, sec);
        }
        return null;
    }

    Track(String fileURI, Map<String, Object> metadata, Double duration) {
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
            if (key.equals("genre"))
                this.genre = metadata.get("genre").toString();
            if (key.equals("title"))
                this.title = metadata.get("title").toString();
            if (key.equals("track count"))
                this.track_count = (Integer) metadata.get("track count");
            if (key.equals("track number"))
                this.track_number = (Integer) metadata.get("track number");
        }
        this.duration = duration;

        if(this.title == null){
            String str=fileURI.replace("/", "\\");
            String st[] = str.split("\\\\");
            this.title=st[(st.length-1)];
        }
        
        discoteca.put(fileURI, this);
        int sz = playedList.size();
        if (sz > 1 && !playedList.get(sz - 1).equals(fileURI)) {
            playedList.add(fileURI);

        }

    }

    public String getAllinfo() {
        String allInfo = "";
        if (this.album_artist != null)
            allInfo += "Artista:" + this.album_artist + " ";
        if (this.album != null)
            allInfo += "Album:" + this.album + " ";
        if (this.artist != null)
            allInfo += "Intérprete:" + this.artist + " ";
        if (this.composer != null)
            allInfo += "Compositor:" + this.composer + " ";
        if (this.year != null)
            allInfo += "Año:" + this.year + " ";
        if (this.disc_count != null)
            allInfo += "Discos tot,:" + this.disc_count + " ";
        if (this.disc_number != null)
            allInfo += "Disco:" + this.disc_number + " ";
        if (this.duration != null)
            allInfo += "Duración:" + toMmSs(this.duration) + " ";
        if (this.genre != null)
            allInfo += "Género:" + this.genre + " ";
        if (this.title != null)
            allInfo += "Título:" + this.title + " ";
        if (this.track_count != null)
            allInfo += "Traks:" + this.track_count + " ";
        if (this.track_number != null)
            allInfo += "Track:" + this.track_number + " ";
        return allInfo;
    }

    public String getFileURI() {
        return fileURI;
    }

    public String getAlbum_artist() {
        return album_artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getComposer() {
        return composer;
    }

    public Integer getYear() {
        return year;
    }

    public String getDisc_count() {
        return disc_count;
    }

    public String getDisc_number() {
        return disc_number;
    }

    public String getDurationString() {
        if (duration != null)
            return toMmSs(duration);
        else
            return "00:00";
    }

    public Double getDuration(){
        return duration;
    }


    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public Integer getTrack_count() {
        return track_count;
    }

    public Integer getTrack_number() {
        return track_number;
    }

}
