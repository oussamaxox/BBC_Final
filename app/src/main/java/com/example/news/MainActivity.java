package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.news.activities.LanguageActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    androidx.appcompat.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        DrawerLayout drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration=new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_fvrt)
                .setOpenableLayout(drawer)
                .build();
        NavController navController=Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.action_help)
        {
            showHelpDialog();
        }
        if(item.getItemId()==R.id.action_language)
        {
            startActivity(new Intent(this, LanguageActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.help))
                .setMessage(R.string.main_help)
                .setPositiveButton(R.string.ok,null)
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController=Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}