package com.doomshell.property_bull;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doomshell.property_bull.adapter.ViewPager_adapter;
import com.doomshell.property_bull.model.SharedPrefManager;

public class RegnLogin_Main extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    TabLayout tableLayout;
    ViewPager viewPager;
    ViewPager_adapter pageadapter;
    boolean login;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_registration);

      /*  actionBar=getSupportActionBar();
        actionBar.hide();
*/
        login = SharedPrefManager.getInstance(getApplicationContext()).getProperty_Login_status("login");

        if (login) {

            Intent intent = new Intent(RegnLogin_Main.this, Home_Activity.class);
            startActivity(intent);
            finish();
        }

        tableLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.myviewpager);

//        tableLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pageadapter = new ViewPager_adapter(getSupportFragmentManager());

        pageadapter.addFragment(new RegistrationNew(), "Register");
        pageadapter.addFragment(new Login_Fragment(), "Login");

        viewPager.setAdapter(pageadapter);
        tableLayout.setupWithViewPager(viewPager);

        //    tableLayout.setOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
