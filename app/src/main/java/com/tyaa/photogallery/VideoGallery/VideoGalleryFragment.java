package com.tyaa.photogallery.VideoGallery;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.tyaa.photogallery.R;
import com.tyaa.photogallery.ThumbDownloader;
import com.tyaa.photogallery.VideoGallery.Video.VideoActivity;

/**
 * Created by Павел on 10/11/2015.
 */
public class VideoGalleryFragment extends Fragment {
    private static final String TAG = "VideoGalleryFragment";
    public static final String EXTRA_RESULT = "com.tyaa.photogallery.videogalleryfragment.result";

    private ThumbDownloader<ImageView> mThumbThread;
    private VideoGalleryItem<String> videos;
    private GridView mGrid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        int id= (Integer) getActivity().getIntent().getExtras().get(GalleryFragment.EXTRA_RESULT);
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
        View v = inflater.inflate(R.layout.fragment_gallery, container,
                false);
        mGrid = (GridView)v.findViewById(R.id.itemGrid);
        setupAdapter();
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
    private class GalleryItemAdapter<String> extends ArrayAdapter<String> {
        public GalleryItemAdapter(VideoGalleryItem<String> items) {
            super(getActivity(), 0, items);
            this.items=items;
        }
        private VideoGalleryItem<String> items=null;
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.gallery_item, parent, false);
            }
            ImageView imageView = (ImageView)convertView
                    .findViewById(R.id.gallery_item_imageView);
            imageView.setImageResource(R.drawable.video_default);
            TextView textView=(TextView)convertView
                    .findViewById(R.id.gallery_item_title);
            final String item = getItem(position);
            java.lang.String image=(java.lang.String)item;
            mThumbThread.queueThumbnail(imageView, image);
            //тут можно вызывать просмотрщик полной версии
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View arg0) {
                    Intent i = new Intent(getActivity(), VideoActivity.class);
                    i.putExtra(EXTRA_RESULT, (java.lang.String) items.getYoutube());
                    startActivity(i);
                    return true;
                }
            });
            return convertView;
        }
    }
    void setupAdapter() {
        if (getActivity() == null || mGrid == null) return;
        if (videos.getYoutube() != null) {
            mGrid.setAdapter(new GalleryItemAdapter(videos));
        } else {
            mGrid.setAdapter(null);
        }
    }
}
