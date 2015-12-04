package com.tyaa.OPRST.GalleryContainer.VideoContainer.VideoGallery;


import android.support.v4.app.Fragment;

import com.tyaa.OPRST.R;
import com.tyaa.OPRST.SingleFragmentActivity;

/**
 * Created by Павел on 10/11/2015.
 */
public class VideoCommentActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        VideoCommentFragment<VideoCommentItem> fragment = new VideoCommentFragment<>();
        fragment.mErrorHandler = mErrorHandler;
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }
}
