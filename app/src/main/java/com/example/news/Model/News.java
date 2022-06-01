package com.example.news.Model;

import java.io.Serializable;

public class News implements Serializable {


    public News() {
    }
    public News(String title, String url,String description,String pubDate) {

        this.description=description;
        Title=title;
        Url=url;
        this.pubDate=pubDate;
    }



    String id;
    String Title;
    String Url;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate=pubDate;
    }

    String pubDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    String description;

    public Boolean getFvrt() {
        return isFvrt;
    }

    public void setFvrt(Boolean fvrt) {
        isFvrt=fvrt;
    }

    Boolean isFvrt;


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title=title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url=url;
    }





    @Override
    public String toString() {
        return getTitle();
    }
}
