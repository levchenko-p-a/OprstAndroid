package com.tyaa.OPRST;

import android.support.v4.app.Fragment;

/**
 * Created by Pavlo on 12/02/2015.
 */
public interface Callbacks{
    void onFragmentNext(Fragment prev, Class<?> next);
    void onFragmentPrev(Fragment next);
}
