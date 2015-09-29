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
    public static final String PREF_SEARCH_QUERY = "searchQuery";
    private static final String OPRST_PHOTO_ENDPOINT = "http://oprst.com.ua/rest/getItemsPhoto.php";
    private static final String OPRST_VIDEO_ENDPOINT = "http://oprst.com.ua/rest/getItemsPhoto.php";

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
    JSONObject getJSONObject(String urlSpec) throws IOException, JSONException {
        String result=new String(getUrlBytes(urlSpec));
        return new JSONObject(result);
    }
    //превращает ответ сервера в JSON vfccbd
    JSONArray getJSONArray(String urlSpec) throws IOException, JSONException {
        String result=new String(getUrlBytes(urlSpec));
        return new JSONArray(result);
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
        public ArrayList<PhotoGalleryItem> fetchPhotoItems(int offset,int lenght){
            JSONArray jsonValues=null;
            try{
                String url=Uri.parse(OPRST_PHOTO_ENDPOINT).buildUpon()
                        .appendQueryParameter("offset", Integer.toString(offset))
                        .appendQueryParameter("lenght", Integer.toString(lenght))
                        .build().toString();
                JSONObject jsonObject=getJSONObject(url);
                JSONArray jsonNames=jsonObject.names();
                jsonValues=jsonObject.getJSONArray("photoItems");
                setCount(jsonObject.getInt("count"));
            }catch(IOException ioe){
                Log.e(TAG, "Failed to fetch items",ioe);
            }catch(JSONException ioj){
                Log.e(TAG, "Failed to convert json",ioj);
            }
            return photoFromJson(jsonValues);
        }
        //преобразует JSON массив в массив объектов PhotoGalleryItem используя метод fromJson
        private ArrayList<PhotoGalleryItem> photoFromJson(JSONArray json){
            ArrayList<PhotoGalleryItem> mCollages = new ArrayList<PhotoGalleryItem>();
                try {
                    for (int index = 0; index < json.length(); ++index) {
                        PhotoGalleryItem item = PhotoGalleryItem.fromJson(json.
                                getJSONObject(index));
                        if (null != item) mCollages.add(item);
                    }
                } catch (JSONException ioj) {
                    Log.e(TAG, "Failed to convert json", ioj);
                }
            return mCollages;
        }
    public ArrayList<VideoGalleryItem> fetchVideoItems(int offset,int lenght){
        JSONArray jsonValues=null;
        try{
            String url=Uri.parse(OPRST_VIDEO_ENDPOINT).buildUpon()
                    .appendQueryParameter("offset", Integer.toString(offset))
                    .appendQueryParameter("lenght", Integer.toString(lenght))
                    .build().toString();
            JSONObject jsonObject=getJSONObject(url);
            JSONArray jsonNames=jsonObject.names();
            jsonValues=jsonObject.getJSONArray("photoItems");
            setCount(jsonObject.getInt("count"));
        }catch(IOException ioe){
            Log.e(TAG, "Failed to fetch items",ioe);
        }catch(JSONException ioj){
            Log.e(TAG, "Failed to convert json",ioj);
        }
        return videoFromJson(jsonValues);
    }
    //преобразует JSON массив в массив объектов PhotoGalleryItem используя метод fromJson
    private ArrayList<VideoGalleryItem> videoFromJson(JSONArray json){
        ArrayList<VideoGalleryItem> mCollages = new ArrayList<VideoGalleryItem>();
        try {
            for (int index = 0; index < json.length(); ++index) {
                VideoGalleryItem item = VideoGalleryItem.fromJson(json.
                        getJSONObject(index));
                if (null != item) mCollages.add(item);
            }
        } catch (JSONException ioj) {
            Log.e(TAG, "Failed to convert json", ioj);
        }
        return mCollages;
    }
}