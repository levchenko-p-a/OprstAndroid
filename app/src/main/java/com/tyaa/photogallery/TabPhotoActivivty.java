package com.tyaa.photogallery;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by Kitsune on 07.10.2015.
 */
public class TabPhotoActivivty extends Activity {

    GridView mPhotoGrid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_gallery_view);

        //setRetainInstance(true);

        new FetchItemsTask().execute();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container,
                false);
        mPhotoGrid = (GridView)v.findViewById(R.id.photoGrid);
        //mVideoGrid = (GridView)v.findViewById(R.id.videoGrid);
        return v;
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                new SiteConnector().fetchItems(0,5,false);
            }catch(Exception ex){

            }
            return null;
        }
    }
}