package com.tyaa.OPRST.GalleryContainer.PhotoContainer;

import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tyaa.OPRST.Cache.PhotoContainerItemCache;
import com.tyaa.OPRST.GridAdapter.AbstractArrayAdapter;
import com.tyaa.OPRST.GridAdapter.AbstractContainerFragment;
import com.tyaa.OPRST.GridAdapter.AbstractListener;
import com.tyaa.OPRST.GalleryContainer.PhotoContainer.PhotoGallery.PhotoGalleryActivity;
import com.tyaa.OPRST.GridAdapter.ItemAdapter;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.CollectionActivity;

import java.lang.Integer;import java.lang.Override;import java.lang.String;import java.lang.Void;
import java.util.ArrayList;

//тут мы становимся гордым владельцем пустого экрана
public class PhotoContainerFragment<I> extends AbstractContainerFragment<I> {
    private static final String TAG = "PhotoContainerFragment";
    public static final String EXTRA_RESULT = "com.tyaa.GalleryContainer.PhotoContainerFragment.result";

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
        new FetchItemsTask().execute();
    }

    @Override
    protected View getInflaterView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_gallery, container,
                false);
    }

    private class ClickListener extends AbstractListener {
        private PhotoContainerItem msg;
        public void onClick(View arg0) {
            Intent i = new Intent(getActivity(), PhotoGalleryActivity.class);
            i.putExtra(EXTRA_RESULT, msg.getId());
            getContext().startActivity(i);
        }
        @Override
        public void setMsg(Object msg) {
            this.msg =(PhotoContainerItem) msg;
        }
    }
    //получение фото галереи в фоновом потоке
    private class FetchItemsTask extends AsyncTask<Integer,Void,ArrayList<I>> {
        @Override
        protected ArrayList<I> doInBackground(Integer... params) {
            int width=getResources().getDimensionPixelSize(R.dimen.gallery_column_width);
            return (ArrayList<I>) new PhotoContainerItemCache(getContext(),
                    TAG+".dat",new Point(width,0)).get();
        }
        @Override
        protected void onPostExecute(ArrayList<I> collages){
            setupAdapter(collages);
        }
    }
}
