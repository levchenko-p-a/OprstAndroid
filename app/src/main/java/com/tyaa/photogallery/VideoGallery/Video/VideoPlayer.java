package com.tyaa.photogallery.VideoGallery.Video;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.tyaa.photogallery.R;

import java.net.URI;

/**
 * Created by Павел on 10/11/2015.
 */

public class VideoPlayer {

    private MediaPlayer mPlayer;

    public void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void play(Context c,Uri source) {

        stop();
        mPlayer = MediaPlayer.create(c, source);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });

        mPlayer.start();
    }

    public boolean isPlaying() {
        return mPlayer != null;
    }

}