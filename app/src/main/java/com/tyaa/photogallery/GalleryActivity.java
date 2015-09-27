package com.tyaa.photogallery;

import android.support.v4.app.Fragment;

//главная активность
public class GalleryActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new GalleryFragment();
    }
}
