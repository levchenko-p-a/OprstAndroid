package com.tyaa.photogallery;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;

public class GalleryItem {
    public static final String TAG = "GalleryItem";
    protected static final String OPRST_ENDPOINT = "http://oprst.com.ua/";
    protected static final String OPRST_THUMBS="assets/.thumbs/images/photo-preview/";

    private int id;
    private String pagetitle;
    private String alias;
    private String caption;
    private String preview;
    private boolean our;
    protected String youtube;

    public GalleryItem(int id, String pagetitle, String alias, String caption, String preview,boolean our, String youtube) {
        setId(id);
        setPagetitle(pagetitle);
        setAlias(alias);
        setCaption(caption);
        setPreview(preview);
        setOur(our);
        setYoutube(youtube);
    }
    public static GalleryItem fromJson(JSONObject object) {
        int id=0;
        String pagetitle=null,alias=null,caption=null,preview=null,our=null,
                youtube=null;
        try {
            id = object.getInt("id");
            pagetitle= object.getString("pagetitle");
            alias= object.getString("alias");
            caption= object.getString("caption");
            preview= object.getString("preview");
            our = object.getString("our");
            youtube= object.getString("youtube");
            return new GalleryItem(id,pagetitle,alias,caption,preview,Boolean.getBoolean(our),youtube);
        } catch (JSONException ioj) {
            Log.i(TAG, "Get VideoGallery", ioj);
            return new GalleryItem(id,pagetitle,alias,caption,preview,Boolean.getBoolean(our),youtube);
        }
    }
    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getThumb() {
        return OPRST_ENDPOINT+OPRST_THUMBS+
                getPreview().substring(getPreview().lastIndexOf('/') + 1, getPreview().length() );
    }
    public boolean isOur() {
        return our;
    }

    public void setOur(boolean our) {
        this.our = our;
    }

    public String getPreview() {
        return OPRST_ENDPOINT+preview;
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
