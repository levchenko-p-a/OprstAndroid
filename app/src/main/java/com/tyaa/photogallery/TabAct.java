package com.tyaa.photogallery;

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


        tabHost = getTabHost();


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
        // указываем название и картинку
        // в нашем случае вместо картинки идет xml-файл,
        // который определяет картинку по состоянию вкладки
        tabSpec.setIndicator("ВидOS");
        tabSpec.setContent(new Intent(this, TabVideoActivivty.class));
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTabByTag("tag1");


    }


}

