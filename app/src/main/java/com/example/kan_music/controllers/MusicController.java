package com.example.kan_music.controllers;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.example.kan_music.entities.Song;

import java.util.ArrayList;
import java.util.List;

public class MusicController {
    public static List<Song> songs = new ArrayList<>();

    private int mCurrentIndex;
    private String mCurrentId;
    private boolean isStart;
    private boolean isPause;
    private boolean isLast;
    private Context mContext;
    private boolean isPrepare;

    MediaPlayer mediaPlayer;


    public MusicController(Context context) {
        this.mContext = context;
        mCurrentIndex = -1;
        isPrepare = false;
        isStart = true;
        isLast = false;
        mediaPlayer = new MediaPlayer();
        mCurrentId = "";
        isPause = false;
    }

    public boolean isPrepare() {
        return isPrepare;
    }

    public void loadSource(String fileUri) {
        try {
            if (this.mContext == null || mediaPlayer == null) {
                Log.d("Context Null Error :","In loadSource method.");
                return;
            }
            Uri uri = Uri.parse(fileUri);
            mediaPlayer.setDataSource(mContext, uri);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener((e)->{
                if (e.getDuration() != -1){
                    isPrepare = true;
                    e.start();
                }
            });
        } catch (Exception e) {
            Log.d("Error load music source  : ", e.getMessage());
        }
    }

    public void playAt(String songId,int pos){
        try{
            // check is pause

            if (pos == mCurrentIndex){
                if (!isPause){
                    isPause = true;
                    mediaPlayer.pause();
                    return;
                }
                isPause = false;
                mediaPlayer.start();
                return;
            }
            //check playing other
            if (this.mCurrentId != "" && mCurrentIndex != -1){
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                mediaPlayer.reset();
            }

            // update new source
            if (pos > songs.size() || pos < 0){
                return;
            }
            isPrepare = false;
            isPause = false;
            loadSource(songs.get(pos).getSong_uri());
            mCurrentIndex = pos;
            mCurrentId  = songs.get(pos).getId();
        }catch (Exception e){
            Log.d("Error when play at new source.",e.getMessage());
        }
    }


//

    public int getmCurrentIndex() {
        return mCurrentIndex;
    }

    public void setmCurrentIndex(int mCurrentIndex) {
        this.mCurrentIndex = mCurrentIndex;
    }

    public String getmCurrentId() {
        return mCurrentId;
    }

    public void setmCurrentId(String mCurrentId) {
        this.mCurrentId = mCurrentId;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public void setPrepare(boolean prepare) {
        isPrepare = prepare;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }
}
