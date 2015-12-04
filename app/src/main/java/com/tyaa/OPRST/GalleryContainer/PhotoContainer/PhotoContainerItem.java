package com.tyaa.OPRST.GalleryContainer.PhotoContainer;

import android.util.Log;

import com.tyaa.OPRST.GridAdapter.AdapterItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kitsune on 10.10.2015.
 */
public class PhotoContainerItem extends AdapterItem {
    public static final String TAG = "PhotoContainerItem";
    public static final String CAPTION = "photo preview";

    public PhotoContainerItem () {}

    @Override
    public String getCaption() {
        return CAPTION;
    }

    private boolean our;
    public boolean isOur() {
        return our;
    }

    public void setOur(boolean our) {
        this.our = our;
    }

    public PhotoContainerItem(String id, String pagetitle, String preview,String thumb,
                              boolean our) {
        setId(id);
        setPagetitle(pagetitle);
        setPreview(preview);
        setThumb(thumb);
        setOur(our);
    }
    public PhotoContainerItem(PhotoContainerItem item){
        setId(item.id);
        setPagetitle(item.pagetitle);
        setPreview(item.preview);
        setThumb(item.thumb);
        setOur(item.our);
    }
    public static PhotoContainerItem fromJson(JSONObject object,String endPoint) {
        String id=null, pagetitle=null,preview=null,thumb=null,our=null;
        try {
            id = object.getString("id");
            pagetitle= object.getString("pagetitle");
            thumb= endPoint+object.getString("thumb");
            preview= endPoint+object.getString("preview");
            our = object.getString("our");
            return new PhotoContainerItem(id,pagetitle,thumb,preview,Boolean.getBoolean(our));
        } catch (JSONException ioj) {
            Log.i(TAG, "Get PhotoGallery", ioj);
        }
        return null;
    }

    //преобразует JSON массив в массив объектов PhotoGalleryItem используя метод fromJson
    public static ArrayList<PhotoContainerItem> photoArray(JSONArray json, String endPoint){
        ArrayList<PhotoContainerItem> mCollages = new ArrayList<PhotoContainerItem>();
        try {
            for (int index = 0; index < json.length(); ++index) {
                PhotoContainerItem item = PhotoContainerItem.fromJson(json.
                        getJSONObject(index), endPoint);
                if (null != item) {
                    mCollages.add(item);
                }
            }
        } catch (JSONException ioj) {
            Log.e(TAG, "Failed to convert json", ioj);
        }
        return mCollages;
    }
}