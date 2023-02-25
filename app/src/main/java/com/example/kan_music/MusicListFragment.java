package com.example.kan_music;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kan_music.adapters.MusicListAdapter;
import com.example.kan_music.controllers.MusicController;
import com.example.kan_music.entities.Song;
import com.example.kan_music.utils.MusicHelper;

import java.util.List;


public class MusicListFragment extends Fragment {
    public MusicListFragment() {
        // Required empty public constructor
    }

    RecyclerView mRcySong;
    MusicListAdapter mMusicAdapter;
    SeekBar mMusicDurationSkb;
    ImageButton mImbControl;
    ImageButton mImbPrev;
    TextView mTxtSongName;

    ImageButton mImbNext;
    MusicController mMainMusicController;
    UpdateListener updateViewListener;

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_music_list, container, false);
        loadElements(root);
        initSong();
        return root;
    }

    public void initSong() {
        if (MainActivity.musicViewModel.getCount() == 0) {
//            MusicHelper musicHelper = new MusicHelper(getActivity(), this);
//            musicHelper.execute();
        } else {
            loadMusic(getActivity(), MainActivity.musicViewModel.getSongs());
        }
    }

    public void loadElements(View root) {
        mRcySong = root.findViewById(R.id.rcy_song);
        mMusicDurationSkb = root.findViewById(R.id.skb_duration);
        mImbControl = root.findViewById(R.id.imb_control);
        mImbPrev = root.findViewById(R.id.imb_prev);
        mTxtSongName = root.findViewById(R.id.txt_song_name);
        mImbNext = root.findViewById(R.id.imb_next);
        mMainMusicController = MainActivity.musicViewModel.getMusicController();
    }

    public void onSeekBarListener() {

    }
    public void initListener(){
        updateViewListener = (isPlay) -> {
            //set view
            updateIsPlaying();
            //set click
            setOnClickBtnControl();
            //check next and prev
            setImbControl();
            if (mMainMusicController.isPlaying()){
                mImbControl.setImageResource(R.drawable.ic_pause_btn);
            }
            //set
            mTxtSongName.setText(mMainMusicController.getSongs().get(mMainMusicController.getmCurrentIndex()).getSong_name());
        };
    }

    public void initSeekBar() {

    }

    public void initMusicPlayer() {
        mMainMusicController.initSong();
    }

    public void updateIsPlaying(){
        if (mMainMusicController.isPlaying()) {
            mImbControl.setImageResource(R.drawable.ic_pause_btn);
        } else {
            mImbControl.setImageResource(R.drawable.ic_play_btn);
        }
    }
    public void setOnClickBtnControl(){
        mImbControl.setOnClickListener((v)->{
            if (!mMainMusicController.isPlaying()) {
                Log.d("play click   : ","play");
                // update view
                mMainMusicController.play();
                mImbControl.setImageResource(R.drawable.ic_pause_btn);
            } else {
                mMainMusicController.pause();
                mImbControl.setImageResource(R.drawable.ic_play_btn);
            }
        });

        mImbNext.setOnClickListener((v)->{
            Log.d("next click   : ","next");
            mMainMusicController.playNext();
        });
        mImbPrev.setOnClickListener((v)->{
            Log.d("prev click   : ","prev");
            mMainMusicController.playPrev();
        });
    }
    public void setImbControl(){
    }


    public void loadMusic(Context contextParent, List<Song> songs) {
        // render to view
        MainActivity.musicViewModel.setSongs(songs);
        if (mRcySong != null) {
            mMusicAdapter = new MusicListAdapter(contextParent);
            mMusicAdapter.setOnItemCLickListener((pos -> {
                MainActivity.musicViewModel.getMusicController().playAt("", pos);
            }));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(contextParent, RecyclerView.VERTICAL, false);
            mRcySong.setLayoutManager(linearLayoutManager);
            mMusicAdapter.setData(MainActivity.musicViewModel.getSongs());
            mRcySong.setAdapter(mMusicAdapter);

            // init music controller
            initListener();
//            mMainMusicController.setUpdateListener(updateViewListener);
            initMusicPlayer();
            initSeekBar();
            onSeekBarListener();
            updateViewListener.onUpdate(false);
        }
    }

    public interface UpdateListener{
        public void onUpdate(boolean isPlay);
    }
}