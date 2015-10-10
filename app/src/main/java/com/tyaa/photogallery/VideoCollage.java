package com.tyaa.photogallery;

import org.json.JSONObject;

public class VideoCollage extends Collage{
    protected String youtube;

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }
    public VideoCollage(){
    }
    public VideoCollage(int id, String pagetitle, String alias, String caption,String youtube) {
        this.id = id;
        this.pagetitle = pagetitle;
        this.alias = alias;
        this.caption = caption;
        this.youtube=youtube;
    }
    public static VideoCollage fromJson(JSONObject object) {
        int id = object.optInt("id", 0);
        String pagetitle= object.optString("pagetitle", "");
        String alias= object.optString("alias", "");
        String caption= object.optString("caption", "");
        String youtube= object.optString("youtube", "");
        return new VideoCollage(id,pagetitle,alias,caption,youtube);
    }
}