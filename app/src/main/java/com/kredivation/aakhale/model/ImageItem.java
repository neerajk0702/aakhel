package com.kredivation.aakhale.model;

import android.graphics.Bitmap;

import java.io.File;

public class ImageItem {
    private Bitmap image;
    private String title;
    private File imagFile;
    private String imageStr;
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getImagFile() {
        return imagFile;
    }

    public void setImagFile(File imagFile) {
        this.imagFile = imagFile;
    }

    public String getImageStr() {
        return imageStr;
    }

    public void setImageStr(String imageStr) {
        this.imageStr = imageStr;
    }
}