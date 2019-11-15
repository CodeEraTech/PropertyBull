package com.doomshell.property_bull.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.doomshell.property_bull.R;
import com.doomshell.property_bull.helper.ImageViewTouchViewPager;

import java.util.ArrayList;

/**
 * Created by Vipin on 6/27/2017.
 */


public class RecentSliderAdaptor extends PagerAdapter {
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
    Activity activity;


    public RecentSliderAdaptor(Context context, ArrayList<String> images,Activity activity) {
        this.context = context;
        this.images = images;
        this.activity=activity;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        final View itemView = layoutInflater.inflate(R.layout.slideritem, container, false);

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

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imagedata);
       // final ProgressBar rc_img_pb = (ProgressBar) itemView.findViewById(R.id.rc_img_pb);
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


             //  showImage_dialogue(activity,position);

                AlertDialog alertDialog=null;

                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                LayoutInflater inflater= activity.getLayoutInflater();
                View alerview=inflater.inflate(R.layout.item,null);
                builder.setView(alerview);
                alertDialog=builder.create();

                ImageViewTouchViewPager imageView=(ImageViewTouchViewPager)alerview.findViewById(R.id.slider);
                DialogSliderAdaptor myCustomPagerAdapter = new DialogSliderAdaptor(context, images,activity);

                imageView.setAdapter(myCustomPagerAdapter);
                imageView.setCurrentItem(position);

              //  imageView.setOffscreenPageLimit(myCustomPagerAdapter.getCount());
               // imageView.setPageMargin(15);
             //   imageView.setClipChildren(true);

                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                alertDialog.show();

             //  myCustomPagerAdapter.setPrimaryItem(container,imageView.getCurrentItem(),itemView);

               // imageView.requestDisallowInterceptTouchEvent(true);

       //         dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

            }
        });

        container.addView(itemView);

        //listening to image click


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

void showImage_dialogue(Activity activity, int position)
{

}


}
