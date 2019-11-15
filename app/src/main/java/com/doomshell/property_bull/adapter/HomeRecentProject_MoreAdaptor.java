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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.doomshell.property_bull.R;
import com.doomshell.property_bull.RecentPropertyDetails;
import com.doomshell.property_bull.helper.CustomProgressDialog;
import com.doomshell.property_bull.model.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anuj on 1/11/2017.
 */

public class HomeRecentProject_MoreAdaptor extends RecyclerView.Adapter<HomeRecentProject_MoreAdaptor.MyViewHolder> {

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
    HashMap<String, String> contnet;
    ArrayList<String> contentlist = new ArrayList<>();
    ArrayList<String> addresslist = new ArrayList<>();
    // ArrayList<String> localitylist=new ArrayList<>();
    ArrayList<String> landmarklist = new ArrayList<>();

    public static Intent callIntent;
    public static int CallState = 101;
    ArrayList<String> latitude_list = new ArrayList<>();
    ArrayList<String> longitude_list = new ArrayList<>();
    ArrayList<String> locality_list = new ArrayList<>();
    ArrayList<String> landmark_list = new ArrayList<>();

    ArrayList<String> type_list = new ArrayList<>();
    ArrayList<String> floor_list;
    ArrayList<String> bathroomlist;
    ArrayList<String> urllist;

    //Popup iteems
    Button btnSubmit;
    EditText detailName, detailMob, detailEmail;
    RadioGroup radioGroup;
    CheckBox checkboxterm;
    TextView builderName, propertyName, detailtermsandcond;
    String buildername, propName;
    String ispropertyDealer;
    ImageView closePopButton;
    RadioButton radioButton;
    PopupWindow popupWindow;


    public HomeRecentProject_MoreAdaptor(Context context,
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
                                         ArrayList<String> latitude_list,
                                         ArrayList<String> longitude_list,
                                         ArrayList<String> locality_list,
                                         ArrayList<String> landmark_list,
                                         ArrayList<String> type_list,
                                         ArrayList<String> floor_list, ArrayList<String> bathroomlist, ArrayList<String> urllist, Activity activity) {
        this.context = context;
        this.urllist = urllist;
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
        this.latitude_list = latitude_list;
        this.longitude_list = longitude_list;
        this.locality_list = locality_list;
        this.landmark_list = landmark_list;
        this.type_list = type_list;
        this.floor_list = floor_list;
        this.activity = activity;
        this.bathroomlist = bathroomlist;

        contentmanager = context.getSharedPreferences("recent_fav_position", Context.MODE_PRIVATE);
        contnet = new HashMap<>();
        contnet = (HashMap<String, String>) contentmanager.getAll();


    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_search_project_cardview, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);

        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {

            displayMetrics = context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi = (int) (screenHeight * 0.24);


        } else {

            displayMetrics = context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi = (int) (screenHeight * 0.60);

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
        final ImageView favstar = holder.favstar;
        final CustomProgressDialog pb = holder.recent_img_progress;

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
                //    Toast.makeText(context, "in adapter", Toast.LENGTH_SHORT).show();
                try {


                    RecentPropertyDetails propertyDetails = new RecentPropertyDetails();
                    Bundle bundle = new Bundle();
                    bundle.putString("pid_list", pid_list.get(position).toString());
                    bundle.putString("pname_list", pname_list.get(position).toString());
                    bundle.putString("ppostedby_list", ppostedby_list.get(position).toString());
                    bundle.putString("pmobile_list", pmobile_list.get(position).toString());
                    bundle.putString("ptype_list", ptype_list.get(position).toString());
                    bundle.putString("pbedroom_list", pbedroom_list.get(position).toString());
                    bundle.putString("ptotalarea_list", ptotalarea_list.get(position).toString());
                    bundle.putString("ptotalprice_list", ptotalprice_list.get(position).toString());
                    bundle.putString("ppriceperunit_list", ppriceperunit_list.get(position).toString());
                    bundle.putString("pimage_list", pimage_list.get(position).toString());
                    bundle.putString("type", type_list.get(position).toString());
                    bundle.putString("landmark", landmark_list.get(position).toString());
                    bundle.putString("latitude", latitude_list.get(position).toString());
                    bundle.putString("longitude", longitude_list.get(position).toString());
                    bundle.putString("url", urllist.get(position).toString());
                    bundle.putString("floor", floor_list.get(position));
                    bundle.putString("bathroom", bathroomlist.get(position));
                    bundle.putInt("currentpos", position);

                    propertyDetails.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, propertyDetails);
                    fragmentTransaction.addToBackStack(propertyDetails.getClass().toString());
                    fragmentTransaction.commit();
                } catch (Exception e) {

                }

