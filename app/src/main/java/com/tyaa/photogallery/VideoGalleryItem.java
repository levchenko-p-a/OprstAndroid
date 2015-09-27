package com.tyaa.photogallery;

import org.json.JSONException;
import org.json.JSONObject;

public class VideoGalleryItem extends GalleryItem {
    protected String youtube;

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }
    public VideoGalleryItem(){
    }
    public VideoGalleryItem(int id, String pagetitle, String alias, String caption, String preview,boolean our, String youtube) {
        setId(id);
        setPagetitle(pagetitle);
        setAlias(alias);
        setCaption(caption);
        setPreview(preview);
        setOur(our);
        setYoutube(youtube);
    }
    public static VideoGalleryItem fromJson(JSONObject object) {
        try {
            int id = object.getInt("id");
            String pagetitle= object.getString("pagetitle");
            String alias= object.getString("alias");
            String caption= object.getString("caption");
            String preview= object.getString("preview");
            String our = object.getString("our");
            String youtube= object.getString("youtube");
        return new VideoGalleryItem(id,pagetitle,alias,caption,preview,Boolean.getBoolean(our),youtube);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
