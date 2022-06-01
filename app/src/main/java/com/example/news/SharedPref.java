package com.example.news;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref
{


    public static void  saveLocale(Context context, String locale)
    {

        SharedPreferences sharedPreferences=context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sharedPreferences.edit();
        edit.putString("Locale",locale);
        edit.apply();



    }

    public static String  getLocale(Context context)
    {

        SharedPreferences sharedPreferences=context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        return sharedPreferences.getString("Locale","");

    }


}
