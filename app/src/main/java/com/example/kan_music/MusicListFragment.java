package com.example.kan_music;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Transition;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kan_music.adapters.MusicListAdapter;
import com.example.kan_music.entities.Song;
import com.example.kan_music.utils.MusicHelper;

import java.util.List;


public class MusicListFragment extends Fragment {
    public MusicListFragment() {
        // Required empty public constructor
    }
    RecyclerView mRcySong;
    MusicListAdapter mMusicAdapter;

    public static MusicListFragment newInstance(String param1, String param2) {
        MusicListFragment fragment = new MusicListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_music_list,container,false);
        mRcySong = root.findViewById(R.id.rcy_song);
        if (MainActivity.musicViewModel.getCount() == 0){
            MusicHelper musicHelper = new MusicHelper(getActivity(),this);
            musicHelper.execute();
        }else {
            loadMusic(getActivity(),MainActivity.musicViewModel.getSongs());
        }
        return  root;
    }
    public void loadMusic(Context contextParent, List<Song> songs){
        // render to view
        MainActivity.musicViewModel.setSongs(songs);
        if (mRcySong!=null){
            mMusicAdapter = new MusicListAdapter(contextParent);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(contextParent,RecyclerView.VERTICAL,false);
            mRcySong.setLayoutManager(linearLayoutManager);
            mMusicAdapter.setData(MainActivity.musicViewModel.getSongs());
            mRcySong.setAdapter(mMusicAdapter);
        }
    }
}