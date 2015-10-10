package com.tyaa.photogallery.GalleryContainer;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kitsune on 10.10.2015.
 */
public class GalleryItem {
    public static final String TAG = "GalleryItem";

    private int id;
    private String pagetitle;
    private String alias;
    private String caption;
    private String preview;
    private boolean our;
    private String youtube;
    private String thumb;

    public GalleryItem(int id, String pagetitle, String alias, String caption, String preview,
                       String thumb,boolean our, String youtube) {
        setId(id);
        setPagetitle(pagetitle);
        setAlias(alias);
        setCaption(caption);
        setPreview(preview);
        setThumb(thumb);
        setOur(our);
        setYoutube(youtube);
    }
    public static GalleryItem fromJson(JSONObject object,String endPoint) {
        int id=0;
        String pagetitle=null,alias=null,caption=null,preview=null,thumb=null,our=null,
                youtube=null;
        try {
            id = object.getInt("id");
            pagetitle= object.getString("pagetitle");
            alias= object.getString("alias");
            caption= object.getString("caption");
            preview= endPoint+object.getString("preview");
            thumb= endPoint+object.getString("thumb");
            our = object.getString("our");
            youtube= object.getString("youtube");
            return new GalleryItem(id,pagetitle,alias,caption,preview,thumb,Boolean.getBoolean(our),youtube);
        } catch (JSONException ioj) {
            Log.i(TAG, "Get VideoGallery", ioj);
            return new GalleryItem(id,pagetitle,alias,caption,preview,thumb,Boolean.getBoolean(our),youtube);
        }
    }
    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getThumb() {
        return thumb;
    }
    public boolean isOur() {
        return our;
    }

    public void setOur(boolean our) {
        this.our = our;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPagetitle() {
        return pagetitle;
    }

    public void setPagetitle(String pagetitle) {
        this.pagetitle = pagetitle;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}