package com.example.kan_music;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kan_music.adapters.MusicListAdapter;
import com.example.kan_music.utils.MusicHelper;


public class MusicListFragment extends Fragment {
    public MusicListFragment() {
        // Required empty public constructor
    }

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
        MusicHelper loadMusicTask = new MusicHelper(getActivity(),root);
        loadMusicTask.execute();
        return  root;
    }
}