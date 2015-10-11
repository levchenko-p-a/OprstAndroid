package com.tyaa.photogallery.VideoGallery.Video;

import android.support.v4.app.Fragment;

import com.tyaa.photogallery.PhotoGallery.Photo.PhotoFragment;
import com.tyaa.photogallery.SingleFragmentActivity;

/**
 * Created by Павел on 10/11/2015.
 */
public class VideoActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        PhotoFragment fragment=new PhotoFragment();
        return fragment;
    }
}
