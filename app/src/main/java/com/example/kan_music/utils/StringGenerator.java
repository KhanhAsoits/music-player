package com.example.kan_music.utils;

import android.net.Uri;
import android.util.Log;

import java.io.File;

public class StringGenerator {

    public static String myUri(String uri){
        try{
            File file = new File(uri);
            if (!file.exists()){
                return "";
            }
            return file.getAbsolutePath();
        }catch (Exception e){
            Log.d("Exception : ",e.getMessage());
        }
        return "";
    }

    public static String UUID_G(int length){
        StringBuilder strb = new StringBuilder();
        String []chars = {"1","2","3","4","5","6","7","8","9","0","A","a","b","B","c","C","d","D","e","E","f","F","g","G" +
                "h","H","i","I","k","K","m","M","n","N","x","X","Y","y","z","Z","o","O","!","@","#","$","%","^","&","*","-","_"};
        for (int i = 0 ; i < length - 1 ;i ++){
            int randN = Integer.valueOf((int) Math.round(Math.random() * (chars.length - 1) + 1));
            strb.append(chars[randN]);
        }
        return  strb.toString();
    }
    public static String TimeConverter(long dur){
        String out = "";
        String seconds = String.valueOf((dur % 60000) / 1000);
        String minutes = String.valueOf(dur / 60000);
        out = minutes + ":" + seconds;
        return String.format("%d",out);
    }
}
