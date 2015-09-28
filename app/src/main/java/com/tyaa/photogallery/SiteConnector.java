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

    public JSONConvert<PhotoGalleryItem> photoGallery=new JSONConvert<>(OPRST_PHOTO_ENDPOINT);
    public JSONConvert<VideoGalleryItem> videoGallery=new JSONConvert<>(OPRST_VIDEO_ENDPOINT);
    private int mCount;

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
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
    public class JSONConvert<T>{
        private String mEndPoint;
        protected JSONConvert(String mEndPoint){
            this.mEndPoint=mEndPoint;
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
        //разбивает ответ сервера на массив объектов и количество объектов на сервере
        //принимает в себя начальный индекс объекта и количество возвращаемых объектов после него
        public ArrayList<T> fetchItems(int offset,int lenght,T mCollage){
            JSONArray jsonValues=null;
            try{
                String url=Uri.parse(mEndPoint).buildUpon()
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
            return arrayFromJson(jsonValues,mCollage);
        }
        //преобразует JSON массив в массив объектов T используя метод fromJson класса T
        private ArrayList<T> arrayFromJson(JSONArray json,T mCollage){
            ArrayList<T> mCollages = new ArrayList<T>();
                try {
                    for (int index = 0; index < json.length(); ++index) {
                        mCollage = (T) mCollage.getClass().getMethod("fromJson", JSONObject.class).
                                invoke(null, json.getJSONObject(index));
                        if (null != mCollage) mCollages.add(mCollage);
                    }
                } catch (JSONException ioj) {
                    Log.e(TAG, "Failed to convert json", ioj);
                } catch (InvocationTargetException ioit) {
                    Log.e(TAG, "Failed to invoke method", ioit);
                } catch (NoSuchMethodException ionsm) {
                    Log.e(TAG, "Failed to find method", ionsm);
                } catch (IllegalAccessException ioia) {
                    Log.e(TAG, "Failed access to private method", ioia);
                }
            return mCollages;
        }
    }
}