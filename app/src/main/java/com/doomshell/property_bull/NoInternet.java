package com.doomshell.property_bull;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoInternet extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver=null;
    Button no_internet_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        no_internet_btn=(Button)findViewById(R.id.no_internet_btn);
        no_internet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(isNetworkAvailable())
                {
                    if(isOnline())
                    {
                        finish();
                    }
                }
            }
        };

    }
    private Boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return networkInfo!=null &&networkInfo.isConnectedOrConnecting();
    }
    private Boolean isOnline()
    {
        try
        {
            java.lang.Process process=java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = process.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public void onStart()
    {
        super.onStart();

        String name= String.valueOf(ConnectivityManager.CONNECTIVITY_ACTION);
        //  Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();
        registerReceiver(broadcastReceiver,new IntentFilter(name));
    }

    public void onStop()
    {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}
