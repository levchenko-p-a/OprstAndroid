package com.tyaa.photogallery.PhotoGallery;

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
import com.tyaa.photogallery.R;
import com.tyaa.photogallery.SiteConnector;
import com.tyaa.photogallery.ThumbDownloader;

/**
 * Created by Павел on 10/10/2015.
 */
public class PhotoGalleryFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";

    private GridView mGrid;
    private PhotoGalleryItem<String> photos;
    private ThumbDownloader<ImageView> mThumbThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        int id= (Integer) getActivity().getIntent().getExtras().get(GalleryFragment.EXTRA_RESULT);
        new FetchItemsTask().execute(Integer.toString(id));
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
        public GalleryItemAdapter(PhotoGalleryItem<String> items) {
            super(getActivity(), 0, items);
            this.items=items;
        }
        private PhotoGalleryItem<String> items=null;
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
            String item = getItem(position);
            java.lang.String image=(java.lang.String)items.getThumbs()+item;
            mThumbThread.queueThumbnail(imageView, image);
            //тут можно вызывать просмотрщик полной версии
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View arg0) {

                    return true;
                }
            });
            return convertView;
        }
    }
    void setupAdapter() {
        if (getActivity() == null || mGrid == null) return;
        if (photos != null) {
            mGrid.setAdapter(new GalleryItemAdapter(photos));
        } else {
            mGrid.setAdapter(null);
        }
    }
    //получение фото галереи в фоновом потоке
    private class FetchItemsTask extends AsyncTask<String,Void,PhotoGalleryItem<String>> {
        @Override
        protected PhotoGalleryItem<String> doInBackground(String... params) {
            return new SiteConnector().jsonToStrings(params[0]);
        }
        @Override
        protected void onPostExecute(PhotoGalleryItem<String> collages){
            photos =collages;
            setupAdapter();
        }
    }
}
