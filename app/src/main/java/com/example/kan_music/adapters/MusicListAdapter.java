package com.example.kan_music.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kan_music.MainActivity;
import com.example.kan_music.MusicListFragmentUI;
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
    private OnItemClickListener mItemCLickListener;
    MusicListFragmentUI.UpdateListener updateListener;

    public void setOnItemCLickListener (OnItemClickListener listener){
        this.mItemCLickListener = listener;
    }

    @NonNull
    @Override
    public MusicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_card_ui,parent,false);
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
        holder.bindView(position,song,musicController,holder,this);

    }

    @Override
    public int getItemCount() {
        if (mListSong.size() > 0)
            return mListSong.size();
        return 0;
    }

    public void setUpdateListener(MusicListFragmentUI.UpdateListener updateListener){
        this.updateListener = updateListener;
    }
    public static class MusicListViewHolder extends RecyclerView.ViewHolder{
        TextView mTxtSongName;
        TextView mTxtSongDuration;

        public MusicListViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtSongName = itemView.findViewById(R.id.txt_music_title);
            mTxtSongDuration = itemView.findViewById(R.id.txt_music_duration);
        }
        public void bindView(int position,Song song,MusicController musicController,MusicListViewHolder holder,MusicListAdapter this_){
            mTxtSongName.setText(song.getSong_name().replace(".mp3",""));
            mTxtSongDuration.setText(song.getSonG_duration_str());
            if (position == musicController.getmCurrentIndex()){
                itemView.setBackgroundColor(Color.parseColor("#f5f5f5"));
            }else {
                itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    musicController.playAt("",position);
                    this_.notifyDataSetChanged();
                    this_.updateListener.onUpdate(true);
                }
            });
            // animated
        }
    }
    public interface OnItemClickListener{
        public void onClick(int pos);
    }
}