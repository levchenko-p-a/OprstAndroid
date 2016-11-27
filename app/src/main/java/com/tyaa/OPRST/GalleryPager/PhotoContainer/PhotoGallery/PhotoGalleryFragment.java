package com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoGallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.DataService.DataService;
import com.tyaa.OPRST.TitlePageAdapter.FragmentCallbacks;
import com.tyaa.OPRST.GalleryPager.BaseContainerFragment;
import com.tyaa.OPRST.GalleryPager.GalleryPagerAdapter;
import com.tyaa.OPRST.GalleryPager.GalleryPagerListener;
import com.tyaa.OPRST.DataService.MsgHolder;
import com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoContainerItem;
import com.tyaa.OPRST.DataService.Method;
import com.tyaa.OPRST.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Павел on 10/10/2015.
 */
public class PhotoGalleryFragment extends BaseContainerFragment implements FragmentCallbacks {
    public static final String TAG = "com.tyaa.GalleryContainer.VideoContainerFragment.";
    public static final String EXTRA_RESULT =TAG+ "result";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery_photo, container, false);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) v.findViewById(R.id.page_photo_fragment);
        mViewPager.setAdapter(null);
        View mPager=(View) v.findViewById(R.id.pager_panel);
        listener = new GalleryPagerListener(mPager, mViewPager);
        mViewPager.clearOnPageChangeListeners();
        mViewPager.addOnPageChangeListener(listener);
        return v;
    }

    @Override
    public void getItems() {
        if(mViewPager!=null && mItems==null && mViewPager.getAdapter()==null){
            String gsonString = getArguments().getString(CollectionActivity.ARG_MESSAGE);
            Type listType = new TypeToken<PhotoContainerItem>() {}.getType();
            PhotoContainerItem item=gson.fromJson(gsonString, listType);
            MsgHolder holder=new MsgHolder();
            int width=getResources().getDimensionPixelSize(R.dimen.gallery_column_width);
            int height=getResources().getDimensionPixelSize(R.dimen.gallery_column_height);
            holder.setId(item.getId());
            holder.setWidth(width);
            holder.setHeight(height);
            holder.setTag(getTag());
            holder.setExtra(getTag());
            holder.setMethod(Method.PhotoGalleryItems);
            Intent mDataIntent = new Intent(getContext(), DataService.class);
            mDataIntent.putExtra(CollectionActivity.EXTRA_RESULT, (Parcelable) holder);
            mCallbacks.startService(mDataIntent);
        }else{
           onDataObtained();
        }
    }

    public void onDataObtained() {
        if(mCallbacks!=null) {
        if(mViewPager!=null && mItems!=null) {
            mGalleryPagerAdapter = new GalleryPagerAdapter(this, PhotoImagePageFragment.class,
                    mItems, count);
            mViewPager.setAdapter(mGalleryPagerAdapter);
            listener.setSize(mGalleryPagerAdapter.getCount());
            if(page!=0){
                mViewPager.setCurrentItem(page);
            }
        }else{
            mCallbacks.getErrorHandler().createMessage(
                        R.string.error_video_gallery_message);
        }}
    }

    @Override
    public void onReceiveIntent(Intent intent) {
        String gsonString=intent.getStringExtra(getTag());
        Type listType = new TypeToken<ArrayList<PhotoGalleryItem>>() {}.getType();
        mItems=gson.fromJson(gsonString, listType);
        onDataObtained();
    }
}
