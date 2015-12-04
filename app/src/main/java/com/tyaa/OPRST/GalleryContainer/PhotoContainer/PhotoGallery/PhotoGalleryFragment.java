package com.tyaa.OPRST.GalleryContainer.PhotoContainer.PhotoGallery;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.GalleryContainer.PhotoContainer.PhotoContainerFragment;
import com.tyaa.OPRST.GalleryContainer.PhotoContainer.PhotoGallery.Photo.PhotoActivity;
import com.tyaa.OPRST.GridAdapter.AbstractArrayAdapter;
import com.tyaa.OPRST.GridAdapter.AbstractContainerFragment;
import com.tyaa.OPRST.GridAdapter.AbstractListener;
import com.tyaa.OPRST.GridAdapter.ItemAdapter;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.SiteConnector;

import java.util.ArrayList;

/**
 * Created by Павел on 10/10/2015.
 */
public class PhotoGalleryFragment <I> extends AbstractContainerFragment<I> {
    private static final String TAG = "PhotoGalleryFragment";
    public static final String EXTRA_RESULT = "com.tyaa.OPRST.photogalleryfragment.result";

    @Override
    protected AbstractArrayAdapter getAdapter() {
        return new ItemAdapter(this);
    }
    @Override
    public AbstractListener createListener() {
        return new ClickListener();
    }

    @Override
    protected void getItems() {
        String id=(String)getActivity().getIntent().getExtras().get(PhotoContainerFragment.EXTRA_RESULT);
        new FetchItemsTask().execute(id,
                String.valueOf(CollectionActivity.parms.width),
                String.valueOf(CollectionActivity.parms.height));
    }

    @Override
    protected View getInflaterView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_gallery, container,
                false);
    }

    private class ClickListener extends AbstractListener {
        private PhotoGalleryItem msg;
        public void onClick(View arg0) {
            Intent i = new Intent(getActivity(), PhotoActivity.class);
            i.putExtra(EXTRA_RESULT,(Parcelable) msg);
            startActivity(i);
        }

        @Override
        public void setMsg(Object msg) {
            this.msg =(PhotoGalleryItem) msg;
        }
    }
    //получение фото галереи в фоновом потоке
    private class FetchItemsTask extends AsyncTask<String,Void,ArrayList<I>> {
        @Override
        protected ArrayList<I> doInBackground(String... params) {
            return (ArrayList<I>) new SiteConnector().fetchPhotoGalleryItems(params[0],
                    params[1],params[2]);
        }
        @Override
        protected void onPostExecute(ArrayList<I> collages){
            setupAdapter(collages);
        }
    }
}
