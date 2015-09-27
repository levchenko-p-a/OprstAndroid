package com.tyaa.photogallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Юлия on 19.09.2015.
 */
public class SiteConnector {

    public static final String TAG = "SiteConnector";
    public static final String PREF_SEARCH_QUERY = "searchQuery";
    public static final String PREF_LAST_RESULT_ID = "lastResultId";
    private static final String ENDPOINT = "https://api.flickr.com/services/rest/";
    private static final String OPRST_PHOTO_ENDPOINT = "localhost/rest/getItemsPhoto.php";
    private static final String API_KEY = "4f721bbafa75bf6d2cb5af54f937bb70";
    private static final String METHOD_GET_RECENT = "flickr.photos.getRecent";
    private static final String PARAM_EXTRAS = "extras";
    private static final String EXTRA_SMALL_URL = "url_s";
    private static final String METHOD_SEARCH = "flickr.photos.search";
    private static final String PARAM_TEXT = "text";
    private static final String XML_PHOTO = "photo";

    //превращает ответ сервера в JSON объект
    JSONArray getJSON(String urlSpec) throws IOException, JSONException {
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
    //превращает ответ сервера в строку
    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }
    public ArrayList<PhotoCollage> fetchPhotoItems(int offset,int lenght){
        JSONArray json=null;
        try{
            String url=Uri.parse(OPRST_PHOTO_ENDPOINT).buildUpon()
                    .appendQueryParameter("offset", Integer.toString(offset))
                    .appendQueryParameter("lenght", Integer.toString(lenght))
                    .build().toString();
            json=getJSON(url);
        }catch(IOException ioe){
            Log.e(TAG, "Failed to fetch items",ioe);
        }catch(JSONException ioj){
            Log.e(TAG, "Failed to convert json",ioj);
        }
        return arrayFromJson(json);
    }
    public ArrayList<PhotoCollage> arrayFromJson(JSONArray json){
        ArrayList<PhotoCollage> photoCollages = new ArrayList<PhotoCollage>();
        try{
        for (int index = 0; index < json.length(); ++index) {
            PhotoCollage collage = PhotoCollage.fromJson(json.getJSONObject(index));
            if (null != collage) photoCollages.add(collage);
        }}catch(JSONException ioj){
            Log.e(TAG, "Failed to convert json",ioj);
        }
        return photoCollages;
    }
}