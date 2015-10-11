package com.tyaa.photogallery.PhotoGallery;

import android.support.v4.app.Fragment;

import com.tyaa.photogallery.SingleFragmentActivity;

/**
 * Created by Павел on 10/10/2015.
 */
public class PhotoGalleryActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        PhotoGalleryFragment fragment=new PhotoGalleryFragment();
        return fragment;
    }
}
