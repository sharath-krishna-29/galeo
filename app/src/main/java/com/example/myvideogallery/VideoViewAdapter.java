package com.example.myvideogallery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.MyVideoViewHolder> {
    private final ArrayList<ModelVideo> videosList;
    private final Context mContext;
    public VideoViewAdapter(ActivityVideoView activityVideoView, ArrayList<ModelVideo> videolist) {
        this.mContext=activityVideoView;
        this.videosList=videolist;
    }

    @NonNull
    @Override
    public MyVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view_layout, parent, false);
        return new MyVideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVideoViewHolder holder, int position) {

        ModelVideo data = videosList.get(position);
        Glide.with(mContext).load(data.getData()).into(holder.getVideo_view());

        holder.getPlayButtonView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.animate().setInterpolator(new BounceInterpolator());
                Intent intent=new Intent(view.getContext(),ActivityPlayer.class);
                intent.putExtra("video_id",data.getId());
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    public static class MyVideoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView video_view;
        private final ImageView playButtonView;

        public MyVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            video_view=itemView.findViewById(R.id.thumbnail_view);
            playButtonView=itemView.findViewById(R.id.play_video_button);


        }

        public ImageView getVideo_view() {
            return video_view;
        }

        public ImageView getPlayButtonView() {
            return playButtonView;
        }


    }
}
