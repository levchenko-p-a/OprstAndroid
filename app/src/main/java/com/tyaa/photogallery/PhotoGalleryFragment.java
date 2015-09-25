package com.tyaa.photogallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.IOException;

//тут мы становимся гордым владельцем пустого экрана
public class PhotoGalleryFragment extends Fragment{

    private static final String TAG = "PhotoGalleryFragment";

    GridView mGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        new FetchItemsTask().execute();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container,
                false);
        mGridView = (GridView)v.findViewById(R.id.gridView);
        return v;
    }
//из-за задержки ответа сервера получение данных происходит в фоновом потоке
    private class FetchItemsTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                new FlickrFetchr().getUrl("http://localhost/rest/getItemsPhoto.php");
            }catch(Exception ex){
                
            }
            return null;
        }
    }
}
