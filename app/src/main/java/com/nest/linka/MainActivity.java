package com.nest.linka;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.nest.linka.Inbox.Inbox;
import com.nest.linka.Util.User;
import com.nest.linka.ServicesActivity;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{



    private static User user;
    private static final int  LOGIN_REQUEST=332;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User(getIntent().getStringExtra("email"));
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.inbox){
            Fragment inbox = new Inbox();
            Bundle bundle = new Bundle();
            bundle.putString("email","test");
            inbox.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_frame,inbox).commit();
        } else if(itemId==R.id.home){
            Intent intent = new Intent(this, ServicesActivity.class);
            startActivity(intent);
        }

        return true;
    }
}

