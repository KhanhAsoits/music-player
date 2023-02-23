package com.example.kan_music.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kan_music.MainActivity;
import com.example.kan_music.R;
import com.example.kan_music.controllers.MusicController;
import com.example.kan_music.entities.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder>{

    List<Song> mListSong = new ArrayList<>();
    Context mContext;
    MusicController musicController;

    @NonNull
    @Override
    public MusicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_card,parent,false);
        return new MusicListViewHolder(view);

    }
    public MusicListAdapter(Context context){
        this.mContext = context;
        musicController = MainActivity.musicViewModel.get();
    }

    public void setData(List<Song> songs){
        this.mListSong = songs;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListViewHolder holder, int position) {
        Song song = mListSong.get(position);
        if (song==null){
            return;
        }
        if (musicController.getmCurrentIndex() == position){
            holder.mImgBtnControl.setImageResource(musicController.isPause() ? R.drawable.ic_play_btn : R.drawable.ic_pause_btn);
        }else {
            holder.mImgBtnControl.setImageResource(R.drawable.ic_play_btn);
        }

        holder.mImgBtnControl.setOnClickListener((v)->{
            musicController.playAt(song.getId(), position);
            notifyDataSetChanged();
        });

        holder.mTxtSongName.setText(song.getSong_name().replace(".mp3",""));
    }

    @Override
    public int getItemCount() {
        if (mListSong.size() > 0)
            return mListSong.size();
        return 0;
    }

    public  class MusicListViewHolder extends RecyclerView.ViewHolder{
        TextView mTxtSongName;
        ImageButton mImgBtnControl;
        public MusicListViewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtSongName = itemView.findViewById(R.id.txt_music_name);
            mImgBtnControl = itemView.findViewById(R.id.btn_control);
            int backgroundColor = Color.parseColor("#80ffffff");
            itemView.setBackgroundColor(backgroundColor);
            ((CardView) itemView).setRadius(4);
        }
    }
}