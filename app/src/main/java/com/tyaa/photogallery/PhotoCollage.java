package com.tyaa.photogallery;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Павел on 27.09.2015.
 */
public class PhotoCollage extends Collage{
    public PhotoCollage(){
    }
    public PhotoCollage(int id, String pagetitle, String alias, String caption,boolean our) {
        this.id = id;
        this.pagetitle = pagetitle;
        this.alias = alias;
        this.caption = caption;
        this.our=our;
    }
    public static PhotoCollage fromJson(JSONObject object) {
        try {
        int id = object.getInt("id");
        String pagetitle= object.getString("pagetitle");
        String alias= object.getString("alias");
        String caption= object.getString("caption");
        String our = object.getString("our");
        return new PhotoCollage(id,pagetitle,alias,caption,Boolean.getBoolean(our));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
