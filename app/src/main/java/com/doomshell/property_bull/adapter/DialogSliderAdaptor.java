package com.doomshell.property_bull.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.darsh.multipleimageselect.models.Image;
import com.doomshell.property_bull.R;

import java.util.ArrayList;

import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Created by Vipin on 6/27/2017.
 */


public class DialogSliderAdaptor extends PagerAdapter {
    Context context;
    ArrayList<String> images;
    LayoutInflater layoutInflater;
    //first you will need to find the dimensions of the screen
    float width;
    float height;
    float currentHeight;

    private int current_pos;

    private ImageView imageView;
    ProgressBar progress;
    ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix = new Matrix();

    Dialog contactdialog;
    ProgressBar bar;
    Activity activity;
    ArrayList<Image> imageslist;

    public DialogSliderAdaptor(Context context, ArrayList<String> images,Activity activity) {
        this.context = context;
        this.images = images;
        this.activity=activity;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    //    initilize_Dialogue();
    }




    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.slideritemdialogzoom, container, false);

      // WebView imageView = (WebView) itemView.findViewById(R.id.imageView);
        final RelativeLayout customdialog=(RelativeLayout)itemView.findViewById(R.id.customdialog);
        ImageView imageView2 = (ImageView) itemView.findViewById(R.id.imageView1);
        Animation a = AnimationUtils.loadAnimation(context, R.anim.rotate);
        a.setDuration(2000);
        imageView2.startAnimation(a);

        a.setInterpolator(new Interpolator()
        {
            private final int frameCount = 8;

            @Override
            public float getInterpolation(float input)
            {
                return (float)Math.floor(input*frameCount)/frameCount;
            }
        });

        it.sephiroth.android.library.imagezoom.ImageViewTouch imageView=(it.sephiroth.android.library.imagezoom.ImageViewTouch) itemView. findViewById(R.id.imageView);
        imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        Glide.with(context).load(images.get(position).toString()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
               // show_dialogue();
                customdialog.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                customdialog.setVisibility(View.GONE);
                return false;
            }
        }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        activity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        container.addView(itemView);

        //listening to image click


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    public void initilize_Dialogue(){
        contactdialog = new Dialog(activity);
        contactdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        contactdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ff1919")));
        bar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
//bar.setProgress()
        bar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        contactdialog.setContentView(bar);
        contactdialog.setCancelable(false);
    }

    public void show_dialogue()
    {

        contactdialog.show();
    }

    public void dismiss_dialogue()
    {
        contactdialog.dismiss();
    }

    public int getCurrent_pos() {
        return current_pos;
    }

    public void setCurrent_pos(int current_pos) {
        this.current_pos = current_pos;
    }
}
