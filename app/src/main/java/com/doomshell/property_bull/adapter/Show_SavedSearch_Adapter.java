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

import com.doomshell.property_bull.Filtered_property;
import com.doomshell.property_bull.R;
import com.doomshell.property_bull.helper.CustomProgressDialog;
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

public class Show_SavedSearch_Adapter extends RecyclerView.Adapter<Show_SavedSearch_Adapter.MyViewHolder> {

    ArrayList<String> idlist=new ArrayList<String>();
    ArrayList<String> Propertytype=new ArrayList<String>();
    ArrayList<String> category=new ArrayList<String>();
    ArrayList<String> location=new ArrayList<String>();
    ArrayList<String> name=new ArrayList<String>();
    ArrayList<String> ptypeid,location_id,owner,age,max_price,min_price,min_area,max_area,min_room,max_room,city_id;;

    AppCompatActivity activity;
    Context context;
    DisplayMetrics displayMetrics;
    double screenHeight;
    double screenWidth;
    int lhi;
    int lwi;



    public Show_SavedSearch_Adapter(Context context, ArrayList<String> idlist, ArrayList<String> name, ArrayList<String> category, ArrayList<String> location, ArrayList<String> propertytype, ArrayList<String> ptypeid, ArrayList<String> location_id, ArrayList<String> max_area, ArrayList<String> min_area, ArrayList<String> max_price, ArrayList<String> min_price, ArrayList<String> max_room, ArrayList<String> min_room, ArrayList<String> age, ArrayList<String> owner, AppCompatActivity activity) {
        this.context=context;
        this.idlist=idlist;
        this.name=name;
        this.category=category;
        this.location=location;
        this.Propertytype=propertytype;
        this.ptypeid=ptypeid;
        this.age=age;
        this.owner=owner;
        this.max_area=max_area;
        this.min_area=min_area;
        this.max_price=max_price;
        this.min_price=min_price;
        this.min_room=min_room;
        this.max_room=max_room;
        this.location_id=location_id;
        this.activity=activity;



    }

 @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.showsavedsearch_layout,parent,false);


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

        if(Propertytype.get(position).equalsIgnoreCase("") || Propertytype.get(position).equalsIgnoreCase("null"))
        {
            holder.type.setText("All Residential");
        }
        else
        {
            holder.type.setText(Propertytype.get(position));
        }


        if(category.get(position).equalsIgnoreCase("Rent"))
       holder.title.setText(name.get(position)+" (Rent)");
        else if (category.get(position).equalsIgnoreCase("Sell"))
        {
            holder.title.setText(name.get(position)+" (Sell)");
        }


        holder.budget.setText("> 4 Lacs");

       // holder.category.setText(type.get(position).toString());
        if(!location.get(position).toString().equalsIgnoreCase("")) {
            if (!location.get(position).toString().equalsIgnoreCase("null")) {
                holder.address.setText(location.get(position).toString() +", Jaipur");
            }
            else
            {
                holder.address.setText("Jaipur");

            }
        }
        else
        {
            holder.address.setText("Jaipur");
        }
        holder.search_fav_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                load_search(category.get(position),ptypeid.get(position),location_id.get(position),max_price.get(position),min_price.get(position),max_room.get(position),min_room.get(position),owner.get(position),age.get(position),min_area.get(position),max_area.get(position),owner.get(position));

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
      TextView type,budget,address,date,title;
        LinearLayout aprtmtlyt,addresslyt,intent_datatypelyt;
        ImageView delete_item,search_fav_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.delete_item = (ImageView) itemView.findViewById(R.id.delete_fav_item);
            this.search_fav_item = (ImageView) itemView.findViewById(R.id.search_fav_item);
            type=(TextView)itemView.findViewById(R.id.type);

           // category=(TextView)itemView.findViewById(R.id.category);
            budget=(TextView)itemView.findViewById(R.id.budget);
            title=(TextView)itemView.findViewById(R.id.title);
            address=(TextView)itemView.findViewById(R.id.address);

            addresslyt=(LinearLayout) itemView.findViewById(R.id.addresslyt);
            this.intent_datatypelyt=(LinearLayout) itemView.findViewById(R.id.intent_datatypelyt);

        }
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

   public void remove_item_server(final int position)
    {
        final AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(activity);
        alertbBuilder.setMessage("Are you sure you want to delete search!");

        alertbBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertbBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Propertytype.remove(position);
                idlist.remove(position);
                location.remove(position);
                category.remove(position);
                name.get(position);
                notifyItemRemoved(position);

                Serverapi serverapi;
                RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(context.getResources().getString(R.string.myurl)).build();
                serverapi=restAdapter.create(Serverapi.class);
                serverapi.delete_search(idlist.get(position),new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        String s=new String(((TypedByteArray) response.getBody()).getBytes());

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");


                            if (success==1) {
                                Toast.makeText(context,"Saved search has been deleted successfully",Toast.LENGTH_SHORT).show();
                            }else {

                                Toast.makeText(context,"Saved search not deleted",Toast.LENGTH_SHORT).show();
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
    void load_search(String option_name, String spid, String loc_id, String maxprice, String minprice, String maxroom, String minroom, String owner, String age,String minarea,String maxarea,String role)
    {
        final CustomProgressDialog dialog=new CustomProgressDialog(activity);
        Serverapi serverapi;
        RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(context.getResources().getString(R.string.myurl)).build();
        serverapi=restAdapter.create(Serverapi.class);
      //  show_dialogue();
         // Toast.makeText(context, "option_name "+option_name, Toast.LENGTH_SHORT).show();

        serverapi.search(
                option_name,
                "27",
                loc_id,
                "",
                spid,
                minprice,
                maxprice,
                maxroom,
                age,
                minarea,
                maxarea,
                role,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        dialog.hide();
                        String s=new String(((TypedByteArray) response.getBody()).getBytes());

                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            int success=jsonObject.getInt("success");
                            if (success==1)
                            {
                                JSONArray jsonArray=jsonObject.getJSONArray("output");
                                Bundle bundle=new Bundle();
                                bundle.putString("jarray",jsonArray.toString());

                             //   dismiss_dialogue();

                                Filtered_property filtered_property=new Filtered_property();
                                bundle.putString("isrequirement","1");
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
                            dialog.hide();
                        }

                       // dismiss_dialogue();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    //    dismiss_dialogue();
                        Log.d("resl",""+error);
                        dialog.hide();
                        Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }

}
