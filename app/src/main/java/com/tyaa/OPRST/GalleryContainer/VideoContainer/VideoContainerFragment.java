package com.tyaa.OPRST.GalleryContainer.VideoContainer;

import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tyaa.OPRST.Cache.VideoContainerItemCache;
import com.tyaa.OPRST.GridAdapter.AbstractArrayAdapter;
import com.tyaa.OPRST.GridAdapter.AbstractContainerFragment;
import com.tyaa.OPRST.GridAdapter.AbstractListener;
import com.tyaa.OPRST.GridAdapter.ItemAdapter;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.GalleryContainer.VideoContainer.VideoGallery.VideoCommentActivity;

import java.util.ArrayList;

/**
 * Created by Pavlo on 10/20/2015.
 */
public class VideoContainerFragment<I> extends AbstractContainerFragment<I> {
    private static final String TAG = "VideoContainerFragment";
    public static final String EXTRA_RESULT = "com.tyaa.GalleryContainer.VideoContainerFragment.result";

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
        private VideoContainerItem msg;
        @Override
        //тут мы преобразуем объект, посланный в адаптере в нужный нам класс
        public void setMsg(Object msg) {
            this.msg =(VideoContainerItem) msg;
        }
        public void onClick(View arg0) {
            Intent i = new Intent(getActivity(), VideoCommentActivity.class);
            i.putExtra(EXTRA_RESULT,(Parcelable) msg);
            getContext().startActivity(i);
        }
    }
    //получение фото галереи в фоновом потоке
    private class FetchItemsTask extends AsyncTask<Integer,Void,ArrayList<I>> {
        @Override
        protected ArrayList<I> doInBackground(Integer... params) {
            int width=getResources().getDimensionPixelSize(R.dimen.gallery_column_width);
            return (ArrayList<I>) new VideoContainerItemCache(getContext(),
                    TAG+".dat",new Point(width,0)).get();
        }
        @Override
        protected void onPostExecute(ArrayList<I> collages){
            setupAdapter(collages);
        }
    }
}