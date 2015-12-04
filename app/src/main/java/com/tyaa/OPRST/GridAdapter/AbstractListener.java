package com.tyaa.OPRST.GridAdapter;

import android.view.View;

/**
 * Created by Pavlo on 10/20/2015.
 */
public abstract class AbstractListener<T> implements View.OnClickListener{
    public abstract void onClick(View arg0);
    public abstract void setMsg(T msg);
}
