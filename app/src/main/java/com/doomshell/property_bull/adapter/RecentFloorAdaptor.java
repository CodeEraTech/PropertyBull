package com.doomshell.property_bull.adapter;

import android.content.Context;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.doomshell.property_bull.R;
import com.doomshell.property_bull.helper.ImageViewTouchViewPager;

import java.util.ArrayList;

import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Created by Vipin on 6/27/2017.
 */


public class RecentFloorAdaptor extends PagerAdapter {
    Context context;
    ArrayList<String> images;
    LayoutInflater layoutInflater;
    //first you will need to find the dimensions of the screen
    float width;
    float height;
    float currentHeight;

    private ImageView imageView;
    ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix = new Matrix();
    AppCompatActivity appCompatActivity;

    public RecentFloorAdaptor(AppCompatActivity appCompatActivity,Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
        this.appCompatActivity=appCompatActivity;

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
    public Object instantiateItem(final ViewGroup container, int position) {
        ImageView imgDisplay;
        Button btnClose;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.slideritemdialogzoom, container,
                false);

        it.sephiroth.android.library.imagezoom.ImageViewTouch imageView=(it.sephiroth.android.library.imagezoom.ImageViewTouch) viewLayout. findViewById(R.id.imageView);
        imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        final RelativeLayout customdialog=(RelativeLayout)viewLayout.findViewById(R.id.customdialog);
        ImageView imageView2 = (ImageView) viewLayout.findViewById(R.id.imageView1);
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
       // Glide.with(context).load(images.get(position).toString()).placeholder(R.drawable.no_image).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        if (images.size() > 0) {
            if (!images.get(position).equalsIgnoreCase(""))
            {
                Glide.with(context).load(images.get(position).toString()).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        customdialog.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        customdialog.setVisibility(View.GONE);
                        return false;
                    }
                }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
            } else {
                imageView.setOnClickListener(null);
                customdialog.setVisibility(View.GONE);
                imageView.setBackground(context.getResources().getDrawable(R.drawable.no_image));
            }
        }else
        {
            imageView.setOnClickListener(null);
            imageView.setBackground(context.getResources().getDrawable(R.drawable.no_image));
            customdialog.setVisibility(View.GONE);
        }


        ((ImageViewTouchViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ImageViewTouchViewPager) container).removeView((LinearLayout) object);

    }
}