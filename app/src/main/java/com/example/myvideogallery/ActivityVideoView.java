package com.example.myvideogallery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ActivityVideoView extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerview_video_view;
    private TextView share_view,fav_view,delete_view,more_view;
    private int position;
    private ArrayList<ModelVideo> videosList;
    private VideoViewModel videoViewModel;
    private VideoViewAdapter videoViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
         videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);

        position = getIntent().getExtras().getInt("position");

        videosList = videoViewModel.getVideosList().getValue();

        initViews();

        recyclerview_video_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerview_video_view.setHasFixedSize(true);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerview_video_view);
        recyclerview_video_view.scrollToPosition(position);
         videoViewAdapter = new VideoViewAdapter(this, videosList);
        recyclerview_video_view.setAdapter(videoViewAdapter);


        share_view.setOnClickListener(this);
        fav_view.setOnClickListener(this);
        delete_view.setOnClickListener(this);
        more_view.setOnClickListener(this);

    }



    private void initViews() {
        recyclerview_video_view = findViewById(R.id.recyclerview_video_view);
        share_view=findViewById(R.id.share_view);
        fav_view=findViewById(R.id.fav_view);
        delete_view=findViewById(R.id.delete_view);
        more_view=findViewById(R.id.more_view);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.share_view:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;

            case R.id.fav_view:
                Toast.makeText(this, "Fav", Toast.LENGTH_SHORT).show();

                break;

            case R.id.delete_view:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("You Don't Like Me?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(ActivityVideoView.this, "Deleted", Toast.LENGTH_SHORT).show();
                                //TODO delete video from db
                                deleteVideo(position);

                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(ActivityVideoView.this, "Canceled", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog dialog = alertDialog.create();
                WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                layoutParams.gravity = Gravity.BOTTOM;
//                dialog.getWindow().setGravity(Gravity.BOTTOM);
                layoutParams.y = 120;
                dialog.show();

                break;

            case R.id.more_view:
                Toast.makeText(this, "More", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;

        }
    }

    private void deleteVideo(int position) {
        long video_id = videosList.get(position).getId();
        videoViewModel.deleteVideo(video_id);
        finish();
    }

}

