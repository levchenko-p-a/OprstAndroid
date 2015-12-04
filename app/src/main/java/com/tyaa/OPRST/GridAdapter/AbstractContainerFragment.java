package com.tyaa.OPRST.GridAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.tyaa.OPRST.Callbacks;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.Share.ShareActivity;
import com.tyaa.OPRST.Share.ShareFragment;
import com.tyaa.OPRST.SingleFragmentActivity;

import java.util.ArrayList;

/**
 * Created by Pavlo on 10/20/2015.
 */
public abstract class AbstractContainerFragment<I> extends Fragment {
    private static final String TAG = "AbstractContainerFragment";

    private GridView mGrid;
    public I getGalleryItem() {
        return mGalleryItem;
    }
    public ArrayList<I> getGalleryItems() {
        return mGalleryItems;
    }
    private I mGalleryItem=null;
    private ArrayList<I> mGalleryItems=null;
    private String mEventUrlString;
    private Thread mErrorThread=null;
    public String getEventUrlString() {
        return mEventUrlString;
    }
    public SingleFragmentActivity.ErrorHandler mErrorHandler;
    public void setEventUrlString(String eventUrlString) {
        mEventUrlString = eventUrlString;
    }
    private Callbacks mCallbacks;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mCallbacks=(Callbacks)context;
        getItems();
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
        //setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.social_links_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        String mShareUrlString="";
        //получаем из приложения намерения адрес страницы сайта с детализацией события
        //mEventUrlString =(String) getArguments().getSerializable(EXTRA_EVENT_URL);
        switch (item.getItemId()){
            /*case R.id.event_page_shareFbButton:
                try {
                    mShareUrlString =
                            "https://www.facebook.com/sharer/sharer.php?sdk=joey&u="
                                    + URLEncoder.encode(mEventUrlString, "UTF-8")
                                    + "&t="
                                    + URLEncoder.encode(mEventDetails.getHeader(), "UTF-8");
                                    startShareIntent(mShareUrlString);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.event_page_shareMailruButton:
                try {
                    mShareUrlString =
                            "http://connect.mail.ru/share?url="
                                    + URLEncoder.encode(mEventUrlString, "UTF-8")
                                    + "&title="
                                    + URLEncoder.encode(mEventDetails.getHeader(),
                                    "UTF-8")
                                    + "&description="
                                    + URLEncoder.encode((mEventDetails.isCinema())
                                    ? mEventDetails.getContent()
                                    : mEventDetails.getSpannedContent()
                                    .toString()
                                    .substring(0, 200) + "...", "UTF-8")
                                    + "&imageurl="
                                    + URLEncoder.encode(mEventDetails.getImageUrlString(),
                                    "UTF-8");
                                    startShareIntent(mShareUrlString);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.event_page_shareOkButton:
                try {
                    mShareUrlString =
                            "http://www.odnoklassniki.ru/dk?st.cmd=addShare&st.s=1&st._surl="
                                    + URLEncoder.encode(mEventUrlString, "UTF-8")
                                    + "&st.comments="
                                    + URLEncoder.encode(mEventDetails.getHeader(), "UTF-8");
                                    startShareIntent(mShareUrlString);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.event_page_shareTwButton:
                try {
                    mShareUrlString = "https://twitter.com/intent/tweet?url="
                            + URLEncoder.encode(mEventUrlString, "UTF-8")
                            + "&text="
                            + URLEncoder.encode(mEventDetails.getHeader(), "UTF-8");
                            startShareIntent(mShareUrlString);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.event_page_shareVkButton:
                try {
                    mShareUrlString = "http://vk.com/share.php?url="
                            + URLEncoder.encode(mEventUrlString, "UTF-8")
                            + "&title="
                            + URLEncoder.encode(mEventDetails.getHeader(), "UTF-8")
                            + "&description="
                            + URLEncoder.encode((mEventDetails.isCinema())
                            ? ((mEventDetails.getContent().length() > 200)
                            ? mEventDetails.getContent().substring(0, 200)
                            : mEventDetails.getContent()) + "..."
                            : (mEventDetails.getSpannedContent().toString().length() > 200)
                            ? mEventDetails.getSpannedContent().toString().substring(0, 200)
                            : mEventDetails.getSpannedContent().toString(), "UTF-8")
                            + "&image="
                            + URLEncoder.encode(mEventDetails.getImageUrlString(),
                            "UTF-8")
                            + "&noparse=true";
                            startShareIntent(mShareUrlString);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;*/
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    private void startShareIntent(String mShareUrlString){
        Intent shareIntent = new Intent(getActivity(), ShareActivity.class);
        shareIntent.putExtra(ShareFragment.EXTRA_SHARE_URL, mShareUrlString);
        startActivity(shareIntent);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = getInflaterView(inflater, container);
        mGrid = (GridView)v.findViewById(R.id.itemGrid);
        mGrid.setAdapter(null);
        return v;
    }
    public void setGalleryItem(I galleryItem) {
        mGalleryItem = galleryItem;
    }
    protected void setupAdapter(I mGalleryItem) {
        if (getActivity() == null || mGrid == null) return;
        if (mGalleryItem != null) {
            setGalleryItem(mGalleryItem);
        } else {
            mGrid.setAdapter(null);
        }
    }
    public void setGalleryItems(ArrayList<I> galleryItems) {
        mGalleryItems = galleryItems;
    }

    protected void setupAdapter(ArrayList<I> mGalleryItems) {
        if (getActivity() == null || mGrid == null) return;
        if (mGalleryItems != null) {
            setGalleryItems(mGalleryItems);
            mGrid.setAdapter(getAdapter());
        } else {
            mGrid.setAdapter(null);
        }
    }

    protected abstract AbstractArrayAdapter getAdapter();
    //тут мы создаём обработчик нажатий на ячейку грида
    public abstract AbstractListener createListener();
    //тут мы получаем данные из сети
    protected abstract void getItems();
    //тут мы получаем разметкудля фрагмента, содержащую грид
    protected abstract View getInflaterView(LayoutInflater inflater, ViewGroup container);
}
