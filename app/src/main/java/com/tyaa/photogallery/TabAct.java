package com.tyaa.photogallery;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.widget.TabHost;

import android.app.TabActivity;


/**
 * Created by Kitsune on 09.10.2015.
 */
public class TabAct extends TabActivity {

    TabHost tabHost;

    /** Called when the activity is first created. */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        tabHost=(TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec;

        // создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag1");
        // название вкладки
        tabSpec.setIndicator("ФотOS");
        // указываем id компонента из FrameLayout, он и станет содержимым
        tabSpec.setContent(new Intent(this, TabPhotoActivivty.class));
        // добавляем в корневой элемент
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");

        tabSpec.setIndicator("ВидOS");
        tabSpec.setContent(new Intent(this, TabVideoActivivty.class));
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTabByTag("tag1");
    }
}

