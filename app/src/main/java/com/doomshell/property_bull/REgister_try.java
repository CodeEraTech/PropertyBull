package com.doomshell.property_bull;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doomshell.property_bull.adapter.ViewPager_adapter;
import com.doomshell.property_bull.model.SharedPrefManager;

public class REgister_try extends AppCompatActivity {

    TabLayout tableLayout;
    ViewPager viewPager;
    ViewPager_adapter pageadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        boolean login = SharedPrefManager.getInstance(getApplicationContext()).getProperty_Login_status("login");

        if (login) {

            Intent intent = new Intent(REgister_try.this, Home_Activity.class);
            startActivity(intent);
            finish();
        }

        tableLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.myviewpager);

        pageadapter = new ViewPager_adapter(getSupportFragmentManager());

        pageadapter.addFragment(new RegistrationNew(), "Register");
        pageadapter.addFragment(new Login_Fragment(), "Login");

        viewPager.setAdapter(pageadapter);
        tableLayout.setupWithViewPager(viewPager);

    }
}
