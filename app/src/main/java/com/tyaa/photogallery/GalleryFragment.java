package com.tyaa.photogallery;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

//тут мы становимся гордым владельцем пустого экрана
public class GalleryFragment extends Fragment{
    private static final String TAG = "GalleryFragment";

    GridView mPhotoGrid;
    GridView mVideoGrid;
    ArrayList<GalleryItem> mGalleryItems;
    ThumbDownloader<ImageView> mThumbThread;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemsTask().execute(0, 5);
        mThumbThread = new ThumbDownloader(new Handler());
        mThumbThread.setListener(new ThumbDownloader.Listener<ImageView>() {
            public void onThumbnailDownloaded(ImageView imageView, Bitmap thumbnail) {
                if (isVisible()) {
                    imageView.setImageBitmap(thumbnail);
                }
            }
        });

        mThumbThread.start();
        mThumbThread.getLooper();
        Log.i(TAG, "Background thread started");

    }
    private class GalleryItemAdapter extends ArrayAdapter<GalleryItem> {
        public GalleryItemAdapter(ArrayList<GalleryItem> items) {
            super(getActivity(), 0, items);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.gallery_item, parent, false);
            }

            ImageView imageView = (ImageView)convertView
                    .findViewById(R.id.gallery_item_imageView);
            imageView.setImageResource(R.drawable.photo_default);
            GalleryItem item = getItem(position);
            mThumbThread.queueThumbnail(imageView, item.getPreview());
            return convertView;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container,
                false);
        mPhotoGrid = (GridView)v.findViewById(R.id.photoGrid);
        setupAdapter();
        mVideoGrid = (GridView)v.findViewById(R.id.videoGrid);
        return v;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbThread.quit();
        Log.i(TAG, "Background thread destroyed");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbThread.clearQueue();
    }
    void setupAdapter() {
        if (getActivity() == null || mPhotoGrid == null) return;

        if (mGalleryItems != null) {
            mPhotoGrid.setAdapter(new GalleryItemAdapter(mGalleryItems));
        } else {
            mPhotoGrid.setAdapter(null);
        }
    }

    //из-за задержки ответа сервера получение данных происходит в фоновом потоке
    private class FetchItemsTask extends AsyncTask<Integer,Void,ArrayList<GalleryItem>> {
        @Override
        protected ArrayList<GalleryItem> doInBackground(Integer... params) {
            return new SiteConnector().fetchPhotoItems(params[0], params[1]);
        }
        @Override
        protected void onPostExecute(ArrayList<GalleryItem> photoCollages){
            mGalleryItems =photoCollages;
            setupAdapter();
        }
    }
}
