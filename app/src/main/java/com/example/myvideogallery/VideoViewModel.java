package com.example.myvideogallery;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Locale;

public class VideoViewModel extends AndroidViewModel {
    private static final String TAG = "VideoViewModel";
    MutableLiveData<ArrayList<ModelVideo>> videosList;
    private ArrayList<ModelVideo> myVideoList = new ArrayList<>();
    private HandlerThread handlerThread;
    private Handler handler;


    public VideoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<ModelVideo>> getVideosList() {
        if (videosList == null) {
            handlerThread=new HandlerThread("VideosHandlerThread",Process.THREAD_PRIORITY_BACKGROUND);
            handlerThread.start();
            handler=new Handler(handlerThread.getLooper());
            handler.post(myRunnable);
            videosList = new MutableLiveData<ArrayList<ModelVideo>>();
            Log.e(TAG,"myvideolist size "+myVideoList.size());
            videosList.setValue(myVideoList);
            Log.e(TAG,"video list viewmodel mutable "+videosList.getValue().size());
        }
        return videosList;
    }

    private final Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            myVideoList=loadVideoList();
        }
    };

    private ArrayList<ModelVideo> loadVideoList() {

        Log.e(TAG, "load videos");
        String[] projection = {MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DURATION};
        String sortOrder = MediaStore.Video.Media.DATE_ADDED + " DESC";
        Cursor cursor = getApplication().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder);
        try {
            if (cursor != null) {
                Log.e(TAG, " inside cursor");
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
//                    int titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);

                while (cursor.moveToNext()) {
                    long id = cursor.getLong(idColumn);
//                        String title = cursor.getString(titleColumn);
                    int duration = cursor.getInt(durationColumn);

                    Uri data = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                    String duration_formatted;
                    int sec = (duration / 1000) % 60;
                    int min = (duration / (1000 * 60)) % 60;
                    int hrs = duration / (1000 * 60 * 60);

                    if (hrs == 0) {
                        duration_formatted = String.valueOf(min).concat(":".concat(String.format(Locale.UK, "%02d", sec)));
                    } else {
                        duration_formatted = String.valueOf(hrs).concat(":".concat(String.format(Locale.UK, "%02d", min).concat(":".concat(String.format(Locale.UK, "%02d", sec)))));
                    }

                    Log.e(TAG, "adding "+duration_formatted);
                    myVideoList.add(new ModelVideo(id, data, duration_formatted));

                }

            }
        } catch (Exception e) {
            Log.e(TAG, "Exception is " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        Log.e(TAG,"final list size "+myVideoList.size());

        return myVideoList;
    }

    public void deleteVideo(long video_id){
        Uri data = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, video_id);
        getApplication().getContentResolver().delete(data,null,null);

//        String[] projection = {MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DURATION};
//        String sortOrder = MediaStore.Video.Media.DATE_ADDED + " DESC";
//
//        Cursor cursor = getApplication().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder);
//
//
//        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
//        while (cursor.moveToNext()) {
//            if (cursor.getLong(idColumn)==video_id) {
//                Uri videoUri = ContentUris.withAppendedId(
//                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, video_id);
//                getApplication().getContentResolver().delete(videoUri, null, null);
//            }
//        }

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        handler.removeCallbacks(myRunnable);
    }
}
