package com.example.kan_music.utils;
import static com.example.kan_music.utils.MusicLoadingUtils.getMp3Files;

import android.app.Activity;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.UUID.*;
import com.example.kan_music.MusicListFragment;
import com.example.kan_music.MusicListFragmentUI;
import com.example.kan_music.entities.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicHelper extends AsyncTask<Void,Void,List<Song>> {
    Activity contextParent;
    View fragment;
    MusicListFragmentUI musicListFragment;

    public static String ROOT_PATH = Environment.getExternalStorageDirectory() + "";

    public MusicHelper(Activity activity, MusicListFragmentUI musicListFragment){
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
                Duration duration = getDuration(s);
                song.setSong_duration(duration.getTime());
                song.setSonG_duration_str(duration.getTimeStr());
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

    public class Duration{
        private long time;
        private String timeStr;

        public Duration(long time, String timeStr) {
            this.time = time;
            this.timeStr = timeStr;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getTimeStr() {
            return timeStr;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
        }
    }
    public Duration getDuration(String fileUri){
        try{
            // load data file
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(fileUri);

            String out = "";
            // get mp3 info

            // convert duration to minute:seconds
            String duration =
                    metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            Log.v("time", duration);
            long dur = Long.parseLong(duration);
            String seconds = String.valueOf((dur % 60000) / 1000);

            Log.v("seconds", seconds);
            String minutes = String.valueOf(dur / 60000);
            out = minutes + ":" + seconds;
            if (seconds.length() == 1) {
                out = "0" + minutes + ":0" + seconds;
            }else {
                out = ("0" + minutes + ":" + seconds);
            }
            Duration duration_ = new Duration(dur,out);
            metaRetriever.release();
            return duration_;
        }catch (Exception e){
            Log.d("error when get duration : ",e.getMessage());
        }
        return new Duration(0,"");
    }

    public static String mTStr(long msec){
        long minutes = (msec / 1000) / 60;

        // formula for conversion for
        // milliseconds to seconds
        long seconds = (msec / 1000) % 60;
        return minutes + ":" + seconds;
    }
}
