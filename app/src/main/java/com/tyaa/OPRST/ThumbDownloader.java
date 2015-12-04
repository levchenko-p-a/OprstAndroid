package com.tyaa.OPRST;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Павел on 27.09.2015.
 */
public class ThumbDownloader <Token> extends HandlerThread {
    private static final String TAG = "ThumbDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    Handler mHandler;
    Map<Token, String> requestMap =
            Collections.synchronizedMap(new HashMap<Token, String>());
    Handler mResponseHandler;
    Listener<Token> mListener;
    public interface Listener<Token> {
        void onThumbnailDownloaded(Token token, Bitmap thumbnail);
    }
    public void setListener(Listener<Token> listener) {
        mListener = listener;
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    @SuppressWarnings("unchecked")
                    Token token = (Token)msg.obj;
                    Log.i(TAG, "Got a request for url: " + requestMap.get(token));
                    handleRequest(token);
                }
            }
        };
    }
    private void handleRequest(final Token token) {
        final String url = requestMap.get(token);
        if (url == null) {
            return;
        }
        byte[] bitmapBytes = new SiteConnector().getUrlBytes(url);
        final Bitmap bitmap = BitmapFactory
                .decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
        Log.i(TAG, "Bitmap created");
        mResponseHandler.post(new Runnable() {
                public void run() {
               if (requestMap.get(token) != url)
                    return;
               requestMap.remove(token);
               mListener.onThumbnailDownloaded(token, bitmap);
            }
            });
    }
    public ThumbDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }
    public void clearQueue() {
        mHandler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }
    public void queueThumbnail(Token token, String imageURL) {
        Log.i(TAG, "Got an URL: " + imageURL);
        requestMap.put(token, imageURL);
        mHandler.obtainMessage(MESSAGE_DOWNLOAD, token).sendToTarget();
    }

}
