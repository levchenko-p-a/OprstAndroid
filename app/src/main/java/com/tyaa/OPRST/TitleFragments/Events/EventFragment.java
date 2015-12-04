package com.tyaa.OPRST.TitleFragments.Events;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tyaa.OPRST.Callbacks;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.GalleryContainer.PhotoContainer.PhotoContainerFragment;
import com.tyaa.OPRST.GalleryContainer.VideoContainer.VideoContainerFragment;
import com.tyaa.OPRST.R;

/**
 * Created by Pavlo on 12/04/2015.
 */
public class EventFragment extends Fragment {
    private static final String TAG = "EventFragment";
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
        View v = inflater.inflate(R.layout.fragment_event, container,
                false);
        Button mPhotoButton= (Button) v.findViewById(R.id.fragment_event_photos_button);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getArguments().getInt(CollectionActivity.ARG_SECTION_NUMBER);
                mCallbacks.onFragmentNext(EventFragment.this,PhotoContainerFragment.class);
            }
        });
        Button mVideoButton= (Button) v.findViewById(R.id.fragment_event_vidos_button);
        mVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getArguments().getInt(CollectionActivity.ARG_SECTION_NUMBER);
                mCallbacks.onFragmentNext(EventFragment.this,VideoContainerFragment.class);
            }
        });
        return v;
    }
}
