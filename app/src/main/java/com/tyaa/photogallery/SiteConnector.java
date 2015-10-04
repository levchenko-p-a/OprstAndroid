package com.tyaa.photogallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Юлия on 19.09.2015.
 */
public class SiteConnector {
    public static final String TAG = "SiteConnector";
    private static final String OPRST_PHOTO_ENDPOINT = "http://oprst.com.ua/rest/getItemsPhoto.php";
    private static final String OPRST_VIDEO_ENDPOINT = "http://oprst.com.ua/rest/getItemsVideo.php";
    private static final String OPRST_PHOTO_GALLERY_ENDPOINT =
            "http://oprst.com.ua/rest/getItemsPhotoGallery.php";
    private int mCount;

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }
    //превращает ответ сервера в строку
    private String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }
    //превращает ответ сервера в JSON объект
    private JSONObject getJSONObject(String urlSpec){
        try {
            String result = new String(getUrlBytes(urlSpec));
            return new JSONObject(result);
        } catch (IOException io) {
            Log.e(TAG, "Failed to connect", io);
        } catch (JSONException ioj) {
            Log.e(TAG, "Failed to convert json", ioj);
        }
        return null;
    }
    //превращает ответ сервера в JSON массив
    private JSONArray getJSONArray(String urlSpec){
        String result= null;
        try {
            result = new String(getUrlBytes(urlSpec));
            return new JSONArray(result);
        } catch (IOException io) {
            Log.e(TAG, "Failed to connect", io);
        } catch (JSONException ioj) {
            Log.e(TAG, "Failed to convert json", ioj);
        }
        return null;
    }
    //превращает ответ сервера в массив байт
    byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
        //разбивает ответ сервера на массив объектов и количество объектов на сервере
        //принимает в себя начальный индекс объекта и количество возвращаемых объектов после него
        public ArrayList<GalleryItem> fetchItems(int offset,int lenght,boolean video){
            JSONArray jsonValues=null;
            String mEndPoint=video?OPRST_VIDEO_ENDPOINT:OPRST_PHOTO_ENDPOINT;
            String key=video?"videoItems":"photoItems";
            try{
                String url=Uri.parse(mEndPoint).buildUpon()
                        .appendQueryParameter("offset", Integer.toString(offset))
                        .appendQueryParameter("lenght", Integer.toString(lenght))
                        .build().toString();
                JSONObject jsonObject=getJSONObject(url);
                jsonValues=jsonObject.getJSONArray(key);
                setCount(jsonObject.getInt("count"));
            }catch(JSONException ioj){
                Log.e(TAG, "Failed to convert json",ioj);
            }
            return fromJson(jsonValues);
        }
        //преобразует JSON массив в массив объектов GalleryItem используя метод fromJson
        private ArrayList<GalleryItem> fromJson(JSONArray json){
            ArrayList<GalleryItem> mCollages = new ArrayList<GalleryItem>();
                try {
                    for (int index = 0; index < json.length(); ++index) {
                        GalleryItem item = GalleryItem.fromJson(json.
                                getJSONObject(index));
                        if (null != item) mCollages.add(item);
                    }
                } catch (JSONException ioj) {
                    Log.e(TAG, "Failed to convert json", ioj);
                }
            return mCollages;
        }
    //преобразует JSON массив в массив объектов GalleryItem используя метод fromJson
    public ArrayList<String> jsonToStrings(String id){
        String url=Uri.parse(OPRST_PHOTO_GALLERY_ENDPOINT).buildUpon()
                .appendQueryParameter("id", id)
                .build().toString();
        JSONArray json=getJSONArray(url);
        ArrayList<String> strings = new ArrayList<String>();
        try {
            for (int index = 0; index < json.length(); ++index) {
                String string = json.getString(index);
                if (null != string) strings.add(string);
            }
        } catch (JSONException ioj) {
            Log.e(TAG, "Failed to convert json", ioj);
        }
        return strings;
    }
}