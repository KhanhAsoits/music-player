package com.example.kan_music;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
        return  root;
    }
}