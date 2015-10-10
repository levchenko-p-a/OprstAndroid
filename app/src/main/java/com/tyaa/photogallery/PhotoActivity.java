package com.tyaa.photogallery;

import android.support.v4.app.Fragment;

//главная активность
public class PhotoActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new PhotoFragment();
    }
}
