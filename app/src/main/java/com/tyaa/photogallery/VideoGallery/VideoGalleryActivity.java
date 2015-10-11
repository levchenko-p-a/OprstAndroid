package com.tyaa.photogallery.VideoGallery;

import android.support.v4.app.Fragment;

import com.tyaa.photogallery.PhotoGallery.PhotoGalleryFragment;
import com.tyaa.photogallery.SingleFragmentActivity;

/**
 * Created by Павел on 10/11/2015.
 */
public class VideoGalleryActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        PhotoGalleryFragment fragment=new PhotoGalleryFragment();
        return fragment;
    }
}
