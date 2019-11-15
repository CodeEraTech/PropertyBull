package com.doomshell.property_bull.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doomshell.property_bull.PropertyDetails;
import com.doomshell.property_bull.R;
import com.doomshell.property_bull.model.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anuj on 1/11/2017.
 */

public class Recent_Property_adapter extends RecyclerView.Adapter<Recent_Property_adapter.MyViewHolder> {

    ArrayList<String> pid_list = new ArrayList<>();
    ArrayList<String> pname_list = new ArrayList<>();
    ArrayList<String> ppostedby_list = new ArrayList<>();
    ArrayList<String> pmobile_list = new ArrayList<>();
    ArrayList<String> ptype_list = new ArrayList<>();
    ArrayList<String> pbedroom_list = new ArrayList<>();
    ArrayList<String> ptotalarea_list = new ArrayList<>();
    ArrayList<String> ptotalprice_list = new ArrayList<>();
    ArrayList<String> ppriceperunit_list = new ArrayList<>();
    ArrayList<String> pimage_list = new ArrayList<>();
    Context context;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    int lhi;
    int lwi;
    Activity activity;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
   public static Intent callIntent;
   public static int CallState=101;

    SharedPreferences recent_contentmanager;
    HashMap<String,String> recent_contnet;
    ArrayList<String> recent_contentlist=new ArrayList<>();



    public Recent_Property_adapter(Context context,
                                   ArrayList<String> pid_list,
                                   ArrayList<String> pname_list,
                                   ArrayList<String> ppostedby_list,
                                   ArrayList<String> pmobile_list,
                                   ArrayList<String> ptype_list,
                                   ArrayList<String> pbedroom_list,
                                   ArrayList<String> ptotalarea_list,
                                   ArrayList<String> ptotalprice_list,
                                   ArrayList<String> ppriceperunit_list,
                                   ArrayList<String> pimage_list,
                                   Activity activity) {
        this.context = context;
        this.pid_list = pid_list;
        this.pname_list = pname_list;
        this.ppostedby_list = ppostedby_list;
        this.pmobile_list = pmobile_list;
        this.ptype_list = ptype_list;
        this.pbedroom_list = pbedroom_list;
        this.ptotalarea_list = ptotalarea_list;
        this.ptotalprice_list = ptotalprice_list;
        this.ppriceperunit_list = ppriceperunit_list;
        this.pimage_list = pimage_list;
        this.activity = activity;

       recent_contentmanager= context.getSharedPreferences("recent_fav_position",Context.MODE_PRIVATE);
        recent_contnet=new HashMap<>();
        recent_contnet= (HashMap<String, String>) recent_contentmanager.getAll();
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_property_cardview, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);

        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {

            displayMetrics = context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi = (int) (screenHeight * 0.20);


        } else {

            displayMetrics = context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi = (int) (screenHeight * 0.60);

        }

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

      //  TextView id_text = holder.id_textview;
        TextView name_text = holder.name;
        TextView state_text = holder.state;
        TextView city_text = holder.city;
        TextView adapter_price = holder.adapter_price;
        CardView cardView = holder.cardView;

        ImageView imageView = holder.imageView;
        ImageView callbtn = holder.callbtn;
        final ImageView favstar = holder.favstar;

        cardView.getLayoutParams().height = lhi;
        cardView.requestLayout();

