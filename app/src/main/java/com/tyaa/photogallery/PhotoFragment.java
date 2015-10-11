package com.tyaa.photogallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Павел on 29.09.2015.
 */
public class PhotoFragment extends Fragment {
    private static final String TAG = "PhotoFragment";
    public static final String EXTRA_RESULT = "com.tyaa.photogallery.result";
    private static final String OPRST_PHOTO_DIR = "http://oprst.com.ua/assets/galleries/";

    private String mId;
    private TextView mTextView;

    public void setmId(String id) {
        this.mId = id;
    }

    public String getmId() {
        return mId;
    }

    private ArrayList<String> urls;
    public String getGallery() {
        return OPRST_PHOTO_DIR+ mId +"/";
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo, container,
                false);
        Integer id= (int) getActivity().getIntent().getExtras().get(GalleryFragment.EXTRA_RESULT);
        setmId(id.toString());
        new FetchStringTask().execute();
        mTextView = (TextView)v.findViewById(R.id.textView);
        return v;
    }
    private void setText(){
        mTextView.setText(urls.get(1));
    }
    //получение видео галереи в фоновом потоке
    private class FetchStringTask extends AsyncTask<Integer,Void,ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            return new SiteConnector().jsonToStrings(getmId());
        }
        @Override
        protected void onPostExecute(ArrayList<String> strings){
            urls =strings;
            setText();
        }
    }
}
