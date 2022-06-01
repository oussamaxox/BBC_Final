package com.example.news.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news.Database.Database;
import com.example.news.Model.News;
import com.example.news.R;

public class DetailActivity extends AppCompatActivity {


    News news;
    TextView txtDesc,txtTitleName,txtUrl,txtPubDate;
    ImageView imgFvrt;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        database=new Database(this);
        news=(News) getIntent().getExtras().getSerializable("News");
        txtDesc=findViewById(R.id.txtDesc);
        txtTitleName=findViewById(R.id.txtTitle);
        txtUrl=findViewById(R.id.txtUrl);
        txtPubDate=findViewById(R.id.txtPubDate);
        imgFvrt=findViewById(R.id.imgFvrt);


        txtTitleName.setText(news.getTitle());
        txtUrl.setText(news.getUrl());
        txtDesc.setText(news.getDescription());
        txtPubDate.setText(news.getPubDate());


        news.setFvrt(database.isInFvrt(news.getTitle()));



        if(news.getFvrt())
        {

            imgFvrt.setImageResource(R.drawable.ic_favorite);
        }
        else
        {
            imgFvrt.setImageResource(R.drawable.ic_not_fvrt);
        }


        imgFvrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(database.isInFvrt(news.getTitle()))
                {

                    database.deleteFvrt(news.getTitle());
                    news.setFvrt(false);
                    imgFvrt.setImageResource(R.drawable.ic_not_fvrt);
                }
                else
                {
                    news.setFvrt(true);
                    database.addToFvrt(news);
                    imgFvrt.setImageResource(R.drawable.ic_favorite);
                }

            }
        });
        txtUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.action_help)
        {
            showHelpDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.help))
                .setMessage(R.string.detail_help)
                .setPositiveButton(R.string.ok,null)
                .show();
    }



}