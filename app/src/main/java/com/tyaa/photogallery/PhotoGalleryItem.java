package com.tyaa.photogallery;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Павел on 27.09.2015.
 */
public class PhotoGalleryItem extends GalleryItem {
    protected static final String OPRST_THUMBS="assets/.thumbs/images/photo-preview/";
    private String thumb;

    public String getThumb() {
        return OPRST_ENDPOINT+OPRST_THUMBS+
                getPreview().substring(getPreview().lastIndexOf('/') + 1, getPreview().length() );
    }
    public PhotoGalleryItem(){}
    private PhotoGalleryItem(int id, String pagetitle, String alias, String caption, String preview,boolean our) {
        setId(id);
        setPagetitle(pagetitle);
        setAlias(alias);
        setCaption(caption);
        setPreview(preview);
        setOur(our);
    }
    public static PhotoGalleryItem fromJson(JSONObject object) {
        try {
        int id = object.getInt("id");
        String pagetitle= object.getString("pagetitle");
        String alias= object.getString("alias");
        String caption= object.getString("caption");
        String preview= object.getString("preview");
        String our = object.getString("our");
        return new PhotoGalleryItem(id,pagetitle,alias,caption,preview,Boolean.getBoolean(our));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