                // context.startActivity(intent);
            }
        });

        if (pimage_list.get(position).equals("https://www.propertybull.com/pro_images/")) {
            Glide.with(context).load(R.drawable.no_image);
            holder.customdialog.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(pimage_list.get(position)).listener(new RequestListener<String, GlideDrawable>() {
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
            }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        }
        //   id_text.setText("" + pid_list.get(poss));
        name_text.setText("" + pname_list.get(position));
        state_text.setText("" + type_list.get(position));
        city_text.setText("" + ptotalarea_list.get(position) + " " + ppriceperunit_list.get(position) + " (" + pbedroom_list.get(position) + " Rooms)");
        adapter_price.setText("\u20B9 " + ptotalprice_list.get(position));
        holder.locality.setText(locality_list.get(position).toString() + " (Jaipur)");
        if (!ptotalprice_list.get(position).equalsIgnoreCase("0"))
            adapter_price.setText("\u20B9 " + ptotalprice_list.get(position));
        else {
            adapter_price.setText("Price On Request");
        }

        favstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                favstar.setColorFilter(new
                        PorterDuffColorFilter(context.getResources().getColor(R.color.button_color), PorterDuff.Mode.SRC_ATOP));
                SharedPrefManager.getInstance(context).save_recent_fav_property("id" + position, "" + pid_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_name" + position, "" + pname_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_postedby" + position, "" + ppostedby_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_mobile" + position, "" + pmobile_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_type" + position, "" + type_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_bedroom" + position, "" + pbedroom_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_area" + position, "" + ptotalarea_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_price" + position, "" + ptotalprice_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_unit" + position, "" + ppriceperunit_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_image" + position, "" + pimage_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_localitylist" + position, "" + locality_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_latitudelist" + position, "" + latitude_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_longitudelist" + position, "" + longitude_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_landmarklist" + position, "" + landmark_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_floorlist" + position, "" + floor_list.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_bathroomlist" + position, "" + bathroomlist.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("recent_urllist" + position, "" + urllist.get(position));
                SharedPrefManager.getInstance(context).save_recent_fav_property("shortlist" + position, "" + "project");
                SharedPrefManager.getInstance(context).save_recent_fav_position("position" + position, "" + position);

                Snackbar.make(activity.getWindow().getDecorView().getRootView(), "Property added in shortlist", Snackbar.LENGTH_SHORT).show();
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

        TextView id_textview, name, state, city, adapter_price;
        ImageView imageView, callbtn, favstar;
        CustomProgressDialog recent_img_progress;
        LinearLayout mainlayout;
        CardView cardView;
        TextView locality;
        RelativeLayout customdialog;


        public MyViewHolder(View itemView) {
            super(itemView);
            //   this.id_textview = (TextView) itemView.findViewById(R.id.c_Propertid);
            this.name = (TextView) itemView.findViewById(R.id.c_Propertname);
            this.state = (TextView) itemView.findViewById(R.id.c_Propertstate);
            this.city = (TextView) itemView.findViewById(R.id.c_Propertcity);
            this.adapter_price = (TextView) itemView.findViewById(R.id.adapter_price);
            this.imageView = (ImageView) itemView.findViewById(R.id.search_propert_image);
            this.callbtn = (ImageView) itemView.findViewById(R.id.plist_callbtn);
            this.favstar = (ImageView) itemView.findViewById(R.id.favstar);
            this.cardView = (CardView) itemView.findViewById(R.id.filtered_propert_card);

            this.locality = (TextView) itemView.findViewById(R.id.locality);
            //    this.recent_img_progress=(CustomProgressDialog) itemView.findViewById(R.id.recent_img_progress);
            this.customdialog = (RelativeLayout) itemView.findViewById(R.id.customdialog);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView1);
            Animation a = AnimationUtils.loadAnimation(context, R.anim.rotate);
            a.setDuration(2000);
            imageView.startAnimation(a);

            a.setInterpolator(new Interpolator() {
                private final int frameCount = 8;

                @Override
                public float getInterpolation(float input) {
                    return (float) Math.floor(input * frameCount) / frameCount;
                }
            });

            // this.localitytext=(TextView)itemView.findViewById(R.id.locality);

            //this.cardView=(CardView)itemView.findViewById(R.id.article_cardview);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
