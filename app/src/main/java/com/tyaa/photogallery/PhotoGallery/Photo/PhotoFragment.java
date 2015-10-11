package com.tyaa.photogallery.PhotoGallery.Photo;

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

import com.tyaa.photogallery.GalleryContainer.GalleryFragment;
import com.tyaa.photogallery.PhotoGallery.PhotoGalleryFragment;
import com.tyaa.photogallery.PhotoGallery.PhotoGalleryItem;
import com.tyaa.photogallery.R;
import com.tyaa.photogallery.SiteConnector;
import com.tyaa.photogallery.ThumbDownloader;

/**
 * Created by Павел on 10/11/2015.
 */
public class PhotoFragment extends Fragment {
    private static final String TAG = "PhotoFragment";

    private ThumbDownloader<ImageView> mThumbThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gallery_item, container,
                false);
        ImageView imageView = (ImageView)v.findViewById(R.id.gallery_item_imageView);
        imageView.setImageResource(R.drawable.photo_default);
        String url= (String) getActivity().getIntent().
                getExtras().get(PhotoGalleryFragment.EXTRA_RESULT);
        mThumbThread.queueThumbnail(imageView, url);
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
}