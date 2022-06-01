package com.example.news;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        changeLocale(this,SharedPref.getLocale(this));

    }
    public static void changeLocale(Context context, String languageCode) {

        if(languageCode.isEmpty())
            return;

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
