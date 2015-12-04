package com.tyaa.OPRST.GalleryContainer.PhotoContainer.PhotoGallery;


import android.support.v4.app.Fragment;

import com.tyaa.OPRST.R;
import com.tyaa.OPRST.SingleFragmentActivity;

/**
 * Created by Павел on 10/10/2015.
 */
public class PhotoGalleryActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        PhotoGalleryFragment<PhotoGalleryItem> fragment=new PhotoGalleryFragment<>();
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }
}
