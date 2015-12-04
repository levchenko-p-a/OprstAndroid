package com.tyaa.OPRST.TitleFragments.Advertising;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyaa.OPRST.Callbacks;
import com.tyaa.OPRST.CollectionActivity;
import com.tyaa.OPRST.R;

/**
 * Created by Pavlo on 12/04/2015.
 */
public class AdvertisingFragment extends Fragment {
    private static final String TAG = "AdvertisingFragment";
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
        View v = inflater.inflate(R.layout.fragment_market, container,
                false);
        return v;
    }
}