package com.tyaa.OPRST;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

/**
 * Created by Pavlo on 12/02/2015.
 */
public class NavigationOnPageChangeListener extends AbstractOnPageChangeListener {
        private Button mPrevText, mCurrText, mNextText;

        public NavigationOnPageChangeListener(Activity activity, ViewPager viewPager,
                SectionsPagerAdapter sectionsPagerAdapter) {
            super(activity, viewPager, sectionsPagerAdapter);
        }
    private void findPortraitButtons(View view){
        mPrevText = (Button) view.findViewById(R.id.prev_button);
        mCurrText = (Button) view.findViewById(R.id.cur_button);
        mNextText = (Button) view.findViewById(R.id.next_button);
    }

    private int currentItem = -1;

    @Override
    public void updateViewPortrait(int position) {
        findPortraitButtons(mActivity.findViewById(R.id.title_buttons));
        currentItem = position;
        int itemCount = mSectionsPagerAdapter.getCount();

        Integer drawable = 0;
        if (currentItem >= 1) {
            drawable = getPageBackground(currentItem - 1);
            mPrevText.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(currentItem - 1));
        } else if (currentItem == 0) {
            drawable = getPageBackground(itemCount - 1);
            mPrevText.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(itemCount-1));
        }
        mPrevText.setBackgroundResource(drawable);


        mCurrText.setBackgroundResource(getCurrentPageBackground(currentItem));
        mCurrText.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(currentItem));


        drawable = 0;
        if (currentItem + 1 < itemCount) {
            drawable = getPageBackground(currentItem + 1);
            mNextText.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(currentItem+1));
        } else if (currentItem + 1 == itemCount) {
            drawable = getPageBackground(0);
            mNextText.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(0));
        }
        mNextText.setBackgroundResource(drawable);
        if (Build.VERSION.SDK_INT > 11) {
            mPrevText.setAlpha((float) 0.8);
            mCurrText.setAlpha(1);
            mNextText.setAlpha((float) 0.8);
        }
    }
    private Button mSuperior, mProjects, mAdvertising,mEvents,mUs;
    private void findLandscapeButtons(View view){
        mSuperior = (Button) view.findViewById(R.id.button_nav_superior);
        mProjects = (Button) view.findViewById(R.id.button_nav_projects);
        mAdvertising = (Button) view.findViewById(R.id.button_nav_advertising);
        mEvents = (Button) view.findViewById(R.id.button_nav_events);
        mUs = (Button) view.findViewById(R.id.button_nav_us);
    }
    private void setLandscapeButtonsText(){
        mSuperior.setText(mContext.getString(R.string.button_nav_superior));
        mProjects.setText(mContext.getString(R.string.button_nav_projects));
        mAdvertising.setText(mContext.getString(R.string.button_nav_advertising));
        mEvents.setText(mContext.getString(R.string.button_nav_events));
        mUs.setText(mContext.getString(R.string.button_nav_us));
    }
    private void setLandscapeButtonsListeners(){
        mSuperior.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(0));
        mProjects.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(1));
        mAdvertising.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(2));
        mEvents.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(3));
        mUs.setOnClickListener(new AbstractOnPageChangeListener.ButtonOnClickListener(4));
    }
    private void setLandscapeButtonsBackground(){
        mSuperior.setBackgroundResource(getPageBackground(0));
        mProjects.setBackgroundResource(getPageBackground(1));
        mAdvertising.setBackgroundResource(getPageBackground(2));
        mEvents.setBackgroundResource(getPageBackground(3));
        mUs.setBackgroundResource(getPageBackground(4));
    }
    @Override
    public void updateViewLandscape(int position) {
        findLandscapeButtons(mActivity.findViewById(R.id.title_buttons_landscape));
        //setLandscapeButtonsText();
        setLandscapeButtonsListeners();
        setLandscapeButtonsBackground();
        switch (position){
            case 0:
                mSuperior.setBackgroundResource(getCurrentPageBackground(position));
                break;
            case 1:
                mProjects.setBackgroundResource(getCurrentPageBackground(position));
                break;
            case 2:
                mAdvertising.setBackgroundResource(getCurrentPageBackground(position));
                break;
            case 3:
                mEvents.setBackgroundResource(getCurrentPageBackground(position));
                break;
            case 4:
                mUs.setBackgroundResource(getCurrentPageBackground(position));
                break;
        }

    }
}
