package com.example.news.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.news.App;
import com.example.news.R;
import com.example.news.SharedPref;

public class LanguageActivity extends AppCompatActivity {


    RadioGroup radioGroup;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);


        radioGroup=findViewById(R.id.radioGroup);
        btnSave=findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioGroup.getCheckedRadioButtonId()==-1)
                {
                    showToast("Please Select Language");
                    return;
                }

                if(radioGroup.getCheckedRadioButtonId()==R.id.radioEnglish)
                {

                    SharedPref.saveLocale(LanguageActivity.this,"en");
                }
                if(radioGroup.getCheckedRadioButtonId()==R.id.radioFrench)
                {

                    SharedPref.saveLocale(LanguageActivity.this,"fr");
                }

                App.changeLocale(LanguageActivity.this,SharedPref.getLocale(LanguageActivity.this));
                finish();
                startActivity(getIntent());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    private void showToast(String msg) {

        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
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
                .setMessage(R.string.langauage_app)
                .setPositiveButton(R.string.ok,null)
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
//        startActivity(new Intent(this, MainActivity.class));
    }
}