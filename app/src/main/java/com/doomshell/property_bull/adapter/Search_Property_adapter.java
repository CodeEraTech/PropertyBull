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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.doomshell.property_bull.PropertyDetails;
import com.doomshell.property_bull.R;
import com.doomshell.property_bull.model.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anuj on 1/11/2017.
 */

public class Search_Property_adapter extends RecyclerView.Adapter<Search_Property_adapter.MyViewHolder> {

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
    static Context context;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    int lhi;
    int lwi;
    Activity activity;
    SharedPreferences contentmanager;
    HashMap<String,String> contnet;
    ArrayList<String> contentlist=new ArrayList<>();
    ArrayList<String> addresslist=new ArrayList<>();
    ArrayList<String> localitylist=new ArrayList<>();
    ArrayList<String> landmarklist=new ArrayList<>();
    ArrayList<String> descriptionlist=new ArrayList<>();
    ArrayList<String> depositlist;
    ArrayList<String> flooringlist;
    ArrayList<String> floorlist;
    ArrayList<String> bathlist;
    ArrayList<String> p_floorlist;
    ArrayList<String> urllist;

   public static Intent callIntent;
   public static int CallState=101;


    public Search_Property_adapter(Context context,
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
                                   ArrayList<String> addresslist,
                                   ArrayList<String> localitylist,
                                   ArrayList<String> landmarklist,
                                   ArrayList<String> descriptionlist,
                                   ArrayList<String> depositlist,
                                   ArrayList<String> flooringlist,
                                   ArrayList<String> floorlist,
                                   ArrayList<String> bathlist,
                                   ArrayList<String> p_floorlist,
                                   ArrayList<String> urllist,
                                   Activity activity) {
        this.context = context;
        this.urllist=urllist;
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
        this.addresslist = addresslist;
        this.localitylist=localitylist;
        this.landmarklist=landmarklist;
        this.descriptionlist=descriptionlist;
        this.depositlist=depositlist;
        this.bathlist=bathlist;
        this.flooringlist=flooringlist;
        this.floorlist=floorlist;
        this.p_floorlist=p_floorlist;
        this.activity = activity;

        contentmanager= context.getSharedPreferences("fav_position",Context.MODE_PRIVATE);
        contnet=new HashMap<>();
        contnet= (HashMap<String, String>) contentmanager.getAll();


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

            lhi = (int) (screenHeight * 0.25);


        } else {

            displayMetrics = context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi = (int) (screenHeight * 0.65);

        }


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //  TextView id_text = holder.id_textview;
        TextView name_text = holder.name;
        TextView state_text = holder.state;
        TextView city_text = holder.city;
        TextView adapter_price = holder.adapter_price;
        CardView cardView = holder.cardView;

        final ImageView imageView = holder.imageView;
        ImageView callbtn = holder.callbtn;
       // final ProgressBar pb = holder.pb;
        final ImageView favstar = holder.favstar;

        cardView.getLayoutParams().height = lhi;
        cardView.requestLayout();

