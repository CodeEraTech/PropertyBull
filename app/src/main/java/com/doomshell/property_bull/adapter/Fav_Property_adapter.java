package com.doomshell.property_bull.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.doomshell.property_bull.PropertyDetails;
import com.doomshell.property_bull.R;
import com.doomshell.property_bull.RecentPropertyDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anuj on 1/11/2017.
 */

public class Fav_Property_adapter extends RecyclerView.Adapter<Fav_Property_adapter.MyViewHolder> {

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
    ArrayList<String> addresslist;
    ArrayList<String> landmarklist;
    ArrayList<String> locality;
    ArrayList<String> shortlisttype;
    ArrayList<String> descriptionlist;

    ArrayList<String> depositlist;
    ArrayList<String> p_floorlist;
    ArrayList<String> floorlist;
    ArrayList<String> flooringlist;
    ArrayList<String> bathlist;
    ArrayList<String> recent_bathroom;
    ArrayList<String> recent_floor;
    Context context;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    ArrayList<String> urllist;
    int lhi;
    int lwi;
    Activity activity;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
    SharedPreferences fav_prefer;
    SharedPreferences recent_fav;
    SharedPreferences fav_position;
    SharedPreferences recent_fav_position;
   public static Intent callIntent;
   public static int CallState=101;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public Fav_Property_adapter(Context context,
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
                                ArrayList<String> locality,
                                ArrayList<String> addresslist,
                                ArrayList<String> landmarklist,
                                ArrayList<String> shortlisttype,
                                ArrayList<String> descriptionlist,
                                ArrayList<String> depositlist,
                                ArrayList<String> p_floorlist,
                                ArrayList<String> floorlist,
                                ArrayList<String> flooringlist,
                                ArrayList<String> bathlist,
                                ArrayList<String> urllist,

                                Activity activity) {
        this.depositlist=depositlist;
        this.p_floorlist=p_floorlist;
        this.flooringlist=flooringlist;
        this.floorlist=floorlist;
        this.bathlist=bathlist;
        this.urllist=urllist;


        this.shortlisttype=shortlisttype;
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
        this.locality=locality;
        this.addresslist=addresslist;
        this.landmarklist=landmarklist;
        this.descriptionlist=descriptionlist;
        this.activity = activity;
        fav_prefer =context.getSharedPreferences("fav",Context.MODE_PRIVATE);
        recent_fav=context.getSharedPreferences("recent_fav",Context.MODE_PRIVATE);
        fav_position=context.getSharedPreferences("fav_position",Context.MODE_PRIVATE);
        recent_fav_position=context.getSharedPreferences("recent_fav_position",Context.MODE_PRIVATE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_property_layout, parent, false);


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

            lhi = (int) (screenHeight * 0.60);

        }

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        TextView id_text = holder.id_textview;
        TextView name_text = holder.name;
        TextView state_text = holder.state;
        TextView city_text = holder.city;
        TextView adapter_price = holder.adapter_price;
        final ImageView delete_fav_item = holder.delete_fav_item;
        CardView cardView = holder.cardView;
        SwipeRevealLayout swipe_layout=holder.swipe_layout;

        ImageView imageView = holder.imageView;
        ImageView callbtn = holder.callbtn;
        final ImageView favstar = holder.favstar;

        cardView.getLayoutParams().height = lhi;
        cardView.requestLayout();

    //   viewBinderHelper.bind(swipe_layout,""+delete_fav_item.getId());

        delete_fav_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(activity);
                alertbBuilder.setMessage("Are you sure you want to delete Property!");

                alertbBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }

                });
                alertbBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String key=findKey(fav_prefer,pid_list.get(position));
                        String recent_key=findKey(recent_fav,pid_list.get(position));

                        if(fav_prefer.contains(key))
                        {
                            String p=key.substring(2,key.length());
                            int pos=Integer.parseInt(p);

                            SharedPreferences.Editor editor = fav_prefer.edit();
                            SharedPreferences.Editor editor2 = fav_position.edit();
                            editor.remove("id"+pos);
                            editor.remove("urllist"+pos);
                            editor.remove("name"+pos);
                            editor.remove("postedby"+pos);
                            editor.remove("mobile"+pos);
                            editor.remove("type"+pos);
                            editor.remove("bedroom"+pos);
                            editor.remove("area"+pos);
                            editor.remove("price"+pos);
                            editor.remove("unit"+pos);
                            editor.remove("image"+pos);
                            editor.remove("localitylist"+pos);
                            editor.remove("addresslist"+pos);
                            editor.remove("landmarklist"+pos);
                            editor.remove("descriptionlist"+pos);
                            editor.remove("bathlist"+pos);
                            editor.remove("flooringlist"+pos);
                            editor.remove("floorlist"+pos);
                            editor.remove("p_floorlist"+pos);
                            editor.remove("depositlist"+pos);
                            editor.remove("shortlist"+pos);
                            editor2.remove("position"+pos);

                            editor.apply();
                            editor2.apply();
                            urllist.remove(position);
                            bathlist.remove(position);
                            flooringlist.remove(position);
                            floorlist.remove(position);
                            p_floorlist.remove(position);
                            depositlist.remove(position);

                            pid_list.remove(position);
                            pname_list.remove(position);
                            ppostedby_list.remove(position);
                            pmobile_list.remove(position);
                            ptype_list.remove(position);
                            pbedroom_list.remove(position);
                            ptotalarea_list.remove(position);
                            ptotalprice_list.remove(position);
                            ppriceperunit_list.remove(position);
                            addresslist.remove(position);
                            landmarklist.remove(position);
                            locality.remove(position);
                            pimage_list.remove(position);
                            shortlisttype.remove(position);
                            notifyItemRemoved(position);
                            descriptionlist.remove(position);
                        }
                        if(recent_fav.contains(recent_key))
                        {
                            String p=recent_key.substring(2,recent_key.length());
                            int pos=Integer.parseInt(p);

                            SharedPreferences.Editor editor = recent_fav.edit();
                            SharedPreferences.Editor editor2= recent_fav_position.edit();
                            editor.remove("id"+pos);
                            editor.remove("recent_"+"name"+pos);
                            editor.remove("recent_"+"urllist"+pos);
                            editor.remove("recent_"+"postedby"+pos);
                            editor.remove("recent_"+"mobile"+pos);
                            editor.remove("recent_"+"type"+pos);
                            editor.remove("recent_"+"bedroom"+pos);
                            editor.remove("recent_"+"area"+pos);
                            editor.remove("recent_"+"price"+pos);
                            editor.remove("recent_"+"unit"+pos);
                            editor.remove("recent_"+"image"+pos);
                            editor.remove("recent_"+"localitylist"+pos);
                            //editor.remove("recent_"+"addresslist"+pos);
                            editor.remove("recent_"+"latitudelist"+pos);
                            editor.remove("recent_"+"longitudelist"+pos);
                            editor.remove("recent_"+"landmarklist"+pos);
                            editor.remove("recent_"+"bathroomlist"+pos);
                            editor.remove("recent_"+"floorlist"+pos);
                            editor.remove("shortlist"+pos);
                            editor2.remove("position"+pos);
                            editor.apply();
                            editor2.apply();

                            pid_list.remove(position);
                            urllist.remove(position);
                            pname_list.remove(position);
                            ppostedby_list.remove(position);
                            pmobile_list.remove(position);
                            ptype_list.remove(position);
                            pbedroom_list.remove(position);
                            ptotalarea_list.remove(position);
                            ptotalprice_list.remove(position);
                            ppriceperunit_list.remove(position);
                            pimage_list.remove(position);
                            addresslist.remove(position);
                            landmarklist.remove(position);
                            locality.remove(position);

                            floorlist.remove(position);
                            bathlist.remove(position);

                            shortlisttype.remove(position);
                            notifyItemRemoved(position);
                        }

                    }
                });
                alertbBuilder.show();

            }
        });

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
                String recent_key= shortlisttype.get(position);
                if(recent_key.equalsIgnoreCase("property")) {

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
                    bundle.putString("localitylist", locality.get(position));
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
                    //    fragmentTransaction.replace(R.id.frame_container,propertyDetails);
                    fragmentTransaction.addToBackStack(propertyDetails.getClass().toString());
                    fragmentTransaction.commit();
                }
                else
                {
                    RecentPropertyDetails propertyDetails = new RecentPropertyDetails();
                    Bundle bundle=new Bundle();
                    bundle.putString("pid_list",pid_list.get(position).toString());
                    bundle.putString("pname_list",pname_list.get(position).toString());
                    bundle.putString("ppostedby_list",ppostedby_list.get(position).toString());
                    bundle.putString("pmobile_list",pmobile_list.get(position).toString());
                    bundle.putString("type_list",ptype_list.get(position).toString());
                    bundle.putString("pbedroom_list",pbedroom_list.get(position).toString());
                    bundle.putString("ptotalarea_list",ptotalarea_list.get(position).toString());
                    bundle.putString("ptotalprice_list",ptotalprice_list.get(position).toString());
                    bundle.putString("ppriceperunit_list",ppriceperunit_list.get(position).toString());
                    bundle.putString("pimage_list",pimage_list.get(position).toString());
                    bundle.putString("type",ptype_list.get(position).toString());
                    bundle.putString("landmark",landmarklist.get(position).toString());
                    bundle.putString("url",urllist.get(position));
                    bundle.putString("floor",floorlist.get(position));
                    bundle.putString("bathroom",bathlist.get(position));
                    if(!addresslist.get(position).isEmpty())
                    {
                        String array[]=addresslist.get(position).split(" ");
                        bundle.putString("latitude",array[0]);
                        bundle.putString("longitude",array[1]);
                    }

                    bundle.putInt("currentpos",position);

                    propertyDetails.setArguments(bundle);

                    FragmentTransaction fragmentTransaction=((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, propertyDetails);

                    fragmentTransaction.addToBackStack(propertyDetails.getClass().toString());
                    fragmentTransaction.commit();

                }

                // context.startActivity(intent);
            }
        });








        Glide.with(context).load(pimage_list.get(position)).placeholder(R.drawable.no_image).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

        //   id_text.setText("" + pid_list.get(position));
        name_text.setText("" + pname_list.get(position));
        holder.locality.setText(""+locality.get(position));
        state_text.setText("" + ptype_list.get(position));
        city_text.setText("" + ptotalarea_list.get(position)+" "+ppriceperunit_list.get(position).toString());
        if(ptotalprice_list.get(position).equalsIgnoreCase("0")) {
            adapter_price.setText("Price On Request");
        }
        else
        {
            adapter_price.setText("\u20B9 " + ptotalprice_list.get(position));
        }



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

        TextView id_textview,name,state,city,adapter_price,locality;
        ImageView imageView,callbtn,favstar,delete_fav_item;
        LinearLayout mainlayout;
        CardView cardView;
        SwipeRevealLayout swipe_layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.delete_fav_item=(ImageView)itemView.findViewById(R.id.delete_fav_item);

            this.swipe_layout=(SwipeRevealLayout)itemView.findViewById(R.id.swipe_layout);

            this.name= (TextView) itemView.findViewById(R.id.c_Propertname);
            this.state= (TextView) itemView.findViewById(R.id.c_Propertstate);
            this.city = (TextView) itemView.findViewById(R.id.c_Propertcity);
            this.adapter_price = (TextView) itemView.findViewById(R.id.adapter_price);
            this.imageView=(ImageView)itemView.findViewById(R.id.search_propert_image);
            this.callbtn=(ImageView)itemView.findViewById(R.id.plist_callbtn);
            this.favstar=(ImageView)itemView.findViewById(R.id.favstar);
            this.cardView=(CardView)itemView.findViewById(R.id.filtered_propert_card);
            this.locality=(TextView) itemView.findViewById(R.id.locality);
            //this.cardView=(CardView)itemView.findViewById(R.id.article_cardview);
        }
    }

    String findKey(SharedPreferences sharedPreferences, String value) {
       HashMap<String,String> hm= (HashMap) sharedPreferences.getAll();

        for(Map.Entry<String,String> entry : hm.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null; // not found
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }

}
