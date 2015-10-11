package com.tyaa.photogallery.Tab;

import android.support.v4.app.Fragment;

import com.tyaa.photogallery.GalleryContainer.GalleryFragment;
import com.tyaa.photogallery.SingleFragmentActivity;

/**
 * Created by Kitsune on 07.10.2015.
 */
public class TabVideoActivivty extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        GalleryFragment fragment=new GalleryFragment();
        fragment.setOnVideo(true);
        return fragment;
    }
}