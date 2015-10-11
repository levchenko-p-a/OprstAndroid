package com.tyaa.photogallery.PhotoGallery.Photo;

import android.support.v4.app.Fragment;

import com.tyaa.photogallery.PhotoGallery.PhotoGalleryFragment;
import com.tyaa.photogallery.SingleFragmentActivity;

/**
 * Created by Павел on 10/11/2015.
 */
public class PhotoActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        PhotoFragment fragment=new PhotoFragment();
        return fragment;
    }
}