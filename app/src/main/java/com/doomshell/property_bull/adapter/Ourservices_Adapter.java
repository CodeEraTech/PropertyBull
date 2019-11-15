package com.doomshell.property_bull.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.doomshell.property_bull.R;
import com.doomshell.property_bull.Service_provider_details;

import java.util.ArrayList;

/**
 * Created by Anuj on 1/11/2017.
 */

public class Ourservices_Adapter extends RecyclerView.Adapter<Ourservices_Adapter.MyViewHolder> {

    ArrayList<String> love_imageurl=new ArrayList<>();
    ArrayList<String> love_title=new ArrayList<>();
    ArrayList<String> love_Content=new ArrayList<>();
    ArrayList<String> idlist;
    static Context context;
    Activity activity;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    int lhi;
    int lwi;

   public Ourservices_Adapter(Context context, ArrayList<String> idlist,ArrayList<String> love_imageurl, ArrayList<String> love_title, ArrayList<String> love_Content, Activity activity)
    {
        this.context=context;
        this.love_imageurl=love_imageurl;
        this.love_title=love_title;
        this.love_Content=love_Content;
        this.activity=activity;
        this.idlist=idlist;
    }
   /* public Ourservices_Adapter(Context context, ArrayList<String> idlist, ArrayList<String> love_title, Activity activity)
    {
        this.context=context;

        this.love_title=love_title;
        this.idlist=idlist;

        this.activity=activity;
    }
*/
    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.service_provider_layout,parent,false);


        MyViewHolder myViewHolder=new MyViewHolder(view);

        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {

            displayMetrics=context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi= (int) (screenHeight*0.23);
            lwi=(int) screenWidth;

        } else {

            displayMetrics=context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi= (int) (screenHeight*0.80);
            lwi=(int) screenWidth;
        }

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.cardView.getLayoutParams().height = lhi;
        holder.cardView.requestLayout();

   //  Glide.with(context).load(love_imageurl.get(position).toString()).into(holder.catimage);
        Glide.with(context).load(love_imageurl.get(position)).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                holder.customdialog.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.customdialog.setVisibility(View.GONE);
                return false;
            }
        }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.catimage);
       /* textViewtitle.setText(love_title.get(position).toString());
        ourservice_content.setText(love_Content.get(position).toString());*/
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(activity);
                dialog.setTitle("About "+love_title.get(position).toString());
                ScrollView scrollView=new ScrollView(context);
                TextView text=new TextView(context);
                text.setTextColor(Color.parseColor("#000000"));
                text.setPadding(5,5,5,5);
                text.setText(love_Content.get(position).toString());
                scrollView.addView(text);
                dialog.setContentView(scrollView);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();

            }
        });
        holder.categoryl.setText(love_title.get(position).toString());
        holder.description.setText(love_Content.get(position).toString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Service_provider_details service_provider_details=new Service_provider_details();
                Bundle bundle=new Bundle();

                bundle.putString("love_title",love_title.get(position).toString());
                bundle.putString("id",idlist.get(position).toString());
                bundle.putString("love_image",love_imageurl.get(position).toString());
                bundle.putString("love_content",love_Content.get(position).toString());

                service_provider_details.setArguments(bundle);

                FragmentTransaction fragmentTransaction=((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, service_provider_details);

                fragmentTransaction.addToBackStack(service_provider_details.getClass().toString());
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return idlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

      /*  TextView textViewtitle,ourservice_content,readmore;
        ImageView imageView;
        RelativeLayout relativeLayout,mainlayout;
        LinearLayout linearLayout;*/
        CardView cardView;
      TextView categoryl,description,more;
        ImageView catimage;
        RelativeLayout customdialog;

        public MyViewHolder(View itemView) {
            super(itemView);
           /* this.textViewtitle = (TextView) itemView.findViewById(R.id.ourservices_title);
            this.readmore = (TextView) itemView.findViewById(R.id.readmore);
            this.ourservice_content= (TextView) itemView.findViewById(R.id.ourservice_content);
            this.imageView = (ImageView) itemView.findViewById(R.id.ourservices_imageview);
            this.linearLayout=(LinearLayout) itemView.findViewById(R.id.ourservices_rel_layout);
         //   this.mainlayout=(RelativeLayout)itemView.findViewById(R.id.loveastrology_layout);*/
            this.cardView=(CardView)itemView.findViewById(R.id.article_cardview);
           categoryl=(TextView)itemView.findViewById(R.id.categoryname);
            description=(TextView)itemView.findViewById(R.id.description);
            catimage=(ImageView)itemView.findViewById(R.id.search_propert_image);
            more=(TextView)itemView.findViewById(R.id.more);
            this.customdialog=(RelativeLayout)itemView.findViewById(R.id.customdialog);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView1);
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
        }
    }
}
