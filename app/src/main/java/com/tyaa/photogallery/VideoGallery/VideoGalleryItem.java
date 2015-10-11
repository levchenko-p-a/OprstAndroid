package com.tyaa.photogallery.VideoGallery;

import java.util.ArrayList;

/**
 * Created by Павел on 10/11/2015.
 */
public class VideoGalleryItem<String> extends ArrayList<String> {
    private String youtube;

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }
}
