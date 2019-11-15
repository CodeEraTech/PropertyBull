package com.doomshell.property_bull.helper;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.doomshell.property_bull.R;

/**
 * Created by Anuj on 7/6/2017.
 */

public class CustomProgressDialog extends Dialog
{
    private Dialog mpd = null;
    private LayoutInflater lyt_Inflater = null;
    public CustomProgressDialog(Context context)
    {
        super(context);
        LodingDialog1(context,"Loading...",false);
    }


    public CustomProgressDialog(Context context,String LoadingText)
    {
        super(context);
        LodingDialog1(context,LoadingText,false);

    }

    public CustomProgressDialog(Context context,String LoadingText, boolean cancelable)
    {
        super(context);
        LodingDialog1(context,LoadingText,cancelable);
    }

    public void LodingDialog1(Context context,String LoadingText, boolean cancelable)
    {
        lyt_Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view_lyt = lyt_Inflater.inflate(     R.layout.dialog, null);

        ImageView imageView = (ImageView) view_lyt.findViewById(R.id.imageView1);
        Animation a = AnimationUtils.loadAnimation(context, R.anim.rotate);
        a.setDuration(2000);
        imageView.startAnimation(a);

        a.setInterpolator(new Interpolator()
        {
            private final int frameCount = 8;

            @Override
            public float getInterpolation(float input)
            {
                return (float)Math.floor(input*frameCount)/frameCount;
            }
        });
        mpd = new Dialog(context, R.style.ThemeDialogCustom);
        mpd.setContentView(view_lyt);
        mpd.setCancelable(cancelable);
        mpd.show();
    }

    public void hide()
    {

        if (mpd != null) {
            if (mpd.isShowing())
            {
                mpd.dismiss();
            }
        }

    }
}