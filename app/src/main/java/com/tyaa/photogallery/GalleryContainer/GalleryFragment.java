package com.tyaa.photogallery.GalleryContainer;

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

import com.tyaa.photogallery.PhotoGallery.PhotoGalleryActivity;
import com.tyaa.photogallery.R;import com.tyaa.photogallery.SiteConnector;import com.tyaa.photogallery.ThumbDownloader;

import java.lang.Integer;import java.lang.Override;import java.lang.String;import java.lang.Void;
import java.util.ArrayList;

//тут мы становимся гордым владельцем пустого экрана
public class GalleryFragment extends Fragment{
    private static final String TAG = "GalleryFragment";
    public static final String EXTRA_RESULT = "com.tyaa.photogallery.galleryfragment.result";

    private GridView mGrid;
    private ArrayList<GalleryItem> mGalleryItems;
    private ThumbDownloader<ImageView> mThumbThread;
    private boolean onVideo;
    TextView mTextView;

    public boolean isOnVideo() {
        return onVideo;
    }

    public void setOnVideo(boolean onVideo) {
        this.onVideo = onVideo;
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container,
                false);
        mTextView=(TextView)v.findViewById(R.id.photoTitle);
        String title=onVideo?getContext().getString(R.string.photo_grid_title):
        getContext().getString(R.string.video_grid_title);
        mTextView.setText(title);
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
            final com.tyaa.photogallery.GalleryContainer.GalleryItem item =
                    (com.tyaa.photogallery.GalleryContainer.GalleryItem)getItem(position);
            mThumbThread.queueThumbnail(imageView, item.getThumb());
            //тут можно вызывать просмотрщик фото или видео
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View arg0) {
                    if (item.getYoutube() == null) {
                        Intent i = new Intent(getActivity(), PhotoGalleryActivity.class);
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
    void setupAdapter() {
        if (getActivity() == null || mGrid == null) return;
        if (mGalleryItems != null) {
            mGrid.setAdapter(new GalleryItemAdapter(mGalleryItems));
        } else {
            mGrid.setAdapter(null);
        }
    }
    //получение фото галереи в фоновом потоке
    private class FetchItemsTask extends AsyncTask<Integer,Void,ArrayList<GalleryItem>> {
        @Override
        protected ArrayList<GalleryItem> doInBackground(Integer... params) {
            return new SiteConnector().fetchItems(params[0], params[1],isOnVideo());
        }
        @Override
        protected void onPostExecute(ArrayList<GalleryItem> collages){
            mGalleryItems =collages;
            setupAdapter();
        }
    }
}
