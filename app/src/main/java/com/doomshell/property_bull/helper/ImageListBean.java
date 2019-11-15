package com.doomshell.property_bull.helper;

import java.util.ArrayList;

/**
 * Created by Vipin on 03-Feb-18.
 */

public class ImageListBean {
    private static ImageListBean mInstance;

    public static synchronized ImageListBean getInstance() {
        if (mInstance == null) {
            mInstance = new ImageListBean();
        }
        return mInstance;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> images;

}
