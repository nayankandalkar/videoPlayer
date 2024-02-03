package com.example.maruti38;

import android.graphics.Bitmap;

public class videoRvModel {
String videoNmae;
String videoPath;
Bitmap thumbnail;

    public videoRvModel(String videoNmae, String videoPath, Bitmap thumbnail) {
        this.videoNmae = videoNmae;
        this.videoPath = videoPath;
        this.thumbnail = thumbnail;
    }

    public String getVideoNmae() {
        return videoNmae;
    }

    public void setVideoNmae(String videoNmae) {
        this.videoNmae = videoNmae;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
