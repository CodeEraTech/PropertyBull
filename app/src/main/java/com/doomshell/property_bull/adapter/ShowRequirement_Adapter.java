package com.doomshell.property_bull.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.doomshell.property_bull.Filtered_property;
import com.doomshell.property_bull.R;
import com.doomshell.property_bull.helper.Serverapi;

import org.json.JSONArray;
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

public class ShowRequirement_Adapter extends RecyclerView.Adapter<ShowRequirement_Adapter.MyViewHolder> {

    ArrayList<String> idlist=new ArrayList<String>();
    ArrayList<String> Propertytype=new ArrayList<String>();
    ArrayList<String> type=new ArrayList<String>();
    ArrayList<String> area=new ArrayList<String>();
    ArrayList<String> rooms=new ArrayList<String>();
    ArrayList<String> location=new ArrayList<String>();

    ArrayList<String> budget=new ArrayList<String>();
    ArrayList<String> createddate=new ArrayList<>();
    ArrayList<String> locationid;
    ArrayList<String> propertytypeid;
    ArrayList<String> max_budgetsearch;
    ArrayList<String> min_budgetsearch;
    Context context;
    String option_name;
    AppCompatActivity activity;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    int lhi;
    int lwi;
    String Pid;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
   public ShowRequirement_Adapter(Context context, ArrayList<String> idlist, ArrayList<String> Propertytype, ArrayList<String> area, ArrayList<String> rooms, ArrayList<String> location, ArrayList<String> budget, ArrayList<String> createddate, String option_name, ArrayList<String> locationid, ArrayList<String> propertytypeid, ArrayList<String> max_budgetsearch, ArrayList<String> min_budgetsearch, AppCompatActivity activity)
    {
        this.context=context;
        this.Propertytype=Propertytype;

        this.area=area;
        this.rooms=rooms;
        this.location=location;
        this.budget=budget;
        this.activity=activity;
        this.idlist=idlist;
        this.createddate=createddate;
        this.option_name=option_name;
        this.propertytypeid=propertytypeid;
        this.max_budgetsearch=max_budgetsearch;
        this.min_budgetsearch=min_budgetsearch;
        this.locationid=locationid;
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
        View view= LayoutInflater.from(context).inflate(R.layout.showrequirement_provider_layout,parent,false);


        MyViewHolder myViewHolder=new MyViewHolder(view);

        if (context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {

            displayMetrics=context.getResources().getDisplayMetrics();
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;

            lhi= (int) (screenHeight*0.20);
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


        holder.type.setText(Propertytype.get(position).toString());
        if(!rooms.get(position).toString().equalsIgnoreCase("")) {
            if (!rooms.get(position).toString().equalsIgnoreCase("null")){
                holder.aprtmtlyt.setVisibility(View.VISIBLE);
            holder.rooms.setText(rooms.get(position).toString());
        }
        else
            {
                holder.aprtmtlyt.setVisibility(View.GONE);
            }
        }
        else {
            holder.aprtmtlyt.setVisibility(View.GONE);
        }
        holder.date.setText(createddate.get(position));

        holder.budget.setText(budget.get(position).toString());
        holder.area.setText(area.get(position).toString());
       // holder.category.setText(type.get(position).toString());
        if(!location.get(position).toString().equalsIgnoreCase("")) {
            if (!location.get(position).toString().equalsIgnoreCase("null")) {
                holder.address.setText(location.get(position).toString());
            }
            else
            {
                holder.addresslyt.setVisibility(View.GONE);
            }
        }
        else
        {
            holder.addresslyt.setVisibility(View.GONE);
        }
        holder.search_fav_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!rooms.get(position).equalsIgnoreCase(""))
                {
                load_search(propertytypeid.get(position),locationid.get(position),max_budgetsearch.get(position),min_budgetsearch.get(position),rooms.get(position).substring(0,1));
            }
            else
                {
                    load_search(propertytypeid.get(position),locationid.get(position),max_budgetsearch.get(position),min_budgetsearch.get(position),"");
                }
            }
        });

        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                remove_item_server(position);
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
        LinearLayout linearLayout;
        CardView cardView;*/
      TextView type,rooms,budget,address,area,date;
        LinearLayout aprtmtlyt,addresslyt;
        ImageView delete_item,search_fav_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.delete_item = (ImageView) itemView.findViewById(R.id.delete_fav_item);
            this.search_fav_item = (ImageView) itemView.findViewById(R.id.search_fav_item);
            type=(TextView)itemView.findViewById(R.id.type);
            rooms=(TextView)itemView.findViewById(R.id.rooms);
           // category=(TextView)itemView.findViewById(R.id.category);
            budget=(TextView)itemView.findViewById(R.id.budget);
            address=(TextView)itemView.findViewById(R.id.address);
            area=(TextView)itemView.findViewById(R.id.area);
            aprtmtlyt=(LinearLayout) itemView.findViewById(R.id.aprtmtlyt);
            addresslyt=(LinearLayout) itemView.findViewById(R.id.addresslyt);
            date=(TextView) itemView.findViewById(R.id.date);
        }
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
    public void remove_item_server(final int position)
    {
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

                Pid=idlist.get(position);

                Propertytype.remove(position);
                rooms.remove(position);
                budget.remove(position);
                area.remove(position);
                createddate.remove(position);
                location.remove(position);
                idlist.remove(position);
                notifyItemRemoved(position);

                Serverapi serverapi;
                RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(context.getResources().getString(R.string.myurl)).build();
                serverapi=restAdapter.create(Serverapi.class);
                serverapi.delete_requirement(Pid,new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                       try{
                        String s=new String(((TypedByteArray) response.getBody()).getBytes());


                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");


                            if (success==1) {
                                Toast.makeText(context,"Requirement Has Been Deleted",Toast.LENGTH_SHORT).show();

                            }else {

                                Toast.makeText(context,"Requirement Not Deleted",Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e)
                        {

                            Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(context,"Something went wrong on server",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        alertbBuilder.show();



    }
    void load_search(String s, String s1, String s2, String s3,String s4)
    {
        Serverapi serverapi;
        RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(context.getResources().getString(R.string.myurl)).build();
        serverapi=restAdapter.create(Serverapi.class);
      //  show_dialogue();
        //  Toast.makeText(context, "ok "+option_name, Toast.LENGTH_SHORT).show();
        serverapi.search(
                option_name,
                "27",
                s1,
                "",
                s,
                s3,
                s2,
                s4,
                "",
                "",
                "",
                "",
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        String s=new String(((TypedByteArray) response.getBody()).getBytes());

                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            int success=jsonObject.getInt("success");
                            if (success==1)
                            {
                                JSONArray jsonArray=jsonObject.getJSONArray("output");
                                Bundle bundle=new Bundle();
                                bundle.putString("jarray",jsonArray.toString());
                                bundle.putString("isrequirement","1");

                             //   dismiss_dialogue();

                                Filtered_property filtered_property=new Filtered_property();
                                filtered_property.setArguments(bundle);
                                FragmentTransaction devicetrans=activity.getSupportFragmentManager().beginTransaction();
                                devicetrans.replace(R.id.frame_container,filtered_property);
                                devicetrans.addToBackStack(filtered_property.getClass().toString());
                                devicetrans.commit();

                            }else if(success==0){
                                Toast.makeText(context,"No Record Found",Toast.LENGTH_SHORT).show();
                            }else  {
                                Toast.makeText(context,"Something went wrong on server",Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                          //  dismiss_dialogue();
                            e.printStackTrace();
                        }

                       // dismiss_dialogue();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    //    dismiss_dialogue();
                        Log.d("resl",""+error);
                        Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }

}
