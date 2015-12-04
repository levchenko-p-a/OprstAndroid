package com.tyaa.OPRST;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Pavlo on 11/20/2015.
 */
public class CollectionActivity extends AppCompatActivity implements Callbacks{
    public static final String TAG = "com.tyaa.OPRST.CollectionActivity";
    public static final String ARG_SECTION_NUMBER = TAG+"section_number";
    public static final String ARG_LEVEL_NUMBER = TAG+"level_number";
    public static final String TITLE_BUTTONS_VISIBILITY = TAG+"title_buttons_visibility";
    public static final String SOCIAL_LINKS_VISIBILITY = TAG+"social_links_visibility";

    public static LinearLayout.LayoutParams parms;

    // Convert DP to PX
    public static int dpToPx(Activity activity, int dps) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }

    private static void setParameters(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth(); // ((display.getWidth()*20)/100)
        int height = width;// ((display.getHeight()*30)/100)
        parms = new LinearLayout.LayoutParams(width, height);
    }

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private PagerTitleStrip mPagerTitleStrip;
    private PagerTabStrip mPagerTabStrip;
    private ArrayList<Button> mButtons;

    private boolean mTitleButtonsVisibility;
    private boolean mSocialLinksVisibility;
    private View mTitleButtonsView;
    private View mShareView;

    @Override
    public void onBackPressed() {
        Fragment fragment=mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem());
        if(fragment!=null){
            Bundle arg=fragment.getArguments();
            if(arg!=null){
                int level=arg.getInt(ARG_LEVEL_NUMBER);
                if(level!=0){
                    onFragmentPrev(fragment);
                }else{
                    moveTaskToBack(true);
                }
            }
        }
    }

    @Override
    public void onFragmentNext(Fragment prev, Class<?> next) {
        mSectionsPagerAdapter.goToNext(prev, next);
    }

    @Override
    public void onFragmentPrev(Fragment next) {
        mSectionsPagerAdapter.goToPrev(next);
    }

    private void setLogoListeners(){
        final ImageButton mLogoImageButton=(ImageButton) findViewById(R.id.logo);
        mLogoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTitleButtonsVisibility) {
                    mTitleButtonsView.setVisibility(View.GONE);
                    mTitleButtonsVisibility = true;
                } else {
                    mTitleButtonsView.setVisibility(View.VISIBLE);
                    mTitleButtonsVisibility = false;
                }
            }
        });
        final Button mLogoTextButton =(Button) findViewById(R.id.logo_text);
        mLogoTextButton.setTextColor(Color.BLACK);
        mLogoTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSocialLinksVisibility) {
                    mShareView.setVisibility(View.GONE);
                    mSocialLinksVisibility = false;
                    mLogoTextButton.setTextColor(Color.BLACK);
                } else {
                    mShareView.setVisibility(View.VISIBLE);
                    mSocialLinksVisibility = true;
                    mLogoTextButton.setTextColor(Color.WHITE);
                }
            }
        });
    }
    public SectionsPagerAdapter getSectionsPagerAdapter() {
        return mSectionsPagerAdapter;
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }
    public void onFirstRun(){
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putBoolean(TITLE_BUTTONS_VISIBILITY, mTitleButtonsVisibility);
        savedInstanceState.putBoolean(SOCIAL_LINKS_VISIBILITY, mSocialLinksVisibility);
        super.onSaveInstanceState(savedInstanceState);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState){
        if(savedInstanceState!=null){
            mTitleButtonsVisibility=savedInstanceState.getBoolean(TITLE_BUTTONS_VISIBILITY);
            mSocialLinksVisibility=savedInstanceState.getBoolean(SOCIAL_LINKS_VISIBILITY);
            if(mTitleButtonsVisibility){
                mTitleButtonsView.setVisibility(View.GONE);
            }else{
                mTitleButtonsView.setVisibility(View.VISIBLE);
            }
            if(!mSocialLinksVisibility){
                mShareView.setVisibility(View.GONE);
            }else{
                mShareView.setVisibility(View.VISIBLE);
            }
        }else{
            onFirstRun();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        //тут мы получаем размеры экрана
        setParameters(this);

        mTitleButtonsView = findViewById(R.id.title_buttons);
        if (mTitleButtonsView == null) {
            mTitleButtonsView = findViewById(R.id.title_buttons_landscape);
        }
        mShareView = findViewById(R.id.idShareButtonLayout);
        onRestoreInstanceState(savedInstanceState);

        setLogoListeners();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.page_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //mPagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
        //mPagerTabStrip=(PagerTabStrip) findViewById(R.id.pager_tab_strip);
        NavigationOnPageChangeListener listener =
                new NavigationOnPageChangeListener(this, mViewPager, mSectionsPagerAdapter);
        mViewPager.clearOnPageChangeListeners();
        mViewPager.addOnPageChangeListener(listener);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        mViewPager.setCurrentItem(3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
