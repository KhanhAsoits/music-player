package com.example.kan_music.utils;
import static com.example.kan_music.utils.MusicLoadingUtils.getMp3Files;
import static com.example.kan_music.utils.StringGenerator.TimeConverter;
import static com.example.kan_music.utils.StringGenerator.UUID_G;

import android.app.Activity;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kan_music.R;
import com.example.kan_music.adapters.MusicListAdapter;
import com.example.kan_music.controllers.MusicController;
import com.example.kan_music.entities.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicHelper extends AsyncTask<Void,Void,List<Song>> {
    Activity contextParent;
    View fragment;
    RecyclerView mRcySong;
    MusicListAdapter mMusicAdapter;

    public static String ROOT_PATH = Environment.getExternalStorageDirectory() + "";

    public MusicHelper(Activity activity,View view){
        this.contextParent = activity;
        this.fragment = view;
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
                song.setId(UUID_G(10));
                song.setSong_name(file.getName());
                song.setSong_uri(s);
                songs.add(song);
            });
        }catch (Exception e){
            Log.d("Music get error:",e.getMessage());
        }
        return songs;
    }


    @Override
    protected void onPostExecute(List<Song> songs) {
        if (songs.size() > 0){
            MusicController.songs = songs;

            mRcySong = fragment.findViewById(R.id.rcy_song);
            mMusicAdapter = new MusicListAdapter(contextParent);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(contextParent,RecyclerView.VERTICAL,false);
            mRcySong.setLayoutManager(linearLayoutManager);
            mMusicAdapter.setData(songs);
            mRcySong.setAdapter(mMusicAdapter);
        }
        //song null
        Toast.makeText(contextParent, "Loading music finish", Toast.LENGTH_SHORT).show();
    }
}
