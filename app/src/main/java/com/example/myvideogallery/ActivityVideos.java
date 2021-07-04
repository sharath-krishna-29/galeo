package com.example.myvideogallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityVideos extends AppCompatActivity{

    private static final String TAG = "ActivityVideos";
    private ArrayList<ModelVideo> videosList;
    private AdapterVideosList adapterVideosList;
    private VideoViewModel videoViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"on create");
        setContentView(R.layout.activity_videos);
        videoViewModel= new ViewModelProvider(this).get(VideoViewModel.class);

        initViews();
        checkPermisiion();

        videoViewModel.getVideosList().observe(this, videoListObserver);

    }

    Observer<ArrayList<ModelVideo>> videoListObserver = new Observer<ArrayList<ModelVideo>>() {
        @Override
        public void onChanged(ArrayList<ModelVideo> modelVideos) {
            videosList=modelVideos;
            adapterVideosList.updateNewVideoList(videosList);
        }
    };




    public void getVideosList(){
        videosList = videoViewModel.getVideosList().getValue();
        if (videosList != null) {
            Log.e(TAG, "getvideolist " + videosList.size());
            adapterVideosList = new AdapterVideosList(this, videosList);
            recyclerView.setAdapter(adapterVideosList);
        }

    }

    private void checkPermisiion() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
            }else {

                getVideosList();

            }
        }else {

            getVideosList();


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==123){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                loadVideos();
//                handler.post(myRunnable);
                getVideosList();


            }else {
                Toast.makeText(this, "Permission was not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initViews() {
         recyclerView=findViewById(R.id.recyclerview_videos);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));// 2= column count
        recyclerView.setHasFixedSize(true);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"ondestroy");
        videoViewModel=null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    Log.e(TAG,"resume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onstart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"on pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"on stop");
    }
}