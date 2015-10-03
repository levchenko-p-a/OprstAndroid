/*package com.tyaa.photogallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

//тут мы становимся гордым владельцем пустого экрана
public class PhotoGalleryFragment extends Fragment{

    private static final String TAG = "PhotoGalleryFragment";

    GridView mPhotoGrid;
    GridView mVideoGrid;

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
        mPhotoGrid = (GridView)v.findViewById(R.id.photoGrid);
        mVideoGrid = (GridView)v.findViewById(R.id.videoGrid);
        return v;
    }
    //из-за задержки ответа сервера получение данных происходит в фоновом потоке
    private class FetchItemsTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                new SiteConnector().fetchPhotoItems(0,5);
            }catch(Exception ex){

            }
            return null;
        }
    }
}
*/