package com.example.myvideogallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ActivityPlayer extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private long videoID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playerView=findViewById(R.id.player_view);
        videoID=getIntent().getExtras().getLong("video_id");


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT>=24){
            initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT < 24 || player == null){
            initializePlayer();
        }
    }

    public void initializePlayer(){
        player=new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        Uri uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,videoID);
        MediaSource mediaSource = BuildMediaSource(uri);
        player.setMediaSource(mediaSource);
        player.setPlayWhenReady(true);
        player.seekTo(0,0);
        player.prepare();
    }

    public void releasePlayer(){
        if (player != null){
            player.release();
            player=null;
        }
    }

    public MediaSource BuildMediaSource(Uri uri){
        DataSource.Factory dataSource = new DefaultDataSourceFactory(this, getString(R.string.app_name));
        return new ProgressiveMediaSource.Factory(dataSource).createMediaSource(uri);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24){
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24){
            releasePlayer();
        }
    }
}