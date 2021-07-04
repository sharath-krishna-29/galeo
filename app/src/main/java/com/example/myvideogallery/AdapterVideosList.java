package com.example.myvideogallery;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class AdapterVideosList extends RecyclerView.Adapter<AdapterVideosList.MyViewHolder> {
    private static final String TAG = "AdapterVideosList";

    private ArrayList<ModelVideo> videosList = new ArrayList<ModelVideo>();
    private Context mContext;

    public AdapterVideosList(Context context, ArrayList<ModelVideo> videosList) {
        this.mContext = context;
        this.videosList = videosList;
    }

    @NonNull
    @Override
    public AdapterVideosList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_videos, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVideosList.MyViewHolder holder, int position) {
        ModelVideo data = videosList.get(position);

//    holder.getTv_title().setText(data.getTitle());
            holder.getTv_duration().setText(data.getDuration());
            Glide.with(mContext).load(data.getData()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.getThumbnail());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),ActivityVideoView.class);
                intent.putExtra("position",holder.getAdapterPosition());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e(TAG, "list size " + videosList.size());
        return videosList.size();
    }

    public void updateNewVideoList(ArrayList<ModelVideo> videosList) {
        this.videosList=videosList;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail;
        private TextView tv_title;
        private TextView tv_duration;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.imageview_thumbnail);
            tv_duration = itemView.findViewById(R.id.duration);
//            tv_title=itemView.findViewById(R.id.tv_title);
        }

        public ImageView getThumbnail() {
            return thumbnail;
        }

        public TextView getTv_duration() {
            return tv_duration;
        }
//
//        public TextView getTv_title() {
//            return tv_title;
//        }
    }
}
