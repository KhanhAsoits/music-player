package com.example.kan_music.utils;
import static com.example.kan_music.utils.MusicLoadingUtils.getMp3Files;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.UUID.*;
import com.example.kan_music.MusicListFragment;
import com.example.kan_music.entities.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicHelper extends AsyncTask<Void,Void,List<Song>> {
    Activity contextParent;
    View fragment;
    MusicListFragment musicListFragment;

    public static String ROOT_PATH = Environment.getExternalStorageDirectory() + "";

    public MusicHelper(Activity activity,MusicListFragment musicListFragment){
        this.contextParent = activity;
        this.musicListFragment  = musicListFragment;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(contextParent, "Loading music ....", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected List<Song> doInBackground(Void... voids) {
        List<Song> songs  = new ArrayList<>();
        try{
            List<String> songsUris = getMp3Files(contextParent);
            songsUris.forEach((s)->{
                File file = new File(s);
                Song song = new Song();
                song.setId(java.util.UUID.randomUUID().toString());
                song.setSong_name(file.getName());
                song.setSong_uri(s);
                songs.add(song);
            });
        }catch (Exception e){
            Log.d("Music get error:",e.getMessage());
        }
        SystemClock.sleep(3000);
        return songs;
    }



    @Override
    protected void onPostExecute(List<Song> songs) {
        if (songs.size() > 0){
            musicListFragment.loadMusic(contextParent,songs);
        }
        //song null
        Toast.makeText(contextParent, "Loading music finish", Toast.LENGTH_SHORT).show();
    }
}
