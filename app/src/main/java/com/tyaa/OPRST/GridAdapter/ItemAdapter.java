package com.tyaa.OPRST.GridAdapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tyaa.OPRST.R;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Pavlo on 10/18/2015.
 */
public class ItemAdapter <AdapterItem> extends AbstractArrayAdapter<AdapterItem> {
    private static final String TAG = "ItemAdapter";
    public ItemAdapter(AbstractContainerFragment fragment) {
        super(fragment);
    }
    protected HashMap<Long, ViewHolder> mViews = new HashMap<>();
    @Override
    public View bindView(View convertView,int position) {
        ViewHolder vh = (ViewHolder) convertView.getTag(R.layout.gallery_item);
        if(vh!=null) {
            final com.tyaa.OPRST.GridAdapter.AdapterItem item =
                    (com.tyaa.OPRST.GridAdapter.AdapterItem) getItem(position);
            vh.position = position;
            vh.textView1.setText(item.getPagetitle());
            vh.textView2.setText(item.getCaption());
            java.lang.String url = item.getThumb();
            picasso.load(url).into(vh.imageView);
            AbstractListener listener = mFragment.createListener();

            listener.setMsg(item);
            vh.imageView.setOnClickListener(listener);
            //Log.i(TAG, "bindView: " + convertView.toString());
        }
        return convertView;
    }
    @Override
    protected View getConvertView(ViewGroup parent) {
        View convertView=mFragment.getActivity().getLayoutInflater()
                .inflate(R.layout.gallery_item, parent, false);
        ViewHolder vh = new ViewHolder();
        vh.imageView = (ImageView) convertView
                .findViewById(R.id.gallery_item_imageView);
        vh.textView1 = (TextView) convertView
                .findViewById(R.id.gallery_item_title);
        vh.textView2 = (TextView) convertView
                .findViewById(R.id.gallery_item_name);
        convertView.setTag(R.layout.gallery_item, vh);
        //Log.i(TAG, "getConvertView: "+convertView.toString());
        return convertView;
    }
    class ViewHolder {
        int position;
        ImageView imageView;
        TextView textView1;
        TextView textView2;
    }
}
