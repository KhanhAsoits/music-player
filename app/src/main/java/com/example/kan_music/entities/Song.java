package com.example.kan_music.entities;

import android.graphics.Bitmap;

public class Song {

    private String id;
    private String song_name;
    private String song_uri;
    private long song_duration;
    private String sonG_duration_str;
    private String song_singer;
    private Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getSong_singer() {
        return song_singer;
    }

    public void setSong_singer(String song_singer) {
        this.song_singer = song_singer;
    }

    public Song(String song_name, String id, long song_duration, String sonG_duration_str) {
        this.song_name = song_name;
        this.song_duration = song_duration;
        this.id = id;
        this.sonG_duration_str = sonG_duration_str;
    }

    public Song() {
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public long getSong_duration() {
        return song_duration;
    }

    public String getSong_uri() {
        return song_uri;
    }

    public void setSong_uri(String song_uri) {
        this.song_uri = song_uri;
    }

    public void setSong_duration(long song_duration) {
        this.song_duration = song_duration;
    }

    public String getSonG_duration_str() {
        return sonG_duration_str;
    }

    public void setSonG_duration_str(String sonG_duration_str) {
        this.sonG_duration_str = sonG_duration_str;
    }

    public String convertSongDuration(){
        return  null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
