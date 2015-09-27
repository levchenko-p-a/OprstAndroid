package com.tyaa.photogallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

//тут мы становимся гордым владельцем пустого экрана
public class PhotoGalleryFragment extends Fragment{

    private static final String TAG = "PhotoGalleryFragment";

    GridView mPhotoGrid;
    ArrayList<PhotoCollage> mPhotoCollages;
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
        setupAdapter1();
        mVideoGrid = (GridView)v.findViewById(R.id.videoGrid);
        return v;
    }
    void setupAdapter1() {
        if (getActivity() == null || mPhotoGrid == null) return;

        if (mPhotoCollages != null) {
            mPhotoGrid.setAdapter(new ArrayAdapter<PhotoCollage>(getActivity(),
                    android.R.layout.simple_gallery_item, mPhotoCollages));
        } else {
            mPhotoGrid.setAdapter(null);
        }
    }

    //из-за задержки ответа сервера получение данных происходит в фоновом потоке
    private class FetchItemsTask extends AsyncTask<Void,Void,ArrayList<PhotoCollage>> {
        @Override
        protected ArrayList<PhotoCollage> doInBackground(Void... params) {
            return new SiteConnector().fetchPhotoItems(0, 5);
        }
        @Override
        protected void onPostExecute(ArrayList<PhotoCollage> photoCollages){
            mPhotoCollages=photoCollages;
            setupAdapter1();
        }
    }
}
