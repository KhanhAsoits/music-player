package com.example.kan_music.entities;

public class Song {

    private String song_name;
    private long song_duration;
    private String sonG_duration_str;

    public Song(String song_name, long song_duration, String sonG_duration_str) {
        this.song_name = song_name;
        this.song_duration = song_duration;
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
}
