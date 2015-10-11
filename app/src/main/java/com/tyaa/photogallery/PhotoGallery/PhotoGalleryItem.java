package com.tyaa.photogallery.PhotoGallery;

import java.util.ArrayList;

/**
 * Created by Павел on 10/10/2015.
 */
public class PhotoGalleryItem<String> extends ArrayList<String>{
    private String id;
    private String originals;
    private String thumbs;
    private String endPoint;

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginals() {
        return originals;
    }

    public void setOriginals(String originals) {
        this.originals = originals;
    }

    public String getThumbs() {
        return thumbs;
    }

    public void setThumbs(String thumbs) {
        this.thumbs = thumbs;
    }
}
