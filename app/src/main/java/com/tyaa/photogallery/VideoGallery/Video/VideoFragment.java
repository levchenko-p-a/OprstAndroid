package com.tyaa.photogallery.VideoGallery.Video;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tyaa.photogallery.PhotoGallery.PhotoGalleryFragment;
import com.tyaa.photogallery.R;
import com.tyaa.photogallery.ThumbDownloader;
import com.tyaa.photogallery.VideoGallery.VideoGalleryFragment;

/**
 * Created by Павел on 10/11/2015.
 */
public class VideoFragment extends Fragment {
    private static final String TAG = "VideoFragment";
    private VideoPlayer mPlayer = new VideoPlayer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gallery_item, container,
                false);
        ImageView imageView = (ImageView)v.findViewById(R.id.gallery_item_imageView);
        imageView.setImageResource(R.drawable.video_default);
        String url= (String) getActivity().getIntent().
                getExtras().get(VideoGalleryFragment.EXTRA_RESULT);
        return v;
    }
}
