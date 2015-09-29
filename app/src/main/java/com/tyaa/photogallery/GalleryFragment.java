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

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

//тут мы становимся гордым владельцем пустого экрана
public class GalleryFragment extends Fragment{
    private static final String TAG = "GalleryFragment";

    GridView mPhotoGrid;
    GridView mVideoGrid;
    ArrayList<PhotoGalleryItem> mPhotoGalleryItems;
    ArrayList<VideoGalleryItem> mVideoGalleryItems;
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
    private class GalleryItemAdapter<T> extends ArrayAdapter<T> {
        public GalleryItemAdapter(ArrayList<T> items) {
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
            T item = getItem(position);
            try {
                String url=item.getClass().getMethod("getPreview").invoke(item).toString();
                mThumbThread.queueThumbnail(imageView,url);
            } catch (InvocationTargetException ioit) {
                Log.e(TAG, "Failed to invoke method", ioit);
            } catch (NoSuchMethodException ionsm) {
                Log.e(TAG, "Failed to find method", ionsm);
            } catch (IllegalAccessException ioia) {
                Log.e(TAG, "Failed access to private method", ioia);
            }
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
    private class FetchPhotoItemsTask extends AsyncTask<Integer,Void,ArrayList<PhotoGalleryItem>> {
        @Override
        protected ArrayList<PhotoGalleryItem> doInBackground(Integer... params) {
            return new SiteConnector().fetchPhotoItems(params[0], params[1]);
        }
        @Override
        protected void onPostExecute(ArrayList<PhotoGalleryItem> photoCollages){
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
    private class FetchVideoItemsTask extends AsyncTask<Integer,Void,ArrayList<VideoGalleryItem>> {
        @Override
        protected ArrayList<VideoGalleryItem> doInBackground(Integer... params) {
            return new SiteConnector().fetchVideoItems(params[0], params[1]);
        }
        @Override
        protected void onPostExecute(ArrayList<VideoGalleryItem> photoCollages){
            mVideoGalleryItems =photoCollages;
            setupVideoAdapter();
        }
    }
}
