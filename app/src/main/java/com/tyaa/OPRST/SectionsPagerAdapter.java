package com.tyaa.OPRST;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.tyaa.OPRST.TitleFragments.Advertising.AdvertisingFragment;
import com.tyaa.OPRST.TitleFragments.Events.EventFragment;
import com.tyaa.OPRST.TitleFragments.Projects.ProjectFragment;
import com.tyaa.OPRST.TitleFragments.Superior.SuperiorFragment;
import com.tyaa.OPRST.TitleFragments.Us.UsFragment;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.zip.Inflater;

/**
 * Created by Pavlo on 12/02/2015.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    private LinkedList<LinkedList<Fragment>> fragments = null;
    private Activity mActivity;
    private FragmentManager mFragmentManager;
    private int size;
    public LinkedList<LinkedList<Fragment>> getFragments() {
        return fragments;
    }

    public void setFragments(LinkedList<LinkedList<Fragment>> fragments) {
        this.fragments = fragments;
    }
    private void initFragments(){
        fragments=new LinkedList<>();
        for(int i=0;i<size;i++){
            fragments.add(new LinkedList<Fragment>());
        }
        fragments.get(0).add(createFragment(SuperiorFragment.class, 0, 0));
        fragments.get(1).add(createFragment(AdvertisingFragment.class, 0, 1));
        fragments.get(2).add(createFragment(ProjectFragment.class, 0, 2));
        fragments.get(3).add(createFragment(EventFragment.class, 0, 3));
        fragments.get(4).add(createFragment(UsFragment.class, 0, 4));
    }
    public SectionsPagerAdapter(Activity activity, FragmentManager fm) {
        super(fm);
        mFragmentManager=fm;
        mActivity=activity;
        size=5;
        initFragments();
    }
    public Fragment createFragment(Class<?> constructor, int levelNumber,int sectionNumber){
        Bundle args = new Bundle();
        args.putInt(CollectionActivity.ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(CollectionActivity.ARG_LEVEL_NUMBER, levelNumber);
        return Fragment.instantiate(mActivity, constructor.getName(), args);
    }
    public void goToPrev(Fragment next){
        for(LinkedList<Fragment> list:fragments){
            if(list.contains(next)){
                list.removeLast();
            }
        }
        notifyDataSetChanged();
    }
    public void goToNext(Fragment prev, Class<?> next){
        for(LinkedList<Fragment> list:fragments){
            if(list.contains(prev)){
                int level=list.indexOf(prev);
                int pos=fragments.indexOf(list);
                list.add(createFragment(next, pos, level + 1));
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment=(Fragment)super.instantiateItem(container,position);
        return fragment;
    }
    public int getFragmentLevel(Object object) {
        int pos=-1;
        for(LinkedList<Fragment> list:fragments){
            if(list.contains(object)){
                pos=list.indexOf(object);
            }
        }
        return pos;
    }
    public int getFragmentPosition(Object object) {
        int level=-1;
        for(LinkedList<Fragment> list:fragments){
            if(list.contains(object)){
                level=fragments.indexOf(list);
            }
        }
        return level;
    }
    @Override
    public int getItemPosition(Object object) {
        if(fragments.contains(object)){
            return POSITION_UNCHANGED;
        }else{
            return POSITION_NONE;
        }
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        return fragments.get(position).getLast();
    }

    @Override
    public int getCount() {
        // Show total pages.
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mActivity.getString(R.string.button_nav_superior);
            case 1:
                return  mActivity.getString(R.string.button_nav_projects);
            case 2:
                return  mActivity.getString(R.string.button_nav_advertising);
            case 3:
                return  mActivity.getString(R.string.button_nav_events);
            case 4:
                return  mActivity.getString(R.string.button_nav_us);
        }
        return null;
    }
}
