package com.doomshell.property_bull.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doomshell.property_bull.R;
import com.doomshell.property_bull.ViewPagerSliderRecent;

import java.util.ArrayList;

/**
 * Created by Anuj on 1/11/2017.
 */

public class RecentProjectAdaptor extends RecyclerView.Adapter<RecentProjectAdaptor.MyViewHolder> {

    ArrayList<String> typelist=new ArrayList<>();
    ArrayList<String> arealist=new ArrayList<>();
    ArrayList<String> floorplan=new ArrayList<>();


    Context context;
    Activity activity;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    int lhi;
    int lwi;
    String areaunit;

   public RecentProjectAdaptor(Context context, ArrayList<String> typelist, ArrayList<String> arealist,String areaunit,ArrayList<String> floorplan, Activity activity) {
       this.context = context;
       this.typelist = typelist;
       this.arealist = arealist;
       this.areaunit=areaunit;
       this.floorplan=floorplan;

       this.activity = activity;
   }
    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recentprojectlistadaptor,parent,false);


        MyViewHolder myViewHolder=new MyViewHolder(view);



        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {





    holder.type.setText(typelist.get(position).toString()+" BHK");
        holder.area.setText(arealist.get(position).toString()+" "+areaunit);
        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewPagerSliderRecent propertyDetails = new ViewPagerSliderRecent();
                Bundle bundle=new Bundle();
                bundle.putString("pid_list",floorplan.get(position).toString());
                bundle.putInt("currentpos",position);

                propertyDetails.setArguments(bundle);

                FragmentTransaction fragmentTransaction=((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, propertyDetails);
                fragmentTransaction.replace(R.id.frame_container,propertyDetails);
                fragmentTransaction.addToBackStack(propertyDetails.getClass().toString());
                fragmentTransaction.commit();

            }
        });




    }

    @Override
    public int getItemCount() {
        return typelist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

     //  ImageView logo;
        TextView type,area;
        LinearLayout header;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.header = (LinearLayout) itemView.findViewById(R.id.header);
            this.type = (TextView) itemView.findViewById(R.id.type);
            this.area= (TextView) itemView.findViewById(R.id.area);



        }
    }
}
