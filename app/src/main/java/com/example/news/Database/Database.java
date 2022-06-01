package com.example.news.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.news.Model.News;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper

{
    private static final String DBNAME="mydb.db";
    private static final int DBVERSION=2;
    private SQLiteDatabase db;
    public Database (Context context)
    {
        super(context,DBNAME,null,DBVERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("Create table  if not exists 'News'(id TEXT,description TEXT,title TEXT,url TEXT,pubDate TEXT);");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF  EXISTS 'News'");
        onCreate(db);
    }

    /**
     * News
     * pubDate
     * description
     * title
     * url
     * @param news
     * @return
     */
    public boolean addToFvrt(News news)
    {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("description",news.getDescription());
        cv.put("pubDate",news.getPubDate());
        cv.put("title",news.getTitle());
        cv.put("url",news.getUrl());
        return  db.insert("News", null, cv)>0;
    }
   public List<News> getFvrtItems()
    {
        db=this.getReadableDatabase();
        String[] Columns = {"title","url","description","pubDate"};
        List<News> newsList=new ArrayList<>();
        Cursor cursor = db.query("News", Columns, null, null, null, null, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
         {
             News contact=new News(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
             newsList.add(contact);
         }

        return newsList;
    }

    public boolean deleteFvrt(String  title)
    {
        db = this.getWritableDatabase();
        return  db.delete("News", "title=?", new String[]{title})>0;
    }

    public boolean isInFvrt(String  title)
    {
        db = this.getWritableDatabase();
       return db.rawQuery("select * from News where title=?;",new String[]{title}).getCount()>0;

    }

}

