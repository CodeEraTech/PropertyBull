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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doomshell.property_bull.PropertyDetails;
import com.doomshell.property_bull.R;
import com.doomshell.property_bull.helper.FooterLoaderAdapter;
import com.doomshell.property_bull.model.Recent_p;
import com.doomshell.property_bull.model.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.doomshell.property_bull.MainHome_Frag.isRecentCalldetais;

/**
 * Created by elanicdroid on 28/10/15.
 */
public class RecentProperty_MoreAdapter extends FooterLoaderAdapter<Recent_p> {

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
    int poss;
    Activity activity;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
    public static Intent callIntent;
    public static int CallState = 101;

    SharedPreferences recent_contentmanager;
    HashMap<String, String> recent_contnet;
    ArrayList<String> recent_contentlist = new ArrayList<>();

    public RecentProperty_MoreAdapter(Context context,
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
        super(context);
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

        recent_contentmanager = context.getSharedPreferences("recent_fav_position", Context.MODE_PRIVATE);
        recent_contnet = new HashMap<>();
        recent_contnet = (HashMap<String, String>) recent_contentmanager.getAll();

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
    }

    @Override
    public long getYourItemId(int position) {
        return mItems.get(position).getId();
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        return new DemoViewHolder(mInflater.inflate(R.layout.search_property_cardview, parent, false));
    }

    @Override
    public void bindYourViewHolder(final RecyclerView.ViewHolder holder, int position) {
        poss = position;
        if (holder instanceof DemoViewHolder) {

            DemoViewHolder viewHolder = (DemoViewHolder) holder;

            TextView name_text = viewHolder.name;
            TextView state_text = viewHolder.state;
            TextView city_text = viewHolder.city;
            TextView adapter_price = viewHolder.adapter_price;
            CardView cardView = viewHolder.cardView;

            ImageView imageView = viewHolder.imageView;
            ImageView callbtn = viewHolder.callbtn;
            final ImageView favstar = viewHolder.favstar;

            cardView.getLayoutParams().height = lhi;
            cardView.requestLayout();

            if (!recent_contnet.isEmpty()) {
                for (Map.Entry<String, String> entry : recent_contnet.entrySet()) {
                    Log.d("map values", entry.getKey() + " : " +
                            entry.getValue().toString());

                    recent_contentlist.add(entry.getValue());
                }
            }
            if (!recent_contentlist.isEmpty()) {
                int cp = Integer.parseInt(recent_contentlist.get(poss));
                if (poss == cp) {
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
                    isRecentCalldetais = true;
                    // RecentPropertyDetails propertyDetails = new RecentPropertyDetails();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("pid_list", pid_list);
                    bundle.putStringArrayList("pname_list", pname_list);
                    bundle.putStringArrayList("ppostedby_list", ppostedby_list);
                    bundle.putStringArrayList("pmobile_list", pmobile_list);
                    bundle.putStringArrayList("ptype_list", ptype_list);
                    bundle.putStringArrayList("pbedroom_list", pbedroom_list);
                    bundle.putStringArrayList("ptotalarea_list", ptotalarea_list);
                    bundle.putStringArrayList("ptotalprice_list", ptotalprice_list);
                    bundle.putStringArrayList("ppriceperunit_list", ppriceperunit_list);
                    bundle.putStringArrayList("pimage_list", pimage_list);
                    bundle.putInt("currentpos", poss);

                    propertyDetails.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, propertyDetails);
                    fragmentTransaction.replace(R.id.frame_container, propertyDetails);
                    fragmentTransaction.addToBackStack(propertyDetails.getClass().toString());
                    fragmentTransaction.commit();

                    // context.startActivity(intent);
                }
            });

            Glide.with(context).load(pimage_list.get(poss)).into(imageView);

            //   id_text.setText("" + pid_list.get(poss));
            name_text.setText("" + pname_list.get(poss));
            state_text.setText("" + ptype_list.get(poss));
            city_text.setText("" + ptotalarea_list.get(poss));
            adapter_price.setText("\u20B9 " + ptotalprice_list.get(poss));

            favstar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    favstar.setColorFilter(new
                            PorterDuffColorFilter(context.getResources().getColor(R.color.button_color), PorterDuff.Mode.SRC_ATOP));
                    SharedPrefManager.getInstance(context).save_recent_fav_property("recent_" + "id" + poss, "" + pid_list.get(poss));
                    SharedPrefManager.getInstance(context).save_recent_fav_property("recent_" + "name" + poss, "" + pname_list.get(poss));
                    SharedPrefManager.getInstance(context).save_recent_fav_property("recent_" + "postedby" + poss, "" + ppostedby_list.get(poss));
                    SharedPrefManager.getInstance(context).save_recent_fav_property("recent_" + "mobile" + poss, "" + pmobile_list.get(poss));
                    SharedPrefManager.getInstance(context).save_recent_fav_property("recent_" + "type" + poss, "" + ptotalprice_list.get(poss));
                    SharedPrefManager.getInstance(context).save_recent_fav_property("recent_" + "bedroom" + poss, "" + pbedroom_list.get(poss));
                    SharedPrefManager.getInstance(context).save_recent_fav_property("recent_" + "area" + poss, "" + ptotalarea_list.get(poss));
                    SharedPrefManager.getInstance(context).save_recent_fav_property("recent_" + "price" + poss, "" + ptotalprice_list.get(poss));
                    SharedPrefManager.getInstance(context).save_recent_fav_property("recent_" + "unit" + poss, "" + ppriceperunit_list.get(poss));
                    SharedPrefManager.getInstance(context).save_recent_fav_property("recent_" + "image" + poss, "" + pimage_list.get(poss));
                    SharedPrefManager.getInstance(context).save_recent_fav_position("recent_" + "position" + poss, "" + poss);

                    Snackbar.make(activity.getWindow().getDecorView().getRootView(), "Property added in shortlist", Snackbar.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });

            callbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pmobile_list.get(poss) == null || pmobile_list.get(poss).equals("")) {
                        Toast.makeText(context, "Number not provided", Toast.LENGTH_SHORT).show();
                    } else {
                        callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + pmobile_list.get(poss)));

                        int myAPI = Build.VERSION.SDK_INT;

                        if (myAPI >= 23) {
                            int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
                            if (result == PackageManager.PERMISSION_GRANTED) {
                                context.startActivity(callIntent);
                            } else {
                                //      Toast.makeText(context, "permission required to make call, Please give permission", Toast.LENGTH_LONG).show();

                                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)) {
                                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},
                                            CallState);
                                } else {
                                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},
                                            CallState);
                                }
                            }

                        } else {
                            context.startActivity(callIntent);
                        }
                    }
                }
            });

        }
    }

    public class DemoViewHolder extends RecyclerView.ViewHolder {

        //  @Bind(R.id.username_view)
        TextView name, state, city, adapter_price;
        ImageView imageView, callbtn, favstar;
        LinearLayout mainlayout;
        CardView cardView;


        public DemoViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            this.name = (TextView) itemView.findViewById(R.id.c_Propertname);
            this.state = (TextView) itemView.findViewById(R.id.c_Propertstate);
            this.city = (TextView) itemView.findViewById(R.id.c_Propertcity);
            this.adapter_price = (TextView) itemView.findViewById(R.id.adapter_price);
            this.imageView = (ImageView) itemView.findViewById(R.id.search_propert_image);
            this.callbtn = (ImageView) itemView.findViewById(R.id.plist_callbtn);
            this.favstar = (ImageView) itemView.findViewById(R.id.favstar);
            this.cardView = (CardView) itemView.findViewById(R.id.filtered_propert_card);
        }
    }
}
