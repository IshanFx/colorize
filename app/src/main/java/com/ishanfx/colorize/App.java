package com.ishanfx.colorize;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by IshanFx on 12/11/2016.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Bariol.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