        if (!contnet.isEmpty()) {
            for (Map.Entry<String, String> entry : contnet.entrySet()) {
                Log.d("map values", entry.getKey() + " : " +
                        entry.getValue().toString());

                contentlist.add(entry.getValue());
            }
        }
        if (!contentlist.isEmpty()) {
            try {
                int cp = Integer.parseInt(contentlist.get(position));
                if (position == cp) {
                    favstar.setColorFilter(new
                            PorterDuffColorFilter(context.getResources().getColor(R.color.button_color), PorterDuff.Mode.SRC_ATOP));
                }
            } catch (Exception e) {

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
                Bundle bundle = new Bundle();
                bundle.putString("pid_list", pid_list.get(position));
                bundle.putString("pname_list", pname_list.get(position));
                bundle.putString("ppostedby_list", ppostedby_list.get(position));
                bundle.putString("pmobile_list", pmobile_list.get(position));
                bundle.putString("ptype_list", ptype_list.get(position));
                bundle.putString("pbedroom_list", pbedroom_list.get(position));
                bundle.putString("ptotalarea_list", ptotalarea_list.get(position));
                bundle.putString("ptotalprice_list", ptotalprice_list.get(position));
                bundle.putString("ppriceperunit_list", ppriceperunit_list.get(position));
                bundle.putString("pimage_list", pimage_list.get(position));
                bundle.putString("localitylist", localitylist.get(position));
                bundle.putString("addresslist", addresslist.get(position));
                bundle.putString("landmarklist", landmarklist.get(position));
                bundle.putString("descriptionlist", descriptionlist.get(position));
                bundle.putString("depositlist",depositlist.get(position));
                bundle.putString("p_floorlist",p_floorlist.get(position));
                bundle.putString("floorlist",floorlist.get(position));
                bundle.putString("flooringlist",flooringlist.get(position));
                bundle.putString("bathlist",bathlist.get(position));
                bundle.putString("url",urllist.get(position));
                bundle.putInt("currentpos", position);

                propertyDetails.setArguments(bundle);

                FragmentTransaction fragmentTransaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, propertyDetails);
                fragmentTransaction.replace(R.id.frame_container, propertyDetails);
                fragmentTransaction.addToBackStack(propertyDetails.getClass().toString());
                fragmentTransaction.commit();

                // context.startActivity(intent);
            }
        });

        String split[] = pimage_list.get(position).split("\\,");
        if (!split[split.length - 1].equalsIgnoreCase("")){
            Glide.with(context).load(split[split.length - 1]).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    // pb.setVisibility(View.GONE);
                    holder.customdialog.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    holder.customdialog.setVisibility(View.GONE);
                    return false;
                }
            }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }
    else {
            holder.customdialog.setVisibility(View.GONE);
        }

     //   id_text.setText("" + pid_list.get(position));
        holder.c_Propertid.setText("ID : "+pid_list.get(position).toString());
        name_text.setText("" + pname_list.get(position).toUpperCase());
        holder.locality.setText(localitylist.get(position).toString());
        state_text.setText("" + ptype_list.get(position));


        if(ptype_list.get(position).equalsIgnoreCase("Residential Plot")||ptype_list.get(position).equalsIgnoreCase("Commercial Land")||
        ptype_list.get(position).equalsIgnoreCase("Commercial Office Space")||ptype_list.get(position).equalsIgnoreCase("Commercial Shop")
        ||ptype_list.get(position).equalsIgnoreCase("Residential Villa")
        ||ptype_list.get(position).equalsIgnoreCase("Commercial Showroom"))
        {
            city_text.setText("" + ptotalarea_list.get(position) + " " + ppriceperunit_list.get(position).toString());
        }
        else {

            if (pbedroom_list.get(position).equals("0") || pbedroom_list.get(position).equals("-")) {
                city_text.setText("" + ptotalarea_list.get(position) + " " + ppriceperunit_list.get(position).toString());
            } else {
                city_text.setText("" + ptotalarea_list.get(position) + " " + ppriceperunit_list.get(position).toString()
                        + " (" + pbedroom_list.get(position) + " bhk)");
            }

        }

        if(ptotalprice_list.get(position).equalsIgnoreCase("0")) {
            adapter_price.setText("Price On Request");
        }
        else
        {
            adapter_price.setText("\u20B9 " + ptotalprice_list.get(position));
        }

        favstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               favstar.setColorFilter(new
                        PorterDuffColorFilter(context.getResources().getColor(R.color.button_color),PorterDuff.Mode.SRC_ATOP));
                SharedPrefManager.getInstance(context).savefav_property("id"+position,""+pid_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("name"+position,""+pname_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("postedby"+position,""+ppostedby_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("mobile"+position,""+pmobile_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("type"+position,""+ptype_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("bedroom"+position,""+pbedroom_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("area"+position,""+ptotalarea_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("price"+position,""+ptotalprice_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("unit"+position,""+ppriceperunit_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("image"+position,""+pimage_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("localitylist"+position,""+localitylist.get(position));
                SharedPrefManager.getInstance(context).savefav_property("addresslist"+position,""+addresslist.get(position));
                SharedPrefManager.getInstance(context).savefav_property("landmarklist"+position,""+landmarklist.get(position));
                SharedPrefManager.getInstance(context).savefav_property("descriptionlist"+position,""+descriptionlist.get(position));
                SharedPrefManager.getInstance(context).savefav_property("depositlist"+position,""+depositlist.get(position));
                SharedPrefManager.getInstance(context).savefav_property("p_floorlist"+position,""+p_floorlist.get(position));
                SharedPrefManager.getInstance(context).savefav_property("floorlist"+position,""+floorlist.get(position));
                SharedPrefManager.getInstance(context).savefav_property("flooringlist"+position,""+flooringlist.get(position));
                SharedPrefManager.getInstance(context).savefav_property("bathlist"+position,""+bathlist.get(position));
                SharedPrefManager.getInstance(context).savefav_property("urllist"+position,""+urllist.get(position));
                SharedPrefManager.getInstance(context).savefav_property("shortlist"+position,""+"property");
                SharedPrefManager.getInstance(context).savefav_position("position"+position,""+position);

                Snackbar.make(activity.getWindow().getDecorView().getRootView(),"Property added in shortlist",Snackbar.LENGTH_SHORT).show();
                notifyDataSetChanged();
               // notifyAll();

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
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

        TextView id_textview,name,state,city,adapter_price;
        ImageView imageView,callbtn,favstar;
        LinearLayout mainlayout;
        CardView cardView;
        TextView locality,c_Propertid;
       // ProgressBar pb;
        RelativeLayout customdialog;

        public MyViewHolder(View itemView) {
            super(itemView);
         //   this.id_textview = (TextView) itemView.findViewById(R.id.c_Propertid);
            this.name= (TextView) itemView.findViewById(R.id.c_Propertname);
            this.state= (TextView) itemView.findViewById(R.id.c_Propertstate);
            this.city = (TextView) itemView.findViewById(R.id.c_Propertcity);
            this.adapter_price = (TextView) itemView.findViewById(R.id.adapter_price);
            this.imageView=(ImageView)itemView.findViewById(R.id.search_propert_image);
            this.callbtn=(ImageView)itemView.findViewById(R.id.plist_callbtn);
            this.favstar=(ImageView)itemView.findViewById(R.id.favstar);
            this.cardView=(CardView)itemView.findViewById(R.id.filtered_propert_card);
            this.locality=(TextView) itemView.findViewById(R.id.locality);
            this.c_Propertid=(TextView) itemView.findViewById(R.id.c_Propertid);
           // this.pb=(ProgressBar) itemView.findViewById(R.id.recent_img_progress);
            //this.cardView=(CardView)itemView.findViewById(R.id.article_cardview);
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


    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
