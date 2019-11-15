package com.doomshell.property_bull.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.doomshell.property_bull.PropertyDetails;
import com.doomshell.property_bull.R;
import com.doomshell.property_bull.helper.Serverapi;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by Anuj on 1/11/2017.
 */

public class My_Property_adapter extends RecyclerView.Adapter<My_Property_adapter.MyViewHolder> {

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
    String Pid;
    public static Intent callIntent;
    public static int CallState = 101;
    ArrayList<String> addresslist = new ArrayList<>();
    ArrayList<String> localitylist = new ArrayList<>();
    ArrayList<String> landmarklist = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();
    ArrayList<String> pdescription_list;
    ArrayList<String> depositlist;
    ArrayList<String> flooringlist, floorlist, bathlist, p_floorlist, urllist;


    public My_Property_adapter(Context context,
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
                               ArrayList<String> status,
                               ArrayList<String> pdescription_list,
                               ArrayList<String> depositlist,
                               ArrayList<String> flooringlist,
                               ArrayList<String> floorlist,
                               ArrayList<String> bathlist,
                               ArrayList<String> p_floorlist,
                               ArrayList<String> urllist,
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
        this.addresslist = addresslist;
        this.localitylist = localitylist;
        this.landmarklist = landmarklist;
        this.status = status;
        this.pdescription_list = pdescription_list;
        this.depositlist = depositlist;
        this.bathlist = bathlist;
        this.flooringlist = flooringlist;
        this.floorlist = floorlist;
        this.p_floorlist = p_floorlist;
        this.urllist = urllist;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mypropertyadaptor, parent, false);


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
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        TextView id_text = holder.id_textview;
        TextView name_text = holder.name;
        TextView state_text = holder.state;
        TextView city_text = holder.city;
        TextView adapter_price = holder.adapter_price;
        CardView cardView = holder.cardView;

        ImageView imageView = holder.imageView;
        ImageView callbtn = holder.callbtn;
        final ImageView delete = holder.delete;

        cardView.getLayoutParams().height = lhi;
        cardView.requestLayout();


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("pidlistvalue", pid_list.get(position));
                remove_item_server(position);

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
                bundle.putString("depositlist", depositlist.get(position));
                bundle.putString("p_floorlist", p_floorlist.get(position));
                bundle.putString("floorlist", floorlist.get(position));
                bundle.putString("flooringlist", flooringlist.get(position));
                bundle.putString("bathlist", bathlist.get(position));
                bundle.putString("url", urllist.get(position));
                bundle.putInt("currentpos", position);
                //String desc="" + pbedroom_list.get(position) + " BHK " + "\u25CF" + " " + pname_list.get(position) + "\u25CF" + " " + ptype_list.get(position);
                bundle.putString("descriptionlist", pdescription_list.get(position));

                propertyDetails.setArguments(bundle);

                FragmentManager fragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, propertyDetails);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                // context.startActivity(intent);
            }
        });
        String split[] = pimage_list.get(position).split(",");
     //   Glide.with(context).load(split[0]).placeholder(R.drawable.no_image).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        String img = split[0];
        if (!TextUtils.isEmpty(img)) {
            Picasso.get().load(img).into(imageView, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {

                }
            });

        }else {
            Picasso.get().load(R.drawable.no_image).into(imageView);
        }
        // id_text.setText("" + pid_list.get(position));
        // Glide.with(context).load(pimage_list.get(position)).placeholder(R.drawable.no_image).into(imageView);

        id_text.setText("ID : " + pid_list.get(position));
        name_text.setVisibility(View.VISIBLE);
        if (status.get(position).equalsIgnoreCase("N")) {
            holder.statusimage.setVisibility(View.VISIBLE);
            //    cardView.setOnClickListener(null);
        } else {
            holder.statusimage.setVisibility(View.GONE);
        }
        name_text.setText("" + pname_list.get(position));
        holder.locality.setText(localitylist.get(position).toString());

        state_text.setText("" + ptype_list.get(position));
        city_text.setText("" + ptotalarea_list.get(position) + " " + ppriceperunit_list.get(position).toString());
        if (ptotalprice_list.get(position).equalsIgnoreCase("0")) {
            adapter_price.setText("Price On Request");
        } else {
            adapter_price.setText("\u20B9 " + ptotalprice_list.get(position));
        }
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("pidlistvalue",pid_list.get(position));
//
//                remove_item_server(position);
//
//
//            }
//        });

     /*   favstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               favstar.setColorFilter(new
                        PorterDuffColorFilter(context.getResources().getColor(R.color.button_color),PorterDuff.Mode.SRC_ATOP));
                SharedPrefManager.getInstance(context).savefav_property("id"+position,""+pid_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("name"+position,""+pname_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("postedby"+position,""+ppostedby_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("mobile"+position,""+pmobile_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("type"+position,""+ptotalprice_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("bedroom"+position,""+pbedroom_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("area"+position,""+ptotalarea_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("price"+position,""+ptotalprice_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("unit"+position,""+ppriceperunit_list.get(position));
                SharedPrefManager.getInstance(context).savefav_property("image"+position,""+pimage_list.get(position));
                SharedPrefManager.getInstance(context).savefav_position("position"+position,""+position);


            }
        });*/

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

        TextView name, state, city, adapter_price, locality, id_textview;
        ImageView imageView, callbtn, delete;
        LinearLayout mainlayout;
        TextView statusimage;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.id_textview = (TextView) itemView.findViewById(R.id.c_Propertid);
            this.name = (TextView) itemView.findViewById(R.id.c_Propertname);
            this.state = (TextView) itemView.findViewById(R.id.c_Propertstate);
            this.city = (TextView) itemView.findViewById(R.id.c_Propertcity);
            this.adapter_price = (TextView) itemView.findViewById(R.id.adapter_price);
            this.imageView = (ImageView) itemView.findViewById(R.id.search_propert_image);
            this.callbtn = (ImageView) itemView.findViewById(R.id.plist_callbtn);
            this.delete = (ImageView) itemView.findViewById(R.id.delete_fav_item);
            this.cardView = (CardView) itemView.findViewById(R.id.filtered_propert_card);
            this.locality = (TextView) itemView.findViewById(R.id.locality);
            this.statusimage = (TextView) itemView.findViewById(R.id.statusimage);
            //this.cardView=(CardView)itemView.findViewById(R.id.article_cardview);
        }
    }

    public void remove_item_server(final int position) {
        final AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(activity);
        alertbBuilder.setMessage("Are you sure you want to delete property!");

        alertbBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertbBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Pid = pid_list.get(position);

                pid_list.remove(position);
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
                localitylist.remove(position);
                landmarklist.remove(position);
                status.remove(position);
                pdescription_list.remove(position);
                depositlist.remove(position);
                flooringlist.remove(position);
                floorlist.remove(position);
                bathlist.remove(position);
                p_floorlist.remove(position);
                notifyItemRemoved(position);


                Serverapi serverapi;
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(context.getResources().getString(R.string.myurl)).build();
                serverapi = restAdapter.create(Serverapi.class);
                serverapi.delete_property(Pid, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        try {
                            String s = new String(((TypedByteArray) response.getBody()).getBytes());


                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");


                            if (success == 1) {
                                Toast.makeText(context, "Property Has Been Deleted Successfully", Toast.LENGTH_SHORT).show();


                            } else {

                                Toast.makeText(context, "Property Not Deleted", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {

                            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(context, "Something went wrong on server", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        alertbBuilder.show();


    }
}
