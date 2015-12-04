package com.tyaa.OPRST;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Pavlo on 12/02/2015.
 */
public class PlaceholderFragment extends Fragment {
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
    public static PlaceholderFragment newInstance(int levelNumber,int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(CollectionActivity.ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(CollectionActivity.ARG_LEVEL_NUMBER, levelNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_share, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.shareTitleTextView);
        int pos = getArguments().getInt(CollectionActivity.ARG_SECTION_NUMBER);
        String text = getString(R.string.hello_world) + String.valueOf(pos);
        textView.setText(text);
        return rootView;
    }
}
