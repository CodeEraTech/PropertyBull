package com.doomshell.property_bull.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.doomshell.property_bull.R;

import java.util.ArrayList;

/**
 * Created by Anuj on 1/11/2017.
 */

public class Project_Of_Month_Adapter extends RecyclerView.Adapter<Project_Of_Month_Adapter.MyViewHolder> {

    ArrayList<String> id_list=new ArrayList<>();
    ArrayList<String> name_list=new ArrayList<>();
    ArrayList<String> city_list=new ArrayList<>();
    ArrayList<String> state_list=new ArrayList<>();
    ArrayList<String> featureimage_list=new ArrayList<>();
    ArrayList<String> buildername=new ArrayList<>();
    ArrayList<String> rooms=new ArrayList<>();
    Context context;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    int lhi;
    int lwi;


    public Project_Of_Month_Adapter(Context context,ArrayList<String> id_list,ArrayList<String> name_list,ArrayList<String> city_list,ArrayList<String> state_list, ArrayList<String> featureimage_list,ArrayList<String> buildername,ArrayList<String> rooms)
    {
        this.context=context;
        this.id_list=id_list;
        this.name_list=name_list;
        this.city_list=city_list;
        this.state_list=state_list;
        this.featureimage_list=featureimage_list;
        this.buildername=buildername;
        this.rooms=rooms;

    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.project_of_month_layout,parent,false);


        MyViewHolder myViewHolder=new MyViewHolder(view);

        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {

            displayMetrics=context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi= (int) (screenHeight*0.20);
            lwi= (int) (screenWidth*0.50);


        } else {

            displayMetrics=context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi= (int) (screenHeight*0.55);
            lwi= (int) (screenWidth*0.50);


        }

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        TextView monthp_name,monthp_id,monthp_state,monthp_city;
        ImageView monthp_featureimg;

        monthp_name=holder.monthp_name;
        monthp_id=holder.monthp_id;
        monthp_state=holder.monthp_state;
        monthp_city=holder.monthp_city;
        monthp_featureimg=holder.monthp_featureimg;

        RelativeLayout mainLayout=holder.mainlayout;
        //CardView cardView=holder.cardView;

        monthp_name.setSelected(true);

            monthp_featureimg.getLayoutParams().height = lhi;
        monthp_featureimg.getLayoutParams().width = lwi;
            monthp_featureimg.requestLayout();



       Glide.with(context).load(featureimage_list.get(position)).placeholder(R.drawable.no_image).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(monthp_featureimg);

        monthp_id.setText("Added by "+buildername.get(position).toString());
        monthp_name.setText(""+name_list.get(position).toString().trim()+" ("+id_list.get(position).toString()+")");
        monthp_state.setText(rooms.get(position).toString().trim()+" "+"BHK Apartment");
        monthp_city.setText(city_list.get(position).toString().trim()+" "+state_list.get(position).toString().trim());
    }

    @Override
    public int getItemCount() {
        return id_list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView monthp_name,monthp_id,monthp_state,monthp_city;
        ImageView monthp_featureimg;
        RelativeLayout relativeLayout,mainlayout;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.monthp_name = (TextView) itemView.findViewById(R.id.monthp_name);
            this.monthp_id = (TextView) itemView.findViewById(R.id.provider);
            this.monthp_state = (TextView) itemView.findViewById(R.id.monthp_state);
            this.monthp_city = (TextView) itemView.findViewById(R.id.monthp_city);
            monthp_featureimg=(ImageView)itemView.findViewById(R.id.monthp_featureimg);
           // this.relativeLayout=(RelativeLayout)itemView.findViewById(R.id.);
       //     this.mainlayout=(RelativeLayout)itemView.findViewById(R.id.property_gallery_mainlayout);
            //this.cardView=(CardView)itemView.findViewById(R.id.article_cardview);
        }
    }
}
