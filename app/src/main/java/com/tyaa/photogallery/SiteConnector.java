package com.tyaa.photogallery;

        import android.net.Uri;
        import android.util.Log;

        import com.tyaa.photogallery.GalleryContainer.GalleryItem;
        import com.tyaa.photogallery.PhotoGallery.PhotoGalleryItem;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;

/**
 * Created by Юлия on 19.09.2015.
 */
public class SiteConnector {
    public static final String TAG = "SiteConnector";
    //protected static final String OPRST_ENDPOINT = "http://oprst.com.ua/";
    protected static final String OPRST_ENDPOINT = "http://weed.esy.es/";
    private static final String OPRST_PHOTO_ENDPOINT = OPRST_ENDPOINT+"rest/getItemsPhoto.php";
    private static final String OPRST_VIDEO_ENDPOINT = OPRST_ENDPOINT+"rest/getItemsVideo.php";
    private static final String OPRST_PHOTO_GALLERY_ENDPOINT =
            OPRST_ENDPOINT+"rest/getItemsPhotoGallery.php";

    private int mCount;
    private String mThumb;
    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }
    //превращает ответ сервера в строку
    private String getUrlR(String urlSpec) {
       return new String(getUrlBytes(urlSpec));
    }
    //превращает ответ сервера в JSON объект
    private JSONObject getJSONObject(String urlSpec){
        try {
            String result = getUrlR(urlSpec);
            JSONObject json=new JSONObject(result);
            return json;
        } catch (JSONException ioj) {
            Log.e(TAG, "Failed to convert json", ioj);
        }
        return null;
    }
    //превращает ответ сервера в JSON массив
    private JSONArray getJSONArray(String urlSpec){
        String result= null;
        try {
            result = getUrlR(urlSpec);
            JSONArray json=new JSONArray(result);
            return json;
        } catch (JSONException ioj) {
            Log.e(TAG, "Failed to convert json", ioj);
        }
        return null;
    }
    //превращает ответ сервера в массив байт
    byte[] getUrlBytes(String urlSpec){
        HttpURLConnection connection =null;
        try {
        URL url = new URL(urlSpec);
        connection = (HttpURLConnection)url.openConnection();
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
        }catch (IOException io) {
            Log.e(TAG, "Failed to connect", io);
        } finally {
            if(connection!=null){
            connection.disconnect();
            }
        }
        return null;
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
        return fromJson(jsonValues,video);
    }
    //преобразует JSON массив в массив объектов GalleryItem используя метод fromJson
    private ArrayList<GalleryItem> fromJson(JSONArray json, boolean video){
        ArrayList<GalleryItem> mCollages = new ArrayList<GalleryItem>();
        try {
            for (int index = 0; index < json.length(); ++index) {
                GalleryItem item = GalleryItem.fromJson(json.
                        getJSONObject(index),OPRST_ENDPOINT);
                if (null != item) {
                    mCollages.add(item);
                }
            }
        } catch (JSONException ioj) {
            Log.e(TAG, "Failed to convert json", ioj);
        }
        return mCollages;
    }
    //преобразует JSON массив в массив объектов GalleryItem используя метод fromJson
    public PhotoGalleryItem<String> jsonToStrings(String id){
        String url=Uri.parse(OPRST_PHOTO_GALLERY_ENDPOINT).buildUpon()
                .appendQueryParameter("id", id)
                .build().toString();
        JSONArray json=getJSONArray(url);
        PhotoGalleryItem<String> photos = new PhotoGalleryItem<String>();
        String endPoint=OPRST_ENDPOINT+"assets/galleries/"+id+"/";
        photos.setEndPoint(endPoint);
        try {
            for (int index = 0; index < json.length(); ++index) {
                String string = json.getString(index);
                if (null != string) {
                    switch (string) {
                        case ".":
                            break;
                        case "..":
                            break;
                        case "original":
                            photos.setOriginals(endPoint+string+"/");
                            break;
                        case "thumbs":
                            photos.setThumbs(endPoint+string+"/");
                            break;
                        default:
                            photos.add(string);
                            break;
                    }
                }
            }
        } catch (JSONException ioj) {
            Log.e(TAG, "Failed to convert json", ioj);
        }
        return photos;
    }
}