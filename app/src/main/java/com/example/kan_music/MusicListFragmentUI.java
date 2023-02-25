package com.example.kan_music;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kan_music.adapters.MusicListAdapter;
import com.example.kan_music.controllers.MusicController;
import com.example.kan_music.entities.Song;
import com.example.kan_music.utils.MusicHelper;

import java.util.List;

public class MusicListFragmentUI extends Fragment {

    RecyclerView mRcySong;
    ImageButton mImbControl;
    ImageButton mImbNext;
    ImageButton mImbPrev;
    TextView mTxtMusicName;
    UpdateListener updateListener;
    MusicController mMainMusicController;
    SeekBar skbDuration;

    MusicListAdapter mMusicAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_music_list_ui,container,false);
        loadElement(root);
        initSong();
        return root;
    }

    public void initSong() {
        if (MainActivity.musicViewModel.getCount() == 0) {
            MusicHelper musicHelper = new MusicHelper(getActivity(), this);
            musicHelper.execute();
        } else {
            loadMusic(getActivity(), MainActivity.musicViewModel.getSongs());
        }
    }
    public void loadElement(View view){
        mMainMusicController = MainActivity.musicViewModel.getMusicController();
        mRcySong = view.findViewById(R.id.rcy_song_ui);
        mImbControl = view.findViewById(R.id.btn_control_light);
        mImbPrev = view.findViewById(R.id.btn_prev);
        mImbNext = view.findViewById(R.id.btn_next);
        skbDuration = view.findViewById(R.id.skb_player);
        mTxtMusicName = view.findViewById(R.id.txt_music_control_title);
        view.findViewById(R.id.skb_player).setPadding(0,0,0,0);
    }


    public void initListener(){
        updateListener = new UpdateListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onUpdate(boolean isPlay) {
                updateIsPlaying();
                //reset seek bar
                //
                setOnClickBtnControl();
                mMusicAdapter.notifyDataSetChanged();
                mTxtMusicName.setText(mMainMusicController.getSongs().get(mMainMusicController.getmCurrentIndex()).getSong_name().replace(".mp3",""));
            }

            @Override
            public void onCallBack() {
                mImbControl.setImageResource(R.drawable.ic_pause_btn_light);
            }

            @Override
            public void onFinish() {
                if (!mMainMusicController.isAutoPlay){
                    if (mMainMusicController.isLooped){
                        mMainMusicController.replay();
                        return;
                    }
                    mImbControl.setImageResource(R.drawable.ic_next_btn);
                    return;
                }
                // if auto play
                if (mMainMusicController.getmCurrentIndex() >= 0){
                    mMainMusicController.playNext();
                }
            }
        };
    }
    public void setOnClickBtnControl(){
        mImbControl.setOnClickListener((v)->{
            if (!mMainMusicController.isPlaying()) {
                // update view
                mMainMusicController.play();
                mImbControl.setImageResource(R.drawable.ic_pause_btn_light);
            } else {
                mMainMusicController.pause();
                mImbControl.setImageResource(R.drawable.ic_next_btn);
            }
        });

        mImbNext.setOnClickListener((v)->{
            mMainMusicController.playNext();
        });
        mImbPrev.setOnClickListener((v)->{
            mMainMusicController.playPrev();
        });
    }
    public void updateIsPlaying(){
        if (mMainMusicController.isPlaying()) {
            mImbControl.setImageResource(R.drawable.ic_pause_btn_light);
        } else {
            mImbControl.setImageResource(R.drawable.ic_next_btn);
        }
    }
    public void loadMusic(Context contextParent, List<Song> songs){
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
            mMainMusicController.setUpdateListener(updateListener);
            initMusicPlayer();
            initSeekBar();
            updateListener.onUpdate(false);
        }
    }

    private void initSeekBar() {
        //
        if (mMainMusicController.gI()){
            int duration = mMainMusicController.getMediaPlayer().getDuration();
            skbDuration.setMax(duration/1000);
            onSeekBarListener();
            skbDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    Log.d("here","here");
//                    mMainMusicController.seekTo(progress , 1000);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    mMainMusicController.isSeek = true;
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mMainMusicController.isSeek = false;
                }
            });
        }
    }

    private void onSeekBarListener() {
        Handler handler = new Handler();
        skbDuration.post(new Runnable() {
            @Override
            public void run() {
                int fps = 1000;
                if (mMainMusicController.isPlaying()){
                    int duration = mMainMusicController.getMediaPlayer().getCurrentPosition() / fps;
                    Log.d("duration : ",duration + "");
                    Log.d("max : ",mMainMusicController.getMediaPlayer().getDuration() / fps + "");
                    skbDuration.setMax(mMainMusicController.getMediaPlayer().getDuration() / fps);
                    skbDuration.setProgress(duration);
                }
                handler.postDelayed(this,fps);
            }
        });
    }

    private void initMusicPlayer() {
        mMainMusicController.initSong();
    }

    public interface UpdateListener{
        public void onUpdate(boolean isPlay);
        public void onCallBack();
        public void onFinish();
    }

}