        if(!recent_contnet.isEmpty())
        {
            for(Map.Entry<String,String> entry : recent_contnet.entrySet()){
                Log.d("map values",entry.getKey() + " : " +
                        entry.getValue().toString());

                recent_contentlist.add(entry.getValue());
            }
        }
        if(!recent_contentlist.isEmpty()) {
            int cp = Integer.parseInt(recent_contentlist.get(position));
            if (position == cp) {
                favstar.setColorFilter(new
                        PorterDuffColorFilter(context.getResources().getColor(R.color.button_color), PorterDuff.Mode.SRC_ATOP));
            }
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*   Intent intent=new Intent(context, PropertyDetails.class);

              intent.putExtra("id_list",id);
              intent.putExtra("name_list",name);
              intent.putExtra("city_list",city);
              intent.putExtra("state_list",name);
              intent.putExtra("image_list",image);
              intent.putExtra("position",position);
*/
                PropertyDetails propertyDetails = new PropertyDetails();
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("pid_list",pid_list);
                bundle.putStringArrayList("pname_list",pname_list);
                bundle.putStringArrayList("ppostedby_list",ppostedby_list);
                bundle.putStringArrayList("pmobile_list",pmobile_list);
                bundle.putStringArrayList("ptype_list",ptype_list);
                bundle.putStringArrayList("pbedroom_list",pbedroom_list);
                bundle.putStringArrayList("ptotalarea_list",ptotalarea_list);
                bundle.putStringArrayList("ptotalprice_list",ptotalprice_list);
                bundle.putStringArrayList("ppriceperunit_list",ppriceperunit_list);
                bundle.putStringArrayList("pimage_list",pimage_list);
                bundle.putInt("currentpos",position);

                propertyDetails.setArguments(bundle);

                FragmentTransaction fragmentTransaction=((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, propertyDetails);
                fragmentTransaction.replace(R.id.frame_container,propertyDetails);
                fragmentTransaction.addToBackStack(propertyDetails.getClass().toString());
                fragmentTransaction.commit();

                // context.startActivity(intent);
            }
        });

        Glide.with(context).load(pimage_list.get(position)).into(imageView);

     //   id_text.setText("" + pid_list.get(position));
        name_text.setText("" + pname_list.get(position));
        state_text.setText("" + ptype_list.get(position));
        city_text.setText("" + ptotalarea_list.get(position));
        adapter_price.setText("\u20B9 " + ptotalprice_list.get(position));

        favstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               favstar.setColorFilter(new
                        PorterDuffColorFilter(context.getResources().getColor(R.color.button_color),PorterDuff.Mode.SRC_ATOP));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_"+"id"+position,""+pid_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_"+"name"+position,""+pname_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_"+"postedby"+position,""+ppostedby_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_"+"mobile"+position,""+pmobile_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_"+"type"+position,""+ptotalprice_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_"+"bedroom"+position,""+pbedroom_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_"+"area"+position,""+ptotalarea_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_"+"price"+position,""+ptotalprice_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_"+"unit"+position,""+ppriceperunit_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_"+"image"+position,""+pimage_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_position("recent_"+"position"+position,""+position);

                Snackbar.make(activity.getWindow().getDecorView().getRootView(),"Property added in shortlist",Snackbar.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pmobile_list.get(position) == null || pmobile_list.get(position).equals("")) {
                    Toast.makeText(context, "Number not provided", Toast.LENGTH_SHORT).show();
                } else {
                    callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + pmobile_list.get(position)));

                    int myAPI = Build.VERSION.SDK_INT;

                    if (myAPI >= 23) {
                        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
                        if (result == PackageManager.PERMISSION_GRANTED) {
                            context.startActivity(callIntent);
                        }else {
                      //      Toast.makeText(context, "permission required to make call, Please give permission", Toast.LENGTH_LONG).show();

                            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE))
                            {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},
                                        CallState);
                            }else {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},
                                        CallState);
                            }
                        }

                    }else {
                        context.startActivity(callIntent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pid_list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,state,city,adapter_price;
        ImageView imageView,callbtn,favstar;
        LinearLayout mainlayout;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
       //     this.id_textview = (TextView) itemView.findViewById(R.id.c_Propertid);
            this.name= (TextView) itemView.findViewById(R.id.c_Propertname);
            this.state= (TextView) itemView.findViewById(R.id.c_Propertstate);
            this.city = (TextView) itemView.findViewById(R.id.c_Propertcity);
            this.adapter_price = (TextView) itemView.findViewById(R.id.adapter_price);
            this.imageView=(ImageView)itemView.findViewById(R.id.search_propert_image);
            this.callbtn=(ImageView)itemView.findViewById(R.id.plist_callbtn);
            this.favstar=(ImageView)itemView.findViewById(R.id.favstar);
            this.cardView=(CardView)itemView.findViewById(R.id.filtered_propert_card);
            //this.cardView=(CardView)itemView.findViewById(R.id.article_cardview);
        }
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

}
