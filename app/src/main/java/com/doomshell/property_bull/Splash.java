package com.doomshell.property_bull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class Splash extends Activity {

  RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
relativeLayout=(RelativeLayout)findViewById(R.id.RelativeLayout1);
        Animation animation= AnimationUtils.loadAnimation(Splash.this,R.anim.fade_in);
        relativeLayout.setAnimation(animation);

        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                redirectview();
            }
        };
        handler.postDelayed(r, 2000);
      }
    public void redirectview() {


       // Intent GoTonext = new Intent(this, RegnLogin_Main.class);
        Intent GoTonext = new Intent(this, RegnLogin_Main.class);
        startActivity(GoTonext);
        overridePendingTransition(R.anim.fade_in, R.anim.zoom_out);
        finish();

    }



}