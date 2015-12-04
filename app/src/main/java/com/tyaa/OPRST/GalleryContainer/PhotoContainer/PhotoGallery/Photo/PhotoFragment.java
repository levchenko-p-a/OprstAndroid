package com.tyaa.OPRST.GalleryContainer.PhotoContainer.PhotoGallery.Photo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tyaa.OPRST.GalleryContainer.PhotoContainer.PhotoGallery.PhotoGalleryFragment;
import com.tyaa.OPRST.GridAdapter.AdapterItem;
import com.tyaa.OPRST.R;

/**
 * Created by Павел on 10/11/2015.
 */
public class PhotoFragment extends Fragment {
    private static final String TAG = "PhotoFragment";

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
        imageView.setImageResource(R.drawable.photo_default);
        TextView textView1=(TextView)v.findViewById(R.id.gallery_item_title);

        AdapterItem item= (AdapterItem) getActivity().getIntent().
                getExtras().get(PhotoGalleryFragment.EXTRA_RESULT);

        textView1.setText(item.getPagetitle());
        Picasso.with(getContext()).load(item.getPreview()).into(imageView);
        return v;
    }
}