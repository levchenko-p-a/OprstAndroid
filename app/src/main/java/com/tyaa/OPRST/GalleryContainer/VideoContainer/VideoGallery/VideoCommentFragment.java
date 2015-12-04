package com.tyaa.OPRST.GalleryContainer.VideoContainer.VideoGallery;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tyaa.OPRST.GalleryContainer.VideoContainer.VideoContainerFragment;
import com.tyaa.OPRST.GalleryContainer.VideoContainer.VideoContainerItem;
import com.tyaa.OPRST.GridAdapter.AbstractArrayAdapter;
import com.tyaa.OPRST.GridAdapter.AbstractContainerFragment;
import com.tyaa.OPRST.GridAdapter.AbstractListener;
import com.tyaa.OPRST.R;
import com.tyaa.OPRST.SingleFragmentActivity;
import com.tyaa.OPRST.SiteConnector;
import com.tyaa.OPRST.GalleryContainer.VideoContainer.VideoGallery.Video.VideoActivity;

import java.util.ArrayList;

/**
 * Created by Павел on 10/11/2015.
 */
public class VideoCommentFragment<I> extends AbstractContainerFragment<I> {
    private static final String TAG = "VideoCommentFragment";
    public static final String EXTRA_RESULT = "com.tyaa.OPRST.videogalleryfragment.result";

    private VideoCommentAdapter mCommentAdapter=null;

    @Override
    protected AbstractArrayAdapter getAdapter() {
        return new VideoCommentAdapter(this);
    }

    @Override
    public AbstractListener createListener() {
        return new ClickListener();
    }

    @Override
    protected void getItems() {
        VideoContainerItem item = (VideoContainerItem)getActivity().getIntent().getExtras()
                .get(VideoContainerFragment.EXTRA_RESULT);
        setGalleryItem((I) item);
        VideoCommentItem.setId(item.getId());
        VideoCommentItem.setPreview(item.getPreview());
        VideoCommentItem.setYoutube(item.getYoutube());
        new FetchItemsTask().execute(item.getId());
    }

    @Override
    protected View getInflaterView(LayoutInflater inflater, ViewGroup container) {
        View v=inflater.inflate(R.layout.activity_comment_video, container,
                false);
        VideoContainerItem item =(VideoContainerItem)getGalleryItem();
        TextView textView=(TextView)v
                .findViewById(R.id.photoTitle);
        textView.setText(item.getPagetitle());
        ImageView imageView = (ImageView)v.findViewById(R.id.imageView);
        Picasso.with(getContext()).load(item.getPreview()).into(imageView);
        AbstractListener listener=createListener();
        listener.setMsg(item);
        imageView.setOnClickListener(listener);
        return v;
    }

    private class ClickListener extends AbstractListener {
        private VideoContainerItem msg;
        @Override
        //тут мы преобразуем объект, посланный в адаптере в нужный нам класс
        public void setMsg(Object msg) {
            this.msg =(VideoContainerItem) msg;
        }
        public void onClick(View arg0) {
            Intent i = new Intent(getActivity(), VideoActivity.class);
            i.putExtra(EXTRA_RESULT, msg.getYoutube());
            getContext().startActivity(i);
        }
    }
    //получение комментариев в фоновом потоке
    private class FetchItemsTask extends AsyncTask<String,Void,ArrayList<I>> {
        @Override
        protected ArrayList<I> doInBackground(String... params) {
            return (ArrayList<I>) new SiteConnector().getVideoComments(params[0]);
        }
        @Override
        protected void onPostExecute(ArrayList<I> items){
            setupAdapter(items);
        }
    }
}
