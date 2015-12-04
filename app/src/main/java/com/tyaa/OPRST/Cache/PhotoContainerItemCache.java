package com.tyaa.OPRST.Cache;

import android.content.Context;
import android.graphics.Point;
import android.util.Size;
import android.view.ViewGroup;

import com.tyaa.OPRST.GalleryContainer.PhotoContainer.PhotoContainerItem;
import com.tyaa.OPRST.SiteConnector;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pavlo on 11/15/2015.
 */
public class PhotoContainerItemCache extends Serializator {
    public static final String TAG = "PhotoContainerItemCache";
    private static class StringHash implements Serializable {
         public static String value;
    }
    private static final String HASH=TAG+"ids.dat";
    private Context context;
    private String filename;
    private Point size;
    private SiteConnector connector;
    public PhotoContainerItemCache(Context context,String filename,
                                   Point size){
        setContext(context);
        String extension = "";
        String file="";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
            file=filename.substring(0,i);
        }
        filename=file+String.valueOf(size.x)+"x"+
                String.valueOf(size.y)+extension;
        setFilename(filename);
        setSize(size);
        connector=new SiteConnector();
    }
    public void put(ArrayList<PhotoContainerItem> items){
        Serialize(items);
    }
    public ArrayList<PhotoContainerItem> get(){
        ArrayList<PhotoContainerItem> items=(ArrayList<PhotoContainerItem>)Desserialize();
        if(items==null){
            items=connector.fetchPhotoItems(size.x,size.y);
            Serialize(items);
        }else{
            String itemsIdHash=connector.fetchPhotoItemsId();
            StringHash.value = (String) Desserialize(HASH);
            if(itemsIdHash==null || StringHash.value==null ||
                    !itemsIdHash.equals(StringHash.value)) {
            items = connector.fetchPhotoItems(size.x, size.y);
            String mHashValue="";
            for(PhotoContainerItem item:items){
                mHashValue+=item.getId();}
            StringHash.value=md5(mHashValue);
            Serialize(items);
            Serialize(StringHash.value,HASH);}
        }
        return items;
    }
    @Override
    protected Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    protected String getFilename() {
        return filename;
    }

    public Point getSize() {
        return size;
    }

    public void setSize(Point size) {
        this.size = size;
    }
}
