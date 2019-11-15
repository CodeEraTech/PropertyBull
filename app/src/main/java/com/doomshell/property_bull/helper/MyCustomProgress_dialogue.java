package com.doomshell.property_bull.helper;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;

/**
 * Created by Anuj on 2/16/2017.
 */

public class MyCustomProgress_dialogue {
     //Context context;
    static Dialog contactdialog;
    static ProgressBar bar;
    Activity activity;
    int color;
    public MyCustomProgress_dialogue(Activity activity,int color)
    {
        this.color=color;
        this.activity=activity;
    }
    public void show_dialogue()
    {
        contactdialog=new Dialog(activity);
        contactdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        contactdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ff1919")));
        bar=new ProgressBar(activity, null, android.R.attr.progressBarStyleLarge);
//bar.setProgress()
        bar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        contactdialog.setContentView(bar);
        contactdialog.setCancelable(false);
        contactdialog.show();
    }

    public void dismiss_dialogue()
    {
        contactdialog.dismiss();
    }

}
