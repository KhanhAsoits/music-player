package com.example.kan_music.controllers;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.example.kan_music.MusicListFragment;
import com.example.kan_music.MusicListFragmentUI;
import com.example.kan_music.entities.Song;
import com.example.kan_music.utils.StringGenerator;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class MusicController {
    public List<Song> songs = new ArrayList<>();

    public  boolean isSeek = false;
    private int mCurrentIndex;
    private String mCurrentId;
    private boolean isStart;
    private boolean isPause;
    private boolean isLast;
    private Context mContext;
    private boolean isPrepare;
    public boolean isLooped;
    public boolean isAutoPlay;
    MusicListFragmentUI.UpdateListener updateListener;

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

    public MusicController() {
        mCurrentIndex = -1;
        isPrepare = false;
        isStart = true;
        isLooped = false;
        isAutoPlay = false;
        isLast = false;
        mediaPlayer = new MediaPlayer();
        mCurrentId = "";
        isPause = false;
    }

    public void setUpdateListener(MusicListFragmentUI.UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public void initSong() {
        mCurrentIndex = mCurrentIndex >= 0 ? mCurrentIndex : 0;
        if (mediaPlayer.getDuration() == 0 && mCurrentIndex == 0){
            prepareMusic(true,false);
            updateListener.onUpdate(false);
        }
    }

    public boolean gI() {
        return mediaPlayer != null;
    }

    public boolean isPlaying() {
        if (gI()) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    public void reset() {
        if (gI()) {
            mediaPlayer.reset();
        }
    }

    public void stop() {
        if (gI()) {
            mediaPlayer.stop();
        }
    }

    public boolean gI_Listener(){
        return updateListener!=null;
    }
    public boolean canPrev() {
        if (gI()) {
            return mCurrentIndex - 1 >= 0;
        }
        return false;
    }

    public boolean canNext(){
        if (gI()){
            return mCurrentIndex + 1 < songs.size();
        }
        return false;
    }

    public boolean play() {
        if (gI()) {
            if (isPrepare){
                mediaPlayer.start();
                return true;
            }
        }
        return false;
    }

    public void pause() {
        if (gI()) {
            mediaPlayer.pause();
        }
    }

    public void prepareMusic(boolean isReset,boolean isP) {
        if (mCurrentIndex > songs.size()) {
            return;
        }
        isPrepare  = false;
        loadSource(songs.get(mCurrentIndex).getSong_uri(),isReset,isP);
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public boolean isPrepare() {
        return isPrepare;
    }

    public void replay(){
        mediaPlayer.start();
    }
    public void loadSource(String fileUri,boolean isR,boolean isP) {
        try {
            if (this.mContext == null || mediaPlayer == null) {
                Log.d("Context Null Error :", "In loadSource method.");
                return;
            }
            //check file is exit
            String st = StringGenerator.myUri(fileUri);
            if (st.equals("")){
                return;
            }
            //
            if (isR){
                mediaPlayer.reset();
            }
            Uri uri = Uri.parse(fileUri);
            mediaPlayer.setDataSource(mContext, uri);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    updateListener.onFinish();
                }
            });
            mediaPlayer.setOnPreparedListener((e) -> {
                if (e.getDuration() != -1) {
                    isPrepare = true;
                    if (isP){
                        updateListener.onCallBack();
                        e.start();
                    }
                }
            });
        } catch (Exception e) {
            Log.d("Error load music source  : ", e.getMessage());
        }
    }

    public void seekTo(int progress,int fps) {
        mediaPlayer.seekTo(progress * fps);
    }

    public void playAt(String songId, int pos) {
        try {
            Log.d("song length  : ", this.songs.size() + "");
            // check is pause
            if (pos == mCurrentIndex) {
                if (!isPause) {
                    isPause = true;
                    mediaPlayer.pause();
                    return;
                }
                isPause = false;
                mediaPlayer.start();
                return;
            }
            //check playing other
            if (this.mCurrentId != "" && mCurrentIndex != -1) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.reset();
            }

            // update new source
            if (pos > songs.size() || pos < 0) {
                return;
            }
            isPrepare = false;
            isPause = false;
            loadSource(songs.get(pos).getSong_uri(),true,true);
            mCurrentIndex = pos;
            mCurrentId = songs.get(pos).getId();
        } catch (Exception e) {
            Log.d("Error when play at new source.", e.getMessage());
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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
//

    public Context getmContext() {
        return mContext;
    }

    public void playNext() {
        if (gI()){
            if (canNext()){
                mCurrentIndex+=1;
            }else {
                mCurrentIndex = 0;
            }
            prepareMusic(true,true);
            updateListener.onUpdate(true);
        }
    }
    public void playPrev(){
        if (gI()){
            if (canPrev()){
                mCurrentIndex-=1;
            }else {
                mCurrentIndex = songs.size() - 1;
            }
            prepareMusic(true,true);
            updateListener.onUpdate(true);
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
}
