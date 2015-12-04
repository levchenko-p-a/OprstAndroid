package com.tyaa.OPRST.GridAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;
import com.tyaa.OPRST.GalleryContainer.VideoContainer.VideoGallery.VideoCommentItem;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.SiteConnector;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavlo on 10/20/2015.
 */
public abstract class AbstractArrayAdapter<I> extends BaseAdapter {
    private static final String TAG = "AbstractArrayAdapter";
    public AbstractArrayAdapter(AbstractContainerFragment fragment) {
        items=fragment.getGalleryItems();
        mFragment=fragment;
        mContext=fragment.getContext();
        picasso=new Picasso.Builder(mContext).build();
    }
    protected ArrayList<I> items;
    protected AbstractContainerFragment mFragment=null;
    protected Context mContext=null;
    protected Picasso picasso=null;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getConvertView(parent);
        }
        convertView = bindView(convertView,position);
        return convertView;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    //тут мы заполняем ячейку грида данными
    public abstract View bindView(View convertView, int position);
    //тут мы получаем разметку для ячейки грида
    protected abstract View getConvertView(ViewGroup parent);
}
