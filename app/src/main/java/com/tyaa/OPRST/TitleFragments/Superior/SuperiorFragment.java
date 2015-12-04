package com.tyaa.OPRST.TitleFragments.Superior;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tyaa.OPRST.Callbacks;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.GalleryContainer.PhotoContainer.PhotoGallery.PhotoGalleryFragment;
import com.tyaa.OPRST.GridAdapter.AdapterItem;
import com.tyaa.OPRST.R;

/**
 * Created by Pavlo on 12/02/2015.
 */
public class SuperiorFragment extends Fragment {
    private static final String TAG = "SuperiorFragment";
    private Callbacks mCallbacks;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mCallbacks=(Callbacks)context;
    }
    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks=null;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_superior, container,
                false);
        ImageView imageVideoView1 = (ImageView)v.findViewById(R.id.superior_video_view1);
        imageVideoView1.setImageResource(R.drawable.video_default);
        ImageView imageVideoView2 = (ImageView)v.findViewById(R.id.superior_video_view2);
        imageVideoView2.setImageResource(R.drawable.video_default);
        ImageView imageVideoView3 = (ImageView)v.findViewById(R.id.superior_video_view3);
        imageVideoView3.setImageResource(R.drawable.video_default);
        ImageView imagePhotoView1 = (ImageView)v.findViewById(R.id.superior_photo_view1);
        imagePhotoView1.setImageResource(R.drawable.photo_default);
        ImageView imagePhotoView2 = (ImageView)v.findViewById(R.id.superior_photo_view2);
        imagePhotoView2.setImageResource(R.drawable.photo_default);
        ImageView imagePhotoView3 = (ImageView)v.findViewById(R.id.superior_photo_view3);
        imagePhotoView3.setImageResource(R.drawable.photo_default);
        return v;
    }
}