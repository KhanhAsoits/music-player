package com.example.kan_music.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class MusicHelper extends AsyncTask<Void,Integer,Boolean> {
    Activity contextParent;

    public MusicHelper(Activity activity){
        this.contextParent = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(contextParent, "Loading music ....", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.d("Background task : ","Loading....");
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        Toast.makeText(contextParent, "Loading finish", Toast.LENGTH_SHORT).show();
        super.onPostExecute(aBoolean);
    }
}
