package com.doomshell.property_bull.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Function {


    @SuppressLint("RestrictedApi")
    public static void setToolBar(final Context context, Toolbar toolbar, String title, boolean back_button) {
        AppCompatActivity activity = (AppCompatActivity) context;
        activity.setSupportActionBar(toolbar);
        activity.setTitle(title);

        if (back_button) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AppCompatActivity) context).onBackPressed();
                }
            });
        }
    }
}
