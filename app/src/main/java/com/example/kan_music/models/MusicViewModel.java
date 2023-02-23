package com.example.kan_music.models;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.kan_music.controllers.MusicController;
import com.example.kan_music.entities.Song;

import java.util.List;

public class MusicViewModel extends ViewModel {
    private MusicController musicController = new MusicController();
    public MusicViewModel(){
    }
    public void setMusicContext(Context context){
        if (musicController.getmContext() == null){
            musicController.setContext(context);
        }
    }

    public void setSongs(List<Song> songs){
        musicController.setSongs(songs);
    }
    public List<Song> getSongs(){return musicController.getSongs();}
    public int getCount(){return musicController.getSongs().size();}

    public MusicController get() {
        return musicController;
    }
}
