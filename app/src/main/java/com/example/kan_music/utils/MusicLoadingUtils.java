package com.example.kan_music.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.kan_music.entities.Song;

import java.io.File;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public  class MusicLoadingUtils {
    public static String ROOT_PATH = Environment.getExternalStorageDirectory()+"";

    public static Thread doRetriedAllSong(){
        Runnable mt  = ()->{
            // DO re
            ArrayList<HashMap<String,String>> songs =  getPlayList(ROOT_PATH);
            // after get show

            if (songs!=null){
                Log.d("size : ",String.valueOf(songs.size()));
            }else {
                Log.d("Song null","song null");
            }
        };
        return new Thread(mt);
    }
    public static ArrayList<String> getMp3Files(Context context) {
        ArrayList<String> mp3Files = new ArrayList<>();
        String[] projection = {MediaStore.Audio.Media.DATA};
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null
        );
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(0);
                if (path.endsWith(".mp3")) {
                    mp3Files.add(path);
                }
            }
            cursor.close();
        }
        return mp3Files;
    }

    public static ArrayList<HashMap<String,String>> getPlayList(String rootPath) {
        ArrayList<HashMap<String,String>> fileList = new ArrayList<>();
        try {
            File rootFolder = new File(rootPath);
            File[] files = rootFolder.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else {
                        break;
                    }
                } else if (file.getName().endsWith(".mp3")) {
                    HashMap<String, String> song = new HashMap<>();
                    song.put("file_path", file.getAbsolutePath());
                    song.put("file_name", file.getName());
                    fileList.add(song);
                }
            }
            return fileList;
        } catch (Exception e) {
            Log.d("Load Media Errors:",e.getMessage());
            return null;
        }
    }
}
