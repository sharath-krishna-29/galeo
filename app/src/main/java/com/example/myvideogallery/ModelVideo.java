package com.example.myvideogallery;

import android.net.Uri;

public class ModelVideo {
    private long id;
//    private String title;
    private String duration;
    private Uri data;

    public ModelVideo(long id,Uri data,String duration) {
        this.id = id;
//        this.title = title;
        this.duration = duration;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Uri getData() {
        return data;
    }

    public void setData(Uri data) {
        this.data = data;
    }






}
