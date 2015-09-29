package com.tyaa.photogallery;

import android.content.Intent;
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
import android.widget.TextView;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

//тут мы становимся гордым владельцем пустого экрана
public class GalleryFragment extends Fragment{
    private static final String TAG = "GalleryFragment";
    public static final String EXTRA_RESULT = "com.tyaa.photogallery.result";

    GridView mPhotoGrid;
    GridView mVideoGrid;
    ArrayList<GalleryItem> mPhotoGalleryItems;
    ArrayList<GalleryItem> mVideoGalleryItems;
    ThumbDownloader<ImageView> mThumbThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchPhotoItemsTask().execute(0, 5);
        new FetchVideoItemsTask().execute(0, 5);
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
    private class GalleryItemAdapter<GalleryItem> extends ArrayAdapter<GalleryItem> {
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
            TextView textView=(TextView)convertView
                    .findViewById(R.id.gallery_item_title);
            final com.tyaa.photogallery.GalleryItem item =
                    (com.tyaa.photogallery.GalleryItem)getItem(position);
            mThumbThread.queueThumbnail(imageView, item);
            //тут можно вызывать просмотрщик фото или ивдео
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View arg0) {
                    if (item.getYoutube() == null) {
                        Intent i = new Intent(getActivity(), PhotoActivity.class);
                        i.putExtra(EXTRA_RESULT, item.getId());
                        startActivity(i);
                    } else {

                    }
                    return true;
                }
            });
            return convertView;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container,
                false);
        mPhotoGrid = (GridView)v.findViewById(R.id.photoGrid);
        setupPhotoAdapter();
        mVideoGrid = (GridView)v.findViewById(R.id.videoGrid);
        setupVideoAdapter();
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
    void setupPhotoAdapter() {
        if (getActivity() == null || mPhotoGrid == null) return;
        if (mPhotoGalleryItems != null) {
            mPhotoGrid.setAdapter(new GalleryItemAdapter(mPhotoGalleryItems));
        } else {
            mPhotoGrid.setAdapter(null);
        }
    }
    //получение фото галереи в фоновом потоке
    private class FetchPhotoItemsTask extends AsyncTask<Integer,Void,ArrayList<GalleryItem>> {
        @Override
        protected ArrayList<GalleryItem> doInBackground(Integer... params) {
            return new SiteConnector().fetchItems(params[0], params[1],false);
        }
        @Override
        protected void onPostExecute(ArrayList<GalleryItem> photoCollages){
            mPhotoGalleryItems =photoCollages;
            setupPhotoAdapter();
        }
    }
    void setupVideoAdapter() {
        if (getActivity() == null || mVideoGrid == null) return;
        if (mVideoGalleryItems != null) {
            mVideoGrid.setAdapter(new GalleryItemAdapter(mVideoGalleryItems));
        } else {
            mVideoGrid.setAdapter(null);
        }
    }
    //получение видео галереи в фоновом потоке
    private class FetchVideoItemsTask extends AsyncTask<Integer,Void,ArrayList<GalleryItem>> {
        @Override
        protected ArrayList<GalleryItem> doInBackground(Integer... params) {
            return new SiteConnector().fetchItems(params[0], params[1],true);
        }
        @Override
        protected void onPostExecute(ArrayList<GalleryItem> photoCollages){
            mVideoGalleryItems =photoCollages;
            setupVideoAdapter();
        }
    }
}
